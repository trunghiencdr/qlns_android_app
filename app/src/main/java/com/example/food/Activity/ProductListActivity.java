package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.food.Adapter.CustomProductGridAdapter;
import com.example.food.Domain.GridProduct;
import com.example.food.R;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        List<GridProduct> listGridProduct = getListData();
        final GridView gridView = findViewById(R.id.gridViewProduct);
        gridView.setAdapter(new CustomProductGridAdapter(this, listGridProduct));

    }
    private List<GridProduct> getListData() {
        List<GridProduct> list = new ArrayList<GridProduct>();
        GridProduct gridItem = new GridProduct("a", "rau",1,"cai");
        GridProduct gridItem1 = new GridProduct("b", "cai",2,"cai");
        GridProduct gridItem2 = new GridProduct("c", "dau",3,"cai");
        GridProduct gridItem3 = new GridProduct("d", "gao",4,"cai");


        list.add(gridItem);
        list.add(gridItem1);
        list.add(gridItem2);
        list.add(gridItem3);

        return list;
    }
}