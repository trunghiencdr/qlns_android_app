package com.example.food.feature.adminhome;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.food.Activity.HomeActivity;
import com.example.food.Domain.request.RequestChangePassword;
import com.example.food.databinding.FragmentChangePasswordScreenBinding;
import com.example.food.feature.account.ChangePasswordFragmentDirections;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.viewmodel.UserViewModel;

public class ChangePasswordFragmentAdmin extends AppCompatActivity {

    private FragmentChangePasswordScreenBinding binding;
    private UserViewModel userViewModel;
    private String navigateTo = "Profile";
    private String phoneNumber = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentChangePasswordScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setControls();
        setEvents();
    }





    private void setEvents() {
        binding.btnBackSignUp.setOnClickListener(view -> finish());
        binding.btnEditProfileScreen.setOnClickListener(view -> {
            if(navigateTo.equals("Home"))
                resetPassword();
            else
            changePassword();
        } );


        // observer user change
        userViewModel.getuserMultable().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                AppUtils.saveAccount2(ChangePasswordFragmentAdmin.this, user);
                Toast.makeText(ChangePasswordFragmentAdmin.this, "Change password success", Toast.LENGTH_SHORT).show();
                finish();
            }


        });

        userViewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ChangePasswordFragmentAdmin.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword() {
        RequestChangePassword request = getRequest();
        if(request!=null)
            // call api change
            userViewModel.callResetPassword(request);
    }



    private void changePassword() {
        //get input
        RequestChangePassword request = getRequest();
        if(request!=null)
        // call api change
        userViewModel.callChangePassword(request);
    }


    private RequestChangePassword getRequest() {
        String oldPass = binding.editTextCurrentPassword.getText().toString();
        String newPass = binding.editTextNewPassword.getText().toString();
        String confirmPass = binding.editTextConfirmPass.getText().toString();
        User user = AppUtils.getAccount2(this);
        String username ="";
        if(user!=null){
            username = user.getUsername();
        }


        RequestChangePassword request = new RequestChangePassword(username, oldPass, newPass, confirmPass);

            if(!request.isValidOldPassword()){
                binding.textInputCurrentPass.setError("Not empty.");
                binding.editTextCurrentPassword.requestFocus();
                return null;
            }else{
                binding.textInputCurrentPass.setErrorEnabled(false);
            }
        if(!request.isValidNewPassword()){
            binding.textInputNewPass.setError("Not empty.");
            binding.editTextNewPassword.requestFocus();
            return null;
        }else{
            binding.textInputNewPass.setErrorEnabled(false);
        }

        if(!request.isValidConfirmPassword()){
            binding.textInputConfirmPass.setError("Not empty.");
            binding.editTextConfirmPass.requestFocus();
            return null;
        }else{
            binding.textInputConfirmPass.setErrorEnabled(false);
        }
        return request;
    }



    private void setControls() {
        // initialize data
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // call api
    }
}
