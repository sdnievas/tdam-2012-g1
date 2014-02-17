package com.tdam.Suport;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.tdam.Class.Contacto;
import com.tdam.ServicioWeb.WebServiceInfo;
import com.tdam.ui.Servicio_Web;
import com.tdam_2012_g1.R;

public class Notificacion {

	private Context context;
	private static final String NOTIFICATION_MSJ = "Nueva Notificacion";
	private WebServiceInfo info;
	private String notificacion;
	private int Type;
	private String userWeb;

	public Notificacion(Context context, WebServiceInfo info, int Type) {
		this.context = context;
		this.info = info;
		this.Type = Type;
	}

	public Notificacion(Context context, WebServiceInfo info, int Type,
			String usuarioWeb) {
		this.context = context;
		this.info = info;
		this.Type = Type;
		this.userWeb = usuarioWeb;
	}

	public void notificionMensajes() {

		switch (info.getCode()) {

		case -1:
			notificacion = context.getString(R.string.notificacion_ServerError);
			break;

		case 0:
			notificacion = context.getString(R.string.notificacion_FatalError);
			break;

		case 1:
			if (Type == 1)
				notificacion = context
						.getString(R.string.notificacion_SuccesUser);
			else if (Type == 2)
				notificacion = context
						.getString(R.string.notificacion_SuccesMessage);
			else if (Type == 3)
				notificacion = context
						.getString(R.string.notificacion_MensajeRecibido)
						+ " de " + userWeb;
			break;

		case 2:
			notificacion = context
					.getString(R.string.notificacion_ErrorMessage);
			break;
		case 5:
			notificacion = context
					.getString(R.string.notificacion_DuplicateUser);
			break;

		}

		nuevaNotificacion();
	}

	private void nuevaNotificacion() {

		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);

		int icon = R.drawable.ic_contacts;
		CharSequence ticketText = NOTIFICATION_MSJ;
		long when = System.currentTimeMillis();
		Notification notification;
		if (userWeb != null) {
			notification = new Notification(R.drawable.image_notification,
					"Mensaje web recibido", 1);
		} else {
			notification = new Notification(R.drawable.image_notification,
					NOTIFICATION_MSJ, 1);
		}
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		RemoteViews contentView = new RemoteViews(context.getPackageName(),
				R.layout.notification);
		contentView.setImageViewResource(R.id.imagenNotification,
				R.drawable.image_notification);
		contentView.setTextViewText(R.id.textoNotification, notificacion);
		notification.contentView = contentView;

		Intent notificationIntent = new Intent();
		if (info.getCode() == 1 && Type == 3) {
			notificationIntent.setClass(context, Servicio_Web.class);
			Contacto contacto = new Contacto();
			contacto.setUserWeb(userWeb);
			notificationIntent.putExtra("contacto", contacto);
		}
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.contentIntent = contentIntent;
		mNotificationManager.notify(1, notification);

	}

}
