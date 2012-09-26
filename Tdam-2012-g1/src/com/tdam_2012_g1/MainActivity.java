package com.tdam_2012_g1;




import android.view.View.OnClickListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener{

	Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageButton contactos = (ImageButton)findViewById(R.id.imgbtn_contactos);
        
        contactos.setOnClickListener(this);
                
    }
    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgbtn_contactos:
			intent = new Intent().setClass(this, Contacts.class);
			startActivity(intent);
			break;
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_profile:
			Intent intent = new Intent(this, Settings_activity.class);
			startActivity(intent);
			break;
		}
		return true;
	}
    
    
}

	



