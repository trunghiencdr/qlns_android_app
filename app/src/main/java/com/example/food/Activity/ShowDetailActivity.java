package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.food.Domain.FoodDomain;
import com.example.food.Helper.ManagementCart;
import com.example.food.R;

public class ShowDetailActivity extends AppCompatActivity {
private TextView addToCartBtn,titleTxt,feeTxt,descriptionTxt,numberOrderTxt;
private ImageView plusBtn,minusBtn,foodPicBtn,backBtn;
private FoodDomain foodDomain;
private int numberOrder =1;
private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managementCart=new ManagementCart(this);
        initView();
        getBundle();
    }

    private void getBundle() {
        foodDomain=(FoodDomain) getIntent().getSerializableExtra("object");

        int drawableResourceId=this.getResources().getIdentifier(foodDomain.getPic(),"drawable",this.getPackageName());

        Glide.with(this)
                .load(drawableResourceId)
                .into(foodPicBtn);

        titleTxt.setText(foodDomain.getTitle());
        feeTxt.setText("$"+foodDomain.getFee());
        descriptionTxt.setText(foodDomain.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));
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
                foodDomain.setNumberInCart(numberOrder);
                managementCart.insertFood(foodDomain);
            }
        });
    }

    private void initView() {
        addToCartBtn=findViewById(R.id.addToCartBtn);
        titleTxt=findViewById(R.id.titleTxt);
        feeTxt=findViewById(R.id.priceTxt);
        descriptionTxt=findViewById(R.id.descriptionTxt);
        numberOrderTxt=findViewById(R.id.numberOrder);
        plusBtn=findViewById(R.id.plusBtn);
        minusBtn=findViewById(R.id.minusBtn);
        backBtn=findViewById(R.id.backBtn);
        foodPicBtn=findViewById(R.id.foodPic);
    }
}