package com.tdam.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tdam.Class.Contacto;
import com.tdam.Class.HistorialLlamada;
import com.tdam.Database.DatabaseHelper;
import com.tdam_2012_g1.R;

public class Historial_Llamadas extends ListActivity implements
		OnItemClickListener {

	private HistorialLLamadasAdapter adapter;
	private Contacto contact;
	private String ordenarForma;
	private String FiltroContactos;
	private int filtro;
	private final int DIALGO_ELIMINACION = -2;
	private static final String LOGIN_SETTINGS = "LoginPreferences";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial__llamadas);

		Bundle extras = getIntent().getExtras();
		if (extras != null)
			// Cargamos el contacto que recibimos del intent de la activity
			// contactos
			contact = (Contacto) extras.getSerializable("contacto");

		adapter = new HistorialLLamadasAdapter();

		getListView().setAdapter(adapter);

		getListView().setOnItemClickListener(this);

		getPreferences();

		loadListData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_historial, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		HistorialLlamada llamada = (HistorialLlamada) adapter.getItem(position);
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + llamada.getNumero()));
		startActivity(callIntent);

	}

	private void loadListData() {

		adapter.limpiar();
		String forma = "date desc";

		if (!ordenarForma.equals("0")) {
			forma = "date asc";
		}

		ContentResolver cr = getContentResolver();

		String[] colSelect = { CallLog.Calls._ID, CallLog.Calls.NUMBER,
				CallLog.Calls.TYPE, CallLog.Calls.CACHED_NAME,
				CallLog.Calls.CACHED_NUMBER_TYPE, CallLog.Calls.DATE,
				CallLog.Calls.DURATION, CallLog.Calls.TYPE };

		String projection = null;
		String[] values = null;
		if (filtro == 1) {
			projection = CallLog.Calls.TYPE + "=" + CallLog.Calls.INCOMING_TYPE;
		}
		if (filtro == 2) {
			projection = CallLog.Calls.TYPE + "=" + CallLog.Calls.OUTGOING_TYPE;
		}
		if (contact != null) {
			if (projection == null) {
				projection = CallLog.Calls.CACHED_NAME + " = ?";
			} else {
				projection = projection + " AND " + CallLog.Calls.CACHED_NAME
						+ " = ?";
			}
			values = new String[] { contact.getName() };
		}

		Cursor cur = cr.query(CallLog.Calls.CONTENT_URI, colSelect, projection,
				values, forma);

		HistorialLlamada historial = null;

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String nomb = cur.getString(cur
						.getColumnIndex(CallLog.Calls.CACHED_NAME));
				String numero = cur.getString(cur
						.getColumnIndex(CallLog.Calls.NUMBER));
				Date hora = (new Date(cur.getLong(cur
						.getColumnIndex(CallLog.Calls.DATE))));
				historial = new HistorialLlamada();
				historial.setNombre(nomb);
				historial.setFecha(hora);
				historial.setNumero(numero);
				adapter.addHistorial(historial);
			}
		}
		cur.close();

		adapter.notifyDataSetChanged();

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

	class Holder {
		private TextView txtNombre;
		private TextView txtHora;
		private TextView txtFecha;
		private TextView txtNumero;
	}

	// Adapter de la lista de contactos
	class HistorialLLamadasAdapter extends BaseAdapter {

		private ArrayList<HistorialLlamada> historial;
		private LayoutInflater inflater;
		private SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

		public HistorialLLamadasAdapter() {
			historial = new ArrayList<HistorialLlamada>();
			inflater = LayoutInflater.from(Historial_Llamadas.this);
		}

		public void addHistorial(HistorialLlamada historials) {
			if (historial != null) {
				historial.add(historials);
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
			Holder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.historial_item, null);
				holder = new Holder();
				holder.txtNombre = (TextView) convertView
						.findViewById(R.id.txtArribaIzquierda);
				holder.txtHora = (TextView) convertView
						.findViewById(R.id.txtArribaDerecha);
				holder.txtNumero = (TextView) convertView
						.findViewById(R.id.txtAbajoIzquierda);
				holder.txtFecha = (TextView) convertView
						.findViewById(R.id.txtAbajoDerecha);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			HistorialLlamada history = (HistorialLlamada) getItem(position);
			holder.txtNombre.setText(history.getNombre());
			holder.txtFecha.setText(history.getFecha().toString().trim());
			holder.txtHora.setText((formatoHora.format(history.getFecha())
					.trim()));
			holder.txtNumero.setText(history.getNumero().trim());
			return convertView;
		}

		public void limpiar() {
			if (historial != null) {
				historial.clear();
			}
		}

	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		Intent intent = null;
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
	protected void onResume() {
		super.onResume();
		this.setListAdapter(null);
		getListView().setAdapter(adapter);
		getListView().setOnItemClickListener(this);
		getPreferences();
		loadListData();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
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
}
