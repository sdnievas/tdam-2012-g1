package com.tdam.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import com.tdam.Class.Contacto;
import com.tdam.Database.DatabaseHelper;
import com.tdam_2012_g1.R;

public class Historial extends TabActivity {

	private Intent intent;
	public Contacto contact;
	private static final String sCALL = "Llamadas";
	private static final String sSMS = "Sms";
	private static final String sWEBMSG = "WebMessage";
	private static final String sMAIL = "Mail";
	private static final String sCONTACT = "contacto";
	private final int DIALGO_ELIMINACION = -2;
	private static final String LOGIN_SETTINGS = "LoginPreferences";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historial);

		Bundle extras = getIntent().getExtras();
		if (extras != null)
			// Cargamos el contacto que recibimos del intent de la activity
			// contactos
			contact = (Contacto) extras.getSerializable(sCONTACT);

		CargarTabs();

	}

	private void CargarTabs() {

		Resources res = getResources();

		TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabs.setup();

		TabHost.TabSpec spec = tabs.newTabSpec(sCALL);
		spec.setIndicator(sCALL,
				res.getDrawable(android.R.drawable.ic_menu_call));
		Intent historialLlamadas = new Intent(this, Historial_Llamadas.class);
		historialLlamadas.putExtra(sCONTACT, contact);
		spec.setContent(historialLlamadas);
		tabs.addTab(spec);

		spec = tabs.newTabSpec(sSMS);
		spec.setIndicator(sSMS,
				res.getDrawable(android.R.drawable.ic_dialog_email));
		Intent historialSms = new Intent(this, Historial_Sms.class);
		historialSms.putExtra(sCONTACT, contact);
		spec.setContent(historialSms);
		tabs.addTab(spec);

		spec = tabs.newTabSpec(sWEBMSG);
		spec.setIndicator(sWEBMSG,
				res.getDrawable(android.R.drawable.stat_notify_chat));
		Intent historialWebMsg = new Intent(this, Historial_WebMsg.class);
		historialWebMsg.putExtra(sCONTACT, contact);
		spec.setContent(historialWebMsg);
		tabs.addTab(spec);

		spec = tabs.newTabSpec(sMAIL);
		spec.setIndicator(sMAIL,
				res.getDrawable(android.R.drawable.ic_dialog_email));
		Intent historialMail = new Intent(this, Historial_Mail.class);
		historialMail.putExtra(sCONTACT, contact);
		spec.setContent(historialMail);
		tabs.addTab(spec);

		// spec=tabs.newTabSpec(sBLUETOOTH);
		// spec.setIndicator(sBLUETOOTH,
		// res.getDrawable(android.R.drawable.stat_sys_data_bluetooth));
		// Intent historialBluetooth = new Intent(this,
		// Historial_Bluetooth.class);
		// historialBluetooth.putExtra(sCONTACT,contact);
		// spec.setContent(historialBluetooth);
		// tabs.addTab(spec);

		tabs.setCurrentTab(0);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_historial, menu);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {

		case R.id.historial_settings:
			intent = new Intent(this, Preference_historial.class);
			break;

		case R.id.historial_delete:
			showDialog(DIALGO_ELIMINACION);
			intent = null;
			break;
		}
		if (intent != null) {
			startActivity(intent);
		}
		return true;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		// TODO Auto-generated method stub
		super.onPrepareDialog(id, dialog, args);
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog = null;
		OnClickListener clickOk = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				eliminarHistorial();
				dismissDialog(DIALGO_ELIMINACION);

			}
		};

		OnClickListener clickCancel = new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismissDialog(DIALGO_ELIMINACION);
			}
		};

		dialog = new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Advertencia")
				.setPositiveButton("OK. Borrar registros", clickOk)
				.setNegativeButton("NO. Cancelar borrado", clickCancel)
				.setMessage(
						"Se eliminarán los registros de emails y mensajes web. Esta acción no se puede deshacer. ¿Está seguro que desea continuar?")
				.create();

		return dialog;

	}

	private void eliminarHistorial() {
		DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
		SharedPreferences preferencias = getSharedPreferences(LOGIN_SETTINGS,
				MODE_PRIVATE);
		String user = preferencias.getString("User", "");
		dbHelper.eliminarRegistros(user);
		Toast.makeText(getApplicationContext(), "Se eliminaron registros",
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}
