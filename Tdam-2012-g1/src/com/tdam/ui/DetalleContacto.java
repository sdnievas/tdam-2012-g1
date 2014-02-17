package com.tdam.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdam.Class.Contacto;
import com.tdam.Class.ContactoWeb;
import com.tdam.Class.Mail;
import com.tdam.Class.Usuario;
import com.tdam.Database.DatabaseHelper;
import com.tdam.Database.SingletonDB;
import com.tdam_2012_g1.R;

public class DetalleContacto extends Activity implements OnClickListener {

	private Intent intent;
	private Contacto contact;
	private HashMap<String, Uri> imgUri;
	private ListView lvCel, lvMail, lvMsgWeb, lvBTUser, lvDirecciones;

	private String adress;
	private static final String TAG = "BluetoothChat";
	private static final boolean D = true;

	private static final String DIALOG_CALL = "Llamar";
	private static final String DIALOG_MSJ = "Enviar un Mensaje";
	private static final String CONTACT = "contacto";

	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_WEBUSER = 2;
	private static final String LOGIN_SETTINGS = "LoginPreferences";
	private static final String USER = "User";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_contacto);

		Bundle extras = getIntent().getExtras();
		contact = (Contacto) extras.getSerializable(CONTACT); // Cargamos el
																// contacto que
																// recibimos del
																// intent de la
																// activity
																// contactos

		TextView nContacto = (TextView) findViewById(R.id.txtNombre);
		nContacto.setText(contact.getName()); // carga nombre de contacto al
												// text de la pantalla

		cargarImagen();

		Button historial = (Button) findViewById(R.id.btnHistorial_DetalleContacto);
		historial.setOnClickListener(this);

		lvCel = (ListView) findViewById(R.id.listNumeros);
		lvMail = (ListView) findViewById(R.id.listEmail);
		lvMsgWeb = (ListView) findViewById(R.id.listMensajeriaWeb);
		lvBTUser = (ListView) findViewById(R.id.listUserBluetooth);
		lvDirecciones = (ListView) findViewById(R.id.listaDirecciones);

		CargarListas();

		updateListViewHeight(lvCel);
		updateListViewHeight(lvMail);
		updateListViewHeight(lvMsgWeb);
		updateListViewHeight(lvBTUser);
		updateListViewHeight(lvDirecciones);

		lvMsgWeb.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				MsgWebto(); // Llama al metodo cuando se seleccion opcion mandar
							// mensaje web

			}

		});

		lvCel.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				dialog(position); // llama al metodo cuadno se quiere realizar
									// accion del tipo llamada o mensaje de
									// texto

			}

		});

		lvMail.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Mailto(position); // llama al metodo cuando se quiere enviar un
									// email

			}

		});


		lvDirecciones.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mostrarMapa(position); // llama al metodo cuando se quiere
										// enviar un mensaje por bluetooth

			}

		});

	}

	private void CargarListas() {

		// Se cargan los adapter con arrays de Strigs que son de los telefonos,
		// mails y nombres de contactos web del contacto seleccionado
		lvCel.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, contact
						.getTelephoneNumbers()));
		lvMail.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, contact.getEmails()));

		if (contact.getuserWeb() != null) {
			String[] MsgWeb = { contact.getuserWeb() };
			lvMsgWeb.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, MsgWeb));
		}

		if (contact.getNomUserBluetooth() != null) {
			String[] BTUser = { contact.getNomUserBluetooth() + " : "
					+ contact.getMACBluetooth() };
			lvBTUser.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, BTUser));
		}
		if (contact.getDirecciones() != null) {
			lvDirecciones.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, contact
							.getDirecciones()));
		}

	}

	private void MsgWebto() {

		intent = new Intent(this, Servicio_Web.class);
		intent.putExtra(CONTACT, contact);
		startActivity(intent);
	}

	private void mostrarMapa(int position) {
		String direccion = contact.getDirecciones().get(position);
		direccion = direccion.replace(' ', '+');
		String uri = "geo:0,0?q=" + direccion;
		intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(uri));
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}

	}

	// dialog de eleccion para mensajes de texto o para llamadas

	private void dialog(int position) {

		// final CharSequence[] items =
		// {R.string.dc_llamar,R.string.dc_enviar_mensaje};
		final CharSequence[] items = { DIALOG_CALL, DIALOG_MSJ };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dc_eleccion);
		final int posicion = position;
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0: // Llamar
					Mainact(posicion); // Llama al metodo para Realizar Llamadas
					break;
				case 1: // Enviar Mensajes
					SmsTo(posicion); // Llama al metodo para enviar mensajes
					break;
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	// En caso de mandar mail llama al Intent que envia los mails
	private void Mailto(int position) {
		DatabaseHelper dbhelper = new DatabaseHelper(this);
		SharedPreferences preferences = getSharedPreferences(LOGIN_SETTINGS,
				MODE_PRIVATE);
		Usuario usuario = dbhelper.buscarUsuarioporNombre(preferences
				.getString(USER, ""));

		Mail mail = new Mail();
		// mail.set_id(0);
		mail.set_idContacto(contact.getId());
		mail.set_mailDestino(contact.getEmails().get(position));
		mail.set_fechaEnvio(new Date());

		// Calendar c = Calendar.getInstance();
		// int mYear = c.get(Calendar.YEAR);
		// int mMonth = c.get(Calendar.MONTH);
		// int mDay = c.get(Calendar.DAY_OF_MONTH);
		// mail.set_fechaEnvio(mDay + "/" + (mMonth + 1) + "/" + mYear);

		dbhelper.addMail(mail);
		dbhelper.close();

		ArrayList<String> mails = contact.getEmails();
		Uri uri = Uri.parse("mailto:" + mails.get(position));
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		startActivity(it);

	}

	/*
	 * En caso de Llamada envia un intent al Action Call con el numero de
	 * Telefono
	 */
	private void Mainact(int position) {

		ArrayList<String> telephone = contact.getTelephoneNumbers();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + telephone.get(position)));
		startActivity(callIntent);

	}

	private void SmsTo(int position) {
		ArrayList<String> telephone = contact.getTelephoneNumbers();
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setType("vnd.android-dir/mms-sms");
		intent.setData(Uri.parse("smsto:" + telephone.get(position)));
		startActivity(intent);
	}

	// Intent del boton Historial que llama al historial del contacto en
	// particular
	public void onClick(View v) {
		contact.setImagen(null);
		intent = new Intent().setClass(this, Historial.class);
		intent.putExtra(CONTACT, contact);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.detalle_contacto_new_websms, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent;
		switch (item.getItemId()) {

		case R.id.userdetalle_newwebsms:
			if (contact.getuserWeb() != null) {
				Toast toast = Toast.makeText(this,
						"El contacto ya posee usuario web", 1000);
				toast.show();
			} else {
				intent = new Intent(this, New_ContactWebSms.class);
				startActivityForResult(intent, REQUEST_WEBUSER);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
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

	private void cargarImagen() {

		ImageView imagen = (ImageView) findViewById(R.id.imgDetalleContacto);

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

		contact.setImagen(imgUri.get(contact.getId()));
		InputStream input;
		Bitmap bm = null;

		try {
			if (contact.getImagen() != null) {
				input = getContentResolver().openInputStream(
						contact.getImagen());
				bm = BitmapFactory.decodeStream(input);
				input.close();
				imagen.setImageBitmap(bm);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		

		case REQUEST_WEBUSER:
			if (resultCode == Activity.RESULT_OK) {
				String Name = data.getExtras().getString(
						New_ContactWebSms.EXTRA_WEB_CONTACT_NAME);
				ContactoWeb con = new ContactoWeb();
				con.set_nombreWeb(Name);
				con.set_id(Integer.parseInt(contact.getId()));
				SingletonDB.getInstance(getApplicationContext())
						.getDatabaseHelper().updateContacto(con);
				CargarListas();
			}
			break;

		}
	}

	public static void updateListViewHeight(ListView myListView) {
		ListAdapter myListAdapter = myListView.getAdapter();
		if (myListAdapter == null) {
			return;
		}
		// get listview height
		int totalHeight = 0;
		int adapterCount = myListAdapter.getCount();
		for (int size = 0; size < adapterCount; size++) {
			View listItem = myListAdapter.getView(size, null, myListView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		// Change Height of ListView
		ViewGroup.LayoutParams params = myListView.getLayoutParams();
		params.height = totalHeight
				+ (myListView.getDividerHeight() * (adapterCount - 1));
		myListView.setLayoutParams(params);
	}

}
