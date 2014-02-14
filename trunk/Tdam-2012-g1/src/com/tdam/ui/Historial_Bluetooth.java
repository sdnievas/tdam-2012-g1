package com.tdam.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.tdam.Class.Contacto;
import com.tdam.Class.ContactoWeb;
import com.tdam.Class.MensajeWeb;
import com.tdam.Database.DatabaseHelper;
import com.tdam.ui.Historial_WebMsg.HistoryWebSmsAdapter;
import com.tdam.ui.Historial_WebMsg.Holder;
import com.tdam_2012_g1.R;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Historial_Bluetooth extends ListActivity implements
OnItemClickListener {

private HistoryBluetoothAdapter adapterBluetooth;
private Contacto contact;
private String ordenarForma;
private String FiltroContactos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial__bluetooth);
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			contact = (Contacto) extras.getSerializable("contacto"); // Cargamos
																		// el
																		// contacto
																		// que
																		// recibimos
																		// del
																		// intent
																		// de la
																		// activity
																		// contactos

		adapterBluetooth = new HistoryBluetoothAdapter();

		getListView().setAdapter(adapterBluetooth);

		getListView().setOnItemClickListener(this);

		this.loadBlutoothData();

	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferences();
		this.loadBlutoothData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.historial__bluetooth, menu);
		return true;
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		MensajeWeb WebSms = (MensajeWeb) adapterBluetooth.getItem(position);
		Contacto contacto = new Contacto();
		contacto.setName(WebSms.get_contacto());
		Intent intent = new Intent(this, Servicio_Web.class);
		intent.putExtra("contacto", contacto);
		startActivity(intent);

	}

	private void getPreferences() {

		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		ordenarForma = myPreference.getString(
				getString(R.string.preference_Historial_Ordenarkey), "0");

		FiltroContactos = myPreference.getString(
				getString(R.string.preference_Historial_Filtrarkey), "0");
	}

	public void loadBlutoothData() {
//		adapterBluetooth.limpiar();
//		getPreferences();
//		String forma = "desc";
//
//		if (!ordenarForma.equals("0")) {
//			forma = "asc";
//		}
//		DatabaseHelper dbhelper = getDatabaseHelper();
//		ArrayList<MensajeWeb> mails = null;
//		if (contact != null) {
//			mails = dbhelper.getContactMensajeWeb(contact,null);
//		} else {
//			mails = dbhelper.getAllLastMensajeWeb(forma,null);
//		}
//
//		adapterBluetooth.addListHistorial(mails);
//		dbhelper.close();
//		adapterBluetooth.notifyDataSetChanged();
	}

	protected DatabaseHelper getDatabaseHelper() {
		return new DatabaseHelper(this);
	}

	class HistoryBluetoothAdapter extends BaseAdapter {

		private ArrayList<MensajeWeb> historial;
		private LayoutInflater inflater;
		private SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
		private SimpleDateFormat formatoFecha = new SimpleDateFormat(
				"dd/MM/yyyy");
		private SimpleDateFormat formateFechaString = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss zzz yyyy");

		public HistoryBluetoothAdapter() {
			historial = new ArrayList<MensajeWeb>();
			inflater = LayoutInflater.from(Historial_Bluetooth.this);
		}

		public void addHistorial(MensajeWeb historials) {
			if (historial != null) {
				historial.add(historials);
			}
		}

		public void addListHistorial(ArrayList<MensajeWeb> historials) {
			historial = historials;
		}

		public void limpiar() {
			if (historial != null) {
				historial.clear();
			}
		}

		@Override
		public int getCount() {
			return historial.size();
		}

		@Override
		public Object getItem(int position) {
			return historial.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
//			Holder holder;
//			if (convertView == null) {
//				convertView = inflater.inflate(R.layout.historial_item, null);
//				holder = new Holder();
//				holder.txtNombre = (TextView) convertView
//						.findViewById(R.id.txtArribaIzquierda);
//				holder.txtFecha = (TextView) convertView
//						.findViewById(R.id.txtArribaDerecha);
//				holder.txtHora = (TextView) convertView
//						.findViewById(R.id.txtAbajoDerecha);
//				holder.ImagenType = (ImageView) convertView
//						.findViewById(R.id.imagehistorialItem);
//				holder.txtMensaje = (TextView) convertView
//						.findViewById(R.id.txtAbajoIzquierda);
//				convertView.setTag(holder);
//			} else {
//				holder = (Holder) convertView.getTag();
//			}
//
//			MensajeWeb history = (MensajeWeb) getItem(position);
//			Date fecha = new Date();
//			try {
//				fecha = formateFechaString.parse(history.get_fechaEnvio());
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			holder.txtNombre.setText(history.get_destinatario());
//			holder.txtFecha.setText(formatoFecha.format(fecha));
//			holder.txtHora.setText(formatoHora.format(fecha));
//			holder.txtMensaje.setText(history.get_detalle());
//			holder.ImagenType
//					.setImageResource(android.R.drawable.stat_notify_chat);

			return convertView;
		}

	}

	class Holder {
		private TextView txtNombre;
		private TextView txtHora;
		private TextView txtFecha;
		private TextView txtMensaje;
		private ImageView ImagenType;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {

		case R.id.historial_settings:
			intent = new Intent(this, Preference_historial.class);
			break;
		}
		startActivity(intent);
		return true;
	}
	


}
