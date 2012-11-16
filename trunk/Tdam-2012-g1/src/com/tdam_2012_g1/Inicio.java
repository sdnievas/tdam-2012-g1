package com.tdam_2012_g1;

import com.tdam_2012_g1.database.DatabaseHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Inicio extends Activity implements OnClickListener{

	private EditText user;
	private EditText pass;
	private Button boton;
	private Intent intent;
	private Button botonNuevoUsuario;
	
	private static final String LOGIN_SETTINGS = "LoginPreferences";
	private static final String REMEMBER_USER_PASSWORD = "rememberUserPassword";
	private static final String USER = "user";
	private static final String PASSWORD = "password";

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity);
        
         user = (EditText) findViewById(R.id.init_NombUser);
         pass = (EditText) findViewById(R.id.init_UserPassword);
    	 boton = (Button)	findViewById(R.id.init_btnInicio);	
    	 
    	/* user.setText("federico");
    	 pass.setText("123456");*/
    	 user.setText("fernando");
    	 pass.setText("1234567");
    	 
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
		
		if(intent != null)
			startActivity(intent);
		
		
	}
	
	private void guardarUsuarioPreference(){
		
		SharedPreferences preferences = getSharedPreferences(
				LOGIN_SETTINGS, MODE_PRIVATE);
		SharedPreferences.Editor edit = preferences.edit();
		edit.clear();
		edit.putBoolean(REMEMBER_USER_PASSWORD,
				true);
		edit.putString(USER, user.getText().toString());
		edit.putString(PASSWORD, pass.getText().toString());
		edit.commit();
		
	}
	
	private void ControlUser(){
		
		boolean contacExist;
		DatabaseHelper dbhelper = new DatabaseHelper(this);
		contacExist = dbhelper.contactExist(user.getText().toString(), pass.getText().toString());
		if(contacExist){
			guardarUsuarioPreference();
			intent = new Intent().setClass(this, MainActivity.class);
		}else{
			
			Dialog dialogo = null;
			dialogo = createAlertDialog();
			dialogo.show();
		}
		dbhelper.close();
		
	}
	
	private Dialog createAlertDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setIcon(R.drawable.image_notification)
				.setTitle("Error")
				.setPositiveButton("Aceptar", null)
				.setMessage("Error en usuario o contraseña").create();
		return dialog;
	}
	
	
	
	
}
