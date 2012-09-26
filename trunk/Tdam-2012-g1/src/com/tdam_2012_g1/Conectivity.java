package com.tdam_2012_g1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class Conectivity extends Activity implements OnClickListener {
	
	
	Button btn_Volver;
	Intent intent;
	CheckBox checkBoxWifi;
	CheckBox checkBox3G;	


	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conectivity);
        
        btn_Volver = (Button)findViewById(R.id.btnVolver);         
        btn_Volver.setOnClickListener(this);
    	checkBoxWifi = (CheckBox) findViewById(R.id.chkWifi);
        checkBoxWifi.setChecked(true);
    	checkBox3G = (CheckBox) findViewById(R.id.chk3G);
        checkBox3G.setChecked(false);    
	}
	
	@Override
	public void onClick(View v) {
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

}
