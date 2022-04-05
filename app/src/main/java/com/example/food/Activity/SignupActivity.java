package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.food.R;
import com.example.food.dto.UserDTO;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText txtUsername, txtPassword, txtConfirmPassword, txtName;
    Button btnSignup;
    ImageView btnBack;
    UserViewModel userViewModel;
    UserDTO userDTOtemp;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        addControls();
        initData();
        addEvents();
    }

    private void initData() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        btnSignup.setOnClickListener(view -> {
            signupProcess();
        });


    }

    @SuppressLint("CheckResult")
    private void signupProcess(){
        String username = txtUsername.getText().toString();
        String name = txtName.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPass = txtConfirmPassword.getText().toString();
        if(password.equals(confirmPass)) {
            userViewModel.makeApiCallSignUp(username, name, password).subscribe(
                    userDTO -> {
                        userDTOtemp = userDTO;
                        if (userDTO.getStatus().equalsIgnoreCase("Ok")) {
                            user = userDTO.getUser();
                            Toast.makeText(this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                            AppUtils.saveAccount(getSharedPreferences(AppUtils.ACCOUNT, MODE_PRIVATE), user);
                            getSharedPreferences("username", 0).edit().putString("username", user.getUsername()).apply();
                            startActivity(new Intent(this, SigninActivity.class));
                        }else{
                            Toast.makeText(this, userDTO.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            , throwable -> {
                        Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
    private void addEvents() {

    }

    private void addControls() {
        txtUsername = findViewById(R.id.edit_text_username_sign_up);
        txtPassword = findViewById(R.id.edit_text_password_sign_up);
        txtName = findViewById(R.id.edit_text_name_sign_up);
        txtConfirmPassword = findViewById(R.id.edit_text_confirm_password_sign_up);
        btnSignup = findViewById(R.id.btn_confirm_sign_up);
        btnBack = findViewById(R.id.btn_back_sign_up);
    }
}