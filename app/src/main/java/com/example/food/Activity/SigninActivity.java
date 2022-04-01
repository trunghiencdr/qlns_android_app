package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food.Adapter.PopularAdapter;
import com.example.food.R;
import com.example.food.model.User;
import com.example.food.viewmodel.CategoryListViewModel;
import com.example.food.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SigninActivity extends AppCompatActivity {

    TextInputEditText txtUsername, txtPassword;
    Button btnSignin;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        addControls();
        addEvents();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

//        userViewModel.makeAPICallGetUsers();
    }

    private void addEvents() {
        btnSignin.setOnClickListener(view -> {
            singinProcess(txtUsername.getText().toString(), txtPassword.getText().toString());
        });
    }

    @SuppressLint("CheckResult")
    private void singinProcess(String username, String password) {
        userViewModel.makeApiCallSignIn(username, password).subscribe(userDTO -> {
            System.out.println("status:" + userDTO.getStatus());
        }, throwable -> {
            throwable.printStackTrace();
        }, ()-> {
            startActivity(new Intent(SigninActivity.this, MainActivity.class));
            Toast.makeText(this, "Sign in successfully!", Toast.LENGTH_SHORT).show();

        });

    }

    private void addControls() {
        txtUsername = findViewById(R.id.edit_text_username_sign_in);
        txtPassword = findViewById(R.id.edit_text_password_sign_in);
        btnSignin = findViewById(R.id.btn_sign_in);
    }
}