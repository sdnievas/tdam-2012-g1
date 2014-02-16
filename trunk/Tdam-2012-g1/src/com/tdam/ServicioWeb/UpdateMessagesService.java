package com.tdam.ServicioWeb;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.tdam.Class.MensajeWeb;
import com.tdam.Class.Type;
import com.tdam.Class.Usuario;
import com.tdam.Database.SingletonDB;
import com.tdam.Suport.Notificacion;
import com.tdam.ui.Servicio_Web;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class UpdateMessagesService extends Service {


	private static final String LOGIN_SETTINGS = "LoginPreferences";
	private static final String USER = "User";
	private static final String PASSWORD = "Password";
	
	Usuario usr;
	
	private NotificationManager notificationManager;
	//private int NOTIFICATION = R.string.serviceNotificationTitle;
	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	private final IBinder binder = new LocalBinder();

	private static final String TAG = "UpdateMessagesService";

	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		SharedPreferences preferences = getSharedPreferences(LOGIN_SETTINGS,MODE_PRIVATE);
		usr = new Usuario();
		usr.set_nombre(preferences.getString(USER, ""));
		usr.set_contraseña(preferences.getString(PASSWORD, ""));
		
		final Handler handler = new Handler();
	    Timer timer = new Timer();
	    TimerTask doAsynchronousTask = new TimerTask() {       
	        @Override
	        public void run() {
	            handler.post(new Runnable() {
	                public void run() {       
	                    try {
	                    	GetReceivedMessagesTask receivedMessagesTask = new GetReceivedMessagesTask(getApplicationContext(),usr);
	                		receivedMessagesTask.execute();
	                    } catch (Exception e) {
	                        // TODO Auto-generated catch block
	                    }
	                }
	            });
	        }
	    };
	    timer.schedule(doAsynchronousTask, 0, 30000); //execute in every 50000 ms
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "intent = " + intent.getAction());

		SharedPreferences preferences = getSharedPreferences(LOGIN_SETTINGS,MODE_PRIVATE);
		usr = new Usuario();
		usr.set_nombre(preferences.getString(USER, ""));
		usr.set_contraseña(preferences.getString(PASSWORD, ""));

//		GetReceivedMessagesTask receivedMessagesTask = new GetReceivedMessagesTask(this,usr);
//		receivedMessagesTask.execute();
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO stop to any network traansaction, don't save de data (or do it)
		super.onDestroy();
		
	}

	private void showNotification(WebServiceInfo info, String usuario) {
		Notificacion noti = new Notificacion(this,info,3,usuario);
		noti.notificionMensajes();
		
		/*Notification notification = new Notification(R.drawable.icon_app,
				getString(R.string.serviceNotificationTitle),
				System.currentTimeMillis());*/

		/*PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MensajeriaWebFragmentActivity.class), 0);

		notification.setLatestEventInfo(this,
				getString(R.string.serviceNotificationTitle),
				getString(R.string.serviceNotificationMessage), contentIntent);
		
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;

		notificationManager.notify(NOTIFICATION, notification);*/
	}

	public class LocalBinder extends Binder {
		UpdateMessagesService getService() {
			return UpdateMessagesService.this;
		}
	}

	protected class GetReceivedMessagesTask extends
			AsyncTask<String, Integer, ArrayList<ReceivedMessageInfo>> {

		Context context;
		Usuario usr;

		public GetReceivedMessagesTask(Context context, Usuario usr) {
			this.context = context;
			this.usr = usr;
		}
		
		
		@Override
		protected ArrayList<ReceivedMessageInfo> doInBackground(
				String... params) { 
			
			String timestamp = SingletonDB
						.getInstance(UpdateMessagesService.this)
						.getDatabaseHelper().getLastMsgWeb(usr).get_fechaEnvio();
			
			if(timestamp == null)
			{
				timestamp= "01/01/1970 00:00:00";		
			}

			WebService webservi = new WebService(usr.get_nombre(), usr.get_contraseña(),this.context);
			
			ArrayList<ReceivedMessageInfo> receivedMessages = new ArrayList<ReceivedMessageInfo>();
			receivedMessages = webservi.getMessages(timestamp);
			return receivedMessages;
		}

		@Override
		protected void onPostExecute(
				ArrayList<ReceivedMessageInfo> receivedMessages) {
			super.onPostExecute(receivedMessages);

			for (ReceivedMessageInfo receivedMessage : receivedMessages)
			{
				if (receivedMessage.getCode() == WebServiceInfo.SUCCESS
						&& receivedMessage.getTimestamp() != null) {
					
					MensajeWeb webMessage = new MensajeWeb();
					webMessage.set_contacto(receivedMessage.getFrom());
					webMessage.set_usuario(usr.get_nombre());

					webMessage.set_fechaEnvio(receivedMessage.getTimestamp());
															
					webMessage.set_detalle(receivedMessage.getMessage());
					
					webMessage.setType(Type.entrada.ordinal());					
					
					SingletonDB.getInstance(context).getDatabaseHelper().addMensaje(webMessage);

					showNotification(receivedMessage, webMessage.get_contacto());
				}
			}
		}
	}
}
