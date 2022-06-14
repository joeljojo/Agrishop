package com.example.peas.Farmers;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peas.R;

public class SponsorProductCategoryActivity extends AppCompatActivity
{
    private ImageView BushBeans, YardLong, PoleBeans;








    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_product_category);


        BushBeans = (ImageView) findViewById(R.id.bushbeans);
        YardLong = (ImageView) findViewById(R.id.yarldlong);
        PoleBeans = (ImageView) findViewById(R.id.polebeans);


        BushBeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SponsorProductCategoryActivity.this, SponsorAddNewProductActivity.class);
                intent.putExtra("category", "bush beans");
                startActivity(intent);
            }
        });


        YardLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SponsorProductCategoryActivity.this, SponsorAddNewProductActivity.class);
                intent.putExtra("category", "yard long beans");
                startActivity(intent);
            }
        });


        PoleBeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SponsorProductCategoryActivity.this, SponsorAddNewProductActivity.class);
                intent.putExtra("category", "pole beans");
                startActivity(intent);
            }
        });



    }
}

