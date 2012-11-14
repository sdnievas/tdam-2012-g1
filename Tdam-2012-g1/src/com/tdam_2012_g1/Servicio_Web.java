package com.tdam_2012_g1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.tdam_2012_g1.entidades.MensajeWeb;
import com.tdam_2012_g1.entidades.Usuario;
import com.tdam_2012_g1.messagesenderclient.EnviarMensajeHandler;
import com.tdam_2012_g1.messagesenderclient.RegistrarUsuarioHandler;

public class Servicio_Web extends Activity implements OnClickListener {
	Intent intent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicio_web);
		Button btnEnviar = (Button) findViewById(R.id.btnEnviar);
		btnEnviar.setOnClickListener(this);
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

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnEnviar:
			MensajeWeb msj = new MensajeWeb();
			EditText txtDestinatario = (EditText) findViewById(R.id.txtDestinatario);
			EditText txtMensaje = (EditText) findViewById(R.id.txtMensaje);
			msj.set_destinatario(txtDestinatario.getText().toString());
			msj.set_detalle(txtMensaje.getText().toString());
			Usuario usr = new Usuario();
			usr.set_nombre("Usuario de prueba");
			usr.set_contraseña("12345");
			EnviarMensajeHandler handler = new EnviarMensajeHandler();
			RegistrarUsuarioHandler handlerUsuario = new RegistrarUsuarioHandler();
			handlerUsuario.registrarUsuario(usr);
			String resultado = handler.enviarMensaje(msj, usr);
			break;
		}
	}
}
