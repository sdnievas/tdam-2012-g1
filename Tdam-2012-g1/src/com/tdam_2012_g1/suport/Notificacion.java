package com.tdam_2012_g1.suport;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.tdam_2012_g1.MainActivity;
import com.tdam_2012_g1.R;



public class Notificacion {

	String mensaje;
	Context context;
	
	public Notificacion(Context context, String Mensaje) {
		this.mensaje = Mensaje;
		this.context = context;
	}
	
	public Notificacion(Context context) {
		this.context = context;
	}
	
	public void notificar(String notificacion){
		String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager =(NotificationManager) context.getSystemService(ns);
        
        
        int icon = R.drawable.ic_contacts;
        CharSequence ticketText = "Notificacion";
        long when = System.currentTimeMillis();
        
        Notification notification = new Notification(R.drawable.ic_contacts,"tiene un nuevo Mensaje",1);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
       
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
        contentView.setImageViewResource(R.id.imagenNotification, R.drawable.image_notification);
        contentView.setTextViewText(R.id.textoNotification, notificacion  );
        notification.contentView = contentView;
        
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0, notificationIntent, 0);
        
        notification.contentIntent = contentIntent;
        mNotificationManager.notify(1, notification);
	}
	
	
}
