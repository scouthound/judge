package com.example.judgecompanion.database;

public class Score {
	private int _eventID;
	private int _teamID;
	private int _judgeID;

	private int _time;
	private float _score;

	/**
	 * Constructor
	 * @param eventID
	 * @param teamID
	 * @param judgeID
	 * @param time
	 * @param score
	 */
	public Score(int eventID, int teamID, int judgeID, int time, float score) {
		_eventID = eventID;
		_teamID = teamID;
		_judgeID = judgeID;
		_time = time;
		_score = score;
	}

	/**
	 * Get Event ID
	 * @return
	 */
	public int getEventID() {
		return _eventID;
	}

	/**
	 * Get Team ID
	 * @return
	 */
	public int getTeamID() {
		return _teamID;
	}

	/**
	 * Get Judge ID
	 * @return
	 */
	public int getJudgeID() {
		return _judgeID;
	}

	/**
	 * Get Time
	 * @return
	 */
	public int getTime() {
		return _time;
	}

	/**
	 * Get Score
	 * @return
	 */
	public float getScore() {
		return _score;
	}
}
