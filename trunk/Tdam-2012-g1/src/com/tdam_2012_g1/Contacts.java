package com.tdam_2012_g1;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Contacts extends ListActivity{ 

	

	ArrayList<HashMap<String,String>> Eventos;

	String[] from=new String[] {"Image","Name","Cel"};
	int[] to=new int[]{R.id.imgvContactoImg,R.id.txtvContactoNombre,R.id.txtvContactoPhone};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		ArrayList<String[]> lista = new ArrayList<String[]>();
		 
	    String[] evento1 = {"p","Jose Perez","156974152", "1"};
	    lista.add(evento1);
	 
	    String[] evento2 = {"k","Roberto Lopez","157894625", "2"};
	    lista.add(evento2);
	 
	    String[] evento3 = {"h","Ignacio Garcia","153894514", "3"};
	    lista.add(evento3);
	 

	    Eventos = new ArrayList<HashMap<String, String>>();
	    for(String[] evento:lista){
	        HashMap<String,String> datosEvento=new HashMap<String, String>();
	 
	        datosEvento.put("Img", evento[0]);
	        datosEvento.put("Name", evento[1]);
	        datosEvento.put("Cel", evento[2]);
	        datosEvento.put("id", evento[3]);
	 
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
    
    
    
}
    
    
    
   
