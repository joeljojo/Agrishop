package com.example.peas.MyHolderViews;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.peas.Interface.ItemClickListner;
import com.example.peas.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice, txtProductStatus,txtproductAvailable,txtpackage;
    public ImageView imageView;
    public Button buttonsend;
    public ItemClickListner listner;






    public ProductViewHolder(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
        txtproductAvailable = (TextView) itemView.findViewById(R.id.product_status);
      //  txtproductAvailable = (TextView) itemView.findViewById(R.id.product_available);
        txtpackage= (TextView) itemView.findViewById(R.id.product_packaging);

        buttonsend=(Button)itemView.findViewById(R.id.viewdetailsbtn);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
