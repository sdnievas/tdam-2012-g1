package com.tdam.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdam.Class.Contacto;
import com.tdam.Class.Mail;
import com.tdam.Database.DatabaseHelper;
import com.tdam_2012_g1.R;

public class Historial_Mail extends ListActivity implements OnItemClickListener {

	private HistoryMailAdapter adaptador;
	private Contacto contact;
	private String ordenarForma;
	private final int DIALGO_ELIMINACION = -2;
	private static final String LOGIN_SETTINGS = "LoginPreferences";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial__mail);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			// Cargamos el contacto que recibimos del intent de la activity
			// contactos
			contact = (Contacto) extras.getSerializable("contacto");
			if (contact != null) {
				ContentResolver cr = getContentResolver();
				Cursor cur = cr
						.query(ContactsContract.Contacts.CONTENT_URI,
								new String[] { ContactsContract.Contacts.DISPLAY_NAME },
								ContactsContract.Contacts._ID + "= ? ",
								new String[] { contact.getId() }, null);
				if (cur.getCount() > 0) {
					while (cur.moveToNext()) {
						contact.setName(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
					}
				}
			}
		}

		adaptador = new HistoryMailAdapter();

		getListView().setAdapter(adaptador);

		getListView().setOnItemClickListener(this);

		loadmailData();

	}

	protected void onResume() {
		super.onResume();
		getPreferences();
		loadmailData();
	}

	private void loadmailData() {

		getPreferences();
		String forma = "desc";

		if (!ordenarForma.equals("0")) {
			forma = "asc";
		}
		DatabaseHelper dbhelper = getDatabaseHelper();
		ArrayList<Mail> mails;
		if (contact != null) {
			mails = (ArrayList<Mail>) dbhelper.getMails(contact, forma);
			// mails = dbhelper.getMailsContacto(contact);
		} else {
			mails = (ArrayList<Mail>) dbhelper.getMails(forma);
			// mails = dbhelper.getTodosMails(forma);
		}

		adaptador.addListHistorial(mails);
		dbhelper.close();
		adaptador.notifyDataSetChanged();
	}

	private void getPreferences() {

		SharedPreferences myPreference = PreferenceManager
				.getDefaultSharedPreferences(this);

		ordenarForma = myPreference.getString(
				getString(R.string.preference_Historial_Ordenarkey), "0");

		myPreference.getString(
				getString(R.string.preference_Historial_Filtrarkey), "0");
	}

	protected DatabaseHelper getDatabaseHelper() {
		return new DatabaseHelper(this);
	}

	class HistoryMailAdapter extends BaseAdapter {

		private ArrayList<Mail> historial;
		private LayoutInflater inflater;
		private SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm");
		private SimpleDateFormat formatoFecha = new SimpleDateFormat(
				"dd/MM/yyyy");

		public HistoryMailAdapter() {
			historial = new ArrayList<Mail>();
			inflater = LayoutInflater.from(Historial_Mail.this);
		}

		public void addHistorial(Mail historials) {
			if (historial != null) {
				historial.add(historials);
			}
		}

		public void addListHistorial(ArrayList<Mail> historials) {
			historial = historials;
		}

		@Override
		public int getCount() {
			return historial.size();
		}

		@Override
		public Object getItem(int position) {
			return historial.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			Holder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.historial_item, null);
				holder = new Holder();
				holder.txtContacto = (TextView) convertView
						.findViewById(R.id.txtArribaIzquierda);
				holder.txtFecha = (TextView) convertView
						.findViewById(R.id.txtArribaDerecha);
				holder.txtMailDestino = (TextView) convertView
						.findViewById(R.id.txtAbajoIzquierda);
				holder.txtHora = (TextView) convertView
						.findViewById(R.id.txtAbajoDerecha);
				holder.ImagenType = (ImageView) convertView
						.findViewById(R.id.imagehistorialItem);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Mail history = (Mail) getItem(position);
			// Date fecha = new Date();
			// try {
			// fecha = history.get_fechaEnvio();
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }

			if (contact != null) {
				holder.txtContacto.setText(contact.getName());
			} else {
				ContentResolver cr = getContentResolver();
				Cursor cur = cr
						.query(ContactsContract.Contacts.CONTENT_URI,
								new String[] { ContactsContract.Contacts.DISPLAY_NAME },
								ContactsContract.Contacts._ID + "= ? ",
								new String[] { history.get_idContacto() }, null);
				if (cur.getCount() > 0) {
					while (cur.moveToNext()) {
						holder.txtContacto
								.setText(cur.getString(cur
										.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
					}
				}
			}
			holder.txtMailDestino.setText(history.get_mailDestino());
			holder.txtHora
					.setText(formatoHora.format(history.get_fechaEnvio()));
			holder.txtFecha.setText(formatoFecha.format(history
					.get_fechaEnvio()));
			holder.ImagenType
					.setImageResource(android.R.drawable.ic_dialog_email);
			return convertView;
		}
	}

	class Holder {
		private TextView txtContacto;
		private TextView txtMailDestino;
		private TextView txtFecha;
		private TextView txtHora;
		private ImageView ImagenType;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_historial, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {

		case R.id.historial_settings:
			intent = new Intent(this, Preference_historial.class);
			break;case R.id.historial_delete:
				showDialog(DIALGO_ELIMINACION);
				intent = null;
				break;
			}
			if (intent != null) {
				startActivity(intent);
			}
		return true;
	}
	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		// TODO Auto-generated method stub
		super.onPrepareDialog(id, dialog, args);
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog = null;
		OnClickListener clickOk = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				eliminarHistorial();
				dismissDialog(DIALGO_ELIMINACION);

			}
		};

		OnClickListener clickCancel = new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismissDialog(DIALGO_ELIMINACION);
			}
		};

		dialog = new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Advertencia")
				.setPositiveButton("OK. Borrar registros", clickOk)
				.setNegativeButton("NO. Cancelar borrado", clickCancel)
				.setMessage(
						"Se eliminar�n los registros de emails y mensajes web. Esta acci�n no se puede deshacer. �Est� seguro que desea continuar?")
				.create();

		return dialog;

	}

	private void eliminarHistorial() {
		DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
		SharedPreferences preferencias = getSharedPreferences(LOGIN_SETTINGS,
				MODE_PRIVATE);
		String user = preferencias.getString("User", "");
		dbHelper.eliminarRegistros(user);
		Toast.makeText(getApplicationContext(), "Se eliminaron registros",
				Toast.LENGTH_LONG).show();
	}
}
