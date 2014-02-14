package com.tdam.Broadcast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.tdam.Class.Conectividad;
import com.tdam.Database.DatabaseHelper;
import com.tdam.Database.SingletonDB;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConectivityBroadcastReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Conectividad conn = new Conectividad();
		String action = intent.getAction();
		
		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			NetworkInfo network = intent
					.getParcelableExtra((ConnectivityManager.EXTRA_NETWORK_INFO));
			
			
			conn.set_conexion(network.getTypeName() + " " + network.getSubtypeName());
			conn.set_estado(network.getState().name());

			Calendar date = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");	
			String fecha = dateFormat.format(date.getTimeInMillis());

			conn.set_fecha(fecha);

			SingletonDB.getInstance(context).getDatabaseHelper().addConectividad(conn);

		}
	}

}
