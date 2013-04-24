/**
 * 
 */
package com.example.judgecompanion.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Siddharth Dahiya
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	private static DBHelper mInstance = null;

	private static final String DATABASE_NAME = "judgecompanionDB";
	private static final int DATABASE_VERSION = 1;

	@SuppressWarnings("unused")
	private Context mCxt;

	public static DBHelper getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new DBHelper(ctx);
		}

		return mInstance;
	}

	private DBHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		this.mCxt = ctx;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			Log.d("DB_EXEC", DBContract.Events.CREATE_TABLE_QRY);
			db.execSQL(DBContract.Events.CREATE_TABLE_QRY);

			Log.d("DB_EXEC", DBContract.Teams.CREATE_TABLE_QRY);
			db.execSQL(DBContract.Teams.CREATE_TABLE_QRY);

			Log.d("DB_EXEC", DBContract.Judges.CREATE_TABLE_QRY);
			db.execSQL(DBContract.Judges.CREATE_TABLE_QRY);

			Log.d("DB_EXEC", DBContract.Scores.CREATE_TABLE_QRY);
			db.execSQL(DBContract.Scores.CREATE_TABLE_QRY);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			Log.e("DB-ERROR", ex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DBContract.SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

	/**
	 * Add a new event to the database
	 * 
	 * @param name
	 *            - Name of the event
	 * @param description
	 *            - Description of the event
	 * @param score
	 *            - Is scoring enabled for the event
	 * @param time
	 *            - Is time-keeping enabled for the event.
	 */
	public void addEvent(String name, String description, boolean score, boolean time) {
		if (name.isEmpty() || description.isEmpty())
			return;
		ContentValues values = new ContentValues();
		values.put(DBContract.Events.COLUMN_NAME_NAME, name);
		values.put(DBContract.Events.COLUMN_NAME_DESCRIPTION, description);
		values.put(DBContract.Events.COLUMN_NAME_SCORE, score ? 1 : 0);
		values.put(DBContract.Events.COLUMN_NAME_TIME, time ? 1 : 0);

		SQLiteDatabase dbs = mInstance.getWritableDatabase();
		dbs.insert(DBContract.Events.TABLE_NAME, null, values);
		dbs.close();
	}

	/**
	 * Get a list of all events
	 * 
	 * @return
	 */
	public ArrayList<Events> getAllEvents() {
		ArrayList<Events> eventList = new ArrayList<Events>();

		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		Cursor mCursor = dbs.query(DBContract.Events.TABLE_NAME, new String[] { DBContract.Events._ID, DBContract.Events.COLUMN_NAME_NAME,
				DBContract.Events.COLUMN_NAME_DESCRIPTION, DBContract.Events.COLUMN_NAME_TIME, DBContract.Events.COLUMN_NAME_SCORE }, null, null,
				null, null, null);

		if (mCursor.moveToFirst()) {
			do {
				int id = mCursor.getInt(0);
				String name = mCursor.getString(1);
				String description = mCursor.getString(2);
				boolean timed = (mCursor.getInt(3) == 1 ? true : false);
				boolean scored = (mCursor.getInt(4) == 1 ? true : false);
				Events evt = new Events(id, name, description, timed, scored);
				eventList.add(evt);
			} while (mCursor.moveToNext());
		}

		return eventList;
	}
	
	/**
	 * Get the number of Events
	 * @return
	 */
	public int getEventCount()
	{
		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		Cursor mCursor = dbs.query(DBContract.Events.TABLE_NAME, new String[] { DBContract.Events._ID, DBContract.Events.COLUMN_NAME_NAME,
				DBContract.Events.COLUMN_NAME_DESCRIPTION, DBContract.Events.COLUMN_NAME_TIME, DBContract.Events.COLUMN_NAME_SCORE }, null, null,
				null, null, null);
		
		return mCursor.getCount();
	}

	/**
	 * Get a all the events in the database.
	 * 
	 * @return a {@link Cursor} for all the events in the database.
	 */
	public Cursor getAllEventsCursor() {
		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		Cursor mCursor = dbs.query(DBContract.Events.TABLE_NAME, new String[] { DBContract.Events._ID, DBContract.Events.COLUMN_NAME_NAME,
				DBContract.Events.COLUMN_NAME_DESCRIPTION, DBContract.Events.COLUMN_NAME_TIME, DBContract.Events.COLUMN_NAME_SCORE }, null, null,
				null, null, null);

		if (mCursor.moveToFirst()) {
			return mCursor;
		} else
			return null;
	}

	/**
	 * Delete the event from the database
	 * 
	 * @param eventID
	 *            - Event ID of the event to be deleted.
	 */
	public void deleteEvent(int eventID) {
		SQLiteDatabase dbs = mInstance.getWritableDatabase();
		dbs.delete(DBContract.Events.TABLE_NAME, DBContract.Events._ID + " = ? ", new String[] { String.valueOf(eventID) });
		dbs.close();
	}

	/**
	 * Add a new Team
	 * 
	 * @param name
	 *            - Team name
	 * @param members
	 *            - Members List
	 * @param inst
	 *            - Name of the Institution
	 */
	public void addTeam(String name, String members, String inst) {
		if (name.isEmpty() || members.isEmpty() || inst.isEmpty())
			return;

		ContentValues values = new ContentValues();
		values.put(DBContract.Teams.COLUMN_NAME_NAME, name);
		values.put(DBContract.Teams.COLUMN_NAME_MEMBERS, members);
		values.put(DBContract.Teams.COLUMN_NAME_INSTITUTION, inst);

		SQLiteDatabase dbs = mInstance.getWritableDatabase();
		Log.d("DB-INSERT", String.valueOf(dbs.insert(DBContract.Teams.TABLE_NAME, null, values)) + " Rows affected");
		dbs.close();
	}

	/**
	 * Get a list of all teams
	 * 
	 * @return
	 */
	public ArrayList<Teams> getAllTeams() {
		ArrayList<Teams> teamsList = new ArrayList<Teams>();

		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		Cursor mCursor = dbs.query(DBContract.Teams.TABLE_NAME, new String[] { DBContract.Teams._ID, DBContract.Teams.COLUMN_NAME_NAME,
				DBContract.Teams.COLUMN_NAME_MEMBERS, DBContract.Teams.COLUMN_NAME_INSTITUTION }, null, null, null, null, null);

		if (mCursor.moveToFirst()) {
			do {
				int id = mCursor.getInt(0);
				String name = mCursor.getString(1);
				String members = mCursor.getString(2);
				String institution = mCursor.getString(3);
				Teams t = new Teams(id, name, institution, members);
				teamsList.add(t);
			} while (mCursor.moveToNext());
		}

		return teamsList;
	}
	
	/**
	 * Get number of teams
	 * @return
	 */
	public int getTeamCount()
	{
		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		Cursor mCursor = dbs.query(DBContract.Teams.TABLE_NAME, new String[] { DBContract.Teams._ID, DBContract.Teams.COLUMN_NAME_NAME,
				DBContract.Teams.COLUMN_NAME_MEMBERS, DBContract.Teams.COLUMN_NAME_INSTITUTION }, null, null, null, null, null);

		return mCursor.getCount();
	}

	/**
	 * Get all teams
	 * 
	 * @return {@link Cursor} of all teams.
	 */
	public Cursor getAllTeamsCursor() {
		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		Cursor mCursor = dbs.query(DBContract.Teams.TABLE_NAME, new String[] { DBContract.Teams._ID, DBContract.Teams.COLUMN_NAME_NAME,
				DBContract.Teams.COLUMN_NAME_MEMBERS, DBContract.Teams.COLUMN_NAME_INSTITUTION }, null, null, null, null, null);

		if (mCursor.moveToFirst()) {
			return mCursor;
		} else {
			return null;
		}
	}

	/**
	 * Delete the team from the list.
	 * 
	 * @param teamID
	 */
	public void deleteTeam(int teamID) {
		SQLiteDatabase dbs = mInstance.getWritableDatabase();
		dbs.delete(DBContract.Teams.TABLE_NAME, DBContract.Teams._ID + " = ? ", new String[] { String.valueOf(teamID) });
		dbs.close();
	}

	/**
	 * Add a new Judge
	 * 
	 * @param name
	 * @param institution
	 * @param email
	 * @param password
	 */
	public void addJudge(String name, String institution, String email, String password) {
		if (name.isEmpty() || institution.isEmpty() || email.isEmpty() || password.isEmpty())
			return;

		ContentValues values = new ContentValues();
		values.put(DBContract.Judges.COLUMN_NAME_NAME, name);
		values.put(DBContract.Judges.COLUMN_NAME_EMAIL, email);
		values.put(DBContract.Judges.COLUMN_NAME_INSTITUTION, institution);
		values.put(DBContract.Judges.COLUMN_NAME_PASSWORD, password);

		SQLiteDatabase dbs = mInstance.getWritableDatabase();
		dbs.insert(DBContract.Judges.TABLE_NAME, null, values);
		dbs.close();
	}

	/**
	 * Get a list of all judges
	 * 
	 * @return
	 */
	public ArrayList<Judges> getAllJudges() {
		ArrayList<Judges> judgeList = new ArrayList<Judges>();

		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		Cursor mCursor = dbs.query(DBContract.Judges.TABLE_NAME, new String[] { DBContract.Judges._ID, DBContract.Judges.COLUMN_NAME_NAME,
				DBContract.Judges.COLUMN_NAME_INSTITUTION, DBContract.Judges.COLUMN_NAME_EMAIL, DBContract.Judges.COLUMN_NAME_PASSWORD }, null, null,
				null, null, null);

		if (mCursor.moveToFirst()) {
			do {
				int id = mCursor.getInt(0);
				String name = mCursor.getString(1);
				String institution = mCursor.getString(2);
				String email = mCursor.getString(3);
				String password = mCursor.getString(4);
				Judges jd = new Judges(id, name, institution, email, password);
				judgeList.add(jd);
			} while (mCursor.moveToNext());
		}

		return judgeList;
	}
	
	/**
	 * Get number of judges.
	 * @return
	 */
	public int getJudgeCount()
	{
		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		Cursor mCursor = dbs.query(DBContract.Judges.TABLE_NAME, new String[] { DBContract.Judges._ID, DBContract.Judges.COLUMN_NAME_NAME,
				DBContract.Judges.COLUMN_NAME_INSTITUTION, DBContract.Judges.COLUMN_NAME_EMAIL, DBContract.Judges.COLUMN_NAME_PASSWORD }, null, null,
				null, null, null);
		
		return mCursor.getCount();
	}

	/**
	 * Get all judges
	 * @return - {@link Cursor} of all judges in the DB.
	 */
	public Cursor getAllJudgesCursor() {
		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		Cursor mCursor = dbs.query(DBContract.Judges.TABLE_NAME, new String[] { DBContract.Judges._ID, DBContract.Judges.COLUMN_NAME_NAME,
				DBContract.Judges.COLUMN_NAME_INSTITUTION, DBContract.Judges.COLUMN_NAME_EMAIL, DBContract.Judges.COLUMN_NAME_PASSWORD }, null, null,
				null, null, null);

		if (mCursor.moveToFirst()) {
			return mCursor;
		} else {
			return null;
		}
	}

	/**
	 * Delete a judge from the list.
	 * 
	 * @param JudgeID
	 */
	public void deleteJudge(int JudgeID) {
		SQLiteDatabase dbs = mInstance.getWritableDatabase();
		dbs.delete(DBContract.Judges.TABLE_NAME, DBContract.Judges._ID + " = ? ", new String[] { String.valueOf(JudgeID) });
		dbs.close();
	}

	/**
	 * Add new scores
	 * 
	 * @param sc
	 *            - {@link Score} to add.
	 */
	public void addScore(Score sc) {
		ContentValues values = new ContentValues();
		values.put(DBContract.Scores.COLUMN_NAME_EVENT_ID, sc.getEventID());
		values.put(DBContract.Scores.COLUMN_NAME_JUDGE_ID, sc.getJudgeID());
		values.put(DBContract.Scores.COLUMN_NAME_TEAM_ID, sc.getTeamID());
		values.put(DBContract.Scores.COLUMN_NAME_TIME, sc.getTime());
		values.put(DBContract.Scores.COLUMN_NAME_SCORE, sc.getScore());

		SQLiteDatabase dbs = mInstance.getWritableDatabase();
		dbs.insert(DBContract.Scores.TABLE_NAME, null, values);
		dbs.close();
	}

	/**
	 * Get Score based on Event ID
	 * 
	 * @param eventID
	 *            - Event ID of the event to get score of
	 * @return {@link ArrayList} of {@link Score}s
	 */
	public ArrayList<Score> getScoreByEvent(int eventID) {
		ArrayList<Score> scoreList = new ArrayList<Score>();

		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		String[] columns = { DBContract.Scores.COLUMN_NAME_EVENT_ID, DBContract.Scores.COLUMN_NAME_TEAM_ID, DBContract.Scores.COLUMN_NAME_JUDGE_ID,
				DBContract.Scores.COLUMN_NAME_TIME, DBContract.Scores.COLUMN_NAME_SCORE };

		Cursor mCursor = dbs.query(DBContract.Scores.TABLE_NAME, columns, DBContract.Scores.COLUMN_NAME_EVENT_ID + " = ? ",
				new String[] { String.valueOf(eventID) }, DBContract.Scores.COLUMN_NAME_TEAM_ID, null, null);

		if (mCursor.moveToFirst()) {
			do {
				int event = mCursor.getInt(0);
				int team = mCursor.getInt(1);
				int judge = mCursor.getInt(2);
				int time = mCursor.getInt(3);
				float score = mCursor.getFloat(4);

				Score scr = new Score(event, team, judge, time, score);
				scoreList.add(scr);
			} while (mCursor.moveToNext());
		}

		return scoreList;
	}

	/**
	 * Get Scores by team
	 * 
	 * @param teamID
	 *            - Team for which scores need to be looked up
	 * @return {@link ArrayList} of {@link Score}s.
	 */
	public ArrayList<Score> getScoreByTeam(int teamID) {
		ArrayList<Score> scoreList = new ArrayList<Score>();

		SQLiteDatabase dbs = mInstance.getReadableDatabase();

		String[] columns = { DBContract.Scores.COLUMN_NAME_EVENT_ID, DBContract.Scores.COLUMN_NAME_TEAM_ID, DBContract.Scores.COLUMN_NAME_JUDGE_ID,
				DBContract.Scores.COLUMN_NAME_TIME, DBContract.Scores.COLUMN_NAME_SCORE };

		Cursor mCursor = dbs.query(DBContract.Scores.TABLE_NAME, columns, DBContract.Scores.COLUMN_NAME_TEAM_ID + " = ? ",
				new String[] { String.valueOf(teamID) }, DBContract.Scores.COLUMN_NAME_TEAM_ID, null, null);

		if (mCursor.moveToFirst()) {
			do {
				int event = mCursor.getInt(0);
				int team = mCursor.getInt(1);
				int judge = mCursor.getInt(2);
				int time = mCursor.getInt(3);
				float score = mCursor.getFloat(4);

				Score scr = new Score(event, team, judge, time, score);
				scoreList.add(scr);
			} while (mCursor.moveToNext());
		}

		return scoreList;
	}
}
