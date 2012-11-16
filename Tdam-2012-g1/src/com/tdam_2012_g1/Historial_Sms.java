package com.tdam_2012_g1;

import java.sql.Date;
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

	
	private HistorySmsAdapter AdapertSms;
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
      
        AdapertSms = new HistorySmsAdapter();
        
        getListView().setAdapter(AdapertSms);

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
    	
    	String forma = "date asc";
    	
    	if(!ordenarForma.equals("0")){
    		forma = "date desc";
    	}
    	
		Uri smsURI = Uri.parse("content://sms/");
		String columns[] = new String[] { "address", "body", "date",
				"thread_id", "type"}; // type 1 = otro contacto, type 2 =
										// usuario
		
		Cursor cur = this.managedQuery(smsURI, columns, null,
				null, forma);

		ArrayList<HistorialSms> smsData = new ArrayList<HistorialSms>();
		HistorialSms sms = null;

		while (cur.moveToNext()) {
			
			sms = new HistorialSms();
			
			sms.setThreadId("thread_id");
			Date date = new Date(cur.getLong(cur.getColumnIndex("date")));
			sms.setNumero(cur.getString(cur.getColumnIndex("address")));
			sms.setMensaje(cur.getString(cur.getColumnIndex("body")));
			sms.setFecha(date);
			sms.setType(Integer.parseInt(cur.getString(cur
					.getColumnIndex("type"))));
			
			smsData.add(sms);
			AdapertSms.addHistorial(sms);
		}

		cur.close();

		AdapertSms.notifyDataSetChanged();
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
				Date date = new Date(cur.getLong(cur.getColumnIndex("date")));
				sms.setNumero(cur.getString(cur.getColumnIndex("address")));
				sms.setMensaje(cur.getString(cur.getColumnIndex("body")));
				sms.setFecha(date);
				sms.setType(Integer.parseInt(cur.getString(cur
						.getColumnIndex("type"))));
				
				AdapertSms.addHistorial(sms);
			}
			
	
			cur.close();
	
			AdapertSms.notifyDataSetChanged();
			i++;
		}
	}
    
    
    
    
	class HistorySmsAdapter extends BaseAdapter {
			
			private ArrayList<HistorialSms> historial;
			private LayoutInflater inflater;
	
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
					holder.txtNameHistorial = (TextView) convertView
							.findViewById(R.id.textNombreHistorialItem);
					holder.txtHora = (TextView) convertView
							.findViewById(R.id.textHoraHistorialItem);	
					holder.ImagenType = (ImageView) convertView
							.findViewById(R.id.imagehistorialItem);
					convertView.setTag(holder);
				} else {
					holder = (Holder) convertView.getTag();
				}
	
				HistorialSms history = (HistorialSms) getItem(position);
				holder.txtNameHistorial.setText(history.getNumero());
				holder.txtHora.setText(history.getFecha().toGMTString());
				holder.ImagenType.setImageResource(android.R.drawable.ic_dialog_email);
				return convertView;
			}
		
	}
	
	class Holder {
		private TextView txtNameHistorial;
		private TextView txtHora;
		private ImageView ImagenType;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		HistorialSms Sms = (HistorialSms) AdapertSms.getItem(position);
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
