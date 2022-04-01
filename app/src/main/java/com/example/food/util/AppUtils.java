package com.example.food.util;

import android.content.SharedPreferences;

import com.example.food.model.User;
import com.google.gson.Gson;

public class AppUtils {

    public static final String ACCOUNT = "account";

    public static void saveAccount(SharedPreferences share, User user){
        SharedPreferences.Editor editor = share.edit();
        editor.putString(ACCOUNT, new Gson().toJson(user));
        editor.apply();
    }

    public static User getAccount(SharedPreferences share){
        String account = share.getString(ACCOUNT, "");
        if(account.equalsIgnoreCase("")) return null;
        return new Gson().fromJson(account, User.class);
    }

    public static void deleteAccount(SharedPreferences share){
        SharedPreferences.Editor editor = share.edit();
        editor.remove(ACCOUNT);
        editor.commit();
    }
}
