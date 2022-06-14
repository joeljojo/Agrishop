package com.example.peas.Customers;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peas.Models.Cart;
import com.example.peas.UserDetails.Prevalent;
import com.example.peas.R;
import com.example.peas.MyHolderViews.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount,txtMsg1;
    private int overTotalPrice = 0,productavail=0;
    private String ProductId=" ",SellerId="",ProductName="",quantity=" ";
    DatabaseReference productref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        NextProcessBtn =  findViewById(R.id.next_process_btn);
        txtTotalAmount = findViewById(R.id.total_price);
        txtMsg1 = findViewById(R.id.msg1);



        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTotalAmount.setText("Total Price = Ksh" + overTotalPrice);
                Intent intent = new  Intent(CartActivity.this,ConfirmFinalOrderActivity.class);
                Toast.makeText(CartActivity.this, "Proceed to complete your order of Ksh" + overTotalPrice, Toast.LENGTH_SHORT).show();
                intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                intent.putExtra("ProductID",String.valueOf(ProductId));
                intent.putExtra("SellerId",String.valueOf(SellerId));
                intent.putExtra("Pname",String.valueOf(ProductName));
                intent.putExtra("quantity",String.valueOf(quantity));
                intent.putExtra("productavail",String.valueOf(productavail));

//
//               productref= FirebaseDatabase.getInstance().getReference().child("Products");
//              productref.addValueEventListener(new ValueEventListener() {
//                  @Override
//                  public void onDataChange(DataSnapshot dataSnapshot) {
//                      if(dataSnapshot.exists()){
//                          int AVailable=(int) dataSnapshot.child("pavail").getValue();
//
//                      }
//
//                  }
//
//                  @Override
//                  public void onCancelled(DatabaseError databaseError) {
//
//                  }
//              });
//









                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
      //  UpdateProducts();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User View")
                                .child(Prevalent.currentOnlineUser.getPhone())
                                .child("Products"),Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                        holder.txttime.setText("Quantity : " + model.getQuantity());
                        holder.txtProductPrice.setText("Price" + model.getPrice() + "Ksh");
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductQuantity.setText(model.getTime()+"|"+model.getDate());
                        int oneTypeProductTPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                        overTotalPrice = overTotalPrice + oneTypeProductTPrice;

                       int oneTypeProduct = ((Integer.valueOf(model.getPavail()))) - Integer.valueOf(model.getQuantity());
                       productavail= productavail + oneTypeProduct;


                        ProductId=String.valueOf(model.getPid());
                        SellerId=String.valueOf(model.getSellerId());
                        ProductName=String.valueOf(model.getPname());
                        quantity=String.valueOf(model.getQuantity());







                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence[] options = new CharSequence[]{
                                        "Edit",
                                        "Remove"
                                };
                                AlertDialog.Builder buider = new AlertDialog.Builder(CartActivity.this);
                                buider.setTitle("Cart Options:");

                                buider.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i == 0){

                                            Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                            intent.putExtra("pid",model.getPid());
                                            startActivity(intent);
                                        }
                                        if (i == 1){
                                            cartListRef.child("User View")
                                                    .child(Prevalent.currentOnlineUser.getPhone())
                                                    .child("Products")
                                                    .child(model.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()){
                                                                Toast.makeText(CartActivity.this,"You have successfully removed the product",Toast.LENGTH_LONG).show();
                                                                Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                                                startActivity(intent);
                                                            }

                                                        }
                                                    });
                                        }
                                    }
                                });
                                buider.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

//    private void UpdateProducts() {
//        DatabaseReference Productref ;
//        Productref =  FirebaseDatabase.getInstance().getReference().child("Products");
//        Productref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.hasChild("pavail")) {
//                    String productavailable = dataSnapshot.child("pavail").getValue().toString();
//                    productavail.setText(productavailable);
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//



  //  }



    private void CheckOrderState(){
        DatabaseReference ordersref ;
        ordersref =  FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        ordersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String shippingstate = dataSnapshot.child("state").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();

                    if (shippingstate.equals("shipped")){
                        txtMsg1.setText("Dear " + userName + " \n order is shipped successfully." );
                        recyclerView.setVisibility(View.GONE);

                        txtMsg1.setVisibility(View.VISIBLE);
                        NextProcessBtn.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this,"You can purchase another product once you received your order.",Toast.LENGTH_LONG).show();


                    }
                    else if (shippingstate.equals("not shipped")) {
                        txtMsg1.setText("Dear " + userName + " \n order is not shipped yet. \n You can purchase another product once your order is verified" );
                        recyclerView.setVisibility(View.GONE);

                        txtMsg1.setVisibility(View.VISIBLE);
                        NextProcessBtn.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this,"You can purchase another product once you received your order.",Toast.LENGTH_LONG).show();

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
