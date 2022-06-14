package com.example.peas.MyHolderViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peas.Interface.ItemClickListner;
import com.example.peas.Models.Export;
import com.example.peas.R;

import java.util.ArrayList;

public class ExportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtName, txtProduct, txtPrice, txtStatus, txtQuantity, txtContact,txtdate;
    public ItemClickListner listner;

    public ExportViewHolder(View itemView) {
        super(itemView);

        txtName = (TextView) itemView.findViewById(R.id.name);

        txtdate = (TextView) itemView.findViewById(R.id.DATE1);
        txtProduct = (TextView) itemView.findViewById(R.id.product);
        txtPrice = (TextView) itemView.findViewById(R.id.payment);
        txtQuantity = (TextView) itemView.findViewById(R.id.Quantity);
        txtStatus= (TextView) itemView.findViewById(R.id.status);
        txtContact= (TextView) itemView.findViewById(R.id.contact);

    }
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
}