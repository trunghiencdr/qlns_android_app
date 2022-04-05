package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import com.example.food.Adapter.CustomProductGridAdapter;
import com.example.food.Api.Api;
import com.example.food.Domain.Product;
import com.example.food.Listener.ProductResponseListener;
import com.example.food.R;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {
    GridView gridView;
    Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setControl();
        int categoryId= getIntent().getIntExtra("categoryID",0);
        api = new Api(ProductListActivity.this);
        if(categoryId!=0){
            api.getListProductByCategoryId(productResponseListener,categoryId);
        }else {
            api.getProducts(productResponseListener);
        }
    }

    private void setControl() {
        gridView = findViewById(R.id.gridViewProduct);
    }

    private final ProductResponseListener productResponseListener = new ProductResponseListener(){
        @Override
        public void didFetch(ArrayList<Product> response, String message) {

            gridView.setAdapter(new CustomProductGridAdapter(ProductListActivity.this, response));
        }

        @Override
        public void didError(String message) {
            Toast.makeText(ProductListActivity.this,"Call api error"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("zzz",message.toString());
        }
    } ;
}