package com.tdam_2012_g1.database;

import com.tdam_2012_g1.entidadesDAO.Contacto;
import com.tdam_2012_g1.entidadesDAO.Registro;

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
	static final String colContraseña = "contraseña";
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
	
//	static final String registrosTable = "registros";
//	static final String colIdRegistro = "idRegistro";
//	static final String colIdContacto = "idContacto";
//	static final String colFecha = "fecha";
//	static final String colIdTipoRegistro = "idTipoRegistro";
//	
//	static final String tiposRegistroTable = "tiposRegistro";
//	static final String colIdTipo = "id";
//	static final String colNombreTipo = "nombre";
//	

	
//	static final String viewRegistros = "viewRegistros";
//	static final String viewMensajesWeb = "viewMensajesWeb";
	
	public DatabaseHelper(Context context) {
		super(context, dbName, null, 33);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE "+contactosAplicacionTable+" ("+colIdContact+ " INTEGER PRIMARY KEY , "+
				colNombreWeb+ " TEXT)");
		
		db.execSQL("CREATE TABLE "+usuariosTable+"( "+colIdUsuario+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+colNombre+ " TEXT, "+
		colContraseña+ "TEXT, "+colMail+ " TEXT )");
		
		db.execSQL("CREATE TABLE "+conectividadesTable+"( "+colIdConectividad+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+colConexion+ " TEXT, "+
				colEstado+ "TEXT, "+colFechaConexion+ " DATETIME )");
		
		db.execSQL("CREATE TABLE "+mailsTable+"( "+colIdMail+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+colIdUsuarioRemitente+ " INTEGER, "+
				colDestinatario+ "TEXT, "+colFechaEnvio+ " DATETIME )");
		
		db.execSQL("CREATE TABLE "+mensajesWebTable+" ("+colIdMensajeWeb+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				colIdContactoRemitente+" INTEGER NOT NULL, "+
				colIdContactoDestinatario+" INTEGER NOT NULL, "+
				colFechaEnvioMsj+" DATETIME, "+colDetalle+" TEXT NOT NULL);");
		
//		db.execSQL("CREATE TABLE "+registrosTable+" ("+colIdRegistro+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
//				colIdContacto+" INTEGER NOT NULL, "+colFecha+" DATETIME, "+colIdTipoRegistro+" INTEGER NOT NULL);");
//		
//		db.execSQL("CREATE TABLE "+tiposRegistroTable+" ("+colIdTipo+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
//				colNombreTipo+" TEXT NOT NULL);");		

		
		
//		db.execSQL("CREATE TRIGGER fk_regcontact_contactid " +
//				" BEFORE INSERT "+
//				" ON "+registrosTable+
//				
//				" FOR EACH ROW BEGIN"+
//				" SELECT CASE WHEN ((SELECT "+colIdContact+" FROM "+contactoAplicacionTable+" WHERE "+colIdContact+"=new."+colIdContacto+" ) IS NULL)"+
//				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
//				"  END;");
		
//		db.execSQL("CREATE TRIGGER fk_regtipo_tipoid " +
//				" BEFORE INSERT "+
//				" ON "+registrosTable+
//				
//				" FOR EACH ROW BEGIN"+
//				" SELECT CASE WHEN ((SELECT "+colIdTipo+" FROM "+tiposRegistroTable+" WHERE "+colIdTipo+"=new."+colIdTipoRegistro+" ) IS NULL)"+
//				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
//				"  END;");
		
		db.execSQL("CREATE TRIGGER fk_msj_contactoremitente " +
				" BEFORE INSERT "+
				" ON "+mensajesWebTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+colIdContact+" FROM "+contactosAplicacionTable+" WHERE "+colIdContact+"=new."+colIdContactoRemitente+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
		
		db.execSQL("CREATE TRIGGER fk_msj_contactodestinatario " +
				" BEFORE INSERT "+
				" ON "+mensajesWebTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+colIdContact+" FROM "+contactosAplicacionTable+" WHERE "+colIdContact+"=new."+colIdContactoDestinatario+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
		
		db.execSQL("CREATE TRIGGER fk_mail_usuarioremitente " +
				" BEFORE INSERT "+
				" ON "+mailsTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+colIdUsuario+" FROM "+usuariosTable+" WHERE "+colIdUsuario+"=new."+colIdUsuarioRemitente+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
		
		
		
//		db.execSQL("CREATE VIEW "+viewRegistros+
//				" AS SELECT "+registrosTable+"."+colIdRegistro+" AS _id,"+
//				" "+registrosTable+"."+colIdContacto+"AS ID Contacto"+
//				" "+registrosTable+"."+colFecha+","+
//				" "+tiposRegistroTable+"."+colNombreTipo+""+
//				" FROM "+registrosTable+" JOIN "+tiposRegistroTable+
//				" ON "+registrosTable+"."+colIdTipoRegistro+" ="+tiposRegistroTable+"."+colIdTipo
//				);
//		//Inserts pre-defined departments
//		InsertDepts(db);		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE IF EXISTS "+contactosAplicacionTable);
		db.execSQL("DROP TABLE IF EXISTS "+usuariosTable);
		db.execSQL("DROP TABLE IF EXISTS "+conectividadesTable);
		db.execSQL("DROP TABLE IF EXISTS "+mensajesWebTable);
		db.execSQL("DROP TABLE IF EXISTS "+mailsTable);
		
		db.execSQL("DROP TRIGGER IF EXISTS fk_msj_contactoremitente");
		db.execSQL("DROP TRIGGER IF EXISTS fk_msj_contactodestinatario");
		db.execSQL("DROP TRIGGER IF EXISTS fk_mail_usuarioremitente");
//		db.execSQL("DROP VIEW IF EXISTS "+viewRegistros);
		onCreate(db);
	}
	
	void AddContacto(Contacto cont)
	{		 
		SQLiteDatabase db= this.getWritableDatabase();		 
		
		ContentValues cv=new ContentValues();
		
		cv.put(colIdContact, cont.get_id());
		cv.put(colNombreWeb, cont.get_nombreWeb());
		
		db.insert(contactosAplicacionTable, colIdContact, cv);
		db.close();		
	}
	
	 int getContactoCount()
	 {
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("Select * from "+contactosAplicacionTable, null);
		int x= cur.getCount();
		cur.close();
		return x;
	 }
	 
	 Cursor getAllEmployees()
	 {
		 SQLiteDatabase db=this.getWritableDatabase();//		 
		 
		 //Cursor cur= db.rawQuery("Select "+colID+" as _id , "+colName+", "+colAge+" from "+employeeTable, new String [] {});
		 Cursor cur= db.rawQuery("SELECT * FROM "+contactosAplicacionTable,null);
		 return cur;
		 
	 }
	 
//		void AddRegistro(Registro reg)
//		{		 
//			SQLiteDatabase db= this.getWritableDatabase();		 
//			
//			ContentValues cv=new ContentValues();
//			
//			cv.put(colIdContacto, reg.get_idContacto());
//			cv.put(colFecha, reg.get_fechaRegistro());
//			cv.put(colIdTipoRegistro, reg.get_idTipoRegistro());
//			
//			db.insert(registrosTable, colIdContacto, cv);
//			db.close();		
//		}
//		
//		 int getRegistrosCount()
//		 {
//			SQLiteDatabase db=this.getWritableDatabase();
//			Cursor cur= db.rawQuery("Select * from "+registrosTable, null);
//			int x= cur.getCount();
//			cur.close();
//			return x;
//		 }
//		 
//		 Cursor getAllRegistros()
//		 {
//			 SQLiteDatabase db=this.getWritableDatabase();//		 
//			 
//			 //Cursor cur= db.rawQuery("Select "+colID+" as _id , "+colName+", "+colAge+" from "+employeeTable, new String [] {});
//			 Cursor cur= db.rawQuery("SELECT * FROM "+viewRegistros,null);
//			 return cur;			 
//		 }
		 
		 
	 
	 
	

}
