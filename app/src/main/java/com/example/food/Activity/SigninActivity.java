package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.food.R;
import com.example.food.dto.UserDTO;
import com.example.food.feature.adminhome.AdminActivity;
import com.example.food.feature.adminhome.HomeAdminFragment;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class SigninActivity extends AppCompatActivity {

    TextInputEditText txtUsername, txtPassword;
    Button btnSignin, btnSignup;
    private UserViewModel userViewModel;
    private User user;
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        addControls();
        initData();
        addEvents();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

//        userViewModel.makeAPICallGetUsers();
    }

    private void initData(){

        String username = getSharedPreferences("username", MODE_PRIVATE).getString("username", "hello");
        if(username!=null){
            txtUsername.setText(username);
        }

    }

    private void addEvents() {
        btnSignin.setOnClickListener(view -> {
            singinProcess(txtUsername.getText().toString(), txtPassword.getText().toString());
        });
        btnSignup.setOnClickListener(view -> startActivity(new Intent(this, SignupActivity.class)));
    }

    @SuppressLint("CheckResult")
    private void singinProcess(String username, String password) {
        userViewModel.makeApiCallSignIn(username, password).subscribe(userDTO -> {
            user = userDTO.getUser();
            if(userDTO.getStatus().equalsIgnoreCase("Ok")){
                AppUtils.saveAccount(getSharedPreferences(AppUtils.ACCOUNT, MODE_PRIVATE), user);
//                if(user.getUsername().equalsIgnoreCase("tnthien"))
//                startActivity(new Intent(SigninActivity.this, HomeActivity.class));
//                else
//                startActivity(new Intent(SigninActivity.this, MainActivity.class));
//                Toast.makeText(this, "Sign in successfully!", Toast.LENGTH_SHORT).show();
                // check role
                if(user.getRoles().size()>=0){
                    if(user.getRoles().stream().filter(role -> role.getName().equals(AppUtils.ROLES[1])).findFirst().isPresent()){
                        startActivity(new Intent(this, AdminActivity.class));
                    }else{
                        startActivity(new Intent(SigninActivity.this, HomeActivity.class));
                    }
                }
            }else{
                Toast.makeText(this, "Sign in failed because " + userDTO.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {
            Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();;

        }, ()-> {
            Toast.makeText(this, "Sign in successfully!", Toast.LENGTH_SHORT).show();

        });

    }

    private void addControls() {
        txtUsername = findViewById(R.id.edit_text_username_sign_in);
        txtPassword = findViewById(R.id.edit_text_password_sign_in);
        btnSignin = findViewById(R.id.btn_sign_in);
        btnSignup = findViewById(R.id.btn_sign_up);
//        cbRemember = findViewById(R.id.checkbox_remember_password);
    }
}