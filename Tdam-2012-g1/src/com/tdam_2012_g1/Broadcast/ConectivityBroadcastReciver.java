package com.tdam_2012_g1.Broadcast;

import java.util.Calendar;
import java.util.Date;

import com.tdam_2012_g1.database.DatabaseHelper;
import com.tdam_2012_g1.entidades.Conectividad;



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

			Calendar c = Calendar.getInstance();
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH);
			int mDay = c.get(Calendar.DAY_OF_MONTH);

			conn.set_fecha(mDay + "/" + (mMonth + 1) + "/" + mYear);

			Date horaSistema = new Date();
			horaSistema.getTime();
			java.sql.Time sqlTime = new java.sql.Time(horaSistema.getTime());

			DatabaseHelper dbhelper = new DatabaseHelper(context);

			dbhelper.addConectividad(conn);
		}
	}

}
