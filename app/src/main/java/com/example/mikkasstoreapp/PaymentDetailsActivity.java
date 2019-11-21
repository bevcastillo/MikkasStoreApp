
package com.example.mikkasstoreapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Objects.DetailedItemSummaryAdapter;
import com.example.mikkasstoreapp.Objects.EmployeeCart;
import com.example.mikkasstoreapp.Objects.Employeelistdata;
import com.example.mikkasstoreapp.Objects.Itemlistdata;
import com.example.mikkasstoreapp.Objects.Items;
import com.example.mikkasstoreapp.Objects.Purchase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView purchaserName, purchaseStatus, purchaseTotalQty, purchaseTotalDue;
    RecyclerView recyclerView;
    String strPurchaserName, strPurchaserStatus;
    double doublePurchaseDue;
    int intPurchaseQty;

    RecyclerView summary;
    Button btnPay;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Employeelistdata> empllist;
    List<Itemlistdata> itemList;
    Map<String, Object> cartMap;
    List<Items> items;


    List<EmployeeCart> cartItems;
    EmployeeCart cartItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        purchaserName = (TextView) findViewById(R.id.summary_purchaser_name);
        purchaseStatus = (TextView) findViewById(R.id.summary_purchase_status);
        purchaseTotalQty = (TextView) findViewById(R.id.summary_tot_qty);
        purchaseTotalDue = (TextView) findViewById(R.id.summary_tot_due);
        summary = (RecyclerView) findViewById(R.id.recycler_summary);
        btnPay = (Button) findViewById(R.id.btn_pay_now);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_summary);

        btnPay.setOnClickListener(this);

        //
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data");

        cartMap = new HashMap<String, Object>();
        cartItems = new ArrayList<>();
        cartItem = new EmployeeCart();
        items = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle!=null){
            strPurchaserName = bundle.getString("purchaser_name");
            strPurchaserStatus = bundle.getString("purchase_status");
            doublePurchaseDue = bundle.getDouble("purchase_due");
            intPurchaseQty = bundle.getInt("purchase_qty");

            purchaserName.setText(strPurchaserName+"'s Purchase History");
            purchaseTotalQty.setText(intPurchaseQty+" items, Total: ");
            purchaseTotalDue.setText("₱ "+doublePurchaseDue);
            purchaseStatus.setText("Status: "+strPurchaserStatus);
        }

        //
        view_purchased_items();
    }

    private void view_purchased_items() {

        databaseReference.child("purchases")
                .orderByChild("purchase_emp_name")
                .equalTo(strPurchaserName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                String purchaseKey = dataSnapshot1.getKey();

                                Purchase purchase = dataSnapshot1.getValue(Purchase.class);
                                Gson gson = new Gson();

                                for (Map.Entry<String, Object> entry: purchase.getEmployee_cart().entrySet()){
                                    if (entry.getValue().toString().contains("items")){
                                        String json = gson.toJson(entry.getValue());
                                        String mJsonString = json;
                                        JsonParser parser = new JsonParser();
                                        JsonElement mJson =  parser.parse(mJsonString);

                                        JsonObject jsonObject = gson.fromJson(mJson, JsonObject.class);
                                        JsonElement itemsJson = jsonObject.get("items");
                                        Items itemsObj = gson.fromJson(itemsJson, Items.class);

                                        cartItem.setItems(itemsObj);
                                        cartItems.add(cartItem);
                                        items.add(itemsObj);

                                        DetailedItemSummaryAdapter adapter = new DetailedItemSummaryAdapter(getApplicationContext(), items);
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true));
                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        recyclerView.setAdapter(adapter);


                                    }else {
                                        Toast.makeText(PaymentDetailsActivity.this, "does not contain any items at AALL", Toast.LENGTH_SHORT).show();
                                    }
                                }


                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_pay_now:
                pay_purchase();
                break;
        }
    }

    private void pay_purchase() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Accept payment?");

        builder.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //we get the payment date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY");
                final String paymentDate = simpleDateFormat.format(new Date());


                databaseReference.child("purchases")
                        .orderByChild("purchase_emp_name")
                        .equalTo(strPurchaserName)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                        String purchaseKey = dataSnapshot1.getKey();
                                        Purchase purchase = dataSnapshot1.getValue(Purchase.class);

                                        String purchaseStatus = purchase.getPurch_status();

                                        if (purchaseStatus.equals("Pending")){
                                            //update the status to from pending to completed

                                            databaseReference.child("purchases/"+purchaseKey+"/purch_status").setValue("Completed");
                                            databaseReference.child("purchases/"+purchaseKey+"/purch_payment_date").setValue(paymentDate);
                                        }else {
                                            //it is completed
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                Toast.makeText(PaymentDetailsActivity.this, "Thank you! Purchase has been paid and completed.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }
}
