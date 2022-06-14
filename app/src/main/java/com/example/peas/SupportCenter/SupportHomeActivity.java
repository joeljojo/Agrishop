package com.example.peas.SupportCenter;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peas.Customers.HomeActivity;
import com.example.peas.Customers.MainActivity;
import com.example.peas.R;

public class SupportHomeActivity extends AppCompatActivity {

    private Button adminLogoutBtn,adminCheckOrdersBtn,adminMaintainProductsBtn,adminApproveProductsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_home);


        adminLogoutBtn = findViewById(R.id.admin_logout_btn);
        adminCheckOrdersBtn = findViewById(R.id.admin_check_orders);
        adminMaintainProductsBtn = findViewById(R.id.maintain_btn);
        adminApproveProductsbtn = findViewById(R.id.check_approve_new_products);

        adminCheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportHomeActivity.this, SupportNewOrdersActivity.class);
                startActivity(intent);
                finish();
            }
        });
        adminApproveProductsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportHomeActivity.this, SupportCheckNewProducts.class);
                startActivity(intent);
                finish();
            }
        });
        adminLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        adminMaintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportHomeActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });

    }
}