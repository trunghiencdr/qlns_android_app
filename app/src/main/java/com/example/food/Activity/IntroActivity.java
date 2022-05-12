package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.food.R;
import com.example.food.feature.adminhome.AdminActivity;
import com.example.food.feature.signin.SigninFragmentDirections;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.viewmodel.UserViewModel;

public class IntroActivity extends AppCompatActivity {

    String password;
    UserViewModel userViewModel;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, MODE_PRIVATE));
        password = AppUtils.getPassword(this);
        //getSupportActionBar().hide();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("CheckResult")
            @Override
            public void run() {
                if(user!=null && !password.equalsIgnoreCase("")) {
                    userViewModel.makeApiCallSignIn(user.getUsername(), password)
                            .subscribe(userDTO -> {
                                if(userDTO.code()==200){
                                    AppUtils.PASS_LOGIN=1;
                                }else {
                                    AppUtils.PASS_LOGIN=0;
                                }
                                startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                                finish();
                            }, throwable -> Toast.makeText(IntroActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
                }else {
                    startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                    finish();
                }

            }
        }, 0);
    }

    @SuppressLint("CheckResult")
    private void checkPass() {

    }
}