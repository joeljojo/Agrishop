package com.example.peas.MyHolderViews;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.peas.Interface.ItemClickListner;
import com.example.peas.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView txtProductName, txtProductPrice, txtProductQuantity,txttime;
    private ItemClickListner itemClickListner;

    public CartViewHolder(View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        txttime=itemView.findViewById(R.id.cart_product_time);
    }


    @Override
    public void onClick(View v) {

        itemClickListner.onClick(v,getAdapterPosition(),false);

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
