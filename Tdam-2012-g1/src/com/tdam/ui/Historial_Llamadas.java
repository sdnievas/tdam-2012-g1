package com.tdam.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.tdam.Class.Contacto;
import com.tdam.Class.ContactoWeb;
import com.tdam.Class.HistorialLlamada;
import com.tdam_2012_g1.R;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.app.ListActivity;
import android.content.ContentResolver;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Historial_Llamadas extends ListActivity implements OnItemClickListener {

	
	private HistorialLLamadasAdapter adapter;
	private Contacto contact;
	private String ordenarForma;
	private String FiltroContactos;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial__llamadas);
        
        Bundle extras = getIntent().getExtras();
	    if(extras != null)
	    	//Cargamos el contacto que recibimos del intent de la activity contactos
	    	contact = (Contacto) extras.getSerializable("contacto"); 
        
        adapter = new HistorialLLamadasAdapter();
        
        getListView().setAdapter(adapter);

      	getListView().setOnItemClickListener(this);
      	
      	getPreferences();
      	
      	loadListData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_historial, menu);
        return true;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		HistorialLlamada llamada = (HistorialLlamada) adapter.getItem(position);
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + llamada.getNumero()));
		startActivity(callIntent);

	}
	
	
	private void loadListData(){
		
		adapter.limpiar();
		String forma = "date desc";
    	
    	if(!ordenarForma.equals("0")){
    		forma = "date asc";
    	}
		
		ContentResolver cr = getContentResolver();
		
		String[] colSelect = { CallLog.Calls._ID, CallLog.Calls.NUMBER,
				CallLog.Calls.TYPE, CallLog.Calls.CACHED_NAME,
				CallLog.Calls.CACHED_NUMBER_TYPE, CallLog.Calls.DATE,
				CallLog.Calls.DURATION, CallLog.Calls.TYPE };
		
		String projection = null;
		String[] values = null;
		
		if(contact != null){
			projection = CallLog.Calls.CACHED_NAME + " = ?";
			values = new String[] {contact.getName()};
		}
		
		Cursor cur = cr.query(CallLog.Calls.CONTENT_URI, colSelect,
				projection , values , forma);

		HistorialLlamada historial = null;

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String nomb = cur.getString(cur
						.getColumnIndex(CallLog.Calls.CACHED_NAME));
				String numero = cur.getString(cur
						.getColumnIndex(CallLog.Calls.NUMBER));
				Date hora = (new Date(cur.getLong(cur
								.getColumnIndex(CallLog.Calls.DATE))));
				historial = new HistorialLlamada();
				historial.setNombre(nomb);
				historial.setFecha(hora);
				historial.setNumero(numero);
				adapter.addHistorial(historial);
			}
		}
		cur.close();

		adapter.notifyDataSetChanged();

	}
	
	private void getPreferences(){
		
		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		ordenarForma = myPreference.getString(
				getString(R.string.preference_Historial_Ordenarkey),"0");
		
		FiltroContactos = myPreference.getString(
				getString(R.string.preference_Historial_Filtrarkey),"0");
				
	}
	
	class Holder {
		private TextView txtNombre;
		private TextView txtHora;
		private TextView txtFecha;
		private TextView txtNumero;
	}
	
	//Adapter de la lista de contactos
	class HistorialLLamadasAdapter extends BaseAdapter {
		
		private ArrayList<HistorialLlamada> historial;
		private LayoutInflater inflater;
		private SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
		
		public HistorialLLamadasAdapter() {
			historial = new ArrayList<HistorialLlamada>();
			inflater = LayoutInflater.from(Historial_Llamadas.this);
		}

		public void addHistorial(HistorialLlamada historials) {
			if (historial != null) {
				historial.add(historials);
			}
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
				holder.txtHora = (TextView) convertView
						.findViewById(R.id.txtArribaDerecha);
				holder.txtNumero = (TextView) convertView
						.findViewById(R.id.txtAbajoIzquierda);
				holder.txtFecha =(TextView) convertView
						.findViewById(R.id.txtAbajoDerecha);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			HistorialLlamada history = (HistorialLlamada) getItem(position);
			holder.txtNombre.setText(history.getNombre());
			holder.txtFecha.setText(history.getFecha().toString().trim());
			holder.txtHora.setText((formatoHora.format(history.getFecha()).trim()));
			holder.txtNumero.setText(history.getNumero().trim());
			return convertView;
		}
		
		public void limpiar(){
			if(historial != null){
				historial.clear();
			}
		}
		

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
	
	@Override
	protected void onResume() {
		super.onResume();
		this.setListAdapter(null);
        getListView().setAdapter(adapter);
      	getListView().setOnItemClickListener(this);      	
      	getPreferences();      	
      	loadListData();
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
