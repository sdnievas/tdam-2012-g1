package com.tdam_2012_g1.suport;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.tdam_2012_g1.MainActivity;
import com.tdam_2012_g1.R;
import com.tdam_2012_g1.mensajesWeb.WebServiceInfo;



public class Notificacion {

	private Context context;
	private static final String NOTIFICATION_MSJ = "Tiene un Nuevo Mensaje";
	private WebServiceInfo info;
	private String notificacion;
	private int Type;
	
	
	
	
	public Notificacion(Context context, WebServiceInfo info , int Type) {
		this.context = context;
		this.info = info;
		this.Type = Type;
	}
	
	public void notificionMensajes(){
		
		switch (info.getCode())
		{
		
		case -1:
				notificacion = context.getString(R.string.notificacion_ServerError);
			break;
		
		case 0:
				notificacion = context.getString(R.string.notificacion_FatalError);
			break;
		
		case 1:
			if(Type == 1)
				notificacion = context.getString(R.string.notificacion_SuccesUser);
			else
				notificacion = context.getString(R.string.notificacion_SuccesMessage);
			break;
			
		case 2:
				notificacion = context.getString(R.string.notificacion_ErrorMessage);
			break;
			
		case 5:
				notificacion = context.getString(R.string.notificacion_DuplicateUser);
			break;
		
		}
							
		nuevaNotificacion();
	}
	
	private void nuevaNotificacion(){
		
		String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager =(NotificationManager) context.getSystemService(ns);
        
        
        int icon = R.drawable.ic_contacts;
        CharSequence ticketText = NOTIFICATION_MSJ;
        long when = System.currentTimeMillis();
        
        Notification notification = new Notification(R.drawable.image_notification,NOTIFICATION_MSJ,1);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
       
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
        contentView.setImageViewResource(R.id.imagenNotification, R.drawable.image_notification);
        contentView.setTextViewText(R.id.textoNotification, notificacion );
        notification.contentView = contentView;
        
        Intent notificationIntent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(context,0, notificationIntent, 0);
        
        notification.contentIntent = contentIntent;
        mNotificationManager.notify(1, notification);
		
	}
	
	
	
	

	
	
}
