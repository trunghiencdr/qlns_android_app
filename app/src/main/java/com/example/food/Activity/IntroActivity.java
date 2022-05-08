package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.food.R;
import com.example.food.feature.adminhome.AdminActivity;
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
                    startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                }else{
                    if(user.getRoles().size()>=0){
                        if(user.getRoles().stream().filter(role -> role.getName().equals(AppUtils.ROLES[1])).findFirst().isPresent()){
                            startActivity(new Intent(IntroActivity.this, AdminActivity.class));
                        }else{
                            startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                        }
                    }
                }
                finish();
            }
        }, 0);
    }
}