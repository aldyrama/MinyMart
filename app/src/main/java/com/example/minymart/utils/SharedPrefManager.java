package com.example.minymart.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String TOKEN = "token";

    public static final String SP_USER_APP = "spUserApp";

    public static final String SP_EEMAIL = "spEmail";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_USER_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public void getToken(String token, String value){
        spEditor.putString(token, value);
        spEditor.commit();
    }

    public String setToken(){
        return sp.getString(TOKEN, "");

    }

    public String getSPNama(){
        return sp.getString(SP_EEMAIL, "");
    }

    public Boolean getSPSudahLogin() {
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
}
