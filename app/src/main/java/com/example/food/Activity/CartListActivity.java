package com.example.food.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Adapter.CardListAdapter;
import com.example.food.Api.Api;
import com.example.food.Domain.Cart;
import com.example.food.Domain.Product;
import com.example.food.Domain.Response.DiscountResponse;
import com.example.food.Domain.Response.OrderDetailResponse;
import com.example.food.Domain.Response.OrderResponse;
import com.example.food.Listener.CartResponseListener;
import com.example.food.Listener.DeleteCartResponseListener;
import com.example.food.Listener.DiscountResponseListener;
import com.example.food.Listener.InsertOrderDetailResponseListener;
import com.example.food.Listener.InsertOrderResponseListener;
import com.example.food.R;
import com.example.food.dto.CartForOrderDetail;
import com.example.food.dto.DiscountDTO;
import com.example.food.dto.OrdersDTO;
import com.example.food.model.User;
import com.example.food.repository.CartRepository;
import com.example.food.util.AppUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CartListActivity extends AppCompatActivity implements CardListAdapter.ChangNumberItemsListener {
    RecyclerView recycleViewCart;
    private CardListAdapter adapterCart;
    Api api;
    TextView ItemTotalFeeTxt, DeliveryFeeTxt,
            txtTotalOrder, btnCheckOut,
            txtValueDiscount, txtSaleOfCart,
    txtTotalItemTemp, txtDeliveryItemTemp;
    TextView btn_add_discount;
    ArrayList<Cart> carts = new ArrayList<>();
    TextInputEditText edit_discount;
    DiscountDTO discountDTO = new DiscountDTO();
    ImageView btnBack;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        setControl();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleViewCart.setLayoutManager(linearLayoutManager);
        api = new Api(CartListActivity.this);
        User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));
        api.getCartsByUserId(cartResponseListener,Integer.parseInt(user.getId()+""));

        setEvent();


    }

    private void setEvent() {
        adapterCart = new CardListAdapter(new ArrayList<>(), CartListActivity.this, this);
        recycleViewCart.setAdapter(adapterCart);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date myDate = new Date();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(myDate);
                Log.d("date", date);
                System.out.println(date + "date");
                User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));
//                Order order = new Order(0,user,null,null,"Chua duyet");
                OrdersDTO ordersDTO = new OrdersDTO(user.getId(), date, discountDTO.getId(), AppUtils.orderState[0]);// chưa duyệt
                System.out.println(ordersDTO.toString());
                alertDialog.show();
                api.insertOrder(insertOrderResponseListener,ordersDTO);


            }
        });

        btn_add_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idDiscount =edit_discount.getText().toString().trim();
                api.getDiscountById(discountResponseListener,idDiscount);
            }
        });
        btnBack.setOnClickListener(view -> onBackPressed());
    }

    private void setControl() {
        alertDialog= new SpotsDialog.Builder().setContext(this).setTheme(R.style.CustomProgressBarDialog).build();

        txtTotalItemTemp = findViewById(R.id.text_view_total_item_temp);
        txtDeliveryItemTemp = findViewById(R.id.text_view_delivery_item_temp);
        btnBack = findViewById(R.id.backBtn_your_cart);
        btn_add_discount = findViewById(R.id.btn_add_discount);
        edit_discount = findViewById(R.id.edit_discount);
        recycleViewCart = findViewById(R.id.recycleViewCart);
        ItemTotalFeeTxt = findViewById(R.id.ItemTotalFeeTxt);
        DeliveryFeeTxt = findViewById(R.id.DeliveryFeeTxt);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        txtValueDiscount = findViewById(R.id.txtValueDiscount);
        txtTotalOrder = findViewById(R.id.text_view_total_order_carts);
        txtSaleOfCart = findViewById(R.id.text_view_sale_off_cart);

    }
    private final DeleteCartResponseListener deleteCartResponseListener=new DeleteCartResponseListener() {
        @Override
        public void didFetch(String response, String message) {
//            Toast.makeText(CartListActivity.this, "Call api delete cart success"+message.toString(),Toast.LENGTH_SHORT).show();

            Log.d("success",message.toString());
        }

        @Override
        public void didError(String message) {
//            Toast.makeText(CartListActivity.this,"Call api delete cart error"+message.toString(),Toast.LENGTH_SHORT).show();

            Log.d("zzz",message.toString());
        }
    };
    private final InsertOrderResponseListener insertOrderResponseListener=new InsertOrderResponseListener() {
        @SuppressLint("CheckResult")
        @Override
        public void didFetch(OrderResponse response, String message) {
//            Toast.makeText(CartListActivity.this, "Call api insert order success" + message.toString(), Toast.LENGTH_SHORT).show();
            Log.d("success", message.toString());

            User user = AppUtils.getAccount2(CartListActivity.this);
            if (user != null)
                Api.getRetrofit(AppUtils.BASE_URL)
                        .create(CartRepository.class)
                        .getCartsByUserId(user.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(listResponse -> {
                                    if (listResponse.code() == 200) {
                                        //insert to order detail
                                        List<CartForOrderDetail> cartDTOs = listResponse.body();
                                        for (int i = 0; i < cartDTOs.size(); i++) {
                                            CartForOrderDetail cartTemp = cartDTOs.get(i);
                                            cartTemp.setOrderId(response.getData().getId());
                                            Api.getRetrofit(AppUtils.BASE_URL)
                                                    .create(CartRepository.class)
                                                    .insertOrderDetails(cartDTOs.get(i))
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(responseObjectResponse -> {

                                                            },
                                                            throwable -> Toast.makeText(CartListActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
                                            if (i == carts.size() - 1) {
                                                api.deleteCartByUserId(deleteCartResponseListener,Integer.parseInt(user.getId()+""));
                                               alertDialog.dismiss();
                                               AppUtils.showSuccessDialog(CartListActivity.this,
                                                       "Đặt hàng thành công");
//                                                Toast.makeText(CartListActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
//                                                finish();
                                            }

                                        }

                                    } else {
                                        Log.d("API1", listResponse.errorBody().string());
                                        Toast.makeText(CartListActivity.this,
                                                "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
//                            Toast.makeText(CartListActivity.this,
//                                            throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("API1", throwable.getMessage());
                                });


        }

        @Override
        public void didError(String message) {
//            Toast.makeText(CartListActivity.this,"Call api insert order error"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("zzz",message.toString());
        }
    };
    private final InsertOrderDetailResponseListener insertOrderDetailResponseListener=new InsertOrderDetailResponseListener() {
        @Override
        public void didFetch(OrderDetailResponse response, String message) {
//            Toast.makeText(CartListActivity.this, "Call api insert order detail success"+message.toString(),Toast.LENGTH_SHORT).show();

        }
        @Override
        public void didError(String message) {
//            Toast.makeText(CartListActivity.this,"Call api insert order detail error"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("zzz",message.toString());
        }
    };

    private final DiscountResponseListener discountResponseListener=new DiscountResponseListener() {

        @Override
        public void didFetch(DiscountResponse response, String message) {
//            Toast.makeText(CartListActivity.this, "Call api discount success"+message.toString(),Toast.LENGTH_SHORT).show();
            discountDTO=response.getData();
            Date today =new Date();
            if(edit_discount.getText().toString().trim()=="") {
                txtValueDiscount.setText("0");

                return;
            }
            if(discountDTO.getStartDate().before(today)&&discountDTO.getEndDate().after(today)) {
                txtValueDiscount.setVisibility(View.VISIBLE);
                txtSaleOfCart.setText("Giảm giá:  ");
                txtValueDiscount.setText((int)(discountDTO.getPercent() * 100) + "");
                Float totalItem = Float.parseFloat(txtTotalItemTemp.getText().toString());
                txtTotalOrder.setText(totalItem * (1 - discountDTO.getPercent()) + "");
                return;
            }
            txtValueDiscount.setText("0");
                return;


        }

        @Override
        public void didError(String message) {
//            Toast.makeText(CartListActivity.this,"Call api discount error"+message.toString(),Toast.LENGTH_SHORT).show();
            if(edit_discount.getText().toString().trim()==""){
                txtValueDiscount.setText("0");
                return;
            }
//            txtValueDiscount.setText("This discount no available");
            return;
        }
    };

    private final CartResponseListener cartResponseListener = new CartResponseListener() {
        @Override
        public void didFetch(ArrayList<Cart> response, String message) {
            if (response != null) {
                if (response.size() == 0) btnCheckOut.setEnabled(false);
                else btnCheckOut.setEnabled(true);
                adapterCart.setValue(response);
                float itemTotalFee = 0;
                int deliveryFee = 0;
                int sl = 0;
                for (int i = 0; i < response.size(); i++) {
                    Product productTemp = response.get(i).getProductDomain();
                    float priceTotalProduct = productTemp.getPrice()*(1-productTemp.getDiscount())*response.get(i).getQuantity();
                    itemTotalFee += priceTotalProduct;
                    sl += response.get(i).getQuantity();
                    carts.add(response.get(i));
                }
                ItemTotalFeeTxt.setText(AppUtils.formatCurrency(itemTotalFee));
                txtTotalItemTemp.setText(itemTotalFee+"");
                if(response.size()!=0)
                DeliveryFeeTxt.setText(AppUtils.formatCurrency(30000f));
                txtDeliveryItemTemp.setText("30000");
                float all = itemTotalFee + 30000;
                txtTotalOrder.setText(AppUtils.formatCurrency(all));
            }
        }

        @Override
        public void didError(String message) {
            Log.d("cart", message);
        }
    };

    @Override
    public void changeQuantity(boolean isPlus, float price) {
        float totalItem = Float.parseFloat(txtTotalItemTemp.getText().toString());
        if (isPlus) {
            totalItem += price;
        } else {
            totalItem -= price;
        }

        float discount = Float.parseFloat(txtValueDiscount.getText().toString());
        float delivery = Float.parseFloat(txtDeliveryItemTemp.getText().toString());
        float allFree = totalItem * (1 - discount / 100) + delivery;
        txtTotalItemTemp.setText(totalItem+"");
        ItemTotalFeeTxt.setText(AppUtils.formatCurrency(totalItem));

        txtTotalOrder.setText(AppUtils.formatCurrency(allFree));


    }
}