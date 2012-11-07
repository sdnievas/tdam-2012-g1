package com.tdam_2012_g1;



import java.util.ArrayList;
import com.tdam_2012_g1.dom.Contacto;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


public class DetalleContacto extends Activity implements OnClickListener{
	Intent intent;

	ListView lvCel ,lvMail, lvMsgWeb ;
	/*String[] Cel = {"+543513955647"};
	String[] Mail = {"jose.perez@gmail.com"};*/
	String[] MsgWeb = {"jose.Perez"};
	Contacto contact;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);
        
        Bundle extras = getIntent().getExtras();
        contact = (Contacto) extras.getSerializable("contacto"); //Cargamos el contacto que recibimos del intent de la activity contactos
        
        Button historial = (Button)findViewById(R.id.btnHistorial_DetalleContacto);

        historial.setOnClickListener(this);
        
        TextView nContacto = (TextView)findViewById(R.id.txtNombre);
        
        nContacto.setText(contact.getName()); //carga nombre de contacto al text de la pantalla
        
        lvCel = (ListView)findViewById(R.id.listNumeros);
        lvMail = (ListView)findViewById(R.id.listEmail);
        lvMsgWeb = (ListView)findViewById(R.id.listMensajeriaWeb);
        
        // Se cargan los adapter con arrays de Strigs que son de los telefonos, mails y nombres de contactos web del contacto seleccionado
        lvCel.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contact.getTelephoneNumbers()));
        lvMail.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contact.getEmails()));
        lvMsgWeb.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,MsgWeb));
        
        lvMsgWeb.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            	
            	method(); //Llama al metodo cuando se seleccion opcion mandar mensaje web
             
            }

          });
        
        lvCel.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            	 
            	dialog(position); // llama al metodo cuadno se quiere realizar accion del tipo llamada o mensaje de texto
            	
            }

          });
        
        lvMail.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {   
            	
            	Mailto(position); // llama al metodo cuando se quiere enviar un email   
            	
            }

          });
        
        
        
    }
    
    public void method(){
    	
    	 intent = new Intent(this, Servicio_Web.class);
    	 startActivity(intent);
    }
    
    //dialog de eleccion para mensajes de texto o para llamadas 
    
    public void dialog(int position){
    	
    	//final CharSequence[] items = {R.string.dc_llamar,R.string.dc_enviar_mensaje};
    	final CharSequence[] items = {"Llamar","Enviar Mensaje"};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(R.string.dc_eleccion);
    	final int posicion = position;
    	builder.setItems(items, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	    	 switch (item) {
    	    	 case 0: //Llamar
    	    		 Mainact(posicion); // Llama al metodo para Realizar Llamadas
    	    		 break;
    	    	 case 1: // Enviar Mensajes
    	    		 			//Llama al metodo para enviar mensajes
    	    		 break;
    	         }   	    	
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();

   }
    
    //En caso de mandar mail llama al Intent que envia los mails
    public void  Mailto(int position){
    	
    	ArrayList<String> mails = contact.getEmails();	
    	Uri uri = Uri.parse("mailto:" + mails.get(position));
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		startActivity(it);
    }
    
    /* En caso de Llamada envia un intent al Action Call con el numero de Telefono*/
    public void Mainact(int position){
    	
	    ArrayList<String> telephone = contact.getTelephoneNumbers();	
	    Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + telephone.get(position)));
		startActivity(callIntent);
	
   	 }
  
    //Intent del boton Historial que llama al historial del contacto en particular
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
