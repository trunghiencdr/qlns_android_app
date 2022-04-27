package com.example.food.feature.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food.databinding.FragmentForgotPasswordScreenBinding;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordScreenBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForgotPasswordScreenBinding.inflate(inflater);
        return binding.getRoot();
    }
}
