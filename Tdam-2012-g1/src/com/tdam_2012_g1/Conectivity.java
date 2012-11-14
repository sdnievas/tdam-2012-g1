package com.tdam_2012_g1;

import java.util.ArrayList;

import com.tdam_2012_g1.Contacts.ContactsAdapter;
import com.tdam_2012_g1.Contacts.Holder;
import com.tdam_2012_g1.dom.Contacto;
import com.tdam_2012_g1.entidades.Conectividad;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Conectivity extends ListActivity implements OnItemClickListener {
	
	
	Intent intent;
	CheckBox checkBoxWifi;
	CheckBox checkBox3G;	
	ConectivityAdapter adapter;


	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conectivity);
        

    	checkBoxWifi = (CheckBox) findViewById(R.id.chkWifi);
        checkBoxWifi.setChecked(true);
    	checkBox3G = (CheckBox) findViewById(R.id.chk3G);
        checkBox3G.setChecked(false);    
        
        
        adapter = new ConectivityAdapter();

		getListView().setAdapter(adapter);

		getListView().setOnItemClickListener(this);
		
		loadListData();
		
		
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




	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	
	class Holder {
		private TextView txtName;
		private TextView Fecha;
	}
	
	//Adapter de la lista de contactos
	class ConectivityAdapter extends BaseAdapter {
		private ArrayList<Conectividad> contacts;
		private LayoutInflater inflater;

		public ConectivityAdapter() {
			contacts = new ArrayList<Conectividad>();
			inflater = LayoutInflater.from(Conectivity.this);
		}

		public void addContact(Conectividad conect) {
			if (conect != null) {
				contacts.add(conect);
			}
		}

		@Override
		public int getCount() {
			return contacts.size();
		}

		@Override
		public Object getItem(int position) {
			return contacts.get(position);
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
						.inflate(R.layout.item_contacts, null);
				holder = new Holder();
				holder.txtName = (TextView) convertView
						.findViewById(R.id.txt_Item_vConectivityA);
				holder.Fecha = (TextView) convertView
						.findViewById(R.id.txt_Item_vConectivityB);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Conectividad conectivity = (Conectividad) getItem(position);
			holder.txtName.setText(conectivity.get_conexion());
			holder.Fecha.setText(conectivity.get_estado());

			return convertView;
		}
	}
	
	
	public void loadListData(){
		
		
		
		
		
		
	}
	

}
