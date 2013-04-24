package com.example.judgecompanion.database;

import java.util.ArrayList;

public class Teams {
	private int _id;
	
	private String _name;
	private String _institution;
	
	private ArrayList<String> _members;
	
	/**
	 * Constructor
	 * @param name
	 * @param institution
	 * @param members
	 */
	public Teams(String name, String institution, String members)
	{
		this(0, name, institution, members);
	}

	/**
	 * Constructor
	 * @param ID
	 * @param name
	 * @param institution
	 * @param members
	 */
	public Teams(int ID, String name, String institution, String members)
	{
		_id = ID;
		_name = name;
		_institution = institution;
		updateMembers(members);
	}
	
	/**
	 * Parse members list
	 * @param members
	 */
	private void updateMembers(String members) {
		_members = new ArrayList<String>();
		String [] mems = members.split(",");
		for (String memName : mems) {
			_members.add(memName);
		}
	}
	
	/**
	 * Get Team ID
	 * @return
	 */
	public int getID()
	{
		return _id;
	}
	
	/**
	 * Set Team ID Manually
	 * @param ID
	 */
	public void setID(int ID)
	{
		_id = ID;
	}
	
	/**
	 * Get Team Name
	 * @return
	 */
	public String getTeamName()
	{
		return _name;
	}
	
	/**
	 * Get Team institution
	 * @return
	 */
	public String getInstitution()
	{
		return _institution;
	}
	
	/**
	 * Get an {@link ArrayList} of members
	 * @return
	 */
	public ArrayList<String> getMemberList()
	{
		return _members;
	}
	
	public String getMemberListString()
	{
		StringBuilder sb = new StringBuilder();
		for (String t : _members) {
			sb.append(t);
		}
		
		return sb.toString();
	}
	
	/**
	 * Add member to the member list
	 * @param member
	 */
	public void addMember(String member)
	{
		_members.add(member);
	}
	
	/**
	 * Remove member from the list.
	 * @param member
	 */
	public void removeMember(String member)
	{
		_members.remove(member);
	}
	
	/**
	 * Returns a String containing the Team Name
	 */
	public String toString()
	{
		return _name;
	}
}
