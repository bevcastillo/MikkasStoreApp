package com.example.mikkasstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Adapters.PaymentsAdapter;
import com.example.mikkasstoreapp.Objects.Purchase;
import com.example.mikkasstoreapp.Objects.Purchaselistdata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PaymentsActivity extends AppCompatActivity {

    RecyclerView paymentsListRecycler;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView noData;

    List<Purchaselistdata> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        paymentsListRecycler = (RecyclerView) findViewById(R.id.recycler_paymentlist);
        noData = (TextView) findViewById(R.id.payments_no_data);

        //
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data");
    }

    @Override
    protected void onStart() {
        super.onStart();

        display_pending_payments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_completed,menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mid = item.getItemId();

        switch (mid){
            case R.id.action_view:
                Intent intent = new Intent(PaymentsActivity.this, CompletedPaymentsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    private void display_pending_payments() {

        databaseReference.child("purchases")
                .orderByChild("purch_status")
                .equalTo("Pending")
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
                                double purchaseDue = purchase.getPurch_total_due();
                                int purchaseQty = purchase.getPurch_tot_qty();

                                purchaselistdata.setPurchase_emp_name(purchaserName);
                                purchaselistdata.setPurch_total_due(purchaseDue);
                                purchaselistdata.setPurch_status(purchaseStatus);
                                purchaselistdata.setPurch_tot_qty(purchaseQty);
                                list.add(purchaselistdata);
                            }

                            PaymentsAdapter adapter = new PaymentsAdapter(getApplicationContext(), list);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            paymentsListRecycler.setLayoutManager(layoutManager);
                            paymentsListRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
                            paymentsListRecycler.setItemAnimator(new DefaultItemAnimator());
                            paymentsListRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            if (list.isEmpty()){
                                noData.setVisibility(View.VISIBLE);
                            }else {
                                noData.setVisibility(View.GONE);
                            }

                        }else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
