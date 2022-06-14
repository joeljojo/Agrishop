package com.example.peas;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.example.peas.Customers.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
public class MpesaActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.number)
    EditText mNumber;

    @BindView(R.id.button)
    Button mButton;

    private TextView mAmount;

    private String totalPay = "";

    private Daraja daraja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpesa);
        ButterKnife.bind(this);

        totalPay = getIntent().getStringExtra("Total Pay");
        Toast.makeText(this, "Total Pay =  Shs " + totalPay, Toast.LENGTH_SHORT).show();

        mAmount =  findViewById(R.id.amount);
        mAmount.setText(String.valueOf(totalPay));

        mButton.setOnClickListener(this);
        daraja = Daraja.with("2hUuRYBIrces22o6HNFxzg1PkU6GsCCO", "Qi9npx61tvcoOcfy", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                Log.i(MpesaActivity.this.getClass().getSimpleName(), accessToken.getAccess_token());
            }

            @Override
            public void onError(String error) {
                Log.e(MpesaActivity.this.getClass().getSimpleName(), error);

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == mButton){
            String phonenumber = mNumber.getText().toString().trim(); //collect the number from the user's input
            String Amount = mAmount.getText().toString().trim(); // amount from the cart


            //check validity of a number
            if(phonenumber.isEmpty() || phonenumber.length() != 10){
                mNumber.setError("Invalid number");
                return;
            }
            LNMExpress lnmExpress = new LNMExpress(
                    "174379",
                    "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                    //https://developer.safaricom.co.ke/test_credentials
                    TransactionType.CustomerPayBillOnline,
                    Amount,
                    "0700000000",
                    "174379",
                    phonenumber,
                    "https://fidelis.com/pea.php",
                    "174379",
                    "Payment for orders from greanbeans."
            );
            daraja.requestMPESAExpress(lnmExpress,
                    new DarajaListener<LNMResult>() {
                        @Override
                        public void onResult(@NonNull LNMResult lnmResult) {
                            Log.i(MpesaActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
//                            Intent intent = new Intent(MpesaActivity.this, HomeActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                            finish();
                        }

                        @Override
                        public void onError(String error) {
                            Log.i(MpesaActivity.this.getClass().getSimpleName(), error);
                        }
                    }
            );
        }
    }

}