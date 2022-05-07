package com.example.food.feature.adminhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.food.Activity.SigninActivity;
import com.example.food.R;
import com.example.food.feature.adminhome.ViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    FloatingActionButton fabCart;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        setUpViewPager();
        fabCart = findViewById(R.id.fab_cart);
        appBarLayout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.tool_bar_admin_main);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.getMenu().findItem(R.id.menu_cart_item).setEnabled(false);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                appBarLayout.setVisibility(View.VISIBLE);
                switch (item.getItemId()){
                    case R.id.fragment_home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.fragment_setting:
                        viewPager2.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

//        viewPager2.setOffscreenPageLimit(2);// attach number of fragment
//        viewPager2.setPageTransformer(new DepthPageTransformer());
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                appBarLayout.setVisibility(View.VISIBLE);
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.fragment_home).setChecked(true);
                        break;
                    case 1:
                        appBarLayout.setVisibility(View.GONE);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.fragment_setting).setChecked(true);
                        break;
                }
            }
        });
        fabCart.setOnClickListener(view ->{
            viewPager2.setCurrentItem(1);
            appBarLayout.setVisibility(View.GONE);
        });


    }


    private void setUpViewPager() {
        viewPager2 = findViewById(R.id.view_pager);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_log_out:
                startActivity(new Intent(this, SigninActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}