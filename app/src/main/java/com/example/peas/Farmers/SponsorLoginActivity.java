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

public class SponsorLoginActivity extends AppCompatActivity {

    private EditText sellerEmail,sellerPassword;
    private Button sellerLogin,noAccountRegister;
    private ProgressDialog loadingBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_login);
        mAuth = FirebaseAuth.getInstance();

        sellerEmail = findViewById(R.id.seller_login_email);
        sellerPassword = findViewById(R.id.seller_login_password);
        sellerLogin = findViewById(R.id.seller_login_btn);
        noAccountRegister = findViewById(R.id.seller_no_account_btn);
        loadingBar = new ProgressDialog(this);

        noAccountRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SponsorLoginActivity.this, SponsorRegistrationActivity.class);
                startActivity(intent);
            }
        });

        sellerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerAccess();
            }
        });
    }

    private void sellerAccess() {

        final String email  = sellerEmail.getText().toString();
        final String password  = sellerPassword.getText().toString();

        if (!email.equals("") && !password.equals("")) {

            loadingBar.setTitle("Verifying Seller Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SponsorLoginActivity.this, "Login Successfull.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent intent = new Intent(SponsorLoginActivity.this, SponsorHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        loadingBar.dismiss();
                        Toast.makeText(SponsorLoginActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
        else {
            Toast.makeText(this, "Ensure all fields are field before login", Toast.LENGTH_LONG).show();
        }
    }
}