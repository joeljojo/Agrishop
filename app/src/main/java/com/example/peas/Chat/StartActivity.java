package com.example.peas.Chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.peas.Customers.HomeActivity;
import com.example.peas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button login,register;

    FirebaseUser firebaseUser;
/*
    @Override
    protected void onStart() {
        super.onStart();      */

       // firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

      /*  //Check if user exists
        if(firebaseUser !=null){
            Intent intent=new Intent(StartActivity.this, ChatMainActivity.class);
            startActivity(intent);
            finish();
        }
    }       */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login=findViewById(R.id.btn_login);
      //  register=findViewById(R.id.btn_register);
       // chat=findViewById(R.id.chat_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StartActivity.this, ChatLoginActivity.class);
                startActivity(intent);
           }
        });
    /*    register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StartActivity.this, ChatRegisterActivity.class);
                startActivity(intent);
            }
        });  */

    }
}