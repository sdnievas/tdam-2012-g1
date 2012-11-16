package com.tdam_2012_g1;

import java.util.ArrayList;

import com.tdam_2012_g1.Historial_Mail.HistoryMailAdapter;
import com.tdam_2012_g1.database.DatabaseHelper;
import com.tdam_2012_g1.dom.Contacto;
import com.tdam_2012_g1.dom.HistorialLlamada;
import com.tdam_2012_g1.dom.HistorialWebSms;
import com.tdam_2012_g1.entidades.MensajeWeb;

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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

public class Historial_WebMsg extends ListActivity implements OnItemClickListener {

	private HistoryWebSmsAdapter AdpaterWebSms;
	private Contacto contact;
	private String ordenarForma;
	private String FiltroContactos;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial__web_msg);
        
        Bundle extras = getIntent().getExtras();
	    if(extras != null)
	    	contact = (Contacto) extras.getSerializable("contacto"); //Cargamos el contacto que recibimos del intent de la activity contactos

	    AdpaterWebSms = new HistoryWebSmsAdapter();
	    
	    getListView().setAdapter(AdpaterWebSms);

      	getListView().setOnItemClickListener(this);
      	
      	loadWebSmsData();
      	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_historial, menu);
        return true;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		MensajeWeb WebSms = (MensajeWeb) AdpaterWebSms.getItem(position);
		Contacto contacto = new Contacto();
		contacto.setName(WebSms.get_destinatario());
		Intent intent = new Intent(this, Servicio_Web.class);
		intent.putExtra("contacto", contacto );
		startActivity(intent);
		
	}
	
	
	private void getPreferences(){
		
		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		ordenarForma = myPreference.getString(
				getString(R.string.preference_Historial_Ordenarkey),"0");
		
		FiltroContactos = myPreference.getString(
				getString(R.string.preference_Historial_Filtrarkey),"0");
	}
	
	
	public void loadWebSmsData(){
		
		getPreferences();
		
		DatabaseHelper dbhelper = getDatabaseHelper();
		ArrayList<MensajeWeb> mails = null;
		if(contact != null){
			mails = dbhelper.getContactMensajeWeb(contact);
		}else{
			mails = dbhelper.getAllMensajeWeb();}
		
		AdpaterWebSms.addListHistorial(mails);
		dbhelper.close();
	}
	
	protected DatabaseHelper getDatabaseHelper(){	
		return new DatabaseHelper(this);
	}
		
  
 
		class HistoryWebSmsAdapter extends BaseAdapter {
			
			private ArrayList<MensajeWeb> historial;
			private LayoutInflater inflater;

			public HistoryWebSmsAdapter() {
				historial = new ArrayList<MensajeWeb>();
				inflater = LayoutInflater.from(Historial_WebMsg.this);
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
		 
		 public boolean onMenuItemSelected(int featureId, MenuItem item) {
			 Intent intent = null;
				switch (item.getItemId()) {
				
				case R.id.historial_settings:
					intent= new Intent(this, Preference_historial.class);	
					break;
				}
				startActivity(intent);
				return true;
			}
}
