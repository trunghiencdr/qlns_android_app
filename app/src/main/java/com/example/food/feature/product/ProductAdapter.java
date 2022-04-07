package com.example.food.feature.product;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Domain.Product;
import com.example.food.R;
import com.example.food.feature.home.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductViewHolder> {
    private HomeViewModel homeViewModel;
    private int itemLayout;

    public ProductAdapter(HomeViewModel homeViewModel, @NonNull DiffUtil.ItemCallback<Product> diffCallback, int itemLayout) {
        super(diffCallback);
        this.homeViewModel = homeViewModel;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, null);
        return new ProductViewHolder(
                view,
                homeViewModel);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(getItem(position));

    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtProductName, txtFeeProduct, btnAddProduct, txtProductSale;
        ImageView imgProduct;
        private HomeViewModel homeViewModel;
        public ProductViewHolder(@NonNull View itemView, HomeViewModel homeViewModel) {
            super(itemView);
            this.homeViewModel
                    = homeViewModel;

            txtProductName = itemView.findViewById(R.id.txt_product_name_item);
            txtFeeProduct = itemView.findViewById(R.id.txt_product_price_item);
            txtProductSale = itemView.findViewById(R.id.txt_product_sale_item);
            imgProduct = itemView.findViewById(R.id.img_product_item);
            btnAddProduct = itemView.findViewById(R.id.btn_add_product_item);

        }

        @Override
        public void onClick(View view) {

        }

        public void bind(Product item)  {
            txtProductName.setText(item.getName());
            txtFeeProduct.setText(item.getPrice()+"");
            Picasso.get().load(item.getImage().getLink()).into(imgProduct);
            System.out.println(item.getImage().getLink());
//            imgProduct.setImageBitmap(item.getImageBitmap());
        }
    }

    public static class ProductDiff extends DiffUtil.ItemCallback<Product>{


        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId()==newItem.getId();
        }


        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getName().equals(newItem.getName())&&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    Double.compare(oldItem.getDiscount(), newItem.getDiscount())==0 &&
                    oldItem.getId()== newItem.getId()&&
                    oldItem.getCalculationUnit().equals(newItem.getCalculationUnit())&&
                    oldItem.getImages().equals(newItem.getImages());
        }
    }
}