package com.example.peas.Customers;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peas.MpesaActivity;
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

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText nameEditText, phoneEditText, addressEditText, cityEditText, IDEdittext;
    private Button confirmOrderBtn;

    private String totalAmount = "";
    private DatabaseReference Ordersref,productref;
    private String productID = "",sellerId="",ProductName="",Quantity="",product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        totalAmount = getIntent().getStringExtra("Total Price");
        productID = getIntent().getStringExtra("ProductID");
        sellerId= getIntent().getStringExtra("SellerId");
        ProductName= getIntent().getStringExtra("Pname");
        Quantity= getIntent().getStringExtra("quantity");
        product= getIntent().getStringExtra("productavail");
       // String productID = getRef(position).getKey();



        Toast.makeText(this, "Total Price =  Shs " + totalAmount, Toast.LENGTH_SHORT).show();


        confirmOrderBtn = findViewById(R.id.confirm_final_order_btn);
        nameEditText = findViewById(R.id.shippment_name);
        phoneEditText = findViewById(R.id.shippment_phone_number);
        addressEditText = findViewById(R.id.shippment_address);
        cityEditText = findViewById(R.id.shippment_city);
        IDEdittext = findViewById(R.id.shippment_id_number);
        Ordersref = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getId());
       // productref=FirebaseDatabase.getInstance().getReference().child("Products").child(productID);
        Ordersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String Bname = dataSnapshot.child("name").getValue().toString();
                    String ClientId = dataSnapshot.child("Id").getValue().toString();
                    String Bphone = dataSnapshot.child("phone").getValue().toString();
                    String Bcountry = dataSnapshot.child("country").getValue().toString();
                    String Bstate = dataSnapshot.child("location").getValue().toString();
                    nameEditText.setText(Bname);
                    phoneEditText.setText(Bphone);
                    addressEditText.setText(Bcountry);
                    cityEditText.setText(Bstate);
                    IDEdittext.setText(ClientId);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });


//        private void Check () {
//            if (TextUtils.isEmpty(nameEditText.getText().toString())) {
//                Toast.makeText(this, "Please provide your full name.", Toast.LENGTH_SHORT).show();
//            } else if (TextUtils.isEmpty(IDEdittext.getText().toString())) {
//                Toast.makeText(this, "Please provide your Id/Passport Number.", Toast.LENGTH_SHORT).show();
//            } else if (TextUtils.isEmpty(phoneEditText.getText().toString())) {
//                Toast.makeText(this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
//            } else if (TextUtils.isEmpty(addressEditText.getText().toString())) {
//                Toast.makeText(this, "Please provide your address.", Toast.LENGTH_SHORT).show();
//            } else if (TextUtils.isEmpty(cityEditText.getText().toString())) {
//                Toast.makeText(this, "Please provide your city name.", Toast.LENGTH_SHORT).show();
//            } else {
//                ConfirmOrder();
//            }
//        }

//        private void ConfirmOrder () {
//            final String saveCurrentDate, saveCurrentTime;
//
//            Calendar calForDate = Calendar.getInstance();
//            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
//            saveCurrentDate = currentDate.format(calForDate.getTime());
//
//            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//            saveCurrentTime = currentDate.format(calForDate.getTime());
//
//            final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
//                    .child("Orders")
//                    .child(Prevalent.currentOnlineUser.getPhone());
//
//            HashMap<String, Object> ordersMap = new HashMap<>();
//            ordersMap.put("totalAmount", totalAmount);
//            ordersMap.put("name", nameEditText.getText().toString());
//            ordersMap.put("phone", phoneEditText.getText().toString());
//            ordersMap.put("address", addressEditText.getText().toString());
//            ordersMap.put("city", cityEditText.getText().toString());
//            ordersMap.put("Passport", IDEdittext.getText().toString());
//
//            ordersMap.put("date", saveCurrentDate);
//            ordersMap.put("time", saveCurrentTime);
//            ordersMap.put("state", "not shipped");
//
//            ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        FirebaseDatabase.getInstance().getReference()
//                                .child("Cart List")
//                                .child("User View")
//                                .child(Prevalent.currentOnlineUser.getPhone())
//                                .removeValue()
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(ConfirmFinalOrderActivity.this, "Your final order has been placed successfully.Now proceed to pay for the items", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(ConfirmFinalOrderActivity.this, MpesaActivity.class);
//                                            intent.putExtra("Total Pay", String.valueOf(totalAmount));
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                            startActivity(intent);
//                                            finish();
//                                        } else {
//                                            Toast.makeText(ConfirmFinalOrderActivity.this, " Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                    }
//                }
//            });
//        }
    }

    private void Check() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())) {
            Toast.makeText(this, "Please provide your full name.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(IDEdittext.getText().toString())) {
            Toast.makeText(this, "Please provide your Id/Passport Number.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phoneEditText.getText().toString())) {
            Toast.makeText(this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            Toast.makeText(this, "Please provide your address.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cityEditText.getText().toString())) {
            Toast.makeText(this, "Please provide your city name.", Toast.LENGTH_SHORT).show();
        } else {
            ConfirmOrder();
            Updatedata();
        }
    }

    private void Updatedata() {
        productref= FirebaseDatabase.getInstance().getReference().child("Products").child(productID);
        productref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
//                    if(Integer.valueOf(product)<=0)
//                    {
//
//                    }
                    HashMap<String, Object> productMap = new HashMap<>();
                    productMap.put("pavail", product);



                    productref.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            }
                        }
                    });













                }

            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });




    }

    private void ConfirmOrder() {
        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());



        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("sellerId",sellerId);
        ordersMap.put("productname",ProductName);
        ordersMap.put("quantity",Quantity);
        //ordersMap.put("products",product);

        ordersMap.put("pid",productID);
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("phone", phoneEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());
        ordersMap.put("Passport", IDEdittext.getText().toString());

        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state", "not shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Your final order has been placed successfully.Now proceed to pay for the items", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this, MpesaActivity.class);
                                        intent.putExtra("Total Pay", String.valueOf(totalAmount));


                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(ConfirmFinalOrderActivity.this, " Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }


    }
