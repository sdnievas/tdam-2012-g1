package com.tdam_2012_g1;


import com.tdam_2012_g1.database.DatabaseHelper;
import android.content.Context;

import android.view.View.OnClickListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener{

	Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageButton contactos = (ImageButton)findViewById(R.id.imgbtn_contactos);
        ImageButton historial = (ImageButton)findViewById(R.id.imgbtn_historial);
        ImageButton perfil = (ImageButton)findViewById(R.id.imgbtn_perfil);
        ImageButton conectividad = (ImageButton)findViewById(R.id.imgbtn_conectividad);
        
        contactos.setOnClickListener(this);
        historial.setOnClickListener(this);
        perfil.setOnClickListener(this);
        conectividad.setOnClickListener(this);
                
    }
    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgbtn_contactos:
			intent = new Intent().setClass(this, Contacts.class);
			break;
			
		case R.id.imgbtn_historial:
			intent = new Intent().setClass(this, Historial.class);
			break;
			
		case R.id.imgbtn_perfil:
			intent = new Intent().setClass(this, User_Profile.class);
			break;
			
		case R.id.imgbtn_conectividad:
			intent = new Intent().setClass(this, Conectivity.class);			
			break;
		}
		startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
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

	



