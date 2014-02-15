package com.tdam.ui;

import com.tdam.Class.Usuario;
import com.tdam.Database.SingletonDB;
import com.tdam_2012_g1.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Inicio extends Activity implements OnClickListener{

	private EditText user;
	private EditText pass;
	private Button boton;
	private CheckBox check;
	private Intent intent;
	
	private static final String LOGIN_SETTINGS = "LoginPreferences";
	private static final String REMEMBER_USER_PASSWORD = "rememberUserPassword";
	private static final String USER = "User";
	private static final String PASSWORD = "Password";
	
	
	private static final String DIALOG_ERROR = "Error";
	private static final String DIALOG_MSJ = "El Usuario o la Contraseña son Incorrectos";
	private static final String DIALOG_BTN = "Aceptar";

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity);
        
         user = (EditText) findViewById(R.id.init_NombUser);
         pass = (EditText) findViewById(R.id.init_UserPassword);
         check =(CheckBox) findViewById(R.id.init_UserRemember);
    	 boton = (Button)findViewById(R.id.init_btnInicio);
		 
    	 boton.setOnClickListener(this);
    	 
    	 userPref();
	}
	
	private void userPref(){
		Usuario usr=  new Usuario();
		SharedPreferences preferences = getSharedPreferences(LOGIN_SETTINGS,
				MODE_PRIVATE);
		usr = new Usuario();
		usr.set_nombre(preferences.getString(USER, ""));
		usr.set_contraseña(preferences.getString(PASSWORD, ""));
		
		boolean pref = preferences.getBoolean(REMEMBER_USER_PASSWORD, false);
		if(pref)
			intent = new Intent(this, MainActivity.class);
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
	
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_inicio, menu);
        return true;
    }
    
 
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
    	switch (item.getItemId()) {
		
			case R.id.nuevo_usuario:
			    intent = new Intent(this, New_User.class);	
			    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				break;
				
			case R.id.configuracion_servidor:
				intent = new Intent(this, ConfiguracionServidor.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				break;

		}
		startActivity(intent);

		return true;
	}

	@Override
	/*
	 * metodo onClick
	 */
	public void onClick(View v) {
		
		switch (v.getId()) {
		
			case R.id.init_btnInicio:
				 ControlUser();
				 break;
		}
		
		if(intent != null)
			startActivity(intent);
		
		
	}
	
	/*
	 * Metodo que guarda en las sharedPreferences el nombre del contacto y la contraseña 
	 */
	private void guardarUsuarioPreference(){
		
		SharedPreferences preferences = getSharedPreferences(
				LOGIN_SETTINGS, MODE_PRIVATE);
		SharedPreferences.Editor edit = preferences.edit();
		edit.clear();
		edit.putBoolean(REMEMBER_USER_PASSWORD,
				check.isChecked());
		edit.putString(USER, user.getText().toString());
		edit.putString(PASSWORD, pass.getText().toString());
		edit.commit();		
	}
	
	/*
	 * Metodo controla que el usuario exista y si existe guarda el contacto en las 
	 * sharedPreferences y crea el intent a la actividad principal
	 * En caso de que el contacto no exista crea un dialogo que indica que el contacto no existe en la aplicacion
	 */
	private void ControlUser(){
		
		Usuario usuario = new Usuario();
		usuario.set_nombre(user.getText().toString());
		usuario.set_contraseña(pass.getText().toString());

		boolean contacExist = SingletonDB.getInstance(this).getDatabaseHelper().UsuarioExiste(usuario);
		
		if(contacExist){
			guardarUsuarioPreference();
			intent = new Intent(this, MainActivity.class);
		}
		else
		{
			Dialog dialogo = null;
			dialogo = createAlertDialog();
			dialogo.show();
		}
		
		
	}
	
	/*
	 * Metodo que crea dialogo que indica que el contacto no existe
	 */
	private Dialog createAlertDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setIcon(R.drawable.image_notification)
				.setTitle(DIALOG_ERROR)
				.setPositiveButton(DIALOG_BTN, null)
				.setMessage(DIALOG_MSJ).create();
		return dialog;
	}
	
	
	
	
}
