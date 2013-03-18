package com.example.judgecompanion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JudgeOpenHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "judge_DB";
	private static final String KEY_WORD = "tempKey";
	private static final String KEY_DEFINITION = "tempKeyValue";
	private static final String DATABASE_TABLE_NAME = "judge";
	private static final String DATABASE_TABLE_CREATE = "CREATE TABLE" + DATABASE_TABLE_NAME + " (" + KEY_WORD + " TEXT, " + KEY_DEFINITION + " TEXT);";
	
	public JudgeOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db){
		db.execSQL(DATABASE_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
}
