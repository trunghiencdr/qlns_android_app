package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.food.R;
import com.example.food.model.User;
import com.example.food.util.AppUtils;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //getSupportActionBar().hide();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, MODE_PRIVATE));
                if(user==null){
                    startActivity(new Intent(IntroActivity.this, SigninActivity.class));
                }else{
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 3000);
    }
}