package com.example.food.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.food.R;
import com.example.food.databinding.FragmentForgotPasswordBinding;
import com.example.food.viewmodel.UserViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {
    FragmentForgotPasswordBinding binding;
    UserViewModel userViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setEvents();
        observer();

    }





    private void setEvents() {
        binding.btnConfirm.setOnClickListener(view -> {
            String sdt = binding.editTextPhone.getText().toString();
            checkSDT(sdt);
        });
        binding.btnBack.setOnClickListener(view -> finish());
    }

    private void observer() {
        userViewModel.getMessage().observe(this, s -> {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });

        userViewModel.getExistUser().observe(this, aBoolean -> {
            if(aBoolean){
                String phoneNumber = binding.editTextPhone.getText().toString();
                if(phoneNumber!=null){
                    Intent intent = new Intent(this, OTPPhoneActivity.class);
                    intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent);
                }

            }
        });
    }



    private void checkSDT(String sdt) {
        userViewModel.checkUserByPhoneNumber(sdt);
    }
}
