package com.tdam_2012_g1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Nuevo_Usuario  extends Activity implements OnClickListener {

	Button btn_RegistrarUsuario;
	Intent intent;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_usuario);
        
        btn_RegistrarUsuario = (Button)findViewById(R.id.btnRegistrarUsuario); 
        
        btn_RegistrarUsuario.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
	}
}
