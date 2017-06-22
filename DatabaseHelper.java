package com.example.exemple_sqlite_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "GestionContacts";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		 * Create the employee table and populate it with sample data.
		 * In step 6, we will move these hardcoded statements to an XML document.
		 */
		String sql = "CREATE TABLE IF NOT EXISTS contacts (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
						"firstName TEXT, " +
						"lastName TEXT, " +
						"telephone TEXT)";
		db.execSQL(sql);
		
		ContentValues values = new ContentValues();

		values.put("firstName", "John");
		values.put("lastName", "Smith");
		values.put("telephone", "617-219-2001");
		db.insert("contacts", "lastName", values);

		values.put("firstName", "Robert");
		values.put("lastName", "Jackson");
		values.put("telephone", "617-219-2001");
		db.insert("contacts", "lastName", values);

		values.put("firstName", "Marie");
		values.put("lastName", "Potter");
		values.put("telephone", "617-219-2001");
		db.insert("contacts", "lastName", values);

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS contacts");
		onCreate(db);
	}



}
