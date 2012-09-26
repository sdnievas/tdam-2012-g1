package com.tdam_2012_g1;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;
import java.util.Set;
import android.app.ListActivity;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class Historial extends ListActivity {
	
	Intent intent;
	
	ArrayList<HashMap<String,String>> Eventos;

	String[] from1=new String[] {"Image","Name","Hora"};
	int[] to1=new int[]{R.id.imagehistorialItem,R.id.textNombreHistorialItem,R.id.textHoraHistorialItem};
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.historial);
		
		ArrayList<String[]> lista = new ArrayList<String[]>();
		 
	    String[] evento1 = {"","Jose Perez","17:30", "1"};
	    lista.add(evento1);
	 
	    String[] evento2 = {"","Roberto Lopez","15:55", "2"};
	    lista.add(evento2);
	 
	    String[] evento3 = {"","Ignacio Garcia","9:00", "3"};
	    lista.add(evento3);
	 

	    Eventos = new ArrayList<HashMap<String, String>>();
	    for(String[] evento:lista){
	        HashMap<String,String> datosEvento=new HashMap<String, String>();
	 
	        datosEvento.put("Image", evento[0]);
	        datosEvento.put("Name", evento[1]);
	        datosEvento.put("Hora", evento[2]);
	        datosEvento.put("id", evento[3]);
	 
	        Eventos.add(datosEvento);
	    }

	    SimpleAdapter ListAdapter= new SimpleAdapter(this, Eventos, R.layout.historial_item, from1, to1);
	    setListAdapter(ListAdapter);

	}
    


	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_historial, menu);
        return true;
    }
	
	 public boolean onMenuItemSelected(int featureId, MenuItem item) {
			switch (item.getItemId()) {
			
			case R.id.historial_settings:
			    intent = new Intent(this, Preference_historial.class);	
				break;
			}
			startActivity(intent);
			return true;
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
