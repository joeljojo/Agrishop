package com.example.peas.Customers;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity
{
    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword,InputEmail,InputIDNO,InputCountry,InputLocation;
    private ProgressDialog loadingBar;
    private TextView farmerLink, customerLink;
    private String parentDbName = "Users";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.seller_register_name);
        InputPassword = (EditText) findViewById(R.id.seller_register_password);
        InputPhoneNumber = (EditText) findViewById(R.id.seller_register_phone);
        InputEmail = (EditText) findViewById(R.id.seller_register_email);
        InputIDNO = (EditText) findViewById(R.id.seller_register_idno);
        InputCountry = (EditText) findViewById(R.id.seller_register_address);
        InputLocation  = (EditText) findViewById(R.id.seller_register_location);


        loadingBar = new ProgressDialog(this);

        farmerLink = (TextView) findViewById(R.id.farmer_panel_link);
        customerLink = (TextView) findViewById(R.id.customer_panel_link);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });


        farmerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccountButton.setText("Create Farmer Account");
                farmerLink.setVisibility(View.INVISIBLE);
                customerLink.setVisibility(View.VISIBLE);
                parentDbName = "Sellers";
            }
        });

        customerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccountButton.setText("Create Customer Account");
                farmerLink.setVisibility(View.VISIBLE);
                customerLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });
    }



    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        String email = InputEmail.getText().toString();
        String Id= InputIDNO.getText().toString();
        String country= InputCountry.getText().toString();
        String location = InputLocation.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your Email...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Id))
        {
            Toast.makeText(this, "Please write your ID_NO/Passport_No...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(country))
        {
            Toast.makeText(this, "Please write your Country..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(location))
        {
            Toast.makeText(this, "Please write your location...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating Buyer Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name, phone, password,email,Id,country,location);
        }
    }



    private void ValidatephoneNumber(final String name, final String phone, final String password,final String email,final String Id,final String country,final String location)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child(parentDbName).child(phone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    userdataMap.put("email", email);
                    userdataMap.put("Id", Id);
                    userdataMap.put("country",country);
                    userdataMap.put("location", location);


                    RootRef.child(parentDbName).child(Id).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "This " + Id + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using valid ID_NO/Passport_Number.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

