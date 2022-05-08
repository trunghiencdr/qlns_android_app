package com.example.food.feature.product;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;
import com.example.food.databinding.FragmentProductListByCategoryBinding;
import com.example.food.feature.home.HomeViewModel;
import com.example.food.util.AppUtils;
import com.example.food.util.ItemMargin;

public class ProductScreenFragment extends Fragment {

    private FragmentProductListByCategoryBinding binding;
    private RecyclerView rvProduct;
    private ProductAdapter productAdapter;
    private HomeViewModel homeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductListByCategoryBinding.inflate(inflater);
        return binding.getRoot();

    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvProduct = binding.recyclerViewProductsByCategory;
        rvProduct.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        rvProduct.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);


        productAdapter = new ProductAdapter(homeViewModel,new ProductAdapter.ProductDiff(), R.layout.item_product_horizental);


        rvProduct.setAdapter(productAdapter);

        if(getArguments() != null) {
            // The getPrivacyPolicyLink() method will be created automatically.
            int categoryId = ProductScreenFragmentArgs.fromBundle(getArguments()).getCategoryId();
            homeViewModel.getProductsByCategoryId(categoryId)
            .subscribe(categoryResponseResponse -> {
                if(categoryResponseResponse.code()==200){
                    System.out.println("get product by category thanh cong");
                    productAdapter.submitList(categoryResponseResponse.body());
                }
            }, throwable -> {
                System.out.println("throw get category:" + throwable.getMessage());
            });
        }

    }
}
