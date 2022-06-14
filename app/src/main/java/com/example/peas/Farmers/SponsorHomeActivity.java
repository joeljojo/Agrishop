package com.example.peas.Farmers;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peas.Customers.MainActivity;
import com.example.peas.Models.Products;
import com.example.peas.R;
import com.example.peas.MyHolderViews.ProductViewHolder;
import com.example.peas.UserDetails.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SponsorHomeActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private DatabaseReference unapprovedProductsRef;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())

            {
                case R.id.navigation_home:
                    Intent homeIntent = new Intent(SponsorHomeActivity.this, SponsorHomeActivity.class);
                    startActivity(homeIntent);
                    return true;

                case R.id.navigation_add:

                    Intent addIntent = new Intent(SponsorHomeActivity.this, SponsorProductCategoryActivity.class);
                    startActivity(addIntent);
                    return true;
                case R.id.navigation_export:

                    Intent exportIntent = new Intent(SponsorHomeActivity.this, SponsorExportActivity.class);
                    startActivity(exportIntent);
                    return true;


                case R.id.navigation_logout:
                    final FirebaseAuth mAuth ;
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();

                    Intent intentMain = new Intent(SponsorHomeActivity.this, MainActivity.class);
                    intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentMain);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_home);

        unapprovedProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = findViewById(R.id.seller_home_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
/*

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(unapprovedProductsRef.orderByChild("sellerId").equalTo(Prevalent.currentOnlineUser.getId()),Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products,ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

//                        holder.txtName.setText(model.getName());
//                        holder.txtProduct.setText(model.getAddress());
//                        holder.txtPrice.setText(model.getTotalAmount());
//                        holder.txtContact.setText(model.getPhone());
//                        holder.txtQuantity.setText(" The total exported quantity is= " + model.getCity());
//                        holder.txtStatus.setText("Your export has been" + model.getState());

                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        //holder.txtProductStatus.setText(model.getProductState());
                        holder.txtpackage.setText("Packed in = "+model.getPackage()+  "  each");
                        holder.txtproductAvailable.setText( " Available packets= "+model.getPavail());

                        holder.txtProductPrice.setText("Price = "+ model.getPrice() + " Ksh");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final  String productID = model.getPid();

                                CharSequence[] options = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(SponsorHomeActivity.this);
                                builder.setTitle("Do you want to delete this Product.");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int position) {
                                        if (position == 0 ){
                                            //deleteProduct(productID);
                                        }
                                        if (position == 1){

                                        }
                                    }
                                });
                                builder.show();

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }    */


    private void deleteProduct(String productID) {
        unapprovedProductsRef.child(productID).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SponsorHomeActivity.this, "The Product has been deleted successfully", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
