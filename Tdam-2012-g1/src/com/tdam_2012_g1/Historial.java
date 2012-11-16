package com.tdam_2012_g1;



import android.app.TabActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.tdam_2012_g1.dom.Contacto;
import com.tdam_2012_g1.database.*;

import android.content.Intent;
import android.content.res.Resources;
import android.widget.TabHost;



public class Historial extends TabActivity {
	
	private Intent intent;
	public Contacto contact;
	final DatabaseHelper db = getDatabaseHelper();

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.historial);

	    Bundle extras = getIntent().getExtras();
	    if(extras != null)
	    	contact = (Contacto) extras.getSerializable("contacto"); //Cargamos el contacto que recibimos del intent de la activity contactos

	    CargarTabs();
				
	}
    

    protected DatabaseHelper getDatabaseHelper(){	
		return new DatabaseHelper(this);
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
		spec.setIndicator("Sms", res.getDrawable(android.R.drawable.ic_dialog_email));
		Intent historialSms = new Intent(this, Historial_Sms.class);
		historialSms.putExtra("contacto",contact);
		spec.setContent(historialSms);
		tabs.addTab(spec);
		
		spec=tabs.newTabSpec("WebMessage"); 
		spec.setIndicator("WebMessage",	res.getDrawable(android.R.drawable.stat_notify_chat));
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
		
		
	
		
}
