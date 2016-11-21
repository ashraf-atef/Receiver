package com.example.ashraf.receiver.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ashraf.receiver.Model.GoogleAccount;

/**
 * Created by ashraf on 11/19/2016.
 */

public class MySharedPreference {

    public static void saveGoogleAccount(GoogleAccount googleAccount , Context context)
    {

        SharedPreferences sharedPref = context.getSharedPreferences("googleAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("idTooken", googleAccount.getIdTooken());
        editor.putString("photoUrl", googleAccount.getPhotoUrl());
        editor.putString("displayName", googleAccount.getDisplayName());
        editor.putString("email", googleAccount.getEmial());
        editor.commit();
    }
    public static void deleteGoogleAccount(Context context)
    {

        SharedPreferences sharedPref = context.getSharedPreferences("googleAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("idTooken", null);
        editor.putString("photoUrl", null);
        editor.putString("displayName", null);
        editor.putString("email", null);
        editor.commit();
    }
    public static GoogleAccount getGoogleAccount( Context context)
    {

        SharedPreferences sharedPref = context.getSharedPreferences("googleAccount", Context.MODE_PRIVATE);
        if(sharedPref.getString("idTooken",null)!=null)
        {
            GoogleAccount googleAccount=new GoogleAccount();
            googleAccount.setIdTooken(sharedPref.getString("idTooken",null));
            googleAccount.setPhotoUrl(sharedPref.getString("photoUrl",null));
            googleAccount.setDisplayName(sharedPref.getString("displayName",null));
            googleAccount.setEmial(sharedPref.getString("email",null));
            return googleAccount ;
        }
        return null ;
    }
}
