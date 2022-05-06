package com.example.food.feature.account;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.food.R;
import com.example.food.databinding.FragmentEditProfileScreenBinding;
import com.example.food.dto.UserRequestForUpdate;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.util.ChooseImageUtil;
import com.example.food.viewmodel.UserViewModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileFragment extends Fragment {

    private String KEY_AVT = "file";

    private FragmentEditProfileScreenBinding binding;
    private String CODE = "choose_image_avt";
    private ProgressDialog mProgressDialog;
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileScreenBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initProgressBar();
        setControls();
        setEvents();

    }

    private void initProgressBar() {
        mProgressDialog = new ProgressDialog(requireContext());
        mProgressDialog.setMessage("Please wait...");
    }

    private void setEvents() {
        binding.btnChooseImageAvtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImageUtil.chooseImage(requireContext(), (ImageView) binding.imgAvtEditProfileScreen, CODE);

            }
        });

        binding.btnSaveEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });

        binding.btnBackSignUp.setOnClickListener(view -> {
            NavDirections action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileScreenFragment();
            Navigation.findNavController(view).navigate(action);
        });
    }

    private void updateUser() {

        mProgressDialog.show();
        setUpParameters();



    }

    @SuppressLint("CheckResult")
    private void setUpParameters() {
        UserRequestForUpdate user = getUserRequestForUpdateFromView();
        RequestBody requestBodyUser = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(user));
        String imageChoosed = ChooseImageUtil.getImageString(CODE);
        int id = AppUtils.getAccount(requireContext().getSharedPreferences(AppUtils.ACCOUNT, 0)).getId();
        if(imageChoosed==null || imageChoosed.equals("")){
            userViewModel.apiUpdateUserNotImage(id, requestBodyUser)
                    .subscribe(userResponse -> {
                        if(userResponse.code()==200){
                            System.out.println("Upload file success");
                        }else{
                            System.out.println("Upload file failed");
                        }
                        mProgressDialog.dismiss();
                    }, throwable -> {
                        System.out.println("upload failed"+ throwable.getLocalizedMessage());
                    });
        }else{
            String realPath = ChooseImageUtil.getImageString(CODE).split("//")[1].trim();
            File file  = new File(realPath);
            RequestBody requestBodyFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part multipartAvt = MultipartBody.Part.createFormData(KEY_AVT, file.getName(), requestBodyFile);
            userViewModel.apiUpdateUser(id, requestBodyUser, multipartAvt)
                    .subscribe(userResponse -> {
                        if(userResponse.code()==200){
                            System.out.println("Upload file success");
                        }else{
                            System.out.println("Upload file failed");
                        }
                        mProgressDialog.dismiss();
                    }, throwable -> {
                        System.out.println("upload failed"+ throwable.getLocalizedMessage());
                    });
        }







    }

    private void setControls() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getUser(AppUtils.getAccount2(requireContext()).getId());
        userViewModel.getuserMultable().observe(requireActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                loadInfoUser(user);
            }
        });


//        File file = new File();

    }

    private void loadInfoUser(User user) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);



        Glide.with(this).load(user.getImageUser().getLink()).apply(options).into(binding.imgAvtEditProfileScreen);
//        Picasso.get()
//                .load(user.getImageUser().getLink())
//                .into(binding.imgAvtEditProfileScreen);
        binding.editTextNameEditProfile.setText(user.getName());
        binding.editTextEmailEditProfile.setText(user.getEmail());
        binding.editTextAddressEditProfile.setText(user.getAddress());
    }

    private UserRequestForUpdate getUserRequestForUpdateFromView() {
        if (checkInput()) {
            return new UserRequestForUpdate(
                    binding.editTextNameEditProfile.getText().toString(),
                    binding.editTextEmailEditProfile.getText().toString(),
                    binding.editTextAddressEditProfile.getText().toString()
            );
        } else return null;

    }

    private boolean checkInput() {
        return true;
    }
}
