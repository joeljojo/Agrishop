package com.example.peas.Farmers;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SponsorRegistrationActivity extends AppCompatActivity {

    private EditText sellerRegisterName,sellerRegisterPhone,sellerRegisterEmail,sellerRegisterAddress,sellerRegisterPassword;
    private Button sellerRegisterBtn, SellerAccountLogin;
    private ProgressDialog loadingBar;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_registration);

        mAuth = FirebaseAuth.getInstance();

        sellerRegisterName = findViewById(R.id.seller_register_name);
        sellerRegisterPhone = findViewById(R.id.seller_register_phone);
        sellerRegisterEmail = findViewById(R.id.seller_register_email);
        sellerRegisterAddress = findViewById(R.id.seller_register_address);
        sellerRegisterPassword = findViewById(R.id.seller_register_password);
        sellerRegisterBtn = findViewById(R.id.seller_register_btn);
        SellerAccountLogin = findViewById(R.id.seller_have_account_btn);
        loadingBar = new ProgressDialog(this);


        SellerAccountLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SponsorRegistrationActivity.this, SponsorLoginActivity.class);
                startActivity(intent);
            }
        });

        sellerRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSeller();
            }
        });
    }

    private void registerSeller() {
        final String name  = sellerRegisterName.getText().toString();
        final String phone  = sellerRegisterPhone.getText().toString();
        final String email  = sellerRegisterEmail.getText().toString();
        String password  = sellerRegisterPassword.getText().toString();
        final String address  = sellerRegisterAddress.getText().toString();



        if (!name.equals("") && !phone.equals("") && !email.equals("") && !password.equals("") && !address.equals("") ){

            loadingBar.setTitle("Creating Farmer Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                final DatabaseReference rootRef;
                                rootRef= FirebaseDatabase.getInstance().getReference();
                                String sID = mAuth.getCurrentUser().getUid();
                                HashMap<String,Object> sellerMap = new HashMap<>();
                                sellerMap.put("sid",sID);
                                sellerMap.put("name",name);
                                sellerMap.put("email",email);
                                sellerMap.put("phone",phone);
                                sellerMap.put("address",address);

                                rootRef.child("Sellers").child(sID).updateChildren(sellerMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(SponsorRegistrationActivity.this, "Congratulations, your account has been successfully created.", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();

                                                    Intent intent = new Intent(SponsorRegistrationActivity.this, SponsorHomeActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else
                                                {
                                                    loadingBar.dismiss();
                                                    Toast.makeText(SponsorRegistrationActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });


                            }
                        }
                    });
        }
        else {
            Toast.makeText(this, "Ensure all fields are field before registering", Toast.LENGTH_LONG).show();
        }
    }
}