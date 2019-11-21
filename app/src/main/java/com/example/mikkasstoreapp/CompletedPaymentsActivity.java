package com.example.mikkasstoreapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Adapters.CompletedPaymentAdapter;
import com.example.mikkasstoreapp.Objects.Purchase;
import com.example.mikkasstoreapp.Objects.Purchaselistdata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CompletedPaymentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Purchaselistdata> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_payments);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_completed);

        //
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data");
    }

    @Override
    protected void onStart() {
        super.onStart();

        display_completed_payments();
    }

    private void display_completed_payments() {

        databaseReference.child("purchases")
                .orderByChild("purch_status")
                .equalTo("Completed")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            list = new ArrayList<>();
                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                String purchaseKey = dataSnapshot1.getKey();
                                Purchase purchase = dataSnapshot1.getValue(Purchase.class);
                                Purchaselistdata purchaselistdata = new Purchaselistdata();

                                String purchaserName = purchase.getPurchase_emp_name();
                                String purchaseStatus = purchase.getPurch_status();
                                String paymentDate = purchase.getPurch_payment_date();
                                double purchaseDue = purchase.getPurch_total_due();
                                int purchaseQty = purchase.getPurch_tot_qty();

                                purchaselistdata.setPurchase_emp_name(purchaserName);
                                purchaselistdata.setPurch_total_due(purchaseDue);
                                purchaselistdata.setPurch_status(purchaseStatus);
                                purchaselistdata.setPurch_payment_date(paymentDate);
                                purchaselistdata.setPurch_tot_qty(purchaseQty);
                                list.add(purchaselistdata);
                            }

                            CompletedPaymentAdapter adapter = new CompletedPaymentAdapter(getApplicationContext(), list);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
