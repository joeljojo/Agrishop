package com.example.peas.SupportCenter;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SupportMaintainProductsActivity extends AppCompatActivity {

    private Button maintainProductsBtn,DeleteProductBtn;
    private EditText name,price,description;
    private ImageView imageView;

    private String productID = "";
    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_maintain_products);

        productID = getIntent().getStringExtra("pid");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        maintainProductsBtn =  findViewById(R.id.maintain_product_btn);
        DeleteProductBtn =  findViewById(R.id.delete_product_btn);
        name =  findViewById(R.id.edit_product_name);
        price =  findViewById(R.id.edit_product_price);
        description =  findViewById(R.id.edit_product_description);
        imageView =  findViewById(R.id.edit_product_image);

        DisplaySpecificProductInfo();

        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateChangesBtn();
            }
        });

        DeleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThisProduct();
            }
        });
    }

    private void deleteThisProduct() {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(SupportMaintainProductsActivity.this, SupportHomeActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(SupportMaintainProductsActivity.this, "The Product is deleted successfully.", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void DisplaySpecificProductInfo()
    {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String product_name = dataSnapshot.child("pname").getValue().toString();
                    String product_price = dataSnapshot.child("price").getValue().toString();
                    String product_description = dataSnapshot.child("description").getValue().toString();
                    String product_image = dataSnapshot.child("image").getValue().toString();

                    name.setText(product_name);
                    price.setText(product_price);
                    description.setText(product_description);
                    Picasso.get().load(product_image).into(imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void UpdateChangesBtn() {

        String updatedName = name.getText().toString();
        String updatedPrice = price.getText().toString();
        String updatedDescription = description.getText().toString();

        if(updatedName.equals("")){
            Toast.makeText(this, "Please enter a new Name", Toast.LENGTH_SHORT).show();
        }
        else if(updatedDescription.equals("")){
            Toast.makeText(this, "Please enter a new description", Toast.LENGTH_SHORT).show();
        }
        else if(updatedPrice.equals("")){
            Toast.makeText(this, "Please enter a new Price", Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("description", updatedDescription);
            productMap.put("price", updatedPrice);
            productMap.put("pname", updatedName);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SupportMaintainProductsActivity.this, "Products details Updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SupportMaintainProductsActivity.this, SupportHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        String message = task.getException().toString();
                        Toast.makeText(SupportMaintainProductsActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}