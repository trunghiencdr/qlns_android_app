package com.example.food.feature.account.signin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.food.Domain.CategoryDomain;
import com.example.food.R;
import com.example.food.viewmodel.CategoryListViewModel;

import java.util.List;

public class CategoryListAdapter extends ListAdapter<CategoryDomain,CategoryListAdapter.CategoryViewHolder> {

    private CategoryListViewModel categoryListViewModel;
//    private List<CategoryDomain> categories;
//    private Context context;

    public CategoryListAdapter(CategoryListViewModel categoryListViewModel, DiffUtil.ItemCallback<CategoryDomain> callback){
        super(callback);
//        this.categories = categories;
//        this.context = context;
       this.categoryListViewModel = categoryListViewModel;
    }
//    public void setCategories(List<CategoryDomain> categories){
//        this.categories = categories;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public CategoryListAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,null);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.CategoryViewHolder holder, int position) {
        holder.bind(this.getCurrentList().get(position));
//        Glide.with(context)
//                .load(this.categories.get(position).getDescription())
//                .apply(RequestOptions.centerCropTransform())
//                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return this.getCurrentList().size();
    }


    public class  CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView txtCategoryId, txtCategoryName, txtCategoryDes;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryId = itemView.findViewById(R.id.txt_category_id_item);
            txtCategoryName = itemView.findViewById(R.id.txt_category_name_item);
            txtCategoryDes = itemView.findViewById(R.id.txt_category_description_item);
        }

        public void bind(CategoryDomain categoryDomain) {
            txtCategoryId.setText(categoryDomain.getId());
            txtCategoryName.setText(categoryDomain.getName());
//            txtCategoryDes.setText(categoryDomain.getDescription());

        }
    }



    public static class CategoryDiff extends DiffUtil.ItemCallback<CategoryDomain>{


        @Override
        public boolean areItemsTheSame(@NonNull CategoryDomain oldItem, @NonNull CategoryDomain newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategoryDomain oldItem, @NonNull CategoryDomain newItem) {
            return oldItem.getName().equals(newItem.getName())
                    && oldItem.getDescription().equals(newItem.getDescription());
        }
    }
}
