package com.tdam_2012_g1;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


import com.tdam_2012_g1.dom.Contacto;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.app.Activity;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Contacts extends ListActivity implements OnItemClickListener { 

	
	private static final int DIALOG_CONTACT_INFO = 0;
	private ContactsAdapter adapter;
	private Contacto contactToShow;
	Intent intent;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
				
		adapter = new ContactsAdapter();

		getListView().setAdapter(adapter);

		getListView().setOnItemClickListener(this);
		
		loadListData();
	    
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

	
	private void loadListData() {

		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);
		
		String ordenarForma = myPreference.getString(
		getString(R.string.preference_Contactos_Ordenamiento), "0");
		
		
		
		ContentResolver cr = getContentResolver();
		
		
		
		
		
		
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		Contacto contact = null;

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				contact = new Contacto();
				contact.setName(name);
				contact.setId(id);

				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					loadContactTelephoneNumbers(contact);
					loadContactEmails(contact);
				}

				adapter.addContact(contact);
			}
		}
		cur.close();

		adapter.notifyDataSetChanged();

	}
    
	class Holder {
		private TextView txtName;
	}
	
	//Adapter de la lista de contactos
	class ContactsAdapter extends BaseAdapter {
		private ArrayList<Contacto> contacts;
		private LayoutInflater inflater;

		public ContactsAdapter() {
			contacts = new ArrayList<Contacto>();
			inflater = LayoutInflater.from(Contacts.this);
		}

		public void addContact(Contacto contact) {
			if (contact != null) {
				contacts.add(contact);
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
						.findViewById(R.id.txtvContactoNombre);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Contacto contact = (Contacto) getItem(position);
			holder.txtName.setText(contact.toString());

			return convertView;
		}
		
		
		

	}
	
	private void loadContactTelephoneNumbers(Contacto contact) {
		
		Cursor pCur = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
				new String[] { contact.getId() }, null);
		while (pCur.moveToNext()) {
			contact.addTelephoneNumber(pCur.getString(pCur
					.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		}
		pCur.close();

	}
	
	private void loadContactEmails(Contacto contact) {
		Cursor emailCur = getContentResolver().query(
				ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
				new String[] { contact.getId() }, null);
		while (emailCur.moveToNext()) {
			// This would allow you get several email addresses
			// if the email addresses were stored in an array
			String email = emailCur
					.getString(emailCur
							.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
			String emailType = emailCur
					.getString(emailCur
							.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
			contact.addEmail(email);
		}
		emailCur.close();

	}




	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		contactToShow = (Contacto) adapter.getItem(position);
		intent = new Intent(this,DetalleContacto.class);
		intent.putExtra("contacto",contactToShow);
		
        startActivity(intent);
		
	}
	
	/*
	
	private Cursor getContacts() {

		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		boolean filtrarTel = myPreference.getBoolean(
				getString(R.string.preferenceContactosFTel), false);
		
		boolean filtrarMail = myPreference.getBoolean(
				getString(R.string.preferenceContactosFM), false);

		String ordenarForma = myPreference.getString(
				getString(R.string.preferenceContactosOForma), "0");

		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME };
		StringBuilder selection = new StringBuilder();
		String[] selectionArgs = null;
		String forma = "ASC";

		String AND = "";
		int selectionSize = selection.length();

		if (!ordenarForma.equals("0"))
			forma = "DESC";
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED " + forma;

		if (consulta != null) {
			selection
					.append(ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?");
			selectionArgs = new String[] { "%" + consulta + "%" };
		}
		if (filtrarTel) {
			selection.append(AND + ContactsContract.Contacts.HAS_PHONE_NUMBER
					+ " = 1");
			AND = " AND ";
		}
		if (filtrarMail)
			filtrarMail(selection, AND, selectionSize);

		if (!filtrarTipo.equals("0")) {
			if (filtrarTipo.equals("1"))
				filtrarTipoTelefono(selection, AND, selectionSize);
			else
				filtrarTipoCuenta(selection, AND, selectionSize, filtrarTipo);
		}

		cursor = null;
		cursor = getContentResolver().query(uri, projection,
				selection.toString(), selectionArgs, sortOrder);
		startManagingCursor(cursor);

		consulta = null;
		return cursor;
	}

	private void filtrarMail(StringBuilder selection, String AND,
			int selectionSize) {
		Cursor emailCur = getContentResolver().query(
				ContactsContract.CommonDataKinds.Email.CONTENT_URI,
				new String[] { ContactsContract.CommonDataKinds.Email._ID,
						ContactsContract.CommonDataKinds.Email.CONTACT_ID },
				null, null, null);

		ArrayList<String> id = new ArrayList<String>();
		emailCur.moveToFirst();
		String contactId;
		while (emailCur.moveToNext()) {
			contactId = emailCur
					.getString(emailCur
							.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID));
			if (contactId != null)
				id.add(contactId);
		}

		selection.append(AND + ContactsContract.Contacts._ID + " IN (");
		for (String contactIdItem : id) {
			selection.append(contactIdItem + ",");
		}
		selectionSize = selection.length();
		selection.replace(selectionSize - 1, selectionSize, ")");
		AND = " AND ";
	}

	*/

}
    
    
    
   
