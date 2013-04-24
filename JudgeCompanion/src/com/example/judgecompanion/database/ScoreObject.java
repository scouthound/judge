/**
 * 
 */
package com.example.judgecompanion.database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Siddharth
 *
 */
public final class ScoreObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8897488096151760304L;
	ArrayList<Events> _events = null;
	ArrayList<Teams> _tms = null;
	ArrayList<Score> _scrs = null;
	
	public ScoreObject(ArrayList<Events> events, ArrayList<Teams> tms, ArrayList<Score> scrs)
	{
		_events = new ArrayList<Events>(events);
		_tms = new ArrayList<Teams>(tms);
		_scrs = new ArrayList<Score>(scrs);
	}
}
