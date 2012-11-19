package com.tdam_2012_g1;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.tdam_2012_g1.dom.Contacto;
import com.tdam_2012_g1.dom.HistorialSms;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Historial_Sms extends ListActivity implements OnItemClickListener {

	
	private HistorySmsAdapter adapertSms;
	private Contacto contact;
	private String ordenarForma;
	private String FiltroContactos;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial__sms);
        
        Bundle extras = getIntent().getExtras();
        
        if(extras != null)
	    	contact = (Contacto) extras.getSerializable("contacto"); //Cargamos el contacto que recibimos del intent de la activity contactos
      
        adapertSms = new HistorySmsAdapter();
        
        getListView().setAdapter(adapertSms);

      	getListView().setOnItemClickListener(this);
      	
      	getPreferences();
      	
      	if(contact != null)
      		{loadConversationSms(contact);}
      	else{
      		loadConversationsSms();
      	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_historial, menu);
        return true;
    }
    
    
    private void getPreferences(){
		
    	SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		ordenarForma = myPreference.getString(
				getString(R.string.preference_Historial_Ordenarkey),"0");
		
		FiltroContactos = myPreference.getString(
				getString(R.string.preference_Historial_Filtrarkey),"0");
	}
    
    
    
    private void loadConversationsSms() {
    	adapertSms.limpiar();
    	String forma = "date asc";
    	
    	if(!ordenarForma.equals("0")){
    		forma = "date desc";
    	}
    	
		Uri smsURI = Uri.parse("content://sms/");
		String columns[] = new String[] { "address", "person", "date", "body","type", "thread_id"}; // type 1 = otro contacto, type 2 =
										// usuario
		
		Cursor cur = this.managedQuery(smsURI, columns, null,
				null, forma);

		ArrayList<HistorialSms> smsData = new ArrayList<HistorialSms>();
		HistorialSms sms = null;

		while (cur.moveToNext()) {
			
			sms = new HistorialSms();
			
			sms.setThreadId(cur.getString(cur.getColumnIndex("thread_id")));
			sms.setNombre(cur.getString(cur.getColumnIndex("person")));
			Date date = new Date(cur.getLong(cur.getColumnIndex("date")));
			sms.setNumero(cur.getString(cur.getColumnIndex("address")));
			sms.setMensaje(cur.getString(cur.getColumnIndex("body")));
			sms.setFecha(date);
			sms.setType(Integer.parseInt(cur.getString(cur
					.getColumnIndex("type"))));
			
			smsData.add(sms);
			adapertSms.addHistorial(sms);
		}

		cur.close();

		adapertSms.notifyDataSetChanged();
	}

	
	public void loadConversationSms(Contacto contacto) {
		
		Uri smsURI = Uri.parse("content://sms/");
		String columns[] = new String[] { "address", "body", "date",
				"thread_id", "type" , "person"}; // type 1 = otro contacto, type 2 =
										// usuario
		String clause = "address = ?";
		
		ArrayList<String> telefonos= contacto.getTelephoneNumbers();
		int i = 0;
		while(telefonos.size() > i){
			String selection[] = new String[] { telefonos.get(i)};
	
			Cursor cur = this.managedQuery(smsURI, columns, clause,
					selection, "date asc");
	
			HistorialSms sms = null;
	
			while (cur.moveToNext()) {
				sms = new HistorialSms();
				sms.setNombre(cur.getString(cur.getColumnIndex("person")));
				Date date = new Date(cur.getLong(cur.getColumnIndex("date")));
				sms.setNumero(cur.getString(cur.getColumnIndex("address")));
				sms.setMensaje(cur.getString(cur.getColumnIndex("body")));
				sms.setFecha(date);
				sms.setType(Integer.parseInt(cur.getString(cur
						.getColumnIndex("type"))));
				
				adapertSms.addHistorial(sms);
			}
			
	
			cur.close();
	
			adapertSms.notifyDataSetChanged();
			i++;
		}
	}
    
    
    
    
	class HistorySmsAdapter extends BaseAdapter {
			
			private ArrayList<HistorialSms> historial;
			private LayoutInflater inflater;
			private SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm");
			private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	
			public HistorySmsAdapter() {
				historial = new ArrayList<HistorialSms>();
				inflater = LayoutInflater.from(Historial_Sms.this);
			}
	
			public void addHistorial(HistorialSms historials) {
				if (historial != null) {
					historial.add(historials);
				}
			}
			
			public void addListHistorial(ArrayList<HistorialSms> historials) {
				historial = historials;
			}
	
			@Override
			public int getCount() {
				return historial.size();
			}
	
			@Override
			public Object getItem(int position) {
				return historial.get(position);
			}
	
			@Override
			public long getItemId(int position) {
				return position;
			}
	
			@Override
			public View getView(int position, View convertView, ViewGroup arg2) {
				Holder holder;
				if (convertView == null) {
					convertView = inflater
							.inflate(R.layout.historial_item, null);
					holder = new Holder();
					holder.txtNombre = (TextView) convertView
							.findViewById(R.id.txtArribaIzquierda);
					holder.txtFecha = (TextView) convertView
							.findViewById(R.id.txtArribaDerecha);
					holder.txtHora = (TextView) convertView
							.findViewById(R.id.txtAbajoDerecha);	
					holder.txtNumero =(TextView) convertView
							.findViewById(R.id.txtAbajoIzquierda);	
					holder.ImagenType = (ImageView) convertView
							.findViewById(R.id.imagehistorialItem);
					convertView.setTag(holder);
				} else {
					holder = (Holder) convertView.getTag();
				}
	
				HistorialSms history = (HistorialSms) getItem(position);
				holder.txtNombre.setText(history.getNumero());
				holder.txtFecha.setText(formatoFecha.format(history.getFecha()));
				holder.txtHora.setText(formatoHora.format(history.getFecha()));
				holder.txtNumero.setText(history.getMensaje());
				holder.ImagenType.setImageResource(android.R.drawable.ic_dialog_email);
				return convertView;
			}
			
			public void limpiar(){
				if(historial != null){
					historial.clear();
				}
			}
		
	}
	
	class Holder {
		private TextView txtNombre;
		private TextView txtHora;
		private ImageView ImagenType;
		private TextView txtFecha;
		private TextView txtNumero;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		HistorialSms Sms = (HistorialSms) adapertSms.getItem(position);
    	Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setType("vnd.android-dir/mms-sms");
		intent.setData(Uri.parse("smsto:" + Sms.getNumero()));
		startActivity(intent);
		
	}
	
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		
		case R.id.historial_settings:
			intent= new Intent(this, Preference_historial.class);	
			break;
		}
		startActivity(intent);
		return true;
	}
}
