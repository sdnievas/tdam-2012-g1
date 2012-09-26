package com.tdam_2012_g1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

public class DetalleContacto extends Activity implements OnClickListener{
	Intent intent;

	ListView lvCel ,lvMail, lvMsgWeb ;
	String[] Cel = {"+543513955647"};
	String[] Mail = {"jose.perez@gmail.com"};
	String[] MsgWeb = {"jose.Perez"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);
        
        Button historial = (Button)findViewById(R.id.btnHistorial_DetalleContacto);
        
        historial.setOnClickListener(this);
        
        lvCel = (ListView)findViewById(R.id.listNumeros);
        lvMail = (ListView)findViewById(R.id.listEmail);
        lvMsgWeb = (ListView)findViewById(R.id.listMensajeriaWeb);
        
        lvCel.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Cel));
        lvMail.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Mail));
        lvMsgWeb.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,MsgWeb));
        
        lvMsgWeb.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
             method();
            }

          });
        
    }
    
    public void method(){
    	
    	 intent = new Intent(this, Servicio_Web.class);
    	 startActivity(intent);
    }

    public void onClick(View v) {
    	intent = new Intent().setClass(this, Historial.class);
		startActivity(intent);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_detalle_contacto, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
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
