package com.tdam.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.R.color;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.FeatureInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdam.Class.Contacto;
import com.tdam.Class.HistorialSms;
import com.tdam.Class.MensajeWeb;
import com.tdam.Class.Usuario;
import com.tdam.Class.Type;
import com.tdam.Database.DatabaseHelper;
import com.tdam.Database.SingletonDB;
import com.tdam.ServicioWeb.WebService;
import com.tdam.ServicioWeb.WebServiceInfo;
import com.tdam.Suport.ConexionInfo;
import com.tdam.Suport.Notificacion;
import com.tdam_2012_g1.R;

public class Servicio_Web extends ListActivity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	private Intent intent;
	private MensajeWeb msj;
	private MensajeWebAdapter adapter;
	private Contacto contact = null;
	private EditText txtDestinatario;
	private EditText txtMensaje;
	private Usuario usr;
	private Boolean actualizar;
	private int filtro;

	private static final String DIALOG_ERROR = "Error";
	private static final String DIALOG_MSJ = "Servidor de Mensajes Web Inaccesible";
	private static final String DIALOG_BTN = "Aceptar";
	private static final String LOGIN_SETTINGS = "LoginPreferences";
	private static final String USER = "User";
	private static final String PASSWORD = "Password";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicio_web);

		txtDestinatario = (EditText) findViewById(R.id.txtDestinatario);
		txtMensaje = (EditText) findViewById(R.id.txtMensajeDetalle);

		Button btnEnviar = (Button) findViewById(R.id.btnEnviar);
		btnEnviar.setOnClickListener(this);

		Bundle extras = getIntent().getExtras();

		// Cargamos el contacto que recibimos del intent de la activity
		// contactos
		if (extras != null) {
			contact = (Contacto) extras.getSerializable("contacto");
		}
		// cargamos el usuario de las shared prefereces
		cargarUsuario();

		getPreferences();
		if (contact != null) {
			txtDestinatario.setText(contact.getuserWeb());
			txtDestinatario.setEnabled(false);
			txtDestinatario.setClickable(false);
			AdapterAndList();
		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_servicio_web, menu);
		return true;
	}

	public void cargarUsuario() {

		SharedPreferences preferences = getSharedPreferences(LOGIN_SETTINGS,
				MODE_PRIVATE);
		usr = new Usuario();
		usr.set_nombre(preferences.getString(USER, ""));
		usr.set_contraseña(preferences.getString(PASSWORD, ""));

	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferences();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnEnviar:
			if (validarVacio()) {
				nuevoMensaje();
				ConexionInfo coninf = new ConexionInfo(this);
				boolean hayconexionInternet = coninf
						.isInternetConnectionAvailable();

				if (hayconexionInternet) {
					NewMsgUserTask _initTask = new NewMsgUserTask(usr, msj,
							this);
					_initTask.execute();
					txtMensaje.setText("");
				} else {
					Dialog dialogo = null;
					dialogo = createAlertDialog();
					dialogo.show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "Campos vacíos",
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {

		case R.id.servicio_web_settings:
			intent = new Intent(this, Preference_servicio_web.class);
			break;
		}
		startActivity(intent);
		return true;
	}

	private Dialog createAlertDialog() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setIcon(R.drawable.image_notification).setTitle(DIALOG_ERROR)
				.setPositiveButton(DIALOG_BTN, null).setMessage(DIALOG_MSJ)
				.create();
		return dialog;
	}

	private void getPreferences() {

		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		String filtroString = myPreference.getString(
				getString(R.string.preference_Mensaje_Web_Filtrarkey), "0");

		filtro = Integer.parseInt(filtroString);

	}

	private void nuevoMensaje() {

		msj = new MensajeWeb();

		msj.set_usuario(usr.get_nombre());
		msj.set_detalle(txtMensaje.getText().toString());
		msj.set_contacto(txtDestinatario.getText().toString());
		Calendar date = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");
		String fecha = dateFormat.format(date.getTimeInMillis());
		msj.set_fechaEnvio(fecha);
	}

	private boolean validarVacio() {
		boolean campo1 = txtDestinatario.getText().toString().equals("");
		boolean campo2 = txtMensaje.getText().toString().equals("");
		return !(campo1 || campo2);
	}

	private void AdapterAndList() {
		adapter = new MensajeWebAdapter();
		getListView().setAdapter(adapter);
		getListView().setOnItemClickListener(this);
		getListView().setOnItemLongClickListener(this);
		loadWebSmsData();

	}

	public void loadWebSmsData() {

		ArrayList<MensajeWeb> mensajesWeb = null;
		if (filtro == 0) {
			if (contact != null)
				mensajesWeb = SingletonDB.getInstance(getApplicationContext())
						.getDatabaseHelper()
						.getContactoMensajeWeb(contact, usr, "asc");
			else {
				Contacto cont = new Contacto();
				cont.setUserWeb(txtDestinatario.getText().toString());
				mensajesWeb = SingletonDB.getInstance(getApplicationContext())
						.getDatabaseHelper()
						.getContactoMensajeWeb(cont, usr, "asc");

			}
		} else {
			if (contact != null)
				mensajesWeb = SingletonDB.getInstance(getApplicationContext())
						.getDatabaseHelper()
						.getContactoMensajeWeb(contact, usr, "desc", filtro);
			else {
				Contacto cont = new Contacto();
				cont.setUserWeb(txtDestinatario.getText().toString());
				mensajesWeb = SingletonDB.getInstance(getApplicationContext())
						.getDatabaseHelper()
						.getContactoMensajeWeb(cont, usr, "desc", filtro);

			}
		}
		adapter.addListHistorial(mensajesWeb);
		this.setSelection(adapter.getCount() - 1);
		adapter.notifyDataSetChanged();
	}

	protected DatabaseHelper getDatabaseHelper() {
		return new DatabaseHelper(this);
	}

	class MensajeWebAdapter extends BaseAdapter {

		private ArrayList<MensajeWeb> historial;
		private LayoutInflater inflater;

		public MensajeWebAdapter() {
			historial = new ArrayList<MensajeWeb>();
			inflater = LayoutInflater.from(Servicio_Web.this);
		}

		public void addHistorial(MensajeWeb historials) {
			if (historial != null) {
				historial.add(historials);
			}
		}

		public void addListHistorial(ArrayList<MensajeWeb> historials) {
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
			MensajeWeb history = (MensajeWeb) getItem(position);

			if (history.getType() == Type.entrada.ordinal()) {
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.webmsg_item_send,
							null);
					holder = new Holder();
					holder.txtMensajeWeb = (TextView) convertView
							.findViewById(R.id.txtWebmensajes);
					holder.txtHora = (TextView) convertView
							.findViewById(R.id.txtMensajeHora);
					holder.Imagen = (ImageView) convertView
							.findViewById(R.id.imagenTriangulo);
					holder.fondomensaje = (LinearLayout) convertView
							.findViewById(R.id.layoutMsgWeb);
					holder.fondoflechamensaje = (LinearLayout) convertView
							.findViewById(R.id.layoutMsgWebflecha);
					convertView.setTag(holder);
				} else {
					holder = (Holder) convertView.getTag();
				}
			} else {

				if (convertView == null) {
					convertView = inflater.inflate(R.layout.webmsg_item, null);
					holder = new Holder();
					holder.txtMensajeWeb = (TextView) convertView
							.findViewById(R.id.txtWebmensajes);
					holder.txtHora = (TextView) convertView
							.findViewById(R.id.txtMensajeHora);
					holder.Imagen = (ImageView) convertView
							.findViewById(R.id.imagenTriangulo);
					holder.fondomensaje = (LinearLayout) convertView
							.findViewById(R.id.layoutMsgWeb);
					holder.fondoflechamensaje = (LinearLayout) convertView
							.findViewById(R.id.layoutMsgWebflecha);
					convertView.setTag(holder);
				} else {
					holder = (Holder) convertView.getTag();
				}

			}

			holder.txtMensajeWeb.setText(history.get_detalle());
			holder.txtHora.setText(history.get_fechaEnvio());
			holder.txtHora.setBackgroundResource(android.R.color.darker_gray);
			holder.txtMensajeWeb
					.setBackgroundResource(android.R.color.darker_gray);
			holder.Imagen.setImageResource(android.R.drawable.arrow_down_float);
			holder.fondomensaje
					.setBackgroundResource(android.R.color.darker_gray);
			if (history.getType() == Type.entrada.ordinal()) {
				holder.fondoflechamensaje.setGravity(android.R.attr.right);

			} else {
				holder.fondoflechamensaje.setGravity(android.R.attr.left);
			}

			return convertView;
		}

	}

	class Holder {
		private LinearLayout fondoflechamensaje;
		private LinearLayout fondomensaje;
		private TextView txtMensajeWeb;
		private TextView txtHora;
		private ImageView Imagen;
	}

	protected class NewMsgUserTask extends
			AsyncTask<String, Integer, WebServiceInfo> {

		Usuario user;
		Context context;
		MensajeWeb msj;

		public NewMsgUserTask(Usuario user, MensajeWeb msg, Context context) {
			this.user = user;
			this.context = context;
			this.msj = msg;
		}

		@Override
		protected WebServiceInfo doInBackground(String... params) {
			WebServiceInfo result = null;

			try {
				WebService webser = new WebService(user.get_nombre(),
						user.get_contraseña(), this.context);
				result = webser.sendMessage(msj);
			} catch (Exception e) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context)
						.setIcon(R.drawable.image_notification);
				builder.setTitle(context.getString(R.string.dialog_info));
				builder.setMessage(R.string.dialog_errormsg);
				builder.setPositiveButton(R.string.dialog_btn, null);
				builder.show();
			}

			if (result != null && WebServiceInfo.SUCCESS == result.getCode()) {
				msj.setType(Type.salida.ordinal());
				SingletonDB.getInstance(context).getDatabaseHelper()
						.addMensaje(msj);
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(context)
						.setIcon(R.drawable.image_notification);
				builder.setTitle(context.getString(R.string.dialog_info));
				builder.setMessage(R.string.dialog_errormsg);
				builder.setPositiveButton(R.string.dialog_btn, null);
				builder.show();
			}

			return result;
		}

		@Override
		protected void onPostExecute(WebServiceInfo result) {
			super.onPostExecute(result);

			if (result.getCode() == -1 || result.getCode() == 0
					|| result.getCode() == 2) {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(context)
						.setIcon(R.drawable.image_notification);
				builder1.setTitle(context.getString(R.string.dialog_info));
				builder1.setMessage(R.string.dialog_errormsg);
				builder1.setPositiveButton(R.string.dialog_btn, null);
				builder1.show();
			}
			Notificacion noti = new Notificacion(context, result, 2);
			noti.notificionMensajes();

			AdapterAndList();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		MensajeWeb WebSms = (MensajeWeb) adapter.getItem(position);
		Intent intent = new Intent(this, Detalle_Msg_web.class);
		intent.putExtra("MensajeWeb", WebSms);
		startActivity(intent);

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		MensajeWeb WebSms = (MensajeWeb) adapter.getItem(arg2);
		dialog(WebSms);
		return false;
	}

	private void dialog(final MensajeWeb parSms) {

		final CharSequence[] items = { "Borrar" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dc_eleccion);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				SingletonDB.getInstance(getApplicationContext())
						.getDatabaseHelper().deleteMsgWeb(parSms);
				loadWebSmsData();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

}
