package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food.Adapter.CardListAdapter;
import com.example.food.Api.Api;
import com.example.food.Domain.Cart;
import com.example.food.Domain.Order;
import com.example.food.Domain.Product;
import com.example.food.Domain.Response.CartResponse;
import com.example.food.Domain.Response.DiscountResponse;
import com.example.food.Domain.Response.OrderDetailResponse;
import com.example.food.Domain.Response.OrderResponse;
import com.example.food.Listener.CartResponseListener;
import com.example.food.Listener.DeleteCartResponseListener;
import com.example.food.Listener.DiscountResponseListener;
import com.example.food.Listener.InsertCartResponseListener;
import com.example.food.Listener.InsertOrderDetailResponseListener;
import com.example.food.Listener.InsertOrderResponseListener;
import com.example.food.R;
import com.example.food.dto.CartDTO;
import com.example.food.dto.DiscountDTO;
import com.example.food.dto.OrderDetailDTO;
import com.example.food.dto.OrdersDTO;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CartListActivity extends AppCompatActivity {
    RecyclerView recycleViewCart;
    private RecyclerView.Adapter adapterCart;
    Api api;
    TextView ItemTotalFeeTxt,DeliveryFeeTxt,AllTxt,btnCheckOut,txtValueDiscount;
    ArrayList<Cart> carts=new ArrayList<>();
    TextInputEditText edit_discount;
    DiscountDTO discountDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        setControl();
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycleViewCart.setLayoutManager(linearLayoutManager);
        api = new Api(CartListActivity.this);
        User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));
        api.getCartsByUserId(cartResponseListener,Integer.parseInt(user.getId()+""));

        setEvent();


    }

    private void setEvent() {
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date myDate = new Date();
                String date=new SimpleDateFormat("yyyy-MM-dd").format(myDate);
                Log.d("date",date);
                System.out.println(date +"date");
                User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));
//                Order order = new Order(0,user,null,null,"Chua duyet");
                OrdersDTO ordersDTO =new OrdersDTO(user.getId(),date,discountDTO.getId(),"chua duyet");
                api.insertOrder(insertOrderResponseListener,ordersDTO);
                api.deleteCartByUserId(deleteCartResponseListener,Integer.parseInt(user.getId()+""));
            }
        });

        edit_discount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String idDiscount =edit_discount.getText().toString().trim();
                api.getDiscountById(discountResponseListener,idDiscount);

                return false;
            }
        });
    }

    private void setControl() {
        edit_discount=findViewById(R.id.edit_discount);
        recycleViewCart=findViewById(R.id.recycleViewCart);
        ItemTotalFeeTxt=findViewById(R.id.ItemTotalFeeTxt);
        DeliveryFeeTxt=findViewById(R.id.DeliveryFeeTxt);
        btnCheckOut=findViewById(R.id.btnCheckOut);
        txtValueDiscount=findViewById(R.id.txtValueDiscount);
    }
    private final DeleteCartResponseListener deleteCartResponseListener=new DeleteCartResponseListener() {
        @Override
        public void didFetch(String response, String message) {
            Toast.makeText(CartListActivity.this, "Call api delete cart success"+message.toString(),Toast.LENGTH_SHORT).show();

            Log.d("success",message.toString());
        }

        @Override
        public void didError(String message) {
            Toast.makeText(CartListActivity.this,"Call api delete cart error"+message.toString(),Toast.LENGTH_SHORT).show();

            Log.d("zzz",message.toString());
        }
    };
    private final InsertOrderResponseListener insertOrderResponseListener=new InsertOrderResponseListener() {
        @Override
        public void didFetch(OrderResponse response, String message) {
            Toast.makeText(CartListActivity.this, "Call api insert order success"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("success",message.toString());

            for (int i = 0; i < carts.size(); i++) {
                OrderDetailDTO orderDetailDTO=new OrderDetailDTO(response.getData().getId()
                                                                    ,Integer.parseInt(carts.get(i).getProductDomain().getProductId()+"")
                                                                    ,carts.get(i).getQuantity()
                                                                    ,carts.get(i).getProductDomain().getPrice()
                                                                    ,0);
                api.insertOrderDetails(insertOrderDetailResponseListener,orderDetailDTO);

            }
        }

        @Override
        public void didError(String message) {
            Toast.makeText(CartListActivity.this,"Call api insert order error"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("zzz",message.toString());
        }
    };
    private final InsertOrderDetailResponseListener insertOrderDetailResponseListener=new InsertOrderDetailResponseListener() {
        @Override
        public void didFetch(OrderDetailResponse response, String message) {
            Toast.makeText(CartListActivity.this, "Call api insert order detail success"+message.toString(),Toast.LENGTH_SHORT).show();

        }
        @Override
        public void didError(String message) {
            Toast.makeText(CartListActivity.this,"Call api insert order detail error"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("zzz",message.toString());
        }
    };

    private final DiscountResponseListener discountResponseListener=new DiscountResponseListener() {

        @Override
        public void didFetch(DiscountResponse response, String message) {
            Toast.makeText(CartListActivity.this, "Call api discount success"+message.toString(),Toast.LENGTH_SHORT).show();
            discountDTO=response.getData();
            Date today =new Date();
            if(edit_discount.getText().toString().trim()==""){
                txtValueDiscount.setText("");
                return;
            }
            if(discountDTO.getStartDate().before(today)&&discountDTO.getEndDate().after(today)){
                txtValueDiscount.setText("You have "+discountDTO.getPercent()+" discount");
                return;
            }
                txtValueDiscount.setText("This discount no available");
                return;


        }

        @Override
        public void didError(String message) {
            Toast.makeText(CartListActivity.this,"Call api discount error"+message.toString(),Toast.LENGTH_SHORT).show();
            if(edit_discount.getText().toString().trim()==""){
                txtValueDiscount.setText("");
                return;
            }
            txtValueDiscount.setText("This discount no available");
            return;
        }
    };

    private final CartResponseListener cartResponseListener = new CartResponseListener() {
        @Override
        public void didFetch(ArrayList<Cart> response, String message) {
            if (response != null) {
                adapterCart = new CardListAdapter(response,CartListActivity.this);
                recycleViewCart.setAdapter(adapterCart);

                float itemTotalFee=0;
                int deliveryFee=0;
                int sl=0;
                for (int i = 0; i < response.size(); i++) {
                    itemTotalFee+=response.get(i).getProductDomain().getPrice()*response.get(i).getQuantity();
                    sl+=response.get(i).getQuantity();
                    carts.add(response.get(i));
                }
                ItemTotalFeeTxt.setText(itemTotalFee+"");
                DeliveryFeeTxt.setText((sl*2)+"");
                float all=itemTotalFee+deliveryFee;
//                AllTxt.setText(all+"");
            }
        }

        @Override
        public void didError(String message) {
            Log.d("cart",message);
        }
    };
}