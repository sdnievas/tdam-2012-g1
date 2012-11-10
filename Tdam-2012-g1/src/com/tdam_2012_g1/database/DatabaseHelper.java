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
	
	static final String contactoAplicacionTable = "contactosAplicacion";
	static final String colIdContact = "idContact";
	static final String colNombreWeb = "nombreWeb";
	
	static final String registrosTable = "registros";
	static final String colIdRegistro = "idRegistro";
	static final String colIdContacto = "idContacto";
	static final String colFecha = "fecha";
	static final String colIdTipoRegistro = "idTipoRegistro";
	
	static final String tiposRegistroTable = "tiposRegistro";
	static final String colIdTipo = "id";
	static final String colNombreTipo = "nombre";
	
	static final String mensajesWebTable = "mensajesWeb";
	static final String colIdMensajeWeb = "id";
	static final String colNroRegistro = "idRegistro";
	static final String colDetalle = "detalle";
	
	static final String viewRegistros = "viewRegistros";
//	static final String viewMensajesWeb = "viewMensajesWeb";
	
	public DatabaseHelper(Context context) {
		super(context, dbName, null, 33);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE "+contactoAplicacionTable+" ("+colIdContact+ " INTEGER PRIMARY KEY , "+
				colNombreWeb+ " TEXT)");
		
		db.execSQL("CREATE TABLE "+registrosTable+" ("+colIdRegistro+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				colIdContacto+" INTEGER NOT NULL, "+colFecha+" DATETIME, "+colIdTipoRegistro+" INTEGER NOT NULL);");
		
		db.execSQL("CREATE TABLE "+tiposRegistroTable+" ("+colIdTipo+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				colNombreTipo+" TEXT NOT NULL);");
		
		db.execSQL("CREATE TABLE "+mensajesWebTable+" ("+colIdMensajeWeb+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				colNroRegistro+" INTEGER NOT NULL, "+colDetalle+" TEXT NOT NULL);");
		
		
		db.execSQL("CREATE TRIGGER fk_regcontact_contactid " +
				" BEFORE INSERT "+
				" ON "+registrosTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+colIdContact+" FROM "+contactoAplicacionTable+" WHERE "+colIdContact+"=new."+colIdContacto+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
		
		db.execSQL("CREATE TRIGGER fk_regtipo_tipoid " +
				" BEFORE INSERT "+
				" ON "+registrosTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+colIdTipo+" FROM "+tiposRegistroTable+" WHERE "+colIdTipo+"=new."+colIdTipoRegistro+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
		
		db.execSQL("CREATE TRIGGER fk_msjreg_regid " +
				" BEFORE INSERT "+
				" ON "+mensajesWebTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+colIdRegistro+" FROM "+registrosTable+" WHERE "+colIdRegistro+"=new."+colNroRegistro+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
		
		
		
		db.execSQL("CREATE VIEW "+viewRegistros+
				" AS SELECT "+registrosTable+"."+colIdRegistro+" AS _id,"+
				" "+registrosTable+"."+colIdContacto+"AS ID Contacto"+
				" "+registrosTable+"."+colFecha+","+
				" "+tiposRegistroTable+"."+colNombreTipo+""+
				" FROM "+registrosTable+" JOIN "+tiposRegistroTable+
				" ON "+registrosTable+"."+colIdTipoRegistro+" ="+tiposRegistroTable+"."+colIdTipo
				);
//		//Inserts pre-defined departments
//		InsertDepts(db);		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE IF EXISTS "+contactoAplicacionTable);
		db.execSQL("DROP TABLE IF EXISTS "+registrosTable);
		db.execSQL("DROP TABLE IF EXISTS "+tiposRegistroTable);
		db.execSQL("DROP TABLE IF EXISTS "+mensajesWebTable);
		
		db.execSQL("DROP TRIGGER IF EXISTS fk_regcontact_contactid");
		db.execSQL("DROP TRIGGER IF EXISTS fk_regtipo_tipoid");
		db.execSQL("DROP TRIGGER IF EXISTS fk_msjreg_regid");
		db.execSQL("DROP VIEW IF EXISTS "+viewRegistros);
		onCreate(db);
	}
	
	void AddContacto(Contacto cont)
	{		 
		SQLiteDatabase db= this.getWritableDatabase();		 
		
		ContentValues cv=new ContentValues();
		
		cv.put(colIdContact, cont.get_id());
		cv.put(colNombreWeb, cont.get_nombreWeb());
		
		db.insert(contactoAplicacionTable, colIdContact, cv);
		db.close();		
	}
	
	 int getContactoCount()
	 {
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("Select * from "+contactoAplicacionTable, null);
		int x= cur.getCount();
		cur.close();
		return x;
	 }
	 
	 Cursor getAllEmployees()
	 {
		 SQLiteDatabase db=this.getWritableDatabase();//		 
		 
		 //Cursor cur= db.rawQuery("Select "+colID+" as _id , "+colName+", "+colAge+" from "+employeeTable, new String [] {});
		 Cursor cur= db.rawQuery("SELECT * FROM "+contactoAplicacionTable,null);
		 return cur;
		 
	 }
	 
		void AddRegistro(Registro reg)
		{		 
			SQLiteDatabase db= this.getWritableDatabase();		 
			
			ContentValues cv=new ContentValues();
			
			cv.put(colIdContacto, reg.get_idContacto());
			cv.put(colFecha, reg.get_fechaRegistro());
			cv.put(colIdTipoRegistro, reg.get_idTipoRegistro());
			
			db.insert(registrosTable, colIdContacto, cv);
			db.close();		
		}
		
		 int getRegistrosCount()
		 {
			SQLiteDatabase db=this.getWritableDatabase();
			Cursor cur= db.rawQuery("Select * from "+registrosTable, null);
			int x= cur.getCount();
			cur.close();
			return x;
		 }
		 
		 Cursor getAllRegistros()
		 {
			 SQLiteDatabase db=this.getWritableDatabase();//		 
			 
			 //Cursor cur= db.rawQuery("Select "+colID+" as _id , "+colName+", "+colAge+" from "+employeeTable, new String [] {});
			 Cursor cur= db.rawQuery("SELECT * FROM "+viewRegistros,null);
			 return cur;			 
		 }
		 
		 
	 
	 
	

}
