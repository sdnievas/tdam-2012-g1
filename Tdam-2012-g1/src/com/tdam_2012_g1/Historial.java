package com.tdam_2012_g1;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;


import com.tdam_2012_g1.dom.Contacto;
import com.tdam_2012_g1.dom.HistorialLlamada;
import com.tdam_2012_g1.dom.HistorialMail;
import com.tdam_2012_g1.dom.HistorialSms;
import com.tdam_2012_g1.database.*;
import com.tdam_2012_g1.entidades.Mail;
import com.tdam_2012_g1.entidades.MensajeWeb;


import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
	HistoryMailAdapter adpatador;
	HistoryWebSmsAdapter AdpaterWebSms;
	HistorySmsAdapter AdapertSms;
	final DatabaseHelper db=new DatabaseHelper(this);
	private Context context;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.historial);

	    Bundle extras = getIntent().getExtras();
	    if(extras != null)
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
	    
	    adpatador = new HistoryMailAdapter();
	    
	    AdpaterWebSms = new HistoryWebSmsAdapter();
	    
	    AdapertSms = new HistorySmsAdapter();

	    getListView().setAdapter(adapter);

		getListView().setOnItemClickListener(this);
		
		
		ListView listasms = (ListView) findViewById(R.id.ListaSms);
		
		listasms.setAdapter(AdapertSms);
		
		listasms.setOnItemClickListener(this);
		
		
		ListView listasmsweb = (ListView) findViewById(R.id.ListaWebSms);
		
		listasmsweb.setAdapter(AdpaterWebSms);
		
		listasmsweb.setOnItemClickListener(this);
		
		
		ListView listamail = (ListView) findViewById(R.id.ListaMail);
		
		listamail.setAdapter(adpatador);
		
		listamail.setOnItemClickListener(this);
		
		
		loadListData();
		
		loadmailData();
		
		loadWebSmsData();
		
//		loadConversationsSms();
		
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
					null, null , null);

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
		
		public void loadmailData(){
			
			DatabaseHelper dbhelper = new DatabaseHelper(this);
	      //  SQLiteDatabase db = dbhelper.getWritableDatabase();

			ArrayList<HistorialMail> mails = dbhelper.getTodosMails();
			adpatador.addListHistorial(mails);
		}
			
	  class HistoryMailAdapter extends BaseAdapter {
			
			private ArrayList<HistorialMail> historial;
			private LayoutInflater inflater;

			public HistoryMailAdapter() {
				historial = new ArrayList<HistorialMail>();
				inflater = LayoutInflater.from(Historial.this);
			}

			public void addHistorial(HistorialMail historials) {
				if (historial != null) {
					historial.add(historials);
				}
			}
			
			public void addListHistorial(ArrayList<HistorialMail> historials) {
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

				HistorialMail history = (HistorialMail) getItem(position);
				holder.txtNameHistorial.setText(history.getMailContacto());
				holder.txtHora.setText(history.getFecha().toString());

				return convertView;
			}
	  }
	  
	  
	  
	  public void loadWebSmsData(){
			
			DatabaseHelper dbhelper = new DatabaseHelper(this);
	      //  SQLiteDatabase db = dbhelper.getWritableDatabase();

			ArrayList<MensajeWeb> mails = dbhelper.getAllMensajeWeb();
			AdpaterWebSms.addListHistorial(mails);
		}
			
	  
	  //Adapter Web Sms Data
	  
			class HistoryWebSmsAdapter extends BaseAdapter {
				
				private ArrayList<MensajeWeb> historial;
				private LayoutInflater inflater;

				public HistoryWebSmsAdapter() {
					historial = new ArrayList<MensajeWeb>();
					inflater = LayoutInflater.from(Historial.this);
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
					holder.txtNameHistorial.setText("Mensaje");
					holder.txtHora.setText(history.get_fechaEnvio());

					return convertView;
				}
			
	  }
			
			
	//------------------------SMS-------------------------------------	
			
			private void loadConversationsSms() {
				Uri smsURI = Uri.parse("content://sms/");
				String columns[] = new String[] { "address", "body", "date",
						"thread_id", "type" }; // type 1 = otro contacto, type 2 =
												// usuario


				Cursor cur = ((Activity) context).managedQuery(smsURI, columns, null,
						null, "date asc");

				ArrayList<HistorialSms> smsData = new ArrayList<HistorialSms>();
				HistorialSms sms = null;

				while (cur.moveToNext()) {
					sms = new HistorialSms();
					sms.setThreadId("12545456");
					Date date = new Date(cur.getLong(cur.getColumnIndex("date")));
					sms.setNumero(cur.getString(cur.getColumnIndex("address")));
					sms.setMensaje(cur.getString(cur.getColumnIndex("body")));
					sms.setFecha(date);
					sms.setType(Integer.parseInt(cur.getString(cur
							.getColumnIndex("type"))));

					smsData.add(sms);
					AdapertSms.addHistorial(sms);
				}

				cur.close();

				AdapertSms.notifyDataSetChanged();
			}

			
	class HistorySmsAdapter extends BaseAdapter {
				
				private ArrayList<HistorialSms> historial;
				private LayoutInflater inflater;

				public HistorySmsAdapter() {
					historial = new ArrayList<HistorialSms>();
					inflater = LayoutInflater.from(Historial.this);
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

					HistorialSms history = (HistorialSms) getItem(position);
					holder.txtNameHistorial.setText(history.getNumero());
					holder.txtHora.setText(history.getFecha().toGMTString());

					return convertView;
				}
			
	  }
			
			
			
	  		
		
}
