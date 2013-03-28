package com.example.judgecompanion;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.judgecompanion.SetupEntry.EntryType;

public final class JudgeOpenHelper extends SQLiteOpenHelper {

	public static final String TABLE_SETUP_ENTRIES = "setup";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ENTRY = "entry_name";
	public static final String COLUMN_ENTRY_TYPE = "entry_type";

	private static final String DATABASE_NAME = "competition.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_SETUP_ENTRIES + "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_ENTRY + " text not null," + COLUMN_ENTRY_TYPE + " text not null);";

	public JudgeOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(JudgeOpenHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETUP_ENTRIES);
		onCreate(db);
	}
	
	public void addEntry(SetupEntry entry) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_ENTRY, entry.getEntry());
		values.put(COLUMN_ENTRY_TYPE, entry.getEntryType());
		
		db.insert(TABLE_SETUP_ENTRIES, null, values);
		db.close();
	}
	
	public SetupEntry getEntry(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_SETUP_ENTRIES, new String[] {COLUMN_ID, COLUMN_ENTRY, COLUMN_ENTRY_TYPE}, COLUMN_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		SetupEntry ent = new SetupEntry(Integer.parseInt(cursor.getString(0)), cursor.getString(1), EntryType.valueOf(cursor.getString(2)));
		cursor.close();
		return ent;
	}
	
	public List<SetupEntry> getAllEntries(EntryType objType) {
		List<SetupEntry> entryList = new ArrayList<SetupEntry>();
		
		String selectQuery = "SELECT * FROM " + TABLE_SETUP_ENTRIES + " WHERE entry_type LIKE '" + objType.toString() + "'";
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst())
		{
			do
			{
				SetupEntry entry = new SetupEntry();
				entry.setID(Integer.parseInt(cursor.getString(0)));
				entry.setEntry(cursor.getString(1));
				entry.setEntryType(EntryType.valueOf(cursor.getString(2)));
				entryList.add(entry);
			} while(cursor.moveToNext());
		}
		
		cursor.close();
		return entryList;		
	}
	
	public int getEntryCount(EntryType objType) {
	
		String selectQuery = "SELECT * FROM " + TABLE_SETUP_ENTRIES + " WHERE entry_type LIKE '" + objType.toString() + "'";
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		int rtnVal = cursor.getCount();
		
		cursor.close();
		
		return rtnVal;
		
	}
	
	public int updateEntry(SetupEntry ent) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_ENTRY, ent.getEntry());
		values.put(COLUMN_ENTRY_TYPE, ent.getEntry());
		
		return db.update(TABLE_SETUP_ENTRIES, values, COLUMN_ID + " = ?", new String[] { String.valueOf(ent.getID())});
	}
	
	public void deleteEntry(SetupEntry ent) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SETUP_ENTRIES, COLUMN_ID + " = ?", new String[] {String.valueOf(ent.getID())});
		db.close();		
	}

}
