package com.tdam_2012_g1;

import com.tdam_2012_g1.database.DatabaseHelper;
import com.tdam_2012_g1.entidades.Contacto;
import com.tdam_2012_g1.entidades.Usuario;
import com.tdam_2012_g1.messagesenderclient.RegistrarUsuarioHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class New_User extends Activity implements OnClickListener{

	Button boton;
	EditText nomuser;
	EditText password;
	EditText Repassword;
	EditText Email;
	
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        
         nomuser = (EditText) findViewById(R.id.new_NombUser);
         password = (EditText) findViewById(R.id.new_PassUser);
         Repassword = (EditText) findViewById(R.id.new_RePassUser);
         Email = (EditText) findViewById(R.id.new_Email);
         
         
    	 boton = (Button)	findViewById(R.id.new_btnNewUser);	
		 	 
    	 boton.setOnClickListener(this);
    	 

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
		
		DatabaseHelper dbhelper = new DatabaseHelper(this);
		Usuario user = new Usuario();
		user.set_nombre(nomuser.getText().toString());
		
		if(password.getText().toString().equals(Repassword.getText().toString()))
			user.set_contraseña(password.getText().toString());
		
		user.set_mail(Email.getText().toString());
		
		dbhelper.addUsuario(user);
		
		RegistrarUsuarioHandler reguser = new RegistrarUsuarioHandler();
		reguser.registrarUsuario(user);
		if(reguser.isSuccess())
			Log.d("todo bien con el user","vamos todavia");
		
		Intent intent = new Intent (this, Inicio.class);
		startActivity(intent);
	}
	
	
}
