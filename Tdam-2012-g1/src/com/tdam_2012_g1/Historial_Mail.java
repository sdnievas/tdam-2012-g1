package com.tdam_2012_g1;

import java.util.ArrayList;

import com.tdam_2012_g1.database.DatabaseHelper;
import com.tdam_2012_g1.dom.Contacto;
import com.tdam_2012_g1.entidades.Mail;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

public class Historial_Mail extends ListActivity implements OnItemClickListener {

	private HistoryMailAdapter adpatador;
	private Contacto contact;
	private String ordenarForma;
	private String FiltroContactos;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial__mail);
        
        Bundle extras = getIntent().getExtras();
	    if(extras != null)
	    	contact = (Contacto) extras.getSerializable("contacto"); //Cargamos el contacto que recibimos del intent de la activity contactos

	    adpatador = new HistoryMailAdapter();
	    
	    getListView().setAdapter(adpatador);

      	getListView().setOnItemClickListener(this);
      	
      	loadmailData();
      	
    }
    
    private void loadmailData(){
    	
    	getPreferences();
    	
		DatabaseHelper dbhelper = getDatabaseHelper();
		ArrayList<Mail> mails;
		if(contact != null){
			mails = dbhelper.getMailsContacto(contact);
		}
		else
			{mails = dbhelper.getTodosMails();}
		
		adpatador.addListHistorial(mails);
		dbhelper.close();
	}
    
    
    private void getPreferences(){
    	
    	SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		ordenarForma = myPreference.getString(
				getString(R.string.preference_Historial_Ordenarkey),"0");
		
		FiltroContactos = myPreference.getString(
				getString(R.string.preference_Historial_Filtrarkey),"0");
	}
    
    
    protected DatabaseHelper getDatabaseHelper(){	
		return new DatabaseHelper(this);
	}
    
    
    class HistoryMailAdapter extends BaseAdapter {
		
		private ArrayList<Mail> historial;
		private LayoutInflater inflater;

		public HistoryMailAdapter() {
			historial = new ArrayList<Mail>();
			inflater = LayoutInflater.from(Historial_Mail.this);
		}

		public void addHistorial(Mail historials) {
			if (historial != null) {
				historial.add(historials);
			}
		}
		
		public void addListHistorial(ArrayList<Mail> historials) {
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

			Mail history = (Mail) getItem(position);
			holder.txtNameHistorial.setText(history.get_mailDestinatario());
			holder.txtHora.setText(history.get_fechaEnvio().toString());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_historial, menu);
        return true;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
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
