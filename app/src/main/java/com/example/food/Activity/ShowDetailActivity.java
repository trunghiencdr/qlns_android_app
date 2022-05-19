package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.food.Adapter.CustomProductGridAdapter;
import com.example.food.Adapter.ExpandableTextViewAdapter;
import com.example.food.Api.Api;
import com.example.food.Domain.Cart;
import com.example.food.Domain.Product;
import com.example.food.Domain.Response.CartResponse;
import com.example.food.Domain.Response.ProductResponse;
import com.example.food.Listener.InsertCartResponseListener;
import com.example.food.Listener.OneProductResponseListener;
import com.example.food.Listener.ProductResponseListener;
import com.example.food.R;
import com.example.food.dto.CartDTO;
import com.example.food.feature.category.CategoryAdapter;
import com.example.food.feature.home.HomeViewModel;
import com.example.food.feature.product.ProductAdapter;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.util.ItemMargin;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn,titleTxt,feeTxt,descriptionTxt,numberOrderTxt, txtPriceSale, txtDiscount;
    private ImageView plusBtn,minusBtn,foodPicBtn,backBtn;
    private Product product;
    private int numberOrder =1;
    private RecyclerView recyclerViewProductRelated;
    private HomeViewModel homeViewModel;
    private ProductAdapter productAdapter;
    private Api api;
    private ExpandableListView descriptionExpandListView;

    ExpandableTextViewAdapter adapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        api = new Api(ShowDetailActivity.this);
        initView();
        getBundle();
        setUpTabHost();
        setEvent();

    }



    private void setEvent() {
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));
                Cart cart=new Cart(user,product,numberOrder);
                CartDTO cartDTO=new CartDTO(Integer.parseInt(cart.getUser().getId()+""),Integer.parseInt(cart.getProductDomain().getProductId()+""),cart.getQuantity());
                api.insertCart(insertCartResponseListener,cartDTO);
            }
        });
        descriptionExpandListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                int height = 0;
                for (int i = 0; i < adapter.getChildrenCount(groupPosition); i++) {
                    height += descriptionExpandListView.getChildAt(i).getMeasuredHeight();
                    height += descriptionExpandListView.getDividerHeight();
                }
                descriptionExpandListView.getLayoutParams().height = (height+6)*5;
            }
        });
        descriptionExpandListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                descriptionExpandListView.getLayoutParams().height = 130;
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private final InsertCartResponseListener insertCartResponseListener=new InsertCartResponseListener() {
        @Override
        public void didFetch(CartResponse response, String message) {
            Toast.makeText(ShowDetailActivity.this, "Đã thêm vào giỏ hàng"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("success",message.toString());
        }

        @Override
        public void didError(String message) {
//            Toast.makeText(ShowDetailActivity.this,"Call api error"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("zzz",message.toString());
        }
    };
    private final OneProductResponseListener oneProductResponseListener=new OneProductResponseListener() {
        @Override
        public void didFetch(ProductResponse response, String message) {
            product=response.getData();
            Picasso.get().load(product.getImage().getLink()).into(foodPicBtn);
            System.out.println(product.getImage().getLink());
            txtDiscount.setText("Sale " + product.getDiscount()*100+" %");
            titleTxt.setText(product.getName());
            feeTxt.setText(AppUtils.formatCurrency(product.getPrice()));
            feeTxt.setPaintFlags(feeTxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtPriceSale.setText(AppUtils.formatCurrency(product.getPrice()*(1-product.getDiscount())));

            //descriptionTxt.setText(product.getDescription());
            numberOrderTxt.setText(String.valueOf(numberOrder));
            /////////////////////////////////
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            // Adding child data
            listDataHeader.add("Mô tả sản phẩm");

            // Adding child data
            List<String> top250 = new ArrayList<String>();
            top250.add(product.getDescription());

            listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
            adapter=new ExpandableTextViewAdapter(ShowDetailActivity.this,listDataHeader, listDataChild);
            descriptionExpandListView.setAdapter(adapter);
            loadProducts(product.getCategory().getId());
//            Toast.makeText(ShowDetailActivity.this, "Call api one product success"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("success",message.toString());
        }

        @Override
        public void didError(String message) {
//            Toast.makeText(ShowDetailActivity.this,"Call api one product error"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("zzz",message.toString());
        }
    };

    @SuppressLint("CheckResult")
    private void loadProducts(int id) {

        homeViewModel.getProductsByCategoryId(id).subscribe(
                response -> {
                    if(response.code()==200){
                        System.out.println("size of products:" +response.body().size());


                        productAdapter.submitList(response.body());
                    }
                }
                , throwable -> {
                    System.out.println("throw get products:" + throwable.getMessage());
                }
        );
    }

    private void setUpTabHost() {
        // initiating the tabhost
        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);

        // setting up the tab host
        tabhost.setup();

        // Code for adding Tab 1 to the tabhost
        TabHost.TabSpec spec = tabhost.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);

        // setting the name of the tab 1 as "Tab One"
        spec.setIndicator("Liên quan");

        // adding the tab to tabhost
        tabhost.addTab(spec);

        // Code for adding Tab 2 to the tabhost
        spec = tabhost.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);

        // setting the name of the tab 1 as "Tab Two"
        spec.setIndicator("Đánh giá");
        tabhost.addTab(spec);
    }

    private void getBundle() {
        int productId= Integer.parseInt( getIntent().getSerializableExtra("object")+"");
        api.getProductDomain(oneProductResponseListener,productId);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDetailActivity.this,MainActivity.class));
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder++;
                numberOrderTxt.setText(numberOrder+"");
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOrder>1){
                    numberOrder--;
                    numberOrderTxt.setText(numberOrder+"");
                }

            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }

    private void initView() {
        txtDiscount = findViewById(R.id.txt_product_sale_product_details);
        txtPriceSale = findViewById(R.id.priceTxtSale);
        addToCartBtn=findViewById(R.id.addToCartBtn);
        titleTxt=findViewById(R.id.titleTxt);
        feeTxt=findViewById(R.id.priceTxt);
        //descriptionTxt=findViewById(R.id.descriptionTxt);
        numberOrderTxt=findViewById(R.id.numberOrder);
        plusBtn=findViewById(R.id.plusBtn);
        minusBtn=findViewById(R.id.minusBtn);
        backBtn=findViewById(R.id.backBtn);
        foodPicBtn=findViewById(R.id.foodPic);
        recyclerViewProductRelated=findViewById(R.id.recyclerViewProductRelated);
        descriptionExpandListView=findViewById(R.id.descriptionExpandListView);

        productAdapter = new ProductAdapter(homeViewModel, new ProductAdapter.ProductDiff(), R.layout.item_product_vertical);
        recyclerViewProductRelated.addItemDecoration(
                new ItemMargin(10, 0, 30, 10));
        recyclerViewProductRelated.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProductRelated.setAdapter(productAdapter);




    }
}