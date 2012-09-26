package com.tdam_2012_g1;





import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Login_Main extends Activity implements OnClickListener{

	Button btn_Iniciar;
	ImageView nuevoUsuario;
	Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        
        nuevoUsuario = (ImageView)findViewById(R.id.imageVNuevoUsuario);        
        nuevoUsuario.setOnClickListener(this);
        
     // btn_Iniciar = (Button)findViewById(R.id.btnIniciar); 
      //btn_Iniciar.setOnClickListener(this);
    }
    
	@Override
	public void onClick(View arg0) {
		 intent= new Intent().setClass(Login_Main.this, Nuevo_Usuario.class);
			startActivity(intent);

	}   
}
