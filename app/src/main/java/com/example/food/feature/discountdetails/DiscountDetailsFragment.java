package com.example.food.feature.discountdetails;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.food.Domain.Discount;
import com.example.food.R;
import com.example.food.databinding.FragmentDiscountDetailsBinding;
import com.example.food.util.AppUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscountDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscountDetailsFragment extends Fragment {

    //
    private View view;
    private FragmentDiscountDetailsBinding binding;
    private String voucherId;
    private Discount discount;
    public DiscountDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscountDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscountDetailsFragment newInstance(String param1, String param2) {
        DiscountDetailsFragment fragment = new DiscountDetailsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDiscountDetailsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null){
            voucherId = DiscountDetailsFragmentArgs.fromBundle(getArguments()).getDiscountId();
            discount =(Discount) getArguments().getSerializable("discount");
            loadInfoDiscount(discount);
            Toast.makeText(requireContext(), discount.toString(), Toast.LENGTH_SHORT).show();
        }

        setControls();
        setEvents();

    }

    private void setEvents() {
    }

    private void setControls() {
        binding.buttonUserVoucher.setOnClickListener(view -> {
           ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("discountId", discount.getId());
            clipboardManager.setPrimaryClip(data);
            Toast.makeText(requireContext(), "Đã copy mã khuyến mãi vào bộ nhớ tạm", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadInfoDiscount(Discount discount) {
        binding.textViewVoucherName.setText(discount.getId() +" | " + discount.getPercent());
        binding.textViewEndDateVoucher.setText(AppUtils.formatDate(discount.getEndDate(), "dd-MM-yyyy"));

        Glide.with(requireContext())
                .load(discount.getImageDiscount().getLink())
                .into(binding.imageViewDiscountImage);
    }
}