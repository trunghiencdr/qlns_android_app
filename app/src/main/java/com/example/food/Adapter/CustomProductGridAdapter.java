package com.example.food.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.food.Domain.GridProduct;
import com.example.food.R;

import java.util.List;

public class CustomProductGridAdapter extends BaseAdapter {
    private List<GridProduct> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomProductGridAdapter(Context aContext, List<GridProduct> listData) {
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

        GridProduct gridItem = this.listData.get(position);
        holder.name_product.setText(gridItem.getName());
        holder.price_product.setText(gridItem.getPrice()+"");
        holder.calculationUnit_product.setText(gridItem.getCalculationUnit());

        int imageId = this.getMipmapResIdByName(gridItem.getImage());

        holder.image_product.setImageResource(imageId);

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
