package com.tdam_2012_g1;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.tdam_2012_g1.database.DatabaseHelper;
import com.tdam_2012_g1.dom.Contacto;
import com.tdam_2012_g1.entidades.MensajeWeb;
import com.tdam_2012_g1.entidades.Usuario;
import com.tdam_2012_g1.mensajesWeb.WebService;
import com.tdam_2012_g1.mensajesWeb.WebServiceInfo;
import com.tdam_2012_g1.suport.ConexionInfo;
import com.tdam_2012_g1.suport.Notificacion;

public class Servicio_Web extends ListActivity implements OnClickListener, OnItemClickListener {
	
	private Intent intent;
	private MensajeWeb msj;
	private MensajeWebAdapter adapter;
	private Contacto contact = null;
	private EditText txtDestinatario;
	private EditText txtMensaje;
	private Usuario usr;
	

	private static final String DIALOG_ERROR = "Error";
	private static final String DIALOG_MSJ = "No hay conexion a ocurrido un error";
	private static final String DIALOG_BTN = "Aceptar";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicio_web);
		
		txtDestinatario = (EditText) findViewById(R.id.txtDestinatario);
		txtMensaje = (EditText) findViewById(R.id.txtMensajeDetalle);
		
		Button btnEnviar = (Button) findViewById(R.id.btnEnviar);
		btnEnviar.setOnClickListener(this);
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null)
			contact = (Contacto) extras.getSerializable("contacto"); //Cargamos el contacto que recibimos del intent de la activity contactos
        
        if(contact != null){
        	txtDestinatario.setText(contact.getName());
        	txtDestinatario.setEnabled(false);
        	AdapterAndList();}
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

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnEnviar:
			nuevoMensaje();
			ConexionInfo coninf = new ConexionInfo(this);
			boolean hayconexionInternet = coninf.isInternetConnectionAvailable();
			
			if(hayconexionInternet){
				NewMsgUserTask _initTask = new NewMsgUserTask(usr,msj,this);
			 	_initTask.execute();
			 }else{
					Dialog dialogo = null;
					dialogo = createAlertDialog();
					dialogo.show();
			 }
			
			break;
		}
	}
	
	private Dialog createAlertDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setIcon(R.drawable.image_notification)
				.setTitle(DIALOG_ERROR)
				.setPositiveButton(DIALOG_BTN, null)
				.setMessage(DIALOG_MSJ).create();
		return dialog;
	}
	
	private void nuevoMensaje(){
		
		msj = new MensajeWeb();
		usr = new Usuario();
		usr.set_nombre("federico");
		usr.set_contraseña("123456");	
		msj.set_remitente(usr.get_nombre());
		msj.set_detalle(txtMensaje.getText().toString());
		msj.set_destinatario(txtDestinatario.getText().toString());
		Calendar date = Calendar.getInstance();
		String fecha =String.valueOf(date.getTimeInMillis());
		msj.set_fechaEnvio(fecha);
	}
	
	private void AdapterAndList()
	{		
		adapter = new MensajeWebAdapter();
		getListView().setAdapter(adapter);
		getListView().setOnItemClickListener(this);
		loadWebSmsData();
	}
	
	public void loadWebSmsData(){
			
						
			DatabaseHelper dbhelper = getDatabaseHelper();
			ArrayList<MensajeWeb> mensajesWeb = null;
			if(contact != null)
				mensajesWeb = dbhelper.getContactMensajeWeb(contact);
			else
			{
				Contacto cont = new Contacto();
				cont.setName(txtDestinatario.getText().toString());
				mensajesWeb = dbhelper.getContactMensajeWeb(cont);
			}
			adapter.addListHistorial(mensajesWeb);
			dbhelper.close();
	}

		protected DatabaseHelper getDatabaseHelper(){	
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
			if (convertView == null) {
				convertView = inflater
						.inflate(R.layout.historial_item, null);
				holder = new Holder();
				holder.txtNameHistorial = (TextView) convertView
						.findViewById(R.id.textNombreHistorialItem);
				holder.txtHora = (TextView) convertView
						.findViewById(R.id.textHoraHistorialItem);					
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			MensajeWeb history = (MensajeWeb) getItem(position);
			holder.txtNameHistorial.setText(history.get_detalle());
			holder.txtHora.setText(history.get_fechaEnvio());

			return convertView;
		}
	
	}
	
	 class Holder {
			private TextView txtNameHistorial;
			private TextView txtHora;
		}

	
	 protected class NewMsgUserTask extends AsyncTask<String, Integer, WebServiceInfo>
	    {
	    	
			Usuario user;
			Context context;
			MensajeWeb msj;

			public NewMsgUserTask( Usuario user, MensajeWeb msg, Context context) {
				this.user = user;
				this.context = context;
				this.msj = msg;
			}
			
			@Override
			protected WebServiceInfo doInBackground(String... params) {
				WebServiceInfo result = null;

				try {
					WebService webser = new WebService(user.get_nombre(), user.get_contraseña());
					result = webser.sendMessage(msj);
				} catch (Exception e) {
				}

				if (result != null && WebServiceInfo.SUCCESS == result.getCode()) {
					DatabaseHelper dbhelper = new DatabaseHelper(context);
					dbhelper.addMensaje(msj);
					dbhelper.close();
				}
				
				return result;
			}
			
			@Override
			protected void onPostExecute(WebServiceInfo result) 
			{
				super.onPostExecute(result);
		
				Notificacion noti = new Notificacion(context);
				noti.notificar(result.getType());
				AdapterAndList();
			}

	    }


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		MensajeWeb WebSms = (MensajeWeb) adapter.getItem(position);
		Intent intent = new Intent(this, Detalle_Msg_web.class);
		intent.putExtra("MensajeWeb", WebSms);
		startActivity(intent);
		
	} 
}
