package com.tdam_2012_g1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class User_Profile extends Activity implements OnClickListener {
	
	
	Button btn_Guardar;
	Intent intent;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        
        btn_Guardar = (Button)findViewById(R.id.btnGuardar);         
        btn_Guardar.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		
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

}
