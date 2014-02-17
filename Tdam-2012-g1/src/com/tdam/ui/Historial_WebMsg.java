package com.tdam.ui;

import java.util.ArrayList;

import com.tdam.Class.Contacto;
import com.tdam.Class.MensajeWeb;
import com.tdam.Class.Usuario;
import com.tdam.Database.SingletonDB;
import com.tdam_2012_g1.R;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

public class Historial_WebMsg extends ListActivity implements
		OnItemClickListener, OnItemLongClickListener {

	private HistoryWebSmsAdapter adapterWeb;
	private Contacto contact;
	private String ordenarForma;
	private String FiltroContactos;
	private Usuario usr;
	private int filtro;

	private static final String LOGIN_SETTINGS = "LoginPreferences";
	private static final String USER = "User";
	private static final String PASSWORD = "Password";
	private static final String NO_MESSAGES = "No hay Mensajes";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial__web_msg);

		Bundle extras = getIntent().getExtras();

		if (extras != null)// Cargamos el contacto que recibimos del intent de
							// la activity contactos
			contact = (Contacto) extras.getSerializable("contacto");

		adapterWeb = new HistoryWebSmsAdapter();

		getListView().setAdapter(adapterWeb);

		getListView().setOnItemClickListener(this);

		getListView().setOnItemLongClickListener(this);

		loadWebSmsData();

	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferences();
		loadWebSmsData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_historial, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		MensajeWeb WebSms = (MensajeWeb) adapterWeb.getItem(position);
		if (!WebSms.get_detalle().equals(NO_MESSAGES)) {
			Contacto contacto = new Contacto();
			contacto.setUserWeb(WebSms.get_contacto());
			Intent intent = new Intent(this, Servicio_Web.class);
			intent.putExtra("contacto", contacto);
			startActivity(intent);
		}

	}

	// devuelve las spreference de la app
	private void getPreferences() {

		SharedPreferences preferences = getSharedPreferences(LOGIN_SETTINGS,
				MODE_PRIVATE);
		usr = new Usuario();
		usr.set_nombre(preferences.getString(USER, ""));
		usr.set_contraseña(preferences.getString(PASSWORD, ""));

		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		ordenarForma = myPreference.getString(
				getString(R.string.preference_Historial_Ordenarkey), "0");

		FiltroContactos = myPreference.getString(
				getString(R.string.preference_Historial_Filtrarkey), "0");
		filtro = Integer.parseInt(FiltroContactos);
	}

	// carga la info de la Db en la lista
	public void loadWebSmsData() {
		adapterWeb.limpiar();
		getPreferences();
		String forma = "desc";

		if (!ordenarForma.equals("0")) {
			forma = "asc";
		}

		ArrayList<MensajeWeb> MensajesWeb = null;
		if (contact != null) {
			if (filtro == 0) {
				MensajesWeb = SingletonDB.getInstance(getApplicationContext())
						.getDatabaseHelper()
						.getContactoMensajeWeb(contact, usr, forma);
			} else {
				if(filtro == 2){
					filtro = 0;
				}
				MensajesWeb = SingletonDB.getInstance(getApplicationContext())
						.getDatabaseHelper()
						.getContactoMensajeWeb(contact, usr, filtro, forma);
			}
		} else {
			if (filtro == 0) {
				MensajesWeb = SingletonDB.getInstance(getApplicationContext())
						.getDatabaseHelper().getMensajesWeb(usr, forma);
			} else {
				if(filtro == 2){
					filtro = 0;
				}
				MensajesWeb = SingletonDB.getInstance(getApplicationContext())
						.getDatabaseHelper().getMensajesWeb(usr, filtro, forma);
			}
		}

		if (MensajesWeb == null || MensajesWeb.isEmpty()) {
			MensajesWeb.add(new MensajeWeb(NO_MESSAGES));
			adapterWeb.addListHistorial(MensajesWeb);
			adapterWeb.notifyDataSetChanged();
		} else {
			adapterWeb.addListHistorial(MensajesWeb);
			adapterWeb.notifyDataSetChanged();
		}
	}

	// Clase adapter de la pantalla de los mensajes Web
	class HistoryWebSmsAdapter extends BaseAdapter {

		private ArrayList<MensajeWeb> historialMsgWeb;
		private LayoutInflater inflater;

		public HistoryWebSmsAdapter() {
			historialMsgWeb = new ArrayList<MensajeWeb>();
			inflater = LayoutInflater.from(Historial_WebMsg.this);
		}

		public void addHistorial(MensajeWeb MsgWeb) {
			if (historialMsgWeb != null) {
				historialMsgWeb.add(MsgWeb);
			}
		}

		public void addListHistorial(ArrayList<MensajeWeb> historials) {
			historialMsgWeb = historials;
		}

		public void limpiar() {
			if (historialMsgWeb != null) {
				historialMsgWeb.clear();
			}
		}

		@Override
		public int getCount() {
			return historialMsgWeb.size();
		}

		@Override
		public Object getItem(int position) {
			return historialMsgWeb.get(position);
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
				holder.txtHora = (TextView) convertView
						.findViewById(R.id.txtAbajoDerecha);
				holder.ImagenType = (ImageView) convertView
						.findViewById(R.id.imagehistorialItem);
				holder.txtMensaje = (TextView) convertView
						.findViewById(R.id.txtAbajoIzquierda);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			MensajeWeb history = (MensajeWeb) getItem(position);
			if(history.getType() == 0){
				holder.txtNombre.setText("Para: " +history.get_contacto());
			}
			else{
				holder.txtNombre.setText("De: " +history.get_contacto());
			}
			//holder.txtNombre.setText(history.get_contacto());
			// holder.txtFecha.setText(history.get_fechaEnvio());
			holder.txtHora.setText(history.get_fechaEnvio());
			holder.txtMensaje.setText(history.get_detalle());
			holder.ImagenType
					.setImageResource(android.R.drawable.stat_notify_chat);

			return convertView;
		}

	}

	class Holder {
		private TextView txtNombre;
		private TextView txtHora;
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

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			int position, long arg3) {

		MensajeWeb WebSms = (MensajeWeb) adapterWeb.getItem(position);
		if (!WebSms.get_detalle().equals(NO_MESSAGES)) {
			dialog(WebSms);
		}
		return false;
	}

	private void dialog(final MensajeWeb parWebSms) {

		final CharSequence[] items = { "Borrar Conversacion",
				"Ver Conversacion" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dc_eleccion);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:
					SingletonDB
							.getInstance(getApplicationContext())
							.getDatabaseHelper()
							.deleteConversation(parWebSms.get_contacto(),
									parWebSms.get_usuario());
					loadWebSmsData();
					break;
				case 1:
					openConersation(parWebSms);
					break;
				}
			}

		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	private void openConersation(MensajeWeb parWebSms) {
		Contacto contacto = new Contacto();
		contacto.setName(parWebSms.get_contacto());
		Intent intent = new Intent(this, Servicio_Web.class);
		intent.putExtra("contacto", contacto);
		startActivity(intent);
	}
}
