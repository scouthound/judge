/**
 * 
 */
package com.example.judgecompanion;

/**
 * @author Siddharth Dahiya
 *
 */
public final class SetupEntry {
	//private variables
    int _id;
    String _entry;
    EntryType _entry_type;
 
    // Empty constructor
    public SetupEntry() {
		// TODO Auto-generated constructor stub
	}
    
    // constructor
    public SetupEntry(int id, String name, EntryType entry_type){
        this._id = id;
        this._entry = name;
        this._entry_type = entry_type;
    }
 
    // constructor
    public SetupEntry(String name, EntryType entry_type){
        this._entry = name;
        this._entry_type = entry_type;
    }
    
    // getting ID
    public int getID(){
        return this._id;
    }
 
    // setting id
    public void setID(int id){
        this._id = id;
    }
 
    // getting name
    public String getEntry(){
        return this._entry;
    }
 
    // setting name
    public void setEntry(String name){
        this._entry = name;
    }
 
    // getting entry type
    public String getEntryType(){
        return this._entry_type.toString();
    }
 
    // setting phone number
    public void setEntryType(EntryType entryType){
        this._entry_type = entryType;
    }
    
    public static enum EntryType
    {
    	JUDGE, TEAM, EVENT
    }
}
