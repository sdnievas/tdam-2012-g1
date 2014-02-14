package com.tdam.ui;

import java.util.ArrayList;

import com.tdam.Class.Conectividad;
import com.tdam.Database.SingletonDB;
import com.tdam_2012_g1.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Conectivity extends ListActivity implements OnItemClickListener {
	
	
	
	private Intent intent;
	
	private ConectivityAdapter adapter;
	
	
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
		private TextView Estado;
		private TextView Date;
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
				holder.Estado = (TextView) convertView
						.findViewById(R.id.txt_Item_vConectivityB);
				holder.Date = (TextView) convertView
						.findViewById(R.id.txt_Item_vConectivityDate);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Conectividad conectivity = (Conectividad) getItem(position);
			holder.txtName.setText(conectivity.get_conexion());
			holder.Estado.setText(conectivity.get_estado());
			holder.Date.setText(conectivity.get_fecha());
			return convertView;
		}
	}
	
	
	public void loadListData(){
		ArrayList<Conectividad> connec = SingletonDB.getInstance(getApplicationContext()).getDatabaseHelper().getAllConectividad();
		adapter.addListConectividad(connec);
	}
	

}
