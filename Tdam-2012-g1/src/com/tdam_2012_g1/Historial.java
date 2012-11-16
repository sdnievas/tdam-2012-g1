package com.tdam_2012_g1;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.app.TabActivity;
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


public class Historial extends TabActivity {
	
	private Intent intent;
	private Historial historyToShow;
	public Contacto contact;
	final DatabaseHelper db=new DatabaseHelper(this);
	private Context context;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.historial);

	    Bundle extras = getIntent().getExtras();
	    if(extras != null)
	    	contact = (Contacto) extras.getSerializable("contacto"); //Cargamos el contacto que recibimos del intent de la activity contactos

	    CargarTabs();
				
	}
    
    private void CargarTabs(){
    	
    	Resources res = getResources();
		
		TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();

		TabHost.TabSpec spec=tabs.newTabSpec("Llamadas");
		spec.setIndicator("Llamadas", res.getDrawable(android.R.drawable.ic_menu_call));
		Intent historialLlamadas = new Intent(this, Historial_Llamadas.class);
		historialLlamadas.putExtra("contacto",contact);
		spec.setContent(historialLlamadas);
		tabs.addTab(spec);

		spec=tabs.newTabSpec("SmS"); 
		spec.setIndicator("Sms", res.getDrawable(android.R.drawable.ic_dialog_dialer));
		Intent historialSms = new Intent(this, Historial_Sms.class);
		historialSms.putExtra("contacto",contact);
		spec.setContent(historialSms);
		tabs.addTab(spec);
		
		spec=tabs.newTabSpec("WebMessage"); 
		spec.setIndicator("WebMessage",	res.getDrawable(android.R.drawable.ic_dialog_alert));
		Intent historialWebMsg = new Intent(this, Historial_WebMsg.class);
		historialWebMsg.putExtra("contacto",contact);
		spec.setContent(historialWebMsg);
		tabs.addTab(spec);
		
		spec=tabs.newTabSpec("Mail"); 
		spec.setIndicator("Mail", res.getDrawable(android.R.drawable.ic_dialog_email));
		Intent historialMail = new Intent(this, Historial_Mail.class);
		historialMail.putExtra("contacto",contact);
		spec.setContent(historialMail);
		tabs.addTab(spec);

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
		
		
		
/*
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}
		
    
		
		
		
			
	  
	  
	  
	  
	  
			
			
	//------------------------SMS-------------------------------------	
			
			
			
			
			
	  	*/	
		
}
