package com.tdam_2012_g1;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Contacts extends ListActivity{ 

	Intent intent;

	ArrayList<HashMap<String,String>> Eventos;

	String[] from=new String[] {"Name","Cel"};
	int[] to=new int[]{R.id.txtvContactoNombre,R.id.txtvContactoPhone};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		ArrayList<String[]> lista = new ArrayList<String[]>();
		 
	    String[] evento1 = {"Jose Perez","", "1"};
	    lista.add(evento1);
	 
	    String[] evento2 = {"Roberto Lopez","", "2"};
	    lista.add(evento2);
	 
	    String[] evento3 = {"Ignacio Garcia","", "3"};
	    lista.add(evento3);
	 

	    Eventos = new ArrayList<HashMap<String, String>>();
	    for(String[] evento:lista){
	        HashMap<String,String> datosEvento=new HashMap<String, String>();
	 
	        datosEvento.put("Name", evento[0]);
	        datosEvento.put("Cel", evento[1]);
	        datosEvento.put("id", evento[2]);
	 
	        Eventos.add(datosEvento);
	    }

	    SimpleAdapter ListadoAdapter=new SimpleAdapter(this, Eventos, R.layout.item_contacts, from, to);
	    setListAdapter(ListadoAdapter);
	    
	}
    
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contacts, menu);
        return true;
    }
    
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.menu_settings:
		    intent = new Intent(this, Preference_user.class);	
			break;
		}
		startActivity(intent);
		return true;
	}
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
 
        intent = new Intent(this,DetalleContacto.class);
        //intent.putExtra("id",Eventos.get(position).get("id"));
        startActivity(intent);
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
    
    
    
   
