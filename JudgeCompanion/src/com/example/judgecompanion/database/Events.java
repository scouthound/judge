/**
 * 
 */
package com.example.judgecompanion.database;

/**
 * @author Siddharth Dahiya
 *
 */
public class Events {
	private int _id;
	
	private String _name;
	private String _description;
	
	private boolean _timed;
	private boolean _scored;
	
	/**
	 * Default Constructor
	 * @param name - Name of the Event
	 * @param description - Description of the event.
	 * @param timed - Is the event timed?
	 * @param scored - Is the event scored?
	 */
	public Events(String name, String description, boolean timed, boolean scored){
		this(-1, name, description, timed, scored);
	}
	
	/**
	 * Alternate Constructor with ID
	 * @param id - ID of the Event.
	 * @param name - Name of the Event
	 * @param description - Description of the event.
	 * @param timed - Is the event timed?
	 * @param scored - Is the event scored?
	 */
	public Events(int id, String name, String description, boolean timed, boolean scored){
		_id = id;
		_name = name;
		_description = description;
		_timed = timed;
		_scored = scored;
	}
	
	
	/**
	 * Setter for ID
	 * @param id
	 */
	public void setID(int id)
	{
		_id = id;
	}
	
	/**
	 * Getter for ID
	 * @return
	 */
	public int getID()
	{
		return _id;
	}
	
	/**
	 * Get event name
	 * @return
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * Get event description
	 * @return
	 */
	public String getDescription()
	{
		return _description;
	}
	
	/**
	 * Is event timed?
	 * @return
	 */
	public boolean isTimed()
	{
		return _timed;
	}
	
	/**
	 * Is event scored?
	 * @return
	 */
	public boolean isScored()
	{
		return _scored;
	}
}
