package com.example.food.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.food.Activity.ProductListActivity;
import com.example.food.Activity.ShowDetailActivity;
import com.example.food.Domain.ProductDomain;
import com.example.food.R;

import java.io.Serializable;
import java.util.List;

public class CustomProductGridAdapter extends BaseAdapter implements Serializable {
    private List<ProductDomain> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomProductGridAdapter(Context aContext, List<ProductDomain> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.product_grid_item_layout, null);
            holder = new GridViewHolder();
            holder.image_product = convertView.findViewById(R.id.image_product);
            holder.name_product = (TextView) convertView.findViewById(R.id.name_product);
            holder.price_product = (TextView) convertView.findViewById(R.id.price_product);
            holder.calculationUnit_product = (TextView) convertView.findViewById(R.id.calculationUnit_product);
            holder.addBtn_product = (TextView) convertView.findViewById(R.id.addBtn_product);
            holder.cardViewProduct = convertView.findViewById(R.id.cardViewProduct);
            convertView.setTag(holder);
        } else {
            holder = (GridViewHolder) convertView.getTag();
        }

        ProductDomain gridItem = this.listData.get(position);
        holder.name_product.setText(gridItem.getName());
        holder.price_product.setText(gridItem.getPrice()+"");
        holder.calculationUnit_product.setText(gridItem.getCalculationUnit());

        int imageId = this.getMipmapResIdByName(gridItem.getImages().get(0).getLink());

        holder.image_product.setImageResource(imageId);
        View finalConvertView = convertView;
        holder.cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(finalConvertView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", listData.get(position));
                finalConvertView.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();

        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
        Log.i("CustomGridView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }
}

class GridViewHolder {
    ImageView image_product;
    TextView name_product;
    TextView price_product;
    TextView calculationUnit_product;
    TextView addBtn_product;


    CardView cardViewProduct;
}
