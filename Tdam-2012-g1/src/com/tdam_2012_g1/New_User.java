package com.tdam_2012_g1;


import com.tdam_2012_g1.database.DatabaseHelper;
import com.tdam_2012_g1.entidades.Usuario;
import com.tdam_2012_g1.mensajesWeb.WebServiceInfo;
import com.tdam_2012_g1.mensajesWeb.WebService;
import com.tdam_2012_g1.suport.ConexionInfo;
import com.tdam_2012_g1.suport.Notificacion;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;


public class New_User extends Activity implements OnClickListener{

	private Button boton;
	private EditText nomuser;
	private EditText password;
	private EditText Repassword;
	private EditText Email;
	
	private static final String DIALOG_ERROR = "Error";
	private static final String DIALOG_MSJ = "No hay conexion a ocurrido un error";
	private static final String DIALOG_BTN = "Aceptar";
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        
   	 	 final Animation escalar = AnimationUtils.loadAnimation(this, R.anim.escalar);
         nomuser = (EditText) findViewById(R.id.new_NombUser);
         password = (EditText) findViewById(R.id.new_PassUser);
         Repassword = (EditText) findViewById(R.id.new_RePassUser);
         Email = (EditText) findViewById(R.id.new_Email);
         
         
    	 boton = (Button)	findViewById(R.id.new_btnNewUser);			 	 
    	 boton.setOnClickListener(this);
    	 
    	 nomuser.setOnFocusChangeListener(new OnFocusChangeListener(){
    		 public void onFocusChange(View v, boolean hasFocus){
    			 if(hasFocus){
    				 escalar.reset();
    				 nomuser.startAnimation(escalar);
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
    	 Repassword.setOnFocusChangeListener(new OnFocusChangeListener(){
    		 public void onFocusChange(View v, boolean hasFocus){
    			 if(hasFocus){
    				 escalar.reset();
    				 Repassword.startAnimation(escalar);
    			 }    			 
    		 }   		 
    	 });    	 
    	 Email.setOnFocusChangeListener(new OnFocusChangeListener(){
    		 public void onFocusChange(View v, boolean hasFocus){
    			 if(hasFocus){
    				 escalar.reset();
    				 Email.startAnimation(escalar);
    			 }    			 
    		 }   		 
    	 });
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
		
		
		Usuario user = new Usuario();
		user.set_nombre(nomuser.getText().toString());
		
		if(password.getText().toString().equals(Repassword.getText().toString()))
			user.set_contraseña(password.getText().toString());
		
		user.set_mail(Email.getText().toString());
		
		ConexionInfo coninf = new ConexionInfo(this);
		boolean hayconexionInternet = coninf.isInternetConnectionAvailable();
		
		if(hayconexionInternet){
			RegisterUserTask _initTask = new RegisterUserTask(user,this);
			_initTask.execute();
		 }
		else{
			Dialog dialogo = null;
			dialogo = createAlertDialog();
			dialogo.show();
			
		}
		

		Intent intent = new Intent (this, Inicio.class);
		startActivity(intent);
	}
	

	private Dialog createAlertDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setIcon(R.drawable.image_notification)
				.setTitle(DIALOG_ERROR)
				.setPositiveButton(DIALOG_BTN, null)
				.setMessage(DIALOG_MSJ).create();
		return dialog;
	}
	
	
    protected class RegisterUserTask extends AsyncTask<String, Integer, WebServiceInfo>
    {
    	
		Usuario user;
		Context context;

		public RegisterUserTask( Usuario user, Context context) {
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
				dbhelper.addUsuario(user);
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
		}

    } 
	
}
