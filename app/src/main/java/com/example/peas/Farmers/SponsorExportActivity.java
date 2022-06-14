package com.example.peas.Farmers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.peas.Models.AdminOrders;
import com.example.peas.MyHolderViews.ExportViewHolder;
import com.example.peas.R;
import com.example.peas.UserDetails.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SponsorExportActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_export);
      databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");
        recyclerView = findViewById(R.id.seller_export_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        EXPORTSTATUS();
    }

    private void EXPORTSTATUS() {
        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(databaseReference.orderByChild("sellerId").equalTo(Prevalent.currentOnlineUser.getId()), AdminOrders.class)
                        .build();

        FirebaseRecyclerAdapter<AdminOrders, ExportViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, ExportViewHolder>(options) {


                    @Override
                    protected void onBindViewHolder(@NonNull ExportViewHolder holder, int position, @NonNull final AdminOrders model) {
                        holder.txtName.setText(model.getName());
                        holder.txtProduct.setText(model.getProductname());
                        holder.txtdate.setText(model.getDate());
                        holder.txtPrice.setText(model.getTotalAmount());
                        holder.txtContact.setText(model.getPhone());
                        holder.txtQuantity.setText( model.getQuantity()+ " "+"packet(s)");
                        holder.txtStatus.setText("The export has been" +" "+ model.getState());
                    }
                    @NonNull
                    @Override
                    public ExportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.export_status, parent, false);
                        ExportViewHolder holder = new ExportViewHolder(view);
                        return holder;
                    }

                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}