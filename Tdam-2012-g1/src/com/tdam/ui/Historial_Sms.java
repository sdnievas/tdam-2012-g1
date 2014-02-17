package com.tdam.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.tdam.Class.Contacto;
import com.tdam.Class.HistorialSms;
import com.tdam.Class.MensajeWeb;
import com.tdam.Database.SingletonDB;
import com.tdam_2012_g1.R;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Historial_Sms extends ListActivity implements OnItemClickListener,
		OnItemLongClickListener {

	private HistorySmsAdapter adapertSms;
	private Contacto contact;
	private String ordenarForma;
	private String FiltroContactos;
	private int filtro;
	private Cursor cur;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial__sms);

		Bundle extras = getIntent().getExtras();

		// Cargamos el contacto que recibimos del intent de la activity
		// contactos
		if (extras != null)
			contact = (Contacto) extras.getSerializable("contacto");

		adapertSms = new HistorySmsAdapter();

		getListView().setAdapter(adapertSms);

		getListView().setOnItemClickListener(this);

		getListView().setOnItemLongClickListener(this);

		getPreferences();

		if (contact != null) {
			loadConversationSms(contact);
		} else {
			loadConversationsSms();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferences();
		if (contact != null) {
			loadConversationSms(contact);
		} else {
			loadConversationsSms();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (cur != null) {
			cur.close();
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (cur != null) {
			cur.close();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_historial, menu);
		return true;
	}

	private void getPreferences() {

		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		ordenarForma = myPreference.getString(
				getString(R.string.preference_Historial_Ordenarkey), "0");

		FiltroContactos = myPreference.getString(
				getString(R.string.preference_Historial_Filtrarkey), "0");

		filtro = Integer.parseInt(FiltroContactos);
	}

	private void loadConversationsSms() {

		adapertSms.limpiar();

		String forma = "date desc";

		if (!ordenarForma.equals("0")) {
			forma = "date asc";
		}

		Uri smsURI = Uri.parse("content://sms/");
		String columns[] = new String[] { "address", "person", "date", "body",
				"type", "thread_id", "MAX(date)" }; // type 1 = otro contacto,
													// type 2 =
		// usuario

		String selection = "1) GROUP BY (thread_id";
		cur = this.managedQuery(smsURI, columns, selection, null, forma);

		ArrayList<HistorialSms> smsData = new ArrayList<HistorialSms>();
		HistorialSms sms = null;

		while (cur.moveToNext()) {

			sms = new HistorialSms();

			sms.setThreadId(cur.getString(cur.getColumnIndex("thread_id")));
			sms.setNombre(cur.getString(cur.getColumnIndex("person")));
			Date date = new Date(cur.getLong(cur.getColumnIndex("date")));
			sms.setNumero(cur.getString(cur.getColumnIndex("address")));
			sms.setMensaje(cur.getString(cur.getColumnIndex("body")));
			sms.setFecha(date);
			sms.setType(Integer.parseInt(cur.getString(cur
					.getColumnIndex("type"))));

			if (filtro != 0 && sms.getType() == filtro) {
				smsData.add(sms);
				adapertSms.addHistorial(sms);
			} else if (filtro == 0) {
				smsData.add(sms);
				adapertSms.addHistorial(sms);
			}
		}

		// cur.close();

		adapertSms.notifyDataSetChanged();
	}

	public void loadConversationSms(Contacto contacto) {

		Uri smsURI = Uri.parse("content://sms/");
		String columns[] = new String[] { "address", "body", "date",
				"thread_id", "type", "person" }; // type 1 = otro contacto, type
													// 2 =
		// usuario
		String clause = "address = ?";

		ArrayList<String> telefonos = contacto.getTelephoneNumbers();
		int i = 0;
		while (telefonos.size() > i) {
			String selection[] = new String[] { telefonos.get(i) };

			cur = this.managedQuery(smsURI, columns, clause, selection,
					"date asc");

			HistorialSms sms = null;

			while (cur.moveToNext()) {
				sms = new HistorialSms();
				sms.setNombre(cur.getString(cur.getColumnIndex("person")));
				Date date = new Date(cur.getLong(cur.getColumnIndex("date")));
				sms.setNumero(cur.getString(cur.getColumnIndex("address")));
				sms.setMensaje(cur.getString(cur.getColumnIndex("body")));
				sms.setFecha(date);
				sms.setType(Integer.parseInt(cur.getString(cur
						.getColumnIndex("type"))));

				if (filtro != 0 && sms.getType() == filtro) {
					adapertSms.addHistorial(sms);
				} else if (filtro == 0) {
					adapertSms.addHistorial(sms);
				}
			}

			// cur.close();

			adapertSms.notifyDataSetChanged();
			i++;
		}
	}

	class HistorySmsAdapter extends BaseAdapter {

		private ArrayList<HistorialSms> historial;
		private LayoutInflater inflater;
		private SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
		private SimpleDateFormat formatoFecha = new SimpleDateFormat(
				"dd/MM/yyyy");

		public HistorySmsAdapter() {
			historial = new ArrayList<HistorialSms>();
			inflater = LayoutInflater.from(Historial_Sms.this);
		}

		public void addHistorial(HistorialSms historials) {
			if (historial != null) {
				historial.add(historials);
			}
		}

		public void addListHistorial(ArrayList<HistorialSms> historials) {
			historial = historials;
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
			Holder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.historial_item, null);
				holder = new Holder();
				holder.txtNombre = (TextView) convertView
						.findViewById(R.id.txtArribaIzquierda);
				holder.txtFecha = (TextView) convertView
						.findViewById(R.id.txtArribaDerecha);
				holder.txtHora = (TextView) convertView
						.findViewById(R.id.txtAbajoDerecha);
				holder.txtNumero = (TextView) convertView
						.findViewById(R.id.txtAbajoIzquierda);
				holder.ImagenType = (ImageView) convertView
						.findViewById(R.id.imagehistorialItem);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			HistorialSms history = (HistorialSms) getItem(position);
			holder.txtNombre.setText(history.getNumero());
			holder.txtFecha.setText(formatoFecha.format(history.getFecha()));
			holder.txtHora.setText(formatoHora.format(history.getFecha()));
			holder.txtNumero.setText(history.getMensaje());
			holder.ImagenType
					.setImageResource(android.R.drawable.ic_dialog_email);
			return convertView;
		}

		public void limpiar() {
			if (historial != null) {
				historial.clear();
			}
		}

	}

	class Holder {
		private TextView txtNombre;
		private TextView txtHora;
		private ImageView ImagenType;
		private TextView txtFecha;
		private TextView txtNumero;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		openSmsConv(position);

	}

	private void openSmsConv(int position) {
		HistorialSms Sms = (HistorialSms) adapertSms.getItem(position);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setType("vnd.android-dir/mms-sms");
		intent.setData(Uri.parse("smsto:" + Sms.getNumero()));
		startActivity(intent);

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

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			int position, long arg3) {

		HistorialSms Sms = (HistorialSms) adapertSms.getItem(position);

		dialog(Sms, position);

		return false;
	}

	private void dialog(final HistorialSms parSms, final int position) {

		final CharSequence[] items = { "Borrar Conversacion",
				"Ver Conversacion" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dc_eleccion);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:
					deleteConversation(parSms);
					break;
				case 1:
					openSmsConv(position);
					break;
				}
			}

		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	private void deleteConversation(HistorialSms parSMS) {
		Uri smsURI = Uri.parse("content://sms/");
		String where = "thread_id = ?";
		String Args[] = { parSMS.getThreadId() };

		getContentResolver().delete(smsURI, where, Args);

		loadConversationsSms();

	}
}
