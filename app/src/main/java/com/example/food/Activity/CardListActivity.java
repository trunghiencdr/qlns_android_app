package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.food.Adapter.CardListAdapter;
import com.example.food.Helper.ManagementCart;
import com.example.food.Interface.ChangNumberItemsListener;
import com.example.food.R;

public class CardListActivity extends AppCompatActivity {
private RecyclerView.Adapter adapter;
private  RecyclerView recyclerViewList;
private ManagementCart managementCart;
private TextView totalTxt,taxTxt,deliverytxt,allTxt,emptyTxt;
private double tax;
private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        managementCart = new ManagementCart(this);
        initView();
        initList();
        CalculateCard();
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter=new CardListAdapter(managementCart.getListCard(), this, new ChangNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCard();
            }
        });
        recyclerViewList.setAdapter(adapter);
        if(managementCart.getListCard().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }else{
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }
    private void CalculateCard(){
        double percentTax=0.02;
        double delivery=10;

        tax=Math.round((managementCart.getTotalFee()*percentTax)*100.0)/100.0;
        double total=Math.round((managementCart.getTotalFee()+tax+delivery)*100.0)/100.0;

        totalTxt.setText("$"+managementCart.getTotalFee());
        taxTxt.setText("$"+tax);
        deliverytxt.setText("$"+delivery);
        allTxt.setText("$"+total);
    }
    private void initView() {
        recyclerViewList=findViewById(R.id.recycleView);
        totalTxt=findViewById(R.id.TotalFeeTxt);
        taxTxt=findViewById(R.id.TaxTxt);
        deliverytxt=findViewById(R.id.DeliveryTxt);
        allTxt=findViewById(R.id.AllTxt);
        emptyTxt=findViewById(R.id.emptyTxt);
        scrollView=findViewById(R.id.scrollView2);
    }
}