package com.example.food.feature.signup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.food.databinding.FragmentSignupBinding;
import com.example.food.dto.UserDTO;
import com.example.food.Domain.User;
import com.example.food.util.AppUtils;
import com.example.food.viewmodel.UserViewModel;

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
        String username = binding.editTextUsernameSignUp.getText().toString() +"";
        String name = binding.editTextNameSignUp.getText().toString() +"";
        String password = binding.editTextPasswordSignUp.getText().toString() +"";
        String confirmPass = binding.editTextConfirmPasswordSignUp.getText().toString() +"";

        if(username.equals("")){
            binding.errorPhoneRegister.setError("Kh??ng ???????c ????? tr???ng");
            binding.editTextUsernameSignUp.requestFocus();
            return;
        }else{
            if(!username.matches("0[0-9]{9}")){
                binding.errorPhoneRegister.setError("Kh??ng ????ng ?????nh d???ng");
                binding.editTextUsernameSignUp.requestFocus();
                return;
            }else{
                binding.errorPhoneRegister.setErrorEnabled(false);
            }
        }

        if(name.equals("")){
            binding.errorNameRegister.setError("Kh??ng ???????c ????? tr???ng");
            binding.editTextNameSignUp.requestFocus();
            return;
        }else{
                binding.errorNameRegister.setErrorEnabled(false);

        }

        if(password.equals("")){
            binding.errorPasswordRegister.setError("Kh??ng ???????c ????? tr???ng");
            binding.editTextPasswordSignUp.requestFocus();
            return;
        }else{
            if(password.length()<6){
                binding.errorPasswordRegister.setError("M???t kh???u ph???i h??n 6 k?? t???");
                binding.editTextPasswordSignUp.requestFocus();
            }else
                binding.errorPasswordRegister.setErrorEnabled(false);

        }

        if(confirmPass.equals("")){
            binding.errorConfirmPasswordRegister.setError("Kh??ng ???????c ????? tr???ng");
            binding.editTextConfirmPasswordSignUp.requestFocus();
            return;
        }else{
            if(!password.equals(confirmPass)){
                binding.errorConfirmPasswordRegister.setError("M???t kh???u kh??ng kh???p");
                binding.editTextConfirmPasswordSignUp.requestFocus();
                return;
            }else{
                binding.errorConfirmPasswordRegister.setErrorEnabled(false);
            }
        }
        if(password.equals(confirmPass)) {
            userViewModel.makeApiCallSignUp(username, name, password).subscribe(
                    userDTO -> {
//                        userDTOtemp = userDTO;
//                        if (userDTO.getStatus().equalsIgnoreCase("Ok")) {
//                            user = userDTO.getUser();
//                            AppUtils.saveAccount2(requireActivity(), user);
//                            AppUtils.savePassword(requireContext(), password);
//                            Toast.makeText(requireContext(), "????ng k?? th??nh c??ng!", Toast.LENGTH_SHORT).show();
//
//                            userViewModel.callUpdateTokenFireBaseUser(user.getId(), AppUtils.getTokenFireBase(requireContext()));
//                            navigateToHome();
//                        }else{
//                            Toast.makeText(requireContext(), "S??? ??i???n tho???i ???? ????ng k?? r???i", Toast.LENGTH_SHORT).show();
//                        }
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
        binding.btnBackSignUp.setOnClickListener(view -> getActivity().onBackPressed());
    }

    private void navigationToSignIn(View view) {
        NavDirections navDirections = SignupFragmentDirections.actionSignupFragmentToSigninFragment();
        Navigation.findNavController(view).navigate(navDirections);
    }

    private void addControls() {
    }
}
