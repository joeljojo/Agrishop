package com.example.peas.Customers;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.peas.Models.Products;
import com.example.peas.UserDetails.Prevalent;
import com.example.peas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class ProductDetailsActivity extends AppCompatActivity {

    private Button addToCartBtn;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productprice,productDescription,productName;
    private EditText productQuantity;
    DatabaseReference productref;



    private String productID = "", state="Normal",SellerId="",AvailableProduct= "",PAvail;
    private int overTotalProduct = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        SellerId = getIntent().getStringExtra("sellerId");
        productID = getIntent().getStringExtra("pid");
        AvailableProduct = getIntent().getStringExtra("PAvail");






        addToCartBtn = findViewById(R.id.pd_add_to_cart);
        productImage = findViewById(R.id.product_image_description);
        productprice = findViewById(R.id.product_price_details);
        productDescription = findViewById(R.id.product_description_details);
        productName = findViewById(R.id.product_name_details);
        productQuantity=findViewById(R.id.product_price_quantity);
       // overTotalProduct=

       // numberButton = findViewById(R.id.number_btn);

        getProductDetails(productID);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                productref= FirebaseDatabase.getInstance().getReference().child("Products");
//                productref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            int AVailable=(int) dataSnapshot.child("pavail").getValue();
//                            int Quantity=Integer.parseInt(productQuantity.getText().toString());
//
//                            if(Quantity>AVailable)
//                            {
//                                Toast.makeText(ProductDetailsActivity.this, "You cannot purchase more than available products", Toast.LENGTH_LONG).show();
//                            }
////                            else
////                            {
////                                addingToCartList();
////                            }
//
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });


                if (state.equals("Order Shipped") || state.equals("Order Placed")){
                    Toast.makeText(ProductDetailsActivity.this, "You can Purchase more Products once your order is confrimed", Toast.LENGTH_LONG).show();
                }



                else{
                    addingToCartList();

                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
    }

    private void addingToCartList() {

        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd , yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("sellerId",SellerId);
        cartMap.put("Pavail",AvailableProduct);


        //cartMap.put("PName",ProductName);
        cartMap.put("pid",productID);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",productprice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",productQuantity.getText().toString());
        cartMap.put("discount","");


        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                    .child("Products").child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(ProductDetailsActivity.this,"Added to Cart List",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }

                                        }
                                    });
                        }
                    }
                });


    }

    private void getProductDetails(final String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);

                    String productavailable = dataSnapshot.child("pavail").getValue().toString();

                    productName.setText(products.getPname());
                    productprice.setText(products.getPrice());
                    productDescription.setText("Available Product: "+products.getPavail());







                  //  PAvail=String.valueOf(products.getPavail());



                   // int oneTypeProductTPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf();
                  //  int oneTypeProductTPrice = ((Integer.valueOf(products.getPavail()))) * Integer.valueOf(model.getQuantity());
                  //  overTotalProduct = overTotalProduct + oneTypeProductTPrice;

                    Picasso.get().load(products.getImage()).into(productImage);











                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void CheckOrderState(){
        DatabaseReference ordersref ;
        ordersref =  FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        ordersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String shippingstate = dataSnapshot.child("state").getValue().toString();


                    if (shippingstate.equals("shipped")){
                        state = "Order Shipped";

                    }
                    else if (shippingstate.equals("not shipped")) {
                        state = "Order Placed";
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
