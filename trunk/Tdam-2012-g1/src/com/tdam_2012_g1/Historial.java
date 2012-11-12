package com.tdam_2012_g1;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;

import com.tdam_2012_g1.dom.Contacto;
import com.tdam_2012_g1.dom.HistorialLlamada;


import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class Historial extends ListActivity implements OnItemClickListener {
	
	Intent intent;
	private HistoryAdapter adapter;
	private Historial historyToShow;
	private Contacto contact;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.historial);

	    Bundle extras = getIntent().getExtras();
	    contact = (Contacto) extras.getSerializable("contacto"); //Cargamos el contacto que recibimos del intent de la activity contactos
	    
	    
	    
		Resources res = getResources();
		
		TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();

		TabHost.TabSpec spec=tabs.newTabSpec("Llmadas");//harcodeade
		spec.setContent(R.id.tabLlamadas);
		spec.setIndicator("Llamadas",
		res.getDrawable(android.R.drawable.ic_menu_call));
		tabs.addTab(spec);

		spec=tabs.newTabSpec("SmS"); //harcodeade
		spec.setContent(R.id.tabSms);
		spec.setIndicator("Sms",
		res.getDrawable(android.R.drawable.ic_dialog_dialer));
		tabs.addTab(spec);
		
		spec=tabs.newTabSpec("WebMessage"); //harcodeade
		spec.setContent(R.id.tabWebSms);
		spec.setIndicator("WebMessage",
		res.getDrawable(android.R.drawable.ic_dialog_alert));
		tabs.addTab(spec);
		
		spec=tabs.newTabSpec("Mail"); //harcodeade
		spec.setContent(R.id.tabMail);
		spec.setIndicator("Mail",
		res.getDrawable(android.R.drawable.ic_dialog_email));
		tabs.addTab(spec);

		tabs.setCurrentTab(0);
		
		
	    adapter = new HistoryAdapter();

	    getListView().setAdapter(adapter);

		getListView().setOnItemClickListener(this);
		
		
		ListView listasms = (ListView) findViewById(R.id.ListaSms);
		
		listasms.setAdapter(adapter);
		
		listasms.setOnItemClickListener(this);
		
		
		ListView listasmsweb = (ListView) findViewById(R.id.ListaWebSms);
		
		listasmsweb.setAdapter(adapter);
		
		listasmsweb.setOnItemClickListener(this);
		
		
		ListView listamail = (ListView) findViewById(R.id.ListaMail);
		
		listamail.setAdapter(adapter);
		
		listamail.setOnItemClickListener(this);
		
		
		loadListData();
		
		
		
		
		

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
			}
			startActivity(intent);
			return true;
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
		
		
		class Holder {
			private TextView txtNameHistorial;
			private TextView txtHora;
		}
		
		//Adapter de la lista de contactos
		class HistoryAdapter extends BaseAdapter {
			
			private ArrayList<HistorialLlamada> historial;
			private LayoutInflater inflater;

			public HistoryAdapter() {
				historial = new ArrayList<HistorialLlamada>();
				inflater = LayoutInflater.from(Historial.this);
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

				HistorialLlamada history = (HistorialLlamada) getItem(position);
				holder.txtNameHistorial.setText(history.getNombre());
				holder.txtHora.setText(history.getFecha().toString());

				return convertView;
			}
			
			
			

		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}
		
    
		public void loadListData(){
			
			ContentResolver cr = getContentResolver();
					
			String[] strFields = { CallLog.Calls._ID, CallLog.Calls.NUMBER,
					CallLog.Calls.TYPE, CallLog.Calls.CACHED_NAME,
					CallLog.Calls.CACHED_NUMBER_TYPE, CallLog.Calls.DATE,
					CallLog.Calls.DURATION, CallLog.Calls.TYPE };
			
			
			Cursor cur = cr.query(CallLog.Calls.CONTENT_URI, strFields,
					CallLog.Calls.NUMBER + " = ?", new String[] { contact.getTelephoneNumbers().get(0) } , null);

			HistorialLlamada historial = null;

			if (cur.getCount() > 0) {
				while (cur.moveToNext()) {
					String nomb = cur.getString(cur
							.getColumnIndex(CallLog.Calls.NUMBER));
					Date hora = (new Date(cur.getLong(cur
									.getColumnIndex(CallLog.Calls.DATE))));

					historial = new HistorialLlamada();
					historial.setNombre(nomb);
					historial.setFecha(hora);

					adapter.addHistorial(historial);
				}
			}
			cur.close();

			adapter.notifyDataSetChanged();

		}
			
			
			
		
}
