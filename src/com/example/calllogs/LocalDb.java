package com.example.calllogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalDb {
	
	private static final int DATABASE_VERSION=3;
	private DatabaseHelper dbhelp;
	public SQLiteDatabase mdb;
	static final String DATABASE_NAME="Dbase2";
	static final String TABLE_NAME="CallLogs1";
	public static final String CALL_ROWID = "_id";
	public static final String CALL_NUMBER = "cnumber";
	public static final String CALL_DURATION = "cduration";
	public static final String CALL_TYPE = "ctype";
	public static final String TIME="cdateandtime";
	Context context;
 private static final String CallLog = " create table " + TABLE_NAME + " ( " + CALL_ROWID + " INTEGER PRIMARY KEY, " + CALL_NUMBER + " INTEGER, "+ CALL_DURATION + " INTEGER, " + CALL_TYPE + " INTEGER,"  + TIME +" TEXT " + ")";
	
 private static class DatabaseHelper extends SQLiteOpenHelper
 {

	public DatabaseHelper(Context context) 
	{
		super(context, DATABASE_NAME,null,DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		//Log.i("b","b");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CallLog);
		//Log.i("c","c");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IS EXITS "+ CallLog);
		//Log.i("d","d");
		
	}
	 
 }
 public LocalDb(Context c)
 {
	 this.context=c;
 }
 
 public LocalDb open() throws SQLException
 {
     dbhelp = new DatabaseHelper(context);
     mdb = dbhelp.getWritableDatabase();
    // Log.i("open","open");

     return this; 
 }
 
/* public void close()
 {
	 dbhelp.close();
	 Log.i("close","close");
 }*/
 
 public long insert_values(String table, ContentValues values)
 {
	 mdb = dbhelp.getWritableDatabase();
	 return mdb.insert(TABLE_NAME, null, values);
	 
}
 
 public void close()
 {
	 dbhelp.close();
	// Log.i("close","close");
 }
 
 public Cursor fetchInfo()
 {

     String sql = "SELECT CALL_ROWID,CALL_NUMBER,CALL_DURATION,CALL_TYPE,DATE,TIME FROM CallLogs1 ";
     Cursor cur = mdb.rawQuery(sql, null);
    // Log.i("fetch","fetch");
     
     return cur ;

 }
 

public void mdbclose()
 {
	 mdb.close();
	// Log.i("mdb closed","mdb closed");
 }
 
}
