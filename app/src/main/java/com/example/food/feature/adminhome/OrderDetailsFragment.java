package com.example.food.feature.adminhome;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.food.Domain.Order;
import com.example.food.R;
import com.example.food.databinding.FragmentAdminOrderBinding;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.viewmodel.OrderViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class OrderDetailsFragment extends BottomSheetDialogFragment {
    private static final String KEY_ORDER_DETAILS="order_details";
    private static ClickButton mClickButton;
    private Order mOrder;
    private TextView txtTotal, txtProductName, txtDeliveryAddress, txtOrderId;
    private Button btnCancel, btnAccept;
    private OrderViewModel orderViewModel;
    private View view;


    public interface ClickButton{
        void clickButtonAccept(int idOrder, String state);
    }

    public static OrderDetailsFragment newInstance(Order order, ClickButton clickButton){
        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ORDER_DETAILS, order);
        orderDetailsFragment.setArguments(bundle);
        mClickButton = clickButton;
        return orderDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleReceive = getArguments();
        if(bundleReceive!=null){
            mOrder = (Order) bundleReceive.getSerializable(KEY_ORDER_DETAILS);

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_order_details, null);
        bottomSheetDialog.setContentView(view);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        initView(view);
        setDataOrder();
        btnCancel.setOnClickListener(view1 -> bottomSheetDialog.dismiss());
        btnAccept.setOnClickListener(view1 -> {
            mClickButton.clickButtonAccept(mOrder.getId(), AppUtils.orderState[2]);
            bottomSheetDialog.dismiss();
        });// dda giao
        return bottomSheetDialog;
    }



    public View getView(){
        return view;
    }

    private void initView(View view) {
        txtTotal = view.findViewById(R.id.text_view_total_order);
        txtProductName = view.findViewById(R.id.text_view_product_name_of_order);
        txtDeliveryAddress = view.findViewById(R.id.text_view_address_of_order);
        txtOrderId = view.findViewById(R.id.text_view_order_details_id);
        btnCancel = view.findViewById(R.id.button_cancel_order_details);
        btnAccept = view.findViewById(R.id.button_accept_order_details);
    }

    private void setDataOrder(){
        if(mOrder==null) return;
        if(!mOrder.getState().equals(AppUtils.orderState[0])){
            btnAccept.setVisibility(View.INVISIBLE);
        }else{
            btnAccept.setVisibility(View.VISIBLE);
        }
        txtOrderId.setText("Mã đơn hàng: " + mOrder.getId());
        txtTotal.setText(AppUtils.formatCurrency(mOrder.getTotalPriceOfProducts()));
        txtProductName.setText(mOrder.getProductsName());
        txtDeliveryAddress.setText(mOrder.getUser().getAddress()+"");
    }


}
