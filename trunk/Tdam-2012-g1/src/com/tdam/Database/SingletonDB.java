package com.tdam.Database;

import android.content.Context;

public class SingletonDB {
	
	private DatabaseHelper dbHelper = null;
	private static SingletonDB SingletonDB = null;

	private SingletonDB(Context context) {
		dbHelper = new DatabaseHelper(context.getApplicationContext());
	}

	private static void createInstance(Context context) {
		if (SingletonDB == null)
			SingletonDB = new SingletonDB(context);
	}

	public static SingletonDB getInstance(Context context) {
		if (SingletonDB == null)
			createInstance(context);

		return SingletonDB;
	}

	public DatabaseHelper getDatabaseHelper() {
		return dbHelper;
	}

}
