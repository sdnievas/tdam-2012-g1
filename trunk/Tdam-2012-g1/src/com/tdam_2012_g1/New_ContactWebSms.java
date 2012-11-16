package com.tdam_2012_g1;

import com.tdam_2012_g1.database.DatabaseHelper;
import com.tdam_2012_g1.dom.Contacto;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class New_ContactWebSms extends Activity implements OnClickListener {

	private Contacto contact;
	private TextView nContactoWeb ;
	private Button boton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__contact_web_sms);
        
        Bundle extras = getIntent().getExtras();
        contact = (Contacto) extras.getSerializable("contacto"); //Cargamos el contacto que recibimos del intent de la activity contactos
        
        nContactoWeb = (TextView)findViewById(R.id.newWebUser_NombUser);
        boton =(Button) findViewById(R.id.newWebUser_btnNewUser);
        
        boton.setOnClickListener(this);
        
        
               
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.activity_new__contact_web_sms, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		
		 DatabaseHelper dbhelper = new DatabaseHelper(this);
		com.tdam_2012_g1.entidades.Contacto contactoweb = 
				new com.tdam_2012_g1.entidades.Contacto(Integer.parseInt(contact.getId()), nContactoWeb.getText().toString());
		dbhelper.addContacto(contactoweb);
		contact.setUserWeb(contactoweb.get_nombreWeb());
		Intent intent = new Intent(this , DetalleContacto.class);
		intent.putExtra("contacto",contact);
		startActivity(intent);
	}
}
