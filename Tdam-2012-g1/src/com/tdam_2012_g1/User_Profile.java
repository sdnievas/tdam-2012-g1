package com.tdam_2012_g1;


import com.tdam_2012_g1.database.DatabaseHelper;
import com.tdam_2012_g1.entidades.Usuario;
import com.tdam_2012_g1.mensajesWeb.WebService;
import com.tdam_2012_g1.mensajesWeb.WebServiceInfo;
import com.tdam_2012_g1.suport.Notificacion;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class User_Profile extends Activity implements OnClickListener {
	
	
	private Button btn_Guardar;
	private Intent intent;
	private EditText user;
	private EditText password;
	private EditText repassword;
	private EditText mail;
	private Usuario usuario;
	
	private static final String LOGIN_SETTINGS = "LoginPreferences";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        final Animation escalar = AnimationUtils.loadAnimation(this, R.anim.escalar);
        
        btn_Guardar = (Button)findViewById(R.id.btnGuardar);         
        btn_Guardar.setOnClickListener(this);
        user = (EditText) findViewById(R.id.txtUsuario);
        password = (EditText) findViewById(R.id.txtUserProfilePassword);
        repassword = (EditText) findViewById(R.id.txtUserProfileRePassword);
        mail = (EditText) findViewById(R.id.txtEmail);
        cargarDatos();
        
        user.setOnFocusChangeListener(new OnFocusChangeListener(){
   		 public void onFocusChange(View v, boolean hasFocus){
   			 if(hasFocus){
   				 escalar.reset();
   				 user.startAnimation(escalar);
   			 }    			 
   		 }   		 
   	 	});
        
        password.setOnFocusChangeListener(new OnFocusChangeListener(){
   		 public void onFocusChange(View v, boolean hasFocus){
   			 if(hasFocus){
   				 escalar.reset();
   				 password.startAnimation(escalar);
   			 }    			 
   		 }   		 
        });    	
        
        repassword.setOnFocusChangeListener(new OnFocusChangeListener(){
   		 public void onFocusChange(View v, boolean hasFocus){
   			 if(hasFocus){
   				 escalar.reset();
   				repassword.startAnimation(escalar);
   			 }    			 
   		 }   		 
        });    	
        
        mail.setOnFocusChangeListener(new OnFocusChangeListener(){
   		 public void onFocusChange(View v, boolean hasFocus){
   			 if(hasFocus){
   				 escalar.reset();
   				mail.startAnimation(escalar);
   			 }    			 
   		 }   		 
        });    	
        
	}
	
	
	@Override
	public void onClick(View v) {
		
		
		Usuario Updateuser = new Usuario();
		Updateuser.set_id(usuario.get_id());
		Updateuser.set_nombre(user.getText().toString());
		Updateuser.set_contraseña(password.getText().toString());
		Updateuser.set_mail(mail.getText().toString());
		
		UpdateUserTask _initTask = new UpdateUserTask(Updateuser,this);
		 _initTask.execute();
		 
		 intent = new Intent(this,MainActivity.class);
		 startActivity(intent);

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
	
	public void cargarDatos(){
		
		SharedPreferences preferences = getSharedPreferences(LOGIN_SETTINGS,
				MODE_PRIVATE);
		user.setText(preferences.getString(USER, ""));
		password.setText(preferences.getString(PASSWORD, ""));
		repassword.setText(preferences.getString(PASSWORD, ""));
		buscarObjetoContacto();
	}
	
	public void buscarObjetoContacto(){
		
		DatabaseHelper dbhelper = getDatabaseHelper();
		usuario = dbhelper.buscarUsuarioporNombre(user.getText().toString());
		mail.setText(usuario.get_mail());
		dbhelper.close();
	}
	
	protected DatabaseHelper getDatabaseHelper(){	
		return new DatabaseHelper(this);
	}
	
	protected class UpdateUserTask extends AsyncTask<String, Integer, WebServiceInfo>
    {
    	
		Usuario user;
		Context context;

		public UpdateUserTask( Usuario user, Context context) {
			this.user = user;
			this.context = context;
		}
		
		@Override
		protected WebServiceInfo doInBackground(String... params) {
			WebServiceInfo result = null;

			try {
				WebService web = new  WebService(user.get_nombre() , user.get_contraseña());
				result = web.registerUser();
			} catch (Exception e) {
			}

			if (result != null && WebServiceInfo.SUCCESS == result.getCode()) {
				DatabaseHelper dbhelper = new DatabaseHelper(context);
				dbhelper.UpdateUser(user);
				dbhelper.close();
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(WebServiceInfo result) 
		{
			super.onPostExecute(result);
	
			Notificacion noti = new Notificacion(context,result,1);
			noti.notificionMensajes();
			if(result.getCode() == 1){
			SharedPreferences preferences = getSharedPreferences(LOGIN_SETTINGS,
					MODE_PRIVATE);
			SharedPreferences.Editor edit = preferences.edit();
			edit.clear();
			edit.putString(USER, user.get_nombre());
			edit.putString(PASSWORD, user.get_contraseña());
			edit.commit();}
		}

    } 

}
