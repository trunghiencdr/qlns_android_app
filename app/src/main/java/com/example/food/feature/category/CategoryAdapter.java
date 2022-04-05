package com.example.food.feature.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Domain.CategoryDomain;
import com.example.food.R;
import com.example.food.feature.home.HomeScreenFragmentDirections;
import com.example.food.feature.home.HomeViewModel;
import com.example.food.feature.product.ProductScreenFragmentArgs;

public class CategoryAdapter extends ListAdapter<CategoryDomain, CategoryAdapter.CategoryViewHolder>{
    private HomeViewModel homeViewModel;

    public CategoryAdapter(HomeViewModel homeViewModel, @NonNull DiffUtil.ItemCallback<CategoryDomain> diffCallback) {
        super(diffCallback);
        this.homeViewModel = homeViewModel;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cat, parent, false);
        return new CategoryViewHolder(
                view,
                homeViewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        HomeViewModel homeViewModel;
        TextView txt_category_name;
        ConstraintLayout mainLayoutCat;
        CategoryDomain categoryDomain;


        public CategoryViewHolder(@NonNull View itemView, HomeViewModel homeViewModel) {
            super(itemView);
            this.homeViewModel = homeViewModel;
            txt_category_name = itemView.findViewById(R.id.categoryName);
            mainLayoutCat = itemView.findViewById(R.id.mainLayoutCat);

            mainLayoutCat.setOnClickListener(this);
        }


        public void bind(CategoryDomain item) {
            categoryDomain = item;
            txt_category_name.setText(item.getName());

        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.mainLayoutCat){
                Bundle bundle = new Bundle();
                bundle.putInt("categoryId", categoryDomain.getId());
              HomeScreenFragmentDirections.ActionHomeScreenFragmentToProductScreenFragment action = HomeScreenFragmentDirections.actionHomeScreenFragmentToProductScreenFragment().setCategoryId(categoryDomain.getId());

                Navigation.findNavController(view).navigate(action);
//                NavDirections action = HomeScreenFragmentDirections.actionHomeScreenFragmentToProductScreenFragment();

//                Navigation.findNavController(view).navigate(R.id.mainLayoutCat, bundle);
            }

        }
    }

   public static class CategoryDiff extends DiffUtil.ItemCallback<CategoryDomain>{


       @Override
       public boolean areItemsTheSame(@NonNull CategoryDomain oldItem, @NonNull CategoryDomain newItem) {
           return oldItem.getId() == newItem.getId();
       }


       @Override
       public boolean areContentsTheSame(@NonNull CategoryDomain oldItem, @NonNull CategoryDomain newItem) {
           return oldItem.getName().equals(newItem.getDescription()) &&
                   oldItem.getDescription().equals(newItem.getDescription()) &&
                   oldItem.getProducts().equals(newItem.getProducts());
       }
   }

}
