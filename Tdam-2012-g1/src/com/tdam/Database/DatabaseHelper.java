package com.tdam.Database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tdam.Class.Conectividad;
import com.tdam.Class.Contacto;
import com.tdam.Class.ContactoBluetooth;
import com.tdam.Class.ContactoWeb;
import com.tdam.Class.MensajeBluetooth;
import com.tdam.Class.MensajeWeb;
import com.tdam.Class.RegistroAccion;
import com.tdam.Class.Usuario;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	//Creacion de DataBase Tablas y Columnas (ReName)
	//----------------------------------------------------------
	
	static final String dbName = "TdamDB";

	//Tabla contactos Web
	static final String contactosAplicacionTable = "contactosAplicacion";
	static final String colIdContact = "idContacto";
	static final String colNombreWeb = "nombreWeb";

	//Tabla usuarios de la app
	static final String usuariosTable = "usuarios";
	static final String colIdUsuario = "idUsuario";
	static final String colNombre = "nombre";
	static final String colContrase�a = "contrasena";
	static final String colMail = "mailUsuario";

	//Tabla conectividades
	static final String conectividadesTable = "conectividades";
	static final String colIdConectividad = "idConectividad";
	static final String colConexion = "conexion";
	static final String colEstado = "estado";
	static final String colFechaConexion = "fechaConexion";

	//tabla Mensajes web
	static final String mensajesWebTable = "mensajesWeb";
	static final String colIdMensajeWeb = "id";
	static final String colUsuario = "idUsuario";
	static final String colContacto = "idContacto";
	static final String colFechaEnvioMsj = "fechaEnvio";
	static final String colDetalle = "detalle";
	static final String colTipoMensaje= "tipoMensaje";
	
	//Tabla Usuario Bluetooth
	static final String contactoBluetoothTable = "contactoBluetooth";
	static final String colIdContactBluetooth = "IdContactoBluetooth";
	static final String colNombreBluetooth = "NombreBluetooth";
	static final String colMAC = "MAC";
	
	//Tabla Mensaje Bluetooth
	static final String mensajesBluetoothTable = "mensajesBluetooth";
	static final String colIdMensajeBluetooth = "id";
	static final String colUsuarioBluetooth = "idUsuario";
	static final String colContactoBluetooth = "idContacto";
	static final String colFechaEnvioMsjBluetooth = "fechaEnvio";
	static final String colMensajeBluetooth = "mensaje";
	static final String colTipoBluetooth = "tipoMensaje";
	
	//Tabla RegistroAccion
	static final String registroAccionTable = "registroAccion";
	static final String colIdRegistroAccion = "id";
	static final String colFechaRegistro = "fecha";
	static final String colIdContactoRegistro = "idContacto";
	static final String colNombreContactoRegistro = "nombreContacto";
	static final String colTipoAccion = "tipoAccion";

	public DatabaseHelper(Context context) {
		super(context, dbName, null, 38);

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
				+ " TEXT, " + colContrase�a + " TEXT, " + colMail + " TEXT )");

		// Tabla Conectividad
		db.execSQL("CREATE TABLE " + conectividadesTable + "( "
				+ colIdConectividad + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ colConexion + " TEXT, " + colEstado + " TEXT, "
				+ colFechaConexion + " DATETIME )");

	

		// Tabla MensajeWeb
		db.execSQL("CREATE TABLE " + mensajesWebTable + " (" + colIdMensajeWeb
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ colUsuario + " TEXT NOT NULL, "
				+ colContacto + " TEXT NOT NULL, "
				+ colFechaEnvioMsj + " DATETIME, " + colDetalle	+ " TEXT NOT NULL, "
				+ colTipoMensaje + " INTEGER NOT NULL);");

		// Tabla Usuario Bluetooth
		db.execSQL("CREATE TABLE " + contactoBluetoothTable + " ("
				+ colIdContactBluetooth + " INTEGER PRIMARY KEY , " + colNombreBluetooth 
				+ " TEXT, " + colMAC + " TEXT)");
		
		// Tabla MensajeBluetooth
		db.execSQL("CREATE TABLE " + mensajesBluetoothTable + " (" + colIdMensajeBluetooth
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ colUsuarioBluetooth + " INTEGER NOT NULL, "
				+ colContactoBluetooth + " INTEGER NOT NULL, "
				+ colFechaEnvioMsjBluetooth + " DATETIME, " + colMensajeBluetooth	+ " TEXT NOT NULL, "
				+ colTipoBluetooth + " INTEGER NOT NULL)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + contactosAplicacionTable);
		db.execSQL("DROP TABLE IF EXISTS " + usuariosTable);
		db.execSQL("DROP TABLE IF EXISTS " + conectividadesTable);
		db.execSQL("DROP TABLE IF EXISTS " + mensajesWebTable);
		db.execSQL("DROP TABLE IF EXISTS " + contactoBluetoothTable);
		db.execSQL("DROP TABLE IF EXISTS " + mensajesBluetoothTable);
		db.execSQL("DROP TABLE IF EXISTS " + registroAccionTable);

		onCreate(db);
	}
	
	//DataBase Consultas Tablas Contactos WEB
	//----------------------------------------------------------	

	//Agrega Contacto web a la app
	public void addContacto(ContactoWeb cont) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colIdContact, cont.get_id());
		cv.put(colNombreWeb, cont.get_nombreWeb());

		db.insert(contactosAplicacionTable, colIdContact, cv);
		db.close();
	}
	
	//Devuelve el Nombre del contacto con el id del usuario de los contactos de la app
	public ContactoWeb getNameContact(String Userid) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContactoWeb aux = null;
		
		String[] values = {Userid};

		Cursor cursor = db.rawQuery("SELECT * FROM " + contactosAplicacionTable
				+ " WHERE " + colIdContact + " = ? ", values);

		while (cursor.moveToNext()) {
			
			aux = new ContactoWeb();

			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdContact)));

			aux.set_nombreWeb(cursor.getString(cursor
					.getColumnIndex(colNombreWeb)));
		}

		cursor.close();
		db.close();
		
		return aux;

	}

	//Borra el contacto web de la app
	public boolean deleteContacto(ContactoWeb cont){
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		String params[] = {cont.get_nombreWeb()};
		
		String where =  colNombreWeb + " = ? ";
		
		db.delete(contactosAplicacionTable , where, params);
		
		db.close();
		
		return true;
		
	}
	
	//Cambia el nombre del contacto web de la app asociada a un contacto
	public boolean updateContacto(ContactoWeb cont){
		
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colNombreWeb, cont.get_nombreWeb());

		String[] values = { String.valueOf(cont.get_id()) };
		
		String Where = colIdContact + "= ?";
		
		db.update(contactosAplicacionTable, cv, Where , values);
		db.close();
		
		return true;
	
	}
	
	public boolean exiteContacto(String cont){
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContactoWeb aux = null;
		
		String[] values = {cont};

		Cursor cursor = db.rawQuery("SELECT * FROM " + contactosAplicacionTable
				+ " WHERE " + colNombreWeb + " = ? ", values);

		boolean val = (cursor.getCount()> 0);
				
		cursor.close();
		db.close();
		
		return val;
		
	}
	//DataBase Consultas Tablas Usuarios de la Aplicacion
		//----------------------------------------------------------
	
	//agrega un usuario a la aplicacion
	public void addUsuario(Usuario user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colNombre, user.get_nombre());
		cv.put(colContrase�a, user.get_contrase�a());
		cv.put(colMail, user.get_mail());

		db.insert(usuariosTable, colNombre, cv);
		db.close();
	}

	//existe Contacto
	public boolean UsuarioExiste(Usuario user) {

		SQLiteDatabase db = this.getWritableDatabase();
		String values[] = { user.get_nombre(), user.get_contrase�a() };

		Cursor cursor = db.rawQuery("SELECT * FROM " + usuariosTable
				+ " WHERE " + colNombre + " = ? " + " AND " + colContrase�a
				+ " = ? ", values);

		boolean existe = (cursor.getCount() > 0);
		
		cursor.close();
		db.close();
		
		return existe;

	}
	
	//Buscar Usuario por Nombre
	public Usuario buscarUsuarioporNombre(String nombre) {

		Usuario usuario = null;
		SQLiteDatabase db = this.getWritableDatabase();
		String values[] = { nombre };

		Cursor cursor = db.rawQuery("SELECT * FROM " + usuariosTable
				+ " WHERE " + colNombre + " = ? ", values);

		while (cursor.moveToNext()) {
			usuario = new Usuario();
			
			usuario.set_id(cursor.getInt(cursor.
					getColumnIndex(colIdUsuario)));

			usuario.set_nombre(cursor.getString(cursor
					.getColumnIndex(colNombre)));

			usuario.set_contrase�a(cursor.getString(cursor
					.getColumnIndex(colContrase�a)));

			usuario.set_mail(cursor.getString(cursor.
					getColumnIndex(colMail)));

		}

		cursor.close();
		db.close();
		
		return usuario;

	}

	//Actualizar el Usuario
	public void UpdateUser(Usuario user) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colNombre, user.get_nombre());
		cv.put(colContrase�a, user.get_contrase�a());
		cv.put(colMail, user.get_mail());

		String[] values = { String.valueOf(user.get_id()) };
		
		String Where = colIdUsuario + "= ?";
		
		db.update(usuariosTable, cv, Where , values);
		db.close();

	}

	
	
	//DataBase Consultas Tablas Conexion
	//----------------------------------------------------------	

	//agrega conectividad 
	public void addConectividad(Conectividad conect) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colConexion, conect.get_conexion());
		cv.put(colEstado, conect.get_estado());
		cv.put(colFechaConexion, conect.get_fecha());

		db.insert(conectividadesTable, colConexion, cv);
		db.close();
	}

	//devuelve todas la aplicacione de la tabla
	public ArrayList<Conectividad> getAllConectividad() {

		ArrayList<Conectividad> conec = new ArrayList<Conectividad>();
		
		Conectividad aux = null;
		
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM " + conectividadesTable,
				null);
		
		while (cursor.moveToNext()) {
			
			aux = new Conectividad();
			aux.set_id(cursor.getInt(cursor
					.getColumnIndex(colIdConectividad)));

			aux.set_conexion(cursor.getString(cursor
					.getColumnIndex(colConexion)));
			
			aux.set_fecha(cursor.getString(cursor
					.getColumnIndex(colFechaConexion)));

			aux.set_estado(cursor.getString(cursor
					.getColumnIndex(colEstado)));

			conec.add(aux);
		}	
		

		cursor.close();
		db.close();
		return conec;
	}
	
	//elimina una conectividad de la tabla
	public void deleteConectividad(String date){
			
			SQLiteDatabase db = this.getWritableDatabase();
			
			String params[] = {date};

			String where =  colFechaConexion + " = ? ";
			
			db.delete(mensajesWebTable , where, params);
			db.close();
	
		}

	
	//DataBase Consultas Tablas MensajesWeb
	//----------------------------------------------------------
	
	public void addMensaje(MensajeWeb msj) {
		
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colUsuario, msj.get_usuario());
		cv.put(colContacto, msj.get_contacto());
		cv.put(colFechaEnvioMsj, msj.get_fechaEnvio());
		cv.put(colDetalle, msj.get_detalle());
		cv.put(colDetalle, msj.get_detalle());
		cv.put(colTipoMensaje, msj.getType());
		
		db.insert(mensajesWebTable, null, cv);
		db.close();
	}

	public ArrayList<MensajeWeb> getLastMsgConversations(String parOrdenFecha, Usuario parUser) {

		ArrayList<MensajeWeb> MsgWeb = new ArrayList<MensajeWeb>();
		
		MensajeWeb aux = null;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		String[] select = {parUser.get_nombre()};
		
		
		Cursor cursor = db.rawQuery("SELECT *, max(fechaEnvio) FROM mensajesWeb " +
				" WHERE " + colUsuario + " = ? " +
				" GROUP BY " + colUsuario + " , " + colContacto
				+ " ORDER BY " + colFechaEnvioMsj + " " + parOrdenFecha, select);

		while (cursor.moveToNext()) {
			aux = new MensajeWeb();
			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdMensajeWeb)));

			aux.set_usuario(cursor.getString(cursor
					.getColumnIndex(colUsuario)));

			aux.set_contacto(cursor.getString(cursor
					.getColumnIndex(colContacto)));
		
			aux.set_fechaEnvio(cursor.getString(cursor.getColumnIndex(colFechaEnvioMsj)));
			
			aux.set_detalle(cursor.getString(cursor.getColumnIndex(colDetalle)));

			aux.setType(cursor.getInt(cursor.getColumnIndex(colTipoMensaje)));
			
			MsgWeb.add(aux);
		}

		cursor.close();
		db.close();
		return MsgWeb;

	}

	public ArrayList<MensajeWeb> getContactoMensajeWeb(Contacto cont, Usuario parUsuario) {

		ArrayList<MensajeWeb> mensWeb = new ArrayList<MensajeWeb>();
		MensajeWeb aux = null;
		SQLiteDatabase db = this.getWritableDatabase();
		String[] values = { cont.getuserWeb(), parUsuario.get_nombre() };

			
		Cursor cursor = db.rawQuery(" SELECT * FROM " + mensajesWebTable
				+ " WHERE " + colContacto + " = ? "
				+ " AND " + colUsuario  + " = ? ", values);

		while (cursor.moveToNext()) {
			aux = new MensajeWeb();
			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdMensajeWeb)));

			aux.set_usuario(cursor.getString(cursor
					.getColumnIndex(colUsuario)));

			aux.set_contacto(cursor.getString(cursor
					.getColumnIndex(colContacto)));

			aux.set_fechaEnvio(cursor.getString(cursor.getColumnIndex(colFechaEnvioMsj)));
			
			aux.set_detalle(cursor.getString(cursor.getColumnIndex(colDetalle)));

			aux.setType(cursor.getInt(cursor.getColumnIndex(colTipoMensaje)));
			
			mensWeb.add(aux);
		}

		cursor.close();
		db.close();
		return mensWeb;

	}
	
	public MensajeWeb getLastMsgWeb(Usuario usr){
		
		MensajeWeb aux = null;
		SQLiteDatabase db = this.getWritableDatabase();

		String[] args = {usr.get_nombre()};

		Cursor cursor = db.rawQuery("SELECT * , MAX(fechaEnvio) FROM mensajesWeb WHERE " +  colUsuario + " = ? ",args);

		while (cursor.moveToNext()) {
			aux = new MensajeWeb();
			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdMensajeWeb)));

			aux.set_usuario(cursor.getString(cursor
					.getColumnIndex(colUsuario)));

			aux.set_contacto(cursor.getString(cursor
					.getColumnIndex(colContacto)));
			
			aux.set_fechaEnvio(cursor.getString(cursor.getColumnIndex(colFechaEnvioMsj)));

			aux.set_detalle(cursor.getString(cursor.getColumnIndex(colDetalle)));

			aux.setType(cursor.getInt(cursor.getColumnIndex(colTipoMensaje)));
		}
		cursor.close();
		db.close();
		return aux;
		
	}
	
	public void deleteConversation(String desti, String user){
			
			SQLiteDatabase db = this.getWritableDatabase();
			
			String params1[] = {user, desti};
			
			String where =  colUsuario + " = ? AND " + colContacto + " = ?";
			db.delete(mensajesWebTable , where, params1);
			db.close();
	
		}

	public void deleteMsgWeb(MensajeWeb msjweb){
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		String params1[] = {msjweb.get_usuario(), msjweb.get_fechaEnvio()};
		
		String where =  colUsuario + " = ? AND " + colFechaEnvioMsj + " = ?";
		db.delete(mensajesWebTable , where, params1);
		db.close();
		
	}
	
	//DataBase Consultas Tablas ContactoBluetooth
		//----------------------------------------------------------	

		//Agrega Contacto bluetooth a la app
		public void addContactoBluetooth(ContactoBluetooth cont) {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();

			cv.put(colIdContactBluetooth, cont.get_id());
			cv.put(colNombreBluetooth, cont.get_nombreBluetooth());
			cv.put(colMAC, cont.get_Mac());
			
			db.insert(contactoBluetoothTable, null, cv);
			db.close();
		}
		
		//Devuelve el Nombre del contacto con el id del usuario de los contactos de la app
		public ContactoBluetooth getNameContactBluetooth(String Userid) {

			SQLiteDatabase db = this.getWritableDatabase();
			ContactoBluetooth aux = null;
			
			String[] values = {Userid};

			Cursor cursor = db.rawQuery("SELECT * FROM " + contactoBluetoothTable
					+ " WHERE " + colIdContactBluetooth + " = ? ", values);

			while (cursor.moveToNext()) {
				
				aux = new ContactoBluetooth();

				aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdContactBluetooth)));

				aux.set_nombreBluetooth(cursor.getString(cursor
						.getColumnIndex(colNombreBluetooth)));
				
				aux.set_Mac(cursor.getString(cursor
						.getColumnIndex(colMAC)));
			}

			cursor.close();
			db.close();
			
			return aux;

		}

		//Borra el contacto web de la app
		public boolean deleteContactoBluetooth(ContactoBluetooth cont){
			
			SQLiteDatabase db = this.getWritableDatabase();
			
			String params[] = {cont.get_nombreBluetooth()};
			
			String where =  colNombreBluetooth + " = ? ";
			
			db.delete(contactoBluetoothTable , where, params);
			
			db.close();
			
			return true;
			
		}
		
		//Cambia el nombre del contacto web de la app asociada a un contacto
		public boolean updateContactoBluetooth(ContactoBluetooth cont){
			
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();

			cv.put(colNombreBluetooth, cont.get_nombreBluetooth());

			String[] values = { String.valueOf(cont.get_id()) };
			
			String Where = colIdContactBluetooth + "= ?";
			
			db.update(contactoBluetoothTable, cv, Where , values);
			db.close();
			
			return true;
		
		}
		
		
	/* Tabla Mensajes Bluetooth
	 * ----------------------------------------------------------------------------------
	 */
	
	public void addMensajeBluetooth(MensajeBluetooth msj) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colUsuarioBluetooth, msj.get_usuario());
		cv.put(colContactoBluetooth, msj.get_contactoMAC());
		cv.put(colFechaEnvioMsjBluetooth, msj.get_fechaEnvio());
		cv.put(colMensajeBluetooth, msj.get_Mensaje());
		cv.put(colTipoBluetooth, msj.get_Type());
		
		db.insert(mensajesBluetoothTable, null, cv);
		db.close();
	}

	public ArrayList<MensajeBluetooth> getLastMsgConversationsBluetooth(String parOrdenFecha, Usuario parUser) {

		ArrayList<MensajeBluetooth> MsgBT = new ArrayList<MensajeBluetooth>();
		
		MensajeBluetooth aux = null;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		String[] select = {parUser.get_nombre()};
		
		
		Cursor cursor = db.rawQuery("SELECT *, max(fechaEnvio) FROM " + mensajesBluetoothTable  +
				" GROUP BY " + colUsuarioBluetooth + " , " + colContactoBluetooth
				+ " ORDER BY " + colFechaEnvioMsjBluetooth + " " + parOrdenFecha, null);

		while (cursor.moveToNext()) {
			aux = new MensajeBluetooth();
			
			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdMensajeBluetooth)));

			aux.set_usuario(cursor.getString(cursor
					.getColumnIndex(colUsuarioBluetooth)));

			aux.set_contactoMAC(cursor.getString(cursor
					.getColumnIndex(colContactoBluetooth)));
		
			aux.set_fechaEnvio(cursor.getString(cursor.getColumnIndex(colFechaEnvioMsjBluetooth)));
			
			aux.set_Mensaje(cursor.getString(cursor.getColumnIndex(colMensajeBluetooth)));

			aux.set_Type(cursor.getInt(cursor.getColumnIndex(colTipoBluetooth)));
			
			MsgBT.add(aux);
		}

		cursor.close();
		db.close();
		return MsgBT;

	}
	
	public ArrayList<MensajeBluetooth> getContactMensajeBluetooth(Contacto cont, Usuario parUsuario) {

		ArrayList<MensajeBluetooth> MsgBT = new ArrayList<MensajeBluetooth>();
		MensajeBluetooth aux = null;
		SQLiteDatabase db = this.getWritableDatabase();
		String values[] = { cont.getNomUserBluetooth(), parUsuario.get_nombre() };

		Cursor cursor = db.rawQuery("SELECT * FROM " + mensajesBluetoothTable
				+ " WHERE " + colContactoBluetooth + " = ?"
				+ " AND " + colUsuarioBluetooth  + " = ?", values);

		while (cursor.moveToNext()) {
			aux = new MensajeBluetooth();
			
			aux.set_id(cursor.getInt(cursor.getColumnIndex(colIdMensajeBluetooth)));

			aux.set_usuario(cursor.getString(cursor
					.getColumnIndex(colUsuarioBluetooth)));

			aux.set_contactoMAC(cursor.getString(cursor
					.getColumnIndex(colContactoBluetooth)));
		
			aux.set_fechaEnvio(cursor.getString(cursor.getColumnIndex(colFechaEnvioMsjBluetooth)));
			
			aux.set_Mensaje(cursor.getString(cursor.getColumnIndex(colMensajeBluetooth)));

			aux.set_Type(cursor.getInt(cursor.getColumnIndex(colTipoBluetooth)));
			
			MsgBT.add(aux);
		}

		cursor.close();
		db.close();
		return MsgBT;

	}
	
	public void deleteConversationBluetooth(Contacto cont, Usuario parUsuario){
		

		SQLiteDatabase db = this.getWritableDatabase();
		
		String params1[] = {parUsuario.get_nombre(), cont.getNomUserBluetooth()};
		
		String where =  colUsuarioBluetooth + " = ? AND " + colContactoBluetooth + " = ?";
		db.delete(mensajesBluetoothTable , where, params1);
		db.close();
	}

//Registro de acciones
	public void insertarRegistroAccion(RegistroAccion registro) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colFechaRegistro, registro.getFecha());
		cv.put(colIdContactoRegistro, registro.getIdContacto());
		cv.put(colNombreContactoRegistro, registro.getNombreContacto());
		cv.put(colTipoAccion, registro.getTipoAccion());
		db.insert(registroAccionTable, null, cv);
		db.close();
	}

	public void eliminarRegistroAccion(int idRegistro) {
		SQLiteDatabase db = this.getWritableDatabase();
		String values[] = { idRegistro + "" };
		db.delete(registroAccionTable, colIdRegistroAccion + "=?", values);
	}

	public List<RegistroAccion> seleccionarRegistroAccionPorContacto(
			int idContacto) {
		List<RegistroAccion> listaRegistros = new ArrayList<RegistroAccion>();
		RegistroAccion registro;
		SQLiteDatabase db = this.getWritableDatabase();
		String values[] = { idContacto + "" };
		Cursor cursor = db.rawQuery("SELECT * FROM " + registroAccionTable
				+ " WHERE " + colIdContactoRegistro + " = ? ", values);
		while (cursor.moveToNext()) {
			registro = new RegistroAccion();
			registro.setIdRegistro(cursor.getInt(cursor
					.getColumnIndex(colIdRegistroAccion)));
			registro.setFecha(cursor.getString(cursor
					.getColumnIndex(colFechaRegistro)));
			registro.setIdContacto(cursor.getString(cursor
					.getColumnIndex(colIdContactoRegistro)));
			registro.setNombreContacto(cursor.getString(cursor
					.getColumnIndex(colNombreContactoRegistro)));
			registro.setTipoAccion(cursor.getString(cursor
					.getColumnIndex(colTipoAccion)));
			listaRegistros.add(registro);
		}
		cursor.close();
		db.close();
		return listaRegistros;
	}
}