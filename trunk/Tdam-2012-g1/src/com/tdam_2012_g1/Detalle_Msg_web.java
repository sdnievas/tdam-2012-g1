package com.tdam_2012_g1;

import com.tdam_2012_g1.dom.Contacto;
import com.tdam_2012_g1.entidades.MensajeWeb;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Detalle_Msg_web extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__msg_web);
        TextView id = (TextView) findViewById(R.id.txt_detallemsgweb_id);
        TextView remitente = (TextView) findViewById(R.id.txt_detallemsgweb_remitente);
        TextView destinatario = (TextView) findViewById(R.id.txt_detallemsgweb_destinatario);
        TextView detalle = (TextView) findViewById(R.id.txt_detallemsgweb_detalle);
        TextView fecha = (TextView) findViewById(R.id.txt_detallemsgweb_fecha);
        
        Bundle extras = getIntent().getExtras();
        MensajeWeb WebSms = (MensajeWeb) extras.getSerializable("MensajeWeb"); //Cargamos el contacto que recibimos del intent de la activity contactos
        
        id.setText(R.string.detalle_mensajeWeb_Id);
        remitente.setText(R.string.detalle_mensajeWeb_remitente + "  " +  WebSms.get_remitente());
        destinatario.setText(R.string.detalle_mensajeWeb_destinatario + "  " + WebSms.get_destinatario());
        detalle.setText(R.string.detalle_mensajeWeb_detalle + "  " + WebSms.get_detalle());
        fecha.setText(R.string.detalle_mensajeWeb_fecha + "  " + WebSms.get_fechaEnvio());
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detalle__msg_web, menu);
        return true;
    }
}
