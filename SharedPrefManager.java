package com.example.novelquest;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mAppContext; // Using application context

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "fname";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_LNAME = "lname";

    private SharedPrefManager(Context context) {
        mAppContext = context.getApplicationContext(); // Use application context
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String fname, String lname, String email) {
        SharedPreferences sharedPreferences = mAppContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USERNAME, fname);
        editor.putString(KEY_LNAME, email);
        editor.putString(KEY_USER_EMAIL, lname);

        return editor.commit(); // Use commit() for synchronous execution
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mAppContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.contains(KEY_USER_EMAIL);
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mAppContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        return editor.commit();
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = mAppContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = mAppContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getUserLname() {
        SharedPreferences sharedPreferences = mAppContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LNAME, null);
    }
}
