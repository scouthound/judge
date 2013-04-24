/**
 * 
 */
package com.example.judgecompanion.database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Siddharth This is the setup object. This is passed from servers to
 *         clients to create the setup.
 */
public final class SetupObject implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2843552747169248717L;
	
	ArrayList<Events> evnts = null;
	ArrayList<Teams> tms = null;

	public SetupObject(ArrayList<Events> e, ArrayList<Teams> t) {
		evnts = new ArrayList<Events>(e);
		tms = new ArrayList<Teams>(t);
	}

	/**
	 * Get all events
	 * 
	 * @return {@link ArrayList} of {@link Events}.
	 */
	public ArrayList<Events> getEvents() {
		return evnts;
	}

	/**
	 * Get all teams
	 * 
	 * @return {@link ArrayList} of {@link Teams}.
	 */
	public ArrayList<Teams> getTeams() {
		return tms;
	}

	/**
	 * Set events
	 * 
	 * @param e
	 *            - {@link ArrayList} of {@link Events}.
	 */
	public void setEvents(ArrayList<Events> e) {
		evnts = new ArrayList<Events>(e);
	}

	/**
	 * Set teams
	 * 
	 * @param t
	 *            - {@link ArrayList} of {@link Teams}.
	 */
	public void setTeams(ArrayList<Teams> t) {
		tms = new ArrayList<Teams>(t);
	}
}
