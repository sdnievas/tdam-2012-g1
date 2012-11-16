package com.tdam_2012_g1;

import com.tdam_2012_g1.Conectivity.ConectivityAdapter;
import com.tdam_2012_g1.database.DatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Inicio extends Activity implements OnClickListener{

	EditText user;
	EditText pass;
	Button boton;
	Intent intent;
	Button botonNuevoUsuario;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity);
        
         user = (EditText) findViewById(R.id.init_NombUser);
         pass = (EditText) findViewById(R.id.init_UserPassword);
    	 boton = (Button)	findViewById(R.id.init_btnInicio);	
		 botonNuevoUsuario = (Button) findViewById(R.id.init_btnNewUser);
		 
    	 boton.setOnClickListener(this);
    	 
    	 botonNuevoUsuario.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.init_btnInicio:
			  ControlUser();
			break;
			
		case R.id.init_btnNewUser:
			intent = new Intent(this, New_User.class);
			break;

		}
		startActivity(intent);
		
		
	}
	
	private void ControlUser(){
		
		boolean contacExist;
		DatabaseHelper dbhelper = new DatabaseHelper(this);
		contacExist = dbhelper.contactExist(user.getText().toString(), pass.getText().toString());
		if(contacExist){
			intent = new Intent().setClass(this, MainActivity.class);
		}else{
			
			Toast toast = Toast.makeText(this,
					"No existe el usuario: " + user.getText().toString() , Toast.LENGTH_LONG);
			toast.show();
		}
		dbhelper.close();
		
	}
	
	
	
	
	
}
