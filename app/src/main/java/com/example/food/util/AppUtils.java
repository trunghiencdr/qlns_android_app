package com.example.food.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.example.food.R;
import com.example.food.model.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AppUtils {

    public static String BASE_URL="http://192.168.1.15:8080/";
    public static String[] ROLES={"ROLE_USER", "ROLE_ADMIN"};
    public static int PASS_LOGIN=0;

    public static final List<Integer> listBackgroundCategory=
           Arrays.asList(R.drawable.category_background1,
                   R.drawable.category_background2,
                   R.drawable.category_background3,
                   R.drawable.category_background4,
                   R.drawable.category_background5);

    public static final String ACCOUNT = "account";
    public static final String PASSWORD= "password";
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

    @SuppressLint("ResourceType")
    public static Drawable getDrawableBackgroundRandom(Resources resources) throws XmlPullParserException, IOException {
        List<Drawable> list = new ArrayList<>();
        list.add(Drawable.createFromXml(resources, resources.getXml(R.drawable.category_background1)));
        list.add(Drawable.createFromXml(resources, resources.getXml(R.drawable.category_background2)));
        list.add(Drawable.createFromXml(resources, resources.getXml(R.drawable.category_background3)));
        list.add(Drawable.createFromXml(resources, resources.getXml(R.drawable.category_background4)));
        list.add(Drawable.createFromXml(resources, resources.getXml(R.drawable.category_background5)));

        return list.get(new Random().nextInt(listBackgroundCategory.size()));
    }

    public static User getAccount2(Context context){
        String account = context.getSharedPreferences(ACCOUNT, 0).getString(ACCOUNT, "");
        if(account.equalsIgnoreCase("")) return null;
        return new Gson().fromJson(account, User.class);
    }

    public static void saveAccount2(Context context, User user){
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT, 0).edit();
        editor.putString(ACCOUNT, new Gson().toJson(user));
        editor.commit();
    }



    public static void deleteAccount2(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT, 0).edit();
        editor.remove(ACCOUNT);
        editor.commit();
    }

    public static String getPassword(Context context){
        String password = context.getSharedPreferences(ACCOUNT, 0).getString(PASSWORD, "");
        return password;
    }

    public static void savePassword(Context context, String password){
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT, 0).edit();
        editor.putString(PASSWORD, password);
        editor.commit();
    }



    public static void deletePassword(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT, 0).edit();
        editor.remove(PASSWORD);
        editor.commit();
    }

    public static String formatDate(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String getStringFromJsonObject(String key, String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getErrorMessage( String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
