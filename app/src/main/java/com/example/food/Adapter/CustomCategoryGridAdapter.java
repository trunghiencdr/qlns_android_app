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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.food.Activity.ShowDetailActivity;
import com.example.food.Domain.CategoryDomain;
import com.example.food.Domain.ProductDomain;
import com.example.food.R;

import java.io.Serializable;
import java.util.List;

public class CustomCategoryGridAdapter extends BaseAdapter implements Serializable {
    private List<CategoryDomain> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomCategoryGridAdapter(Context aContext, List<CategoryDomain> listData) {
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
        GridViewCategoryHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.viewholder_cat, null);
            holder = new GridViewCategoryHolder();
            holder.categoryName = convertView.findViewById(R.id.categoryName);
            holder.mainLayout = convertView.findViewById(R.id.mainLayout);
            convertView.setTag(holder);
        } else {
            holder = (GridViewCategoryHolder) convertView.getTag();
        }

        CategoryDomain gridItem = this.listData.get(position);
        holder.categoryName.setText(gridItem.getName());

//        int imageId = this.getMipmapResIdByName(gridItem.getImages().get(0).getLink());
//
//        holder.image_product.setImageResource(imageId);
        View finalConvertView = convertView;
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
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
class GridViewCategoryHolder {
    TextView categoryName;
    ConstraintLayout mainLayout;
}
