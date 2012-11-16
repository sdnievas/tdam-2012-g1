package com.tdam_2012_g1;

import java.util.ArrayList;

import com.tdam_2012_g1.Contacts.ContactsAdapter;
import com.tdam_2012_g1.Contacts.Holder;
import com.tdam_2012_g1.database.DatabaseHelper;
import com.tdam_2012_g1.dom.Contacto;
import com.tdam_2012_g1.entidades.Conectividad;
import com.tdam_2012_g1.entidades.MensajeWeb;

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
	ConectivityAdapter adapter;


	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conectivity);
       
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
	

	class ConectivityAdapter extends BaseAdapter {
		private ArrayList<Conectividad> conexion;
		private LayoutInflater inflater;

		public ConectivityAdapter() {
			conexion = new ArrayList<Conectividad>();
			inflater = LayoutInflater.from(Conectivity.this);
		}

		public void addConectividad(Conectividad conect) {
			if (conect != null) {
				conexion.add(conect);
			}
		}
		
		public void addListConectividad(ArrayList<Conectividad> conect) {
			if (conect != null) {
				conexion = conect;
			}
		}

		@Override
		public int getCount() {
			return conexion.size();
		}

		@Override
		public Object getItem(int position) {
			return conexion.get(position);
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
						.inflate(R.layout.conectivity_item, null);
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
		
		DatabaseHelper dbhelper = new DatabaseHelper(this);
		ArrayList<Conectividad> connec = dbhelper.getAllConectividad();
		adapter.addListConectividad(connec);
		dbhelper.close();

	}
	

}
