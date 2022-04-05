package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.food.Domain.Product;
import com.example.food.R;

public class ShowDetailActivity extends AppCompatActivity {
private TextView addToCartBtn,titleTxt,feeTxt,descriptionTxt,numberOrderTxt;
private ImageView plusBtn,minusBtn,foodPicBtn,backBtn;
private Product product;
private int numberOrder =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        initView();
        getBundle();
    }

    private void getBundle() {
        product =(Product) getIntent().getSerializableExtra("object");

        int drawableResourceId=this.getResources().getIdentifier(product.getImages().get(0).getLink(),"drawable",this.getPackageName());

        Glide.with(this)
                .load(drawableResourceId)
                .into(foodPicBtn);

        titleTxt.setText(product.getName());
        feeTxt.setText("$"+ product.getPrice());
        descriptionTxt.setText(product.getDescription());
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