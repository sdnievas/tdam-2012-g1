package com.tdam_2012_g1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.tdam_2012_g1.database.DatabaseHelper;
import com.tdam_2012_g1.dom.Contacto;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class Contacts extends ListActivity implements OnItemClickListener {


	private ContactsAdapter adapter;
	private Contacto contactToShow;
	private String ordenarForma;
	private String FiltroContactos;
	private boolean conFotos;
	private Intent intent;
	private HashMap<String, Uri> imgUri;
	
	
  	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		AdapterAndList();

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
		this.setListAdapter(null);
		AdapterAndList();
	}
	
	
	private void AdapterAndList()
	{		
		adapter = new ContactsAdapter();
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

	private void loadListData() {

		String forma = "ASC";

		if (!ordenarForma.equals("0"))
			forma = "DESC";
		
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED " + forma;
		
		ContentResolver cr = getContentResolver();
		
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, sortOrder);

		Contacto contact = new Contacto();
		
		getImg();

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
				
				if(conFotos)
					contact.setImagen(imgUri.get(id));

				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					loadContactTelephoneNumbers(contact);	
				}
				
					loadContactEmails(contact);
					
					loadUserMsgWeb(contact);
					
				if(FiltroContactos.equals("0"))
					adapter.addContact(contact);
				
				if(FiltroContactos.equals("1") && !contact.getTelephoneNumbers().isEmpty())
					adapter.addContact(contact);
				
				if(FiltroContactos.equals("2") && !contact.getEmails().isEmpty())
					adapter.addContact(contact);
				
				if(FiltroContactos.equals("3") && contact.getuserWeb() != null)
					adapter.addContact(contact);
			}
		}
		
		cur.close();

		adapter.notifyDataSetChanged();

	}
	
	private void getPreferences(){
		
		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		ordenarForma = myPreference.getString(
				getString(R.string.preference_Contactos_Ordenamiento),"0");
		
		FiltroContactos = myPreference.getString(
				getString(R.string.preference_Contactos_FiltroUsuariokey),"0");
		
		conFotos = myPreference.getBoolean(
				getString(R.string.preference_Contactos_VerFotokey), false);
		
	}

	class Holder {
		private TextView txtName;
		private ImageView imgContact;
	}

	// Adapter de la lista de contactos
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
				convertView = inflater.inflate(R.layout.item_contacts, null);
				holder = new Holder();
				holder.txtName = (TextView) convertView
						.findViewById(R.id.txtvContactoNombre);
				holder.imgContact = (ImageView) convertView
						.findViewById(R.id.contactImage);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Contacto contact = (Contacto) getItem(position);
			
			InputStream input;
			Bitmap bm=null;
			
			try {
				if(contact.getImagen()!=null){
					input = inflater.getContext().getContentResolver().openInputStream(contact.getImagen());
					bm = BitmapFactory.decodeStream(input);
					input.close();
					holder.imgContact.setImageBitmap(bm);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			holder.txtName.setText(contact.getName());
			

			return convertView;
		}

	}

	private void loadUserMsgWeb(Contacto contact){
		
		DatabaseHelper dbhelper = getDatabaseHelper();
		String UserWeb = dbhelper.getNameContact(contact.getId());
		if(UserWeb != null){
			contact.setUserWeb(UserWeb);
		}
		dbhelper.close();
	}
	
	protected DatabaseHelper getDatabaseHelper(){
		return new DatabaseHelper(this);
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
			contact.addEmail(email);
		}
		emailCur.close();

	}
	
	
	private void getImg() {
		imgUri = new HashMap<String, Uri>();
		Cursor cur = getContentResolver()
				.query(ContactsContract.Data.CONTENT_URI,
						new String[] { ContactsContract.Data._ID,
								ContactsContract.Data.CONTACT_ID },
						ContactsContract.Data.MIMETYPE
								+ "='"
								+ ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
								+ "'", null, null);
		if(cur.moveToFirst()){
			do {
				imgUri.put(
						cur.getString(cur
								.getColumnIndex(ContactsContract.Data.CONTACT_ID)),
						Uri.withAppendedPath(
								ContentUris
										.withAppendedId(
												ContactsContract.Contacts.CONTENT_URI,
												Long.parseLong(cur.getString(cur
														.getColumnIndex(ContactsContract.Data.CONTACT_ID)))),
								ContactsContract.Contacts.Photo.CONTENT_DIRECTORY));
			} while (cur.moveToNext());
		}
		cur.close();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		contactToShow = (Contacto) adapter.getItem(position);
		contactToShow.setImagen(null);
		intent = new Intent(this, DetalleContacto.class);
		intent.putExtra("contacto", contactToShow);
		startActivity(intent);

	}

}
