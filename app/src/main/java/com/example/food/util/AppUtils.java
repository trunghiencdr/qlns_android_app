package com.example.food.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.food.R;
import com.example.food.Domain.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Callable;

public class AppUtils {


//    public static String BASE_URL = "http://192.168.1.6:8080/";
//    public static String BASE_URL = "http://192.168.143.130:8080/";
        public static String BASE_URL="http://192.168.1.13:8080/";
    public static String[] ROLES = {"ROLE_USER", "ROLE_ADMIN"};
    public static int PASS_LOGIN = 0;
    public static String[] orderState = {"Chưa duyệt", "Đang giao", "Đã giao", "Đã hủy"};
    public static String[] orderTime = {"Hôm nay", "Tuần này", "Tháng này"};
    public static String dateFormat = "dd-MM-yyyy";

    public static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    public static final String TOKEN_FIREBASE = "tokenFireBase";

    public static final List<Integer> listBackgroundCategory =
            Arrays.asList(R.drawable.category_background1,
                    R.drawable.category_background2,
                    R.drawable.category_background3,
                    R.drawable.category_background4,
                    R.drawable.category_background5);

    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";

    public static void saveAccount(SharedPreferences share, User user) {
        SharedPreferences.Editor editor = share.edit();
        editor.putString(ACCOUNT, new Gson().toJson(user));
        editor.apply();
    }

    public static User getAccount(SharedPreferences share) {
        String account = share.getString(ACCOUNT, "");
        if (account.equalsIgnoreCase("")) return null;
        return new Gson().fromJson(account, User.class);
    }

    public static void deleteAccount(SharedPreferences share) {
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

    public static User getAccount2(Context context) {
        String account = context.getSharedPreferences(ACCOUNT, 0).getString(ACCOUNT, "");
        if (account.equalsIgnoreCase("")) return null;
        return new Gson().fromJson(account, User.class);
    }

    public static void saveAccount2(Context context, User user) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT, 0).edit();
        editor.putString(ACCOUNT, new Gson().toJson(user));
        editor.commit();
    }

    public static void saveTokenFireBase(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT, 0).edit();
        editor.putString(TOKEN_FIREBASE, token);
        editor.commit();
    }

    public static String getTokenFireBase(Context context) {
        return context.getSharedPreferences(ACCOUNT, 0).getString(TOKEN_FIREBASE, "");
    }


    public static void deleteAccount2(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT, 0).edit();
        editor.remove(ACCOUNT);
        editor.commit();
    }

    public static String getPassword(Context context) {
        String password = context.getSharedPreferences(ACCOUNT, 0).getString(PASSWORD, "");
        return password;
    }

    public static void savePassword(Context context, String password) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT, 0).edit();
        editor.putString(PASSWORD, password);
        editor.commit();
    }


    public static void deletePassword(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT, 0).edit();
        editor.remove(PASSWORD);
        editor.commit();
    }

    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String getFirstDayOfWeekNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        // get start of this week in miliseconds
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return sdf.format(calendar.getTime());
    }

    public static String getLastDayOfWeekNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        // get start of this week in miliseconds
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.DATE, 6);
        return sdf.format(calendar.getTime());
    }

    public static String getFirstDayOfMonthNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        // get start of this week in miliseconds
        calendar.set(Calendar.DATE, 1);
        return sdf.format(calendar.getTime());
    }

    public static String getLastDayOfMonthNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        // get start of this week in miliseconds
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DATE, days);
        return sdf.format(calendar.getTime());
    }


    public static String getStringFromJsonObject(String key, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getErrorMessage(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatCurrency(float number) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(number);
    }

    public static void getLocation(Context context, LocationListener locationListener) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    // custom dialog
    public static void showNotiDialog(
            Context context,
            String content,
            Callable<Void> actionAccept
    ) {
        Dialog dialog = new Dialog(context, R.style.CustomDialogStyleNotification);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        TextView txtContentNoti = dialog.findViewById(R.id.txt_content_noti);
        Button btnAcceptNoti = dialog.findViewById(R.id.btn_accept_noti);
        Button btnCancelNoti = dialog.findViewById(R.id.btn_cancel_noti);


        txtContentNoti.setText(content);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_white_color);
        btnCancelNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAcceptNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    actionAccept.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public static void showSuccessDialog(
            Context context,
            String content
    ) {
        Dialog dialog = new Dialog(context, R.style.CustomDialogStyleSuccess);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        TextView txtContentSuccess = dialog.findViewById(R.id.txt_content_success);
        Button btnOk = dialog.findViewById(R.id.btn_ok_success);


        txtContentSuccess.setText(content);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_white_color);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                return;
            }
        });

        dialog.show();
    }

    public static void showErrorDialog(
            Context context,
            String title,
            String content
    ) {
        Dialog dialog = new Dialog(context, R.style.CustomDialogStyleError);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error);
        TextView txtTitleError = dialog.findViewById(R.id.txt_title_error);
        TextView txtContentError = dialog.findViewById(R.id.txt_content_error);
        Button btnTryAgainError = dialog.findViewById(R.id.btn_try_again_error);


        txtTitleError.setText(title);
        txtContentError.setText(content);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_white_color);
        btnTryAgainError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showSuccessDialog(
            Activity activity,
            String content
    ) {
        Dialog dialog = new Dialog(activity, R.style.CustomDialogStyleSuccess);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        TextView txtContentSuccess = dialog.findViewById(R.id.txt_content_success);
        Button btnOk = dialog.findViewById(R.id.btn_ok_success);


        txtContentSuccess.setText(content);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_white_color);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
                return;
            }
        });

        dialog.show();
    }


}
