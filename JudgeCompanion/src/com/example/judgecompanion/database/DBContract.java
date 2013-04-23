package com.example.judgecompanion.database;

import android.provider.BaseColumns;

public class DBContract {
	private DBContract() {
	};

	public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ DBContract.Events.TABLE_NAME + ", " + DBContract.Teams.TABLE_NAME
			+ ", " + DBContract.Judges.TABLE_NAME + ", " + DBContract.Scores.TABLE_NAME;

	public static abstract class Events implements BaseColumns {
		public static final String TABLE_NAME = "Events";

		public static final String COLUMN_NAME_NAME = "Name";
		public static final String COLUMN_TYPE_NAME = "TEXT";

		public static final String COLUMN_NAME_DESCRIPTION = "Description";
		public static final String COLUMN_TYPE_DESCRIPTION = "TEXT";

		public static final String COLUMN_NAME_SCORE = "Score";
		public static final String COLUMN_TYPE_SCORE = "INTEGER";

		public static final String COLUMN_NAME_TIME = "Time";
		public static final String COLUMN_TYPE_TIME = "INTEGER";

		public static final String CREATE_TABLE_QRY = "CREATE TABLE "
				+ TABLE_NAME + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_NAME
				+ " " + COLUMN_TYPE_NAME + "," + COLUMN_NAME_DESCRIPTION + " "
				+ COLUMN_TYPE_DESCRIPTION + "," + COLUMN_NAME_SCORE + " "
				+ COLUMN_TYPE_SCORE + "," + COLUMN_NAME_TIME + " "
				+ COLUMN_TYPE_TIME + " )";
	}

	public static abstract class Teams implements BaseColumns {
		public static final String TABLE_NAME = "Teams";

		public static final String COLUMN_NAME_NAME = "Name";
		public static final String COLUMN_TYPE_NAME = "TEXT";

		public static final String COLUMN_NAME_MEMBERS = "Members";
		public static final String COLUMN_TYPE_MEMBERS = "TEXT";

		public static final String COLUMN_NAME_INSTITUTION = "Institution";
		public static final String COLUMN_TYPE_INSTITUTION = "TEXT";

		public static final String CREATE_TABLE_QRY = "CREATE TABLE "
				+ TABLE_NAME + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_NAME
				+ " " + COLUMN_TYPE_NAME + "," + COLUMN_NAME_MEMBERS + " "
				+ COLUMN_TYPE_MEMBERS + "," + COLUMN_NAME_INSTITUTION + " "
				+ COLUMN_TYPE_INSTITUTION + " )";
	}

	public static abstract class Judges implements BaseColumns {
		public static final String TABLE_NAME = "Judges";

		public static final String COLUMN_NAME_NAME = "Name";
		public static final String COLUMN_TYPE_NAME = "TEXT";

		public static final String COLUMN_NAME_INSTITUTION = "Institution";
		public static final String COLUMN_TYPE_INSTITUTION = "TEXT";

		public static final String COLUMN_NAME_EMAIL = "Score";
		public static final String COLUMN_TYPE_EMAIL = "TEXT";

		public static final String COLUMN_NAME_PASSWORD = "Time";
		public static final String COLUMN_TYPE_PASSWORD = "TEXT";

		public static final String CREATE_TABLE_QRY = "CREATE TABLE "
				+ TABLE_NAME + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_NAME
				+ " " + COLUMN_TYPE_NAME + "," + COLUMN_NAME_INSTITUTION + " "
				+ COLUMN_TYPE_INSTITUTION + "," + COLUMN_NAME_EMAIL + " "
				+ COLUMN_TYPE_EMAIL + "," + COLUMN_NAME_PASSWORD + " "
				+ COLUMN_TYPE_PASSWORD + " )";
	}
	
	public static abstract class Scores implements BaseColumns{
		public static final String TABLE_NAME = "Scores";
		
		public static final String COLUMN_NAME_EVENT_ID = "event_id";
		public static final String COLUMN_TYPE_EVENT_ID = "INT references " + Events.TABLE_NAME + "(" + Events._ID + ")";
		
		public static final String COLUMN_NAME_TEAM_ID = "team_id";
		public static final String COLUMN_TYPE_TEAM_ID = "INT references " + Teams.TABLE_NAME + "(" + Teams._ID + ")";
		
		public static final String COLUMN_NAME_JUDGE_ID = "judge_id";
		public static final String COLUMN_TYPE_JUDGE_ID = "INT references " + Judges.TABLE_NAME + "(" + Judges._ID + ")";
		
		public static final String COLUMN_NAME_TIME = "time";
		public static final String COLUMN_TYPE_TIME = "INTEGER";
		
		public static final String COLUMN_NAME_SCORE = "score";
		public static final String COLUMN_TYPE_SCORE = "REAL";
		
		public static final String CREATE_TABLE_QRY = "CREATE TABLE " 
				+ TABLE_NAME + " (" 
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ COLUMN_NAME_EVENT_ID + " " 
				+ COLUMN_TYPE_EVENT_ID + ","
				+ COLUMN_NAME_TEAM_ID + " " 
				+ COLUMN_TYPE_TEAM_ID + ","
				+ COLUMN_NAME_JUDGE_ID + " " 
				+ COLUMN_TYPE_JUDGE_ID + ","
				+ COLUMN_NAME_SCORE + " " 
				+ COLUMN_TYPE_SCORE + ","
				+ COLUMN_NAME_TIME  + " " 
				+ COLUMN_TYPE_TIME + " )";
	}
}
