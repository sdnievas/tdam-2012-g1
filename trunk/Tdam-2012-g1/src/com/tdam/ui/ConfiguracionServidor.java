package com.tdam.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdam_2012_g1.R;

public class ConfiguracionServidor extends Activity implements OnClickListener {
	private static final String SERVER_SETTINGS = "ServerPreferences";
	private SharedPreferences preferencias;
	private SharedPreferences.Editor editor;
	private EditText txtIP;
	private EditText txtPuerto;
	private Button btnAceptar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion_servidor);
		txtIP = (EditText) findViewById(R.id.txtIP);
		txtPuerto= (EditText) findViewById(R.id.txtPuerto);
		btnAceptar = (Button) findViewById(R.id.configuracion_servidor_btnAceptar);
		preferencias = getSharedPreferences(SERVER_SETTINGS, MODE_PRIVATE);
		txtIP.setText(preferencias.getString("ip_servidor",
				"192.168.0.1"));
		txtPuerto.setText(preferencias.getString("puerto_servidor",
				"8080"));
		btnAceptar.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.configuracion_servidor_btnAceptar:
			editor = preferencias.edit();
			editor.putString("ip_servidor", txtIP.getText().toString());
			editor.putString("puerto_servidor", txtPuerto.getText().toString());
			editor.commit();
			Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, Inicio.class);
			//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			
		}

	}

}
