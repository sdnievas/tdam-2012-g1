package com.tdam_2012_g1.database;

import com.tdam_2012_g1.entidades.*;

import java.util.ArrayList;
import java.util.Date;
import com.tdam_2012_g1.dom.Contacto;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	static final String dbName = "TdamDB";

	static final String contactosAplicacionTable = "contactosAplicacion";
	static final String colIdContact = "idContact";
	static final String colNombreWeb = "nombreWeb";

	static final String usuariosTable = "usuarios";
	static final String colIdUsuario = "idUsuario";
	static final String colNombre = "nombre";
	static final String colContraseña = "contrasena";
	static final String colMail = "mailUsuario";

	static final String conectividadesTable = "conectividades";
	static final String colIdConectividad = "idConectividad";
	static final String colConexion = "conexion";
	static final String colEstado = "estado";
	static final String colFechaConexion = "fechaConexion";

	static final String mailsTable = "mails";
	static final String colIdMail = "idMail";
	static final String colIdUsuarioRemitente = "idUsuarioRemitente";
	static final String colDestinatario = "destinatario";
	static final String colFechaEnvio = "fechaEnvio";

	static final String mensajesWebTable = "mensajesWeb";
	static final String colIdMensajeWeb = "id";
	static final String colIdContactoRemitente = "idContactoRemitente";
	static final String colIdContactoDestinatario = "idContactoDestinatario";
	static final String colFechaEnvioMsj = "fechaEnvio";
	static final String colDetalle = "detalle";

	public DatabaseHelper(Context context) {
		super(context, dbName, null, 35);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// Tabla Contactos
		db.execSQL("CREATE TABLE " + contactosAplicacionTable + " ("
				+ colIdContact + " INTEGER PRIMARY KEY , " + colNombreWeb
				+ " TEXT)");

		// Tabla Usuarios
		db.execSQL("CREATE TABLE " + usuariosTable + "( " + colIdUsuario
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colNombre
				+ " TEXT, " + colContraseña + " TEXT, " + colMail + " TEXT )");

		// Tabla Conectividad
		db.execSQL("CREATE TABLE " + conectividadesTable + "( "
				+ colIdConectividad + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ colConexion + " TEXT, " + colEstado + " TEXT, "
				+ colFechaConexion + " DATETIME )");

		// Tabla mail
		db.execSQL("CREATE TABLE " + mailsTable + "( " + colIdMail
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ colIdUsuarioRemitente + " INTEGER, " + colDestinatario
				+ " TEXT, " + colFechaEnvio + " TEXT)");

		// Tabla MensajeWeb
		db.execSQL("CREATE TABLE " + mensajesWebTable + " (" + colIdMensajeWeb
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ colIdContactoRemitente + " INTEGER NOT NULL, "
				+ colIdContactoDestinatario + " INTEGER NOT NULL, "
				+ colFechaEnvioMsj + " DATETIME, " + colDetalle
				+ " TEXT NOT NULL);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + contactosAplicacionTable);
		db.execSQL("DROP TABLE IF EXISTS " + usuariosTable);
		db.execSQL("DROP TABLE IF EXISTS " + conectividadesTable);
		db.execSQL("DROP TABLE IF EXISTS " + mensajesWebTable);
		db.execSQL("DROP TABLE IF EXISTS " + mailsTable);

		onCreate(db);
	}

	public void addContacto(com.tdam_2012_g1.entidades.Contacto cont) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colIdContact, cont.get_id());
		cv.put(colNombreWeb, cont.get_nombreWeb());

		db.insert(contactosAplicacionTable, colIdContact, cv);
		db.close();
	}

	public int getContactoCount() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("Select * from " + contactosAplicacionTable,
				null);
		int x = cur.getCount();
		cur.close();
		return x;
	}

	public Cursor getAllContactos() {
		SQLiteDatabase db = this.getWritableDatabase();//

		Cursor cur = db.rawQuery("SELECT * FROM " + contactosAplicacionTable,
				null);
		return cur;
	}

	public String getNameContact(String Userid) {

		SQLiteDatabase db = this.getWritableDatabase();//
		com.tdam_2012_g1.entidades.Contacto aux = null;
		String[] values = { Userid };

		Cursor cursor = db.rawQuery("SELECT * FROM " + contactosAplicacionTable
				+ " where " + colIdContact + " = ? ", values);

		while (cursor.moveToNext()) {
			aux = new com.tdam_2012_g1.entidades.Contacto();

			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdContact)));

			aux.set_nombreWeb(cursor.getString(cursor
					.getColumnIndex(colNombreWeb)));
		}

		cursor.close();
		db.close();
		if (aux != null)
			return aux.get_nombreWeb();
		else
			return null;

	}

	public void addMail(Mail mail) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colIdUsuarioRemitente, mail.get_idUsuarioRemitente());
		cv.put(colDestinatario, mail.get_mailDestinatario());
		cv.put(colFechaEnvio, mail.get_fechaEnvio());

		db.insert(mailsTable, colIdMail, cv);
		db.close();
	}

	public Cursor getAllMails() {
		SQLiteDatabase db = this.getWritableDatabase();//

		Cursor cur = db.rawQuery("SELECT * FROM " + mailsTable, null);
		return cur;
	}

	public void addConectividad(Conectividad conect) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colConexion, conect.get_conexion());
		cv.put(colEstado, conect.get_estado());
		cv.put(colFechaConexion, conect.get_fecha());

		db.insert(conectividadesTable, colConexion, cv);
		db.close();
	}

	public Cursor getAllConectividades() {
		SQLiteDatabase db = this.getWritableDatabase();//

		Cursor cur = db.rawQuery("SELECT * FROM " + conectividadesTable, null);
		return cur;
	}

	public void addUsuario(Usuario usu) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colNombre, usu.get_nombre());
		cv.put(colContraseña, usu.get_contraseña());
		cv.put(colMail, usu.get_mail());

		db.insert(usuariosTable, colNombre, cv);
		db.close();
	}

	public Cursor getAllUsuarios() {
		SQLiteDatabase db = this.getWritableDatabase();//

		Cursor cur = db.rawQuery("SELECT * FROM " + usuariosTable, null);
		return cur;
	}

	public void addMensaje(MensajeWeb msj) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colIdContactoRemitente, msj.get_remitente());
		cv.put(colIdContactoDestinatario, msj.get_destinatario());
		cv.put(colFechaEnvioMsj, msj.get_fechaEnvio());
		cv.put(colDetalle, msj.get_detalle());

		db.insert(mensajesWebTable, null, cv);
		db.close();
	}

	public Cursor getAllMensajes() {
		SQLiteDatabase db = this.getWritableDatabase();//

		Cursor cur = db.rawQuery("SELECT * FROM " + mensajesWebTable, null);
		return cur;
	}

	public ArrayList<Mail> getTodosMails(String ordenFecha) {

		ArrayList<Mail> mails = new ArrayList<Mail>();
		Mail aux = null;
		SQLiteDatabase db = this.getWritableDatabase();
		String clause = "";
		String selection[] = null;

		Cursor cursor = db.rawQuery("SELECT * FROM " + mailsTable
				+ " ORDER BY " + colFechaEnvio + " " + ordenFecha, null);

		while (cursor.moveToNext()) {
			aux = new Mail();
			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdMail)));
			aux.set_idUsuarioRemitente(cursor.getString(cursor
					.getColumnIndex(colIdUsuarioRemitente)));

			aux.set_fechaEnvio(cursor.getString(cursor
					.getColumnIndex(colFechaEnvio)));

			aux.set_mailDestinatario(cursor.getString(cursor
					.getColumnIndex(colDestinatario)));

			mails.add(aux);
		}

		cursor.close();
		db.close();
		return mails;

	}

	public ArrayList<MensajeWeb> getAllMensajeWeb(String ordenFecha) {

		ArrayList<MensajeWeb> mails = new ArrayList<MensajeWeb>();
		MensajeWeb aux = null;
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM " + mensajesWebTable
				+ " ORDER BY " + colFechaEnvioMsj + " " + ordenFecha, null);

		while (cursor.moveToNext()) {
			aux = new MensajeWeb();
			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdMensajeWeb)));

			aux.set_destinatario(cursor.getString(cursor
					.getColumnIndex(colIdContactoDestinatario)));

			aux.set_remitente(cursor.getString(cursor
					.getColumnIndex(colIdContactoRemitente)));

			aux.set_fechaEnvio(new Date((cursor.getLong(cursor
					.getColumnIndex(colFechaEnvioMsj)))).toString());

			aux.set_detalle(cursor.getString(cursor.getColumnIndex(colDetalle)));

			mails.add(aux);
		}

		cursor.close();
		db.close();
		return mails;

	}

	public ArrayList<Conectividad> getAllConectividad() {

		ArrayList<Conectividad> conec = new ArrayList<Conectividad>();
		Conectividad aux = null;
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM " + conectividadesTable,
				null);
		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {
				aux = new Conectividad();
				aux.set_id(cursor.getInt(cursor
						.getColumnIndex(colIdConectividad)));

				aux.set_conexion(cursor.getString(cursor
						.getColumnIndex(colConexion)));

				aux.set_fecha(new Date((cursor.getLong(cursor
						.getColumnIndex(colFechaConexion)))).toString());

				aux.set_estado(cursor.getString(cursor
						.getColumnIndex(colEstado)));

				conec.add(aux);
			}
		}

		cursor.close();
		db.close();
		return conec;

	}

	public ArrayList<Mail> getMailsContacto(Contacto contact) {

		ArrayList<Mail> mails = new ArrayList<Mail>();
		Mail aux = null;
		SQLiteDatabase db = this.getWritableDatabase();
		String selection[] = { contact.getId() };

		Cursor cursor = db.rawQuery("SELECT * FROM " + mailsTable + " where "
				+ colIdUsuarioRemitente + " = ? ", selection);

		while (cursor.moveToNext()) {
			aux = new Mail();
			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdMail)));
			aux.set_idUsuarioRemitente(cursor.getString(cursor
					.getColumnIndex(colIdUsuarioRemitente)));

			aux.set_fechaEnvio(cursor.getString(cursor
					.getColumnIndex(colFechaEnvio)));

			aux.set_mailDestinatario(cursor.getString(cursor
					.getColumnIndex(colDestinatario)));

			mails.add(aux);
		}

		cursor.close();
		db.close();
		return mails;

	}

	public ArrayList<MensajeWeb> getContactMensajeWeb(Contacto cont) {

		ArrayList<MensajeWeb> mensWeb = new ArrayList<MensajeWeb>();
		MensajeWeb aux = null;
		SQLiteDatabase db = this.getWritableDatabase();
		String values[] = { cont.getName() };

		Cursor cursor = db.rawQuery("SELECT * FROM " + mensajesWebTable
				+ " WHERE " + colIdContactoDestinatario + " = ?", values);

		while (cursor.moveToNext()) {
			aux = new MensajeWeb();
			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdMensajeWeb)));

			aux.set_destinatario(cursor.getString(cursor
					.getColumnIndex(colIdContactoDestinatario)));

			aux.set_remitente(cursor.getString(cursor
					.getColumnIndex(colIdContactoRemitente)));

			aux.set_fechaEnvio(new Date((cursor.getLong(cursor
					.getColumnIndex(colFechaEnvioMsj)))).toString());

			aux.set_detalle(cursor.getString(cursor.getColumnIndex(colDetalle)));

			mensWeb.add(aux);
		}

		cursor.close();
		db.close();
		return mensWeb;

	}

	public boolean contactExist(String parName, String parPass) {

		SQLiteDatabase db = this.getWritableDatabase();
		String values[] = { parName, parPass };

		Cursor cursor = db.rawQuery("SELECT * FROM " + usuariosTable
				+ " WHERE " + colNombre + " = ? " + " and " + colContraseña
				+ " = ? ", values);

		return (cursor.getCount() > 0);

	}

	public Usuario buscarUsuarioporNombre(String nombre) {

		Usuario usuario = null;
		SQLiteDatabase db = this.getWritableDatabase();
		String values[] = { nombre };

		Cursor cursor = db.rawQuery("SELECT * FROM " + usuariosTable
				+ " WHERE " + colNombre + " = ? ", values);

		while (cursor.moveToNext()) {
			usuario = new Usuario();
			usuario.set_id(cursor.getInt(cursor.getColumnIndex(colIdUsuario)));

			usuario.set_nombre(cursor.getString(cursor
					.getColumnIndex(colNombre)));

			usuario.set_contraseña(cursor.getString(cursor
					.getColumnIndex(colContraseña)));

			usuario.set_mail(cursor.getString(cursor.getColumnIndex(colMail)));

		}

		return usuario;

	}

	public void UpdateUser(Usuario user) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colNombre, user.get_nombre());
		cv.put(colContraseña, user.get_contraseña());
		cv.put(colMail, user.get_mail());

		String values[] = { String.valueOf(user.get_id()) };
		db.update(usuariosTable, cv, colIdUsuario + "= ?", values);
		db.close();

	}

}
