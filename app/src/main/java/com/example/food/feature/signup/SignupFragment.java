package com.example.food.feature.signup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.food.R;
import com.example.food.databinding.FragmentSignupBinding;
import com.example.food.dto.UserDTO;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SignupFragment extends Fragment {

    FragmentSignupBinding binding;
    UserViewModel userViewModel;
    UserDTO userDTOtemp;
    User user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls();
        initData();
        addEvents();
    }

    private void initData() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.btnConfirmSignUp.setOnClickListener(view -> {
            signupProcess();
        });


    }

    @SuppressLint("CheckResult")
    private void signupProcess(){
        String username = binding.editTextUsernameSignUp.getText().toString();
        String name = binding.editTextNameSignUp.getText().toString();
        String password = binding.editTextPasswordSignUp.getText().toString();
        String confirmPass = binding.editTextConfirmPasswordSignUp.getText().toString();
        if(password.equals(confirmPass)) {
            userViewModel.makeApiCallSignUp(username, name, password).subscribe(
                    userDTO -> {
                        userDTOtemp = userDTO;
                        if (userDTO.getStatus().equalsIgnoreCase("Ok")) {
                            user = userDTO.getUser();
                            Toast.makeText(requireContext(), "Sign up successfully!", Toast.LENGTH_SHORT).show();
                            AppUtils.saveAccount2(requireActivity(), user);
                            userViewModel.callUpdateTokenFireBaseUser(user.getId(), AppUtils.getTokenFireBase(requireContext()));
                            navigateToHome();
                        }else{
                            Toast.makeText(requireContext(), userDTO.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    , throwable -> {
                        Toast.makeText(requireContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void navigateToHome() {
        NavDirections navDirections = SignupFragmentDirections.actionSignupFragmentToHomeScreenFragment();
        Navigation.findNavController(requireView()).navigate(navDirections);
    }

    private void addEvents() {

    }

    private void addControls() {
    }
}
