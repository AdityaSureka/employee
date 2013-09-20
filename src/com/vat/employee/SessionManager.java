package com.vat.employee;
import java.util.HashMap;

import com.vat.employee.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


 
public class SessionManager  
{
    // Shared Preferences
    SharedPreferences pref;
     
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREF_NAME = "Vatsana";
     
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
     
    // User name (make vsariable public to access from outside)
    public static final  String KEY_USERNAME = "0";
     
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL ="0";
     
    // Constructor
    @SuppressLint("CommitPrefEdits")
	public SessionManager(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    /**
     * Create login session
     * */
    public void createLoginSession( int success,int success2)
    {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
         
        // Storing name in pref
        editor.putInt(KEY_USERNAME, success);
         
        
        // Storing email in pref
        editor.putInt(KEY_EMAIL, success2);
         
        // commit changes
        editor.commit();
   
    }
    	 
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             
            // Staring Login Activity
            _context.startActivity(i);
        }
         
    }    
   
  
    
	

	public HashMap<String, Integer> getUserDetails()
    {
        HashMap<String, Integer> user = new HashMap<String, Integer>();
        // user name
        user.put(KEY_USERNAME, pref.getInt(KEY_USERNAME,  0));
         
        // user email id
       user.put(KEY_EMAIL, pref.getInt(KEY_EMAIL,  0));
         
        // return user
        return user;
    }
     
    
     //Clear session details
     
	public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
         
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
        // Staring Login Activity
        _context.startActivity(i);
    }
     
    
    
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

	
}