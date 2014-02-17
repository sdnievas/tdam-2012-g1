package com.tdam.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.tdam.Suport.*;
import com.tdam.Bluetooth.BluetoothChat;
import com.tdam.Class.Contacto;
import com.tdam.Class.ContactoBluetooth;
import com.tdam.Class.ContactoWeb;
import com.tdam.Database.SingletonDB;
import com.tdam_2012_g1.R;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Contactos extends ListActivity implements OnItemClickListener,
		TextWatcher {

	private Context mContext = this;

	private ContactsAdapter adapter;
	private Contacto contactToShow;
	private static final int CALL = 1;
	private static final int SMS = 2;
	private static final int MAIL = 3;

	/*
	 * Variables de preferences de contactos
	 */
	private String ordenarForma;
	private String FiltroContactos;
	private boolean conFotos;

	private static final String CONTACT = "contacto";
	private Intent intent;
	private HashMap<String, Uri> imgUri;
	private EditText txtContactSearch;

	// endregion

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_contacts);

		txtContactSearch = (EditText) findViewById(R.id.txtBuscarContactos);

		txtContactSearch.addTextChangedListener(this);

		AdapterAndList("");

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (adapter != null) {
			AdapterAndList(txtContactSearch.getText().toString());
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private void AdapterAndList(String parName) {
		adapter = new ContactsAdapter();
		getListView().setAdapter(adapter);
		getListView().setOnItemClickListener(this);
		getPreferences();
		loadListData(parName);
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

	private void loadListData(String parName) {

		String forma = "ASC";

		if (!ordenarForma.equals("0"))
			forma = "DESC";

		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED " + forma;

		String selection[] = { parName + "%" };

		String where = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED " + " like ? ";

		// Consulta
		ContentResolver cr = getContentResolver();

		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				where, selection, sortOrder);

		Contacto contact;

		// Buscar Imagenes
		getImg();

		// recorre la consulta y llena la lista segun las preferences
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

				if (conFotos)
					contact.setImagen(imgUri.get(id));

				// if (Integer
				// .parseInt(cur.getString(cur
				// .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))
				// > 0) {
				// loadContactTelephoneNumbers(contact);
				// }

				// loadContactEmails(contact);
				//
				// loadUserMsgWeb(contact);
				//
				// loadUserContactBluetooth(contact);
				//
				// loadContactAddress(contact);

				if (FiltroContactos.equals("0"))
					adapter.addContact(contact);

				if (FiltroContactos.equals("1")) {
					if (Integer
							.parseInt(cur.getString(cur
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						adapter.addContact(contact);
					}
				}

				if (FiltroContactos.equals("2")) {
					loadContactEmails(contact);

					if (!contact.getEmails().isEmpty())
						adapter.addContact(contact);
				}
				if (FiltroContactos.equals("3")) {
					loadUserMsgWeb(contact);
					if (contact.getuserWeb() != null) {
						adapter.addContact(contact);
					}
				}

			}
		}

		cur.close();

		adapter.notifyDataSetChanged();

	}

	private void getPreferences() {

		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		ordenarForma = myPreference.getString(
				getString(R.string.preference_Contactos_Ordenamiento), "0");

		FiltroContactos = myPreference.getString(
				getString(R.string.preference_Contactos_FiltroUsuariokey), "0");

		conFotos = myPreference.getBoolean(
				getString(R.string.preference_Contactos_VerFotokey), true);

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
			inflater = LayoutInflater.from(Contactos.this);
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
			Bitmap bm = null;

			try {
				if (contact.getImagen() != null) {
					input = inflater.getContext().getContentResolver()
							.openInputStream(contact.getImagen());
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

	private void loadUserMsgWeb(Contacto contact) {

		ContactoWeb UserWeb = SingletonDB.getInstance(getApplicationContext())
				.getDatabaseHelper().getNameContact(contact.getId());
		if (UserWeb != null) {
			contact.setUserWeb(UserWeb.get_nombreWeb());
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
			contact.addEmail(email);
		}
		emailCur.close();

	}

	private void loadContactAddress(Contacto contact) {
		Cursor addresCur = getContentResolver().query(
				ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
				null,
				ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID
						+ " = ?", new String[] { contact.getId() }, null);
		while (addresCur.moveToNext()) {
			// This would allow you get several email addresses
			// if the email addresses were stored in an array
			String address = addresCur
					.getString(addresCur
							.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DATA));
			contact.addDireccion(address);
		}
		addresCur.close();

	}

	private void loadUserContactBluetooth(Contacto contact) {
		ContactoBluetooth contblue = SingletonDB.getInstance(mContext)
				.getDatabaseHelper().getNameContactBluetooth(contact.getId());
		if (contblue != null) {
			contact.setMACBluetooth(contblue.get_Mac());
			contact.setNomUserBluetooth(contblue.get_nombreBluetooth());
		}

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
		if (cur.moveToFirst()) {
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
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

		final int posicion = position;
		contactToShow = (Contacto) adapter.getItem(posicion);
		loadContactTelephoneNumbers(contactToShow);
		loadContactEmails(contactToShow);

		// loadUserMsgWeb(contactToShow);

		// loadUserContactBluetooth(contactToShow);

		loadContactAddress(contactToShow);

		// array to hold the coordinates of the clicked view
		int[] xy = new int[2];
		// fills the array with the computed coordinates
		v.getLocationInWindow(xy);
		// rectangle holding the clicked view area
		Rect rect = new Rect(xy[0], xy[1], xy[0] + v.getWidth(), xy[1]
				+ v.getHeight());

		// a new QuickActionWindow object
		final QuickActionWindow qa = new QuickActionWindow(mContext, v, rect);

		// adds an item to the badge and defines the quick action to be
		// triggered
		// when the item is clicked on
		qa.addItem(getResources()
				.getDrawable(android.R.drawable.ic_menu_agenda), "Detalle",
				new OnClickListener() {
					public void onClick(View v) {
						contactToShow = (Contacto) adapter.getItem(posicion);
						contactToShow.setImagen(null);
						intent = new Intent(mContext, DetalleContacto.class);
						intent.putExtra("contacto", contactToShow);
						startActivity(intent);
						qa.dismiss();
					}
				});

		qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_call),
				"Llamar", new OnClickListener() {
					public void onClick(View v) {
						contactToShow = (Contacto) adapter.getItem(posicion);
						int cantidadNumeros = contactToShow
								.getTelephoneNumbers().size();
						if (cantidadNumeros == 0) {
							Toast.makeText(getApplicationContext(),
									"El Contacto no tiene números telefónicos",
									Toast.LENGTH_LONG).show();
						}
						if (cantidadNumeros == 1) {
							dialog(contactToShow, CALL);
						}
						if (cantidadNumeros > 1) {
							Intent callIntent = new Intent(Intent.ACTION_CALL);
							callIntent.setData(Uri.parse("tel:"
									+ contactToShow.getTelephoneNumbers()
											.get(0)));
							startActivity(callIntent);
						}
						qa.dismiss();
					}
				});

		qa.addItem(
				getResources().getDrawable(android.R.drawable.ic_dialog_email),
				"SMS", new OnClickListener() {
					public void onClick(View v) {
						contactToShow = (Contacto) adapter.getItem(posicion);
						int cantidadNumeros = contactToShow
								.getTelephoneNumbers().size();
						if (cantidadNumeros == 0) {
							Toast.makeText(getApplicationContext(),
									"El Contacto no tiene números telefónicos",
									Toast.LENGTH_LONG).show();
						}
						if (cantidadNumeros == 1) {
							Intent intent = new Intent(Intent.ACTION_VIEW);
							intent.setType("vnd.android-dir/mms-sms");
							intent.setData(Uri.parse("smsto:"
									+ contactToShow.getTelephoneNumbers()
											.get(0)));
							startActivity(intent);
						}
						if (cantidadNumeros > 1) {
							dialog(contactToShow, SMS);
						}

						qa.dismiss();
					}
				});

		qa.addItem(
				getResources().getDrawable(android.R.drawable.ic_dialog_email),
				"Email", new OnClickListener() {
					public void onClick(View v) {
						contactToShow = (Contacto) adapter.getItem(posicion);
						int cantidadMails = contactToShow.getEmails().size();
						if (cantidadMails == 0) {
							Toast.makeText(
									getApplicationContext(),
									"El Contacto no tiene direcciones de Email",
									Toast.LENGTH_LONG).show();
						}
						if (cantidadMails == 1) {
							Uri uri = Uri.parse("mailto:"
									+ contactToShow.getEmails().get(0));
							intent = new Intent(Intent.ACTION_SENDTO, uri);
							startActivity(intent);
						}
						if (cantidadMails > 1) {
							dialog(contactToShow, MAIL);
						}
						qa.dismiss();
					}
				});
		qa.addItem(
				getResources().getDrawable(android.R.drawable.stat_notify_chat),
				"WebMsg", new OnClickListener() {
					public void onClick(View v) {
						contactToShow = (Contacto) adapter.getItem(posicion);
						if (contactToShow.getuserWeb() != null) {
							intent = new Intent(mContext, Servicio_Web.class);
							intent.putExtra(CONTACT, contactToShow);
							startActivity(intent);
						} else {
							Toast.makeText(getApplicationContext(),
									"El Contacto no tiene usuario web",
									Toast.LENGTH_LONG).show();
						}
						qa.dismiss();
					}
				});

		// qa.addItem(
		// getResources().getDrawable(
		// android.R.drawable.stat_sys_data_bluetooth),
		// "msgbluetooth", new OnClickListener() {
		// public void onClick(View v) {
		// contactToShow = (Contacto) adapter.getItem(posicion);
		// intent = new Intent(mContext, BluetoothChat.class);
		// intent.putExtra(CONTACT,
		// contactToShow.getMACBluetooth());
		// startActivity(intent);
		// qa.dismiss();
		// }
		// });

		// shows the quick action window on the screen
		qa.show();

	}

	@Override
	public void afterTextChanged(Editable arg0) {
		AdapterAndList(arg0.toString());

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	private void dialog(final Contacto cont, final int tipo) {
		String[] items = null;
		if (tipo == CALL || tipo == SMS) {
			items = new String[cont.getTelephoneNumbers().size()];
			for (int i = 0; i < cont.getTelephoneNumbers().size(); i++) {
				items[i] = cont.getTelephoneNumbers().get(i);
			}
		}
		if (tipo == MAIL) {
			items = new String[cont.getEmails().size()];
			for (int i = 0; i < cont.getEmails().size(); i++) {
				items[i] = cont.getEmails().get(i);
			}
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dc_eleccion);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if (tipo == 1) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"
							+ cont.getTelephoneNumbers().get(item)));
					startActivity(callIntent);
				}
				if (tipo == 2) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setType("vnd.android-dir/mms-sms");
					intent.setData(Uri.parse("smsto:"
							+ cont.getTelephoneNumbers().get(item)));
					startActivity(intent);
				}
				if (tipo == 3) {
					Uri uri = Uri.parse("mailto:"
							+ contactToShow.getEmails().get(0));
					Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
					startActivity(intent);
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

}
