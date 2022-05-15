package com.example.food.feature.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food.Activity.CartListActivity;
import com.example.food.Activity.OrderedListActivity;
import com.example.food.Domain.Discount;
import com.example.food.R;
import com.example.food.databinding.FragmentHomeSceenBinding;
import com.example.food.feature.category.CategoryAdapter;
import com.example.food.feature.discount.DiscountAdapter;
import com.example.food.feature.map.MapViewActivity;
import com.example.food.feature.map.MapViewModel;
import com.example.food.feature.product.ProductAdapter;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.util.ItemMargin;

import com.example.food.viewmodel.UserViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeScreenFragment extends Fragment implements DiscountAdapter.ClickItem, LocationListener {
    private FragmentHomeSceenBinding binding;
    private HomeViewModel homeViewModel;
    private CategoryAdapter adapterCate;
    private ProductAdapter productAdapter;
    private DiscountAdapter discountAdapter;
    private RecyclerView rvCate, rvPopular, rvDiscount;
    private SliderView slideDiscount;
    private User user;
    private TextView txtName;
    private CircleImageView imgAvt;
    private UserViewModel userViewModel;
    private MapViewModel mapViewModel;
    String location = "10.84898,106.78736";
    int countUpdateAddress=0;

    private List<Discount> discounts;

    private TextView btnSeeAllCategoriesHomeScreen;
    private FloatingActionButton cartBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeSceenBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);


        setControls();
        loadDiscount();
        loadCategories();
        loadProducts();
        setEvents();
    }

    @SuppressLint("CheckResult")
    private void loadDiscount() {
        homeViewModel.getDiscounts()
                .subscribe(
                        response -> {
                            if (response.code() == 200) {
                                discounts = response.body();

                                discountAdapter.setDiscounts(discounts);
                                int duration = discounts.size() * 200;
                                slideDiscount.setSliderAnimationDuration(duration);
                                slideDiscount.setIndicatorAnimationDuration(duration);
                                slideDiscount.setScrollTimeInSec(4);
                                slideDiscount.startAutoCycle();
                            }
                        }
                        , throwable -> {
                            System.out.println("Load discount failed:" + throwable.getLocalizedMessage());
                        }
                );
    }

    @SuppressLint("CheckResult")
    private void loadProducts() {
        homeViewModel.getTop10Products().subscribe(
                response -> {
                    if (response.code() == 200) {
                        System.out.println("size of products:" + response.body().size());
                        productAdapter.submitList(response.body());
                    }
                }
                , throwable -> {
                    System.out.println("throw get products:" + throwable.getMessage());
                }
        );
    }

    private void setEvents() {

        binding.textViewLocation.setOnClickListener(view -> {
            mapViewModel.callGetPlaceFromGeocode(this.location, "vi-VN", getString(R.string.apikey_here_dot_com));
        });

        binding.imageUserHomeScreen.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeScreenFragment_to_profileScreenFragment);
        });

        binding.cartBtn.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), CartListActivity.class));

        });
        binding.orderedBtn.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), OrderedListActivity.class));
        });

    }

    private void setControls() {
        user = AppUtils.getAccount(requireContext().getSharedPreferences(AppUtils.ACCOUNT, 0));
        rvCate = binding.recyclerViewCategoriesHomeScreen;
        rvPopular = binding.recyclerViewPopularHomeScreen;
        rvDiscount = binding.recyclerViewDiscountHomeScreen;
        txtName = binding.txtNameUserHomeScreen;
        imgAvt = binding.imageUserHomeScreen;

        slideDiscount = binding.slideDiscountHomeScreen;

        mapViewModel.callGetPlaceFromGeocode(this.location, "vi-VN", getString(R.string.apikey_here_dot_com));
        getMyLocation();


        // load information user
        userViewModel.getUser(user.getId());
        userViewModel.getuserMultable().observe(requireActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                loadInfoUser(user);
            }
        });

        mapViewModel.getTitlePlace().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textViewLocation.setText(s.split(",")[0]+"");
            }
        });

        mapViewModel.getClickLocation().observe(requireActivity(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean)
                        startActivity(new Intent(requireActivity(), MapViewActivity.class));
                    }
                });

        discounts = new ArrayList<>();
        discountAdapter = new DiscountAdapter(discounts, this);
        slideDiscount.setSliderAdapter(discountAdapter);
        slideDiscount.setIndicatorAnimation(IndicatorAnimationType.WORM);
        slideDiscount.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);


        btnSeeAllCategoriesHomeScreen = binding.btnSeeAllCategoriesHomeScreen;
        cartBtn = binding.cartBtn;


        adapterCate = new CategoryAdapter(homeViewModel, new CategoryAdapter.CategoryDiff());
        rvCate.addItemDecoration(
                new ItemMargin(10, 0, 30, 10));
        rvCate.setLayoutManager(new GridLayoutManager(requireContext(), 2, LinearLayoutManager.HORIZONTAL, false));
        rvCate.setAdapter(adapterCate);


        productAdapter = new ProductAdapter(homeViewModel, new ProductAdapter.ProductDiff(), R.layout.item_product_vertical);
        rvPopular.addItemDecoration(
                new ItemMargin(10, 0, 30, 10));
        rvPopular.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvPopular.setAdapter(productAdapter);


        rvDiscount.addItemDecoration(
                new ItemMargin(10, 0, 30, 10));
        rvDiscount.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvDiscount.setAdapter(productAdapter);

    }

    private void getMyLocation() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(requireContext(), "Location not accept", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        if(countUpdateAddress%200==0){

        }
        countUpdateAddress++;

    }





    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }


    private void loadInfoUser(User user) {
        if(!user.getUsername().equals("") && user.getUsername()!=null){
            txtName.setText("Hi " + user.getUsername());
        }


        if(user.getImageUser()!=null){
//            Picasso.get()
//                    .load(user.getImageUser().getLink())
//                    .into(imgAvt);
//
//            RequestOptions options = new RequestOptions()
//                    .centerCrop()
//                    .placeholder(R.drawable.user_icon)
//                    .error(R.drawable.user_icon);

            Glide.with(this).load(user.getImageUser().getLink()).into(imgAvt);
        }else{
            Glide.with(this).load(R.drawable.user_icon).into(imgAvt);

        }
    }

    @SuppressLint("CheckResult")
    private void loadCategories() {

        homeViewModel.getCategories().subscribe(
          response -> {
              if(response.code()==200){
                  System.out.println("size of categories:" +response.body().size());

                  adapterCate.submitList(response.body());
              }
          }
          , throwable -> {
                    System.out.println("throw get categories:" + throwable.getMessage());
                }
        );

    }

    @Override
    public void showDiscountDetails(String id) {

        NavDirections action = HomeScreenFragmentDirections
                .actionHomeScreenFragmentToDiscountDetailsFragment()
                .setDiscountId(id);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showDiscountDetails(Discount discount) {
        NavDirections action = HomeScreenFragmentDirections
                .actionHomeScreenFragmentToDiscountDetailsFragment()
                ;
        Bundle bundle = new Bundle();
        bundle.putSerializable("discount", discount);
        this.setArguments(bundle);
        Navigation.findNavController(requireView()).navigate(R.id.action_homeScreenFragment_to_discountDetailsFragment,bundle);
    }
}
