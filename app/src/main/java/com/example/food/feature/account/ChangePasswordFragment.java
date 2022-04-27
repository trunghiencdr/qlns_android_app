package com.example.food.feature.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food.databinding.FragmentChangePasswordScreenBinding;

public class ChangePasswordFragment extends Fragment {

    private FragmentChangePasswordScreenBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChangePasswordScreenBinding.inflate(inflater);
        return binding.getRoot();
    }
}
