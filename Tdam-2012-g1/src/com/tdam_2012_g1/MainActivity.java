package com.tdam_2012_g1;


import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {

	private ImageButton contactos;
	private ImageButton historial;
	private ImageButton perfil;
	private ImageButton conectividad;
	private ImageButton MensajesWeb;
	private Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        contactos = (ImageButton)findViewById(R.id.imgbtn_contactos);
        historial = (ImageButton)findViewById(R.id.imgbtn_historial);
        perfil = (ImageButton)findViewById(R.id.imgbtn_perfil);
        conectividad = (ImageButton)findViewById(R.id.imgbtn_conectividad);
        MensajesWeb = (ImageButton) findViewById(R.id.imgbtn_MensajesWeb);
        contactos.setOnClickListener(this);
        historial.setOnClickListener(this);
        perfil.setOnClickListener(this);
        conectividad.setOnClickListener(this);
        MensajesWeb.setOnClickListener(this);
                
    }
    
    @Override
	public void onClick(View v) {
  	 	final Animation escalar = AnimationUtils.loadAnimation(this, R.anim.escalar);
		switch (v.getId()) {
		case R.id.imgbtn_contactos:
			escalar.reset();
			contactos.startAnimation(escalar);
			intent = new Intent().setClass(this, Contacts.class);
			break;
			
		case R.id.imgbtn_historial:
			escalar.reset();
			historial.startAnimation(escalar);
			intent = new Intent().setClass(this, Historial.class);
			break;
			
		case R.id.imgbtn_perfil:
			escalar.reset();
			perfil.startAnimation(escalar);
			intent = new Intent().setClass(this, User_Profile.class);
			break;
			
		case R.id.imgbtn_conectividad:
			escalar.reset();
			conectividad.startAnimation(escalar);
			intent = new Intent().setClass(this, Conectivity.class);			
			break;
			
		case R.id.imgbtn_MensajesWeb:
			escalar.reset();
			MensajesWeb.startAnimation(escalar);
			intent = new Intent().setClass(this, Servicio_Web.class);
		}
		startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.preference_main:
		    intent = new Intent(this, Settings_activity.class);	
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

	



