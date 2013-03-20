package com.example.judgecompanion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public final class JudgeOpenHelper extends SQLiteOpenHelper{

	public static final String TABLE_SETUP_ENTRIES = "entries";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_ENTRY = "entry_name";
	  public static final String COLUMN_ENTRY_TYPE = "entry_type";

	  private static final String DATABASE_NAME = "competition.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_SETUP_ENTRIES + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_ENTRY
	      + " text not null," + COLUMN_ENTRY_TYPE + " text not null);";

	  public JudgeOpenHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(JudgeOpenHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETUP_ENTRIES);
	    onCreate(db);
	  }

}
