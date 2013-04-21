package com.example.judgecompanion.database;

public class Judges {
	private int _id;
	
	private String _name;
	private String _institution;
	private String _email;
	private String _password;
	
	/**
	 * Constructor
	 * @param name
	 * @param institution
	 * @param email
	 * @param password
	 */
	public Judges(String name, String institution, String email, String password)
	{
		this(0, name, institution, email, password);
	}
	
	/**
	 * Constructor
	 * @param ID
	 * @param name
	 * @param institution
	 * @param email
	 * @param password
	 */
	public Judges(int ID, String name, String institution, String email, String password)
	{
		_id = ID;
		_name = name;
		_institution = institution;
		_email = email;
		_password = password;
	}
	
	/**
	 * Get Judge ID
	 * @return
	 */
	public int getID()
	{
		return _id;
	}
	
	/**
	 * Set Judge ID
	 * @param ID
	 */
	public void setID(int ID)
	{
		_id = ID;
	}
	
	/**
	 * Get Judge Name
	 * @return
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * Get Institution Name
	 * @return
	 */
	public String getInstitution()
	{
		return _institution;
	}
	
	/**
	 * Get Email
	 * @return
	 */
	public String getEmail()
	{
		return _email;
	}
	
	/**
	 * Get Password
	 * @return
	 */
	public String getPassword()
	{
		return _password;
	}
}
