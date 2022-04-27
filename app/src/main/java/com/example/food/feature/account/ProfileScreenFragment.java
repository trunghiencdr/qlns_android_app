package com.example.food.feature.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.food.R;
import com.example.food.databinding.FragmentProfileScreenBinding;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import io.reactivex.disposables.CompositeDisposable;

public class ProfileScreenFragment extends Fragment {

    private FragmentProfileScreenBinding binding;
    private User user;
    private boolean checkUser = false;
    private UserViewModel userViewModel;
    private CompositeDisposable disposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileScreenBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUser();
        setControls();
        setEvents();
        onChangeUser();

    }

    private void onChangeUser() {

    }

    private void setEvents() {
        binding.btnEditProfileScreen.setOnClickListener(view -> {
            NavDirections action = ProfileScreenFragmentDirections.actionProfileScreenFragmentToEditProfileFragment();
            Navigation.findNavController(view).navigate(action);
        });

        binding.btnChangePasswordProfileScreen.setOnClickListener(view -> {
            NavDirections action = ProfileScreenFragmentDirections.actionProfileScreenFragmentToChangePasswordFragment();
            Navigation.findNavController(view).navigate(action);
        });

        binding.btnLogOutProfileScreen.setOnClickListener(view -> {
            NavDirections action = ProfileScreenFragmentDirections.actionProfileScreenFragmentToEditProfileFragment();
            Navigation.findNavController(view).navigate(action);
        });
    }

    private void setControls() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void loadUser() {
        user = AppUtils.getAccount2(requireContext());
        if(user==null){
            binding.imgAvtProfileScreen.setImageResource(R.drawable.hashimoto);
            binding.txtNameProfileScreen.setText("Arina Hashimoto");
            binding.txtUserNameProfileScreen.setText("uid:0988958279");
            binding.btnEditProfileScreen.setText("Sign in");
            checkUser=false;
            return;
        }else{
            checkUser = true;
            int userId = user.getId();
            disposable.add(userViewModel.getUser(userId));

//            Picasso.get().load(user)binding.imgAvtProfileScreen



        }
    }
}
