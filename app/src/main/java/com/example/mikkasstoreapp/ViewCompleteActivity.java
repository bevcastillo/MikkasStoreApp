package com.example.mikkasstoreapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewCompleteActivity extends AppCompatActivity {

    TextView purchaserName, purchaseStatus, purchaseQty, purchaseDue, paymentDate;
    String strempName, strStatus, date;
    int intQty;
    double doubleDue;
    RecyclerView recyclerView;

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
        setContentView(R.layout.activity_view_complete);

        purchaserName = (TextView) findViewById(R.id.comp_purchaser_name);
        purchaseStatus = (TextView) findViewById(R.id.comp_purchase_status);
        purchaseQty = (TextView) findViewById(R.id.comp_item_qty);
        purchaseDue = (TextView) findViewById(R.id.comp_tot_due);
        recyclerView = (RecyclerView) findViewById(R.id.comp_summary);
        paymentDate = (TextView) findViewById(R.id.comp_payment_date);

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
            strempName = bundle.getString("employee_name");
            strStatus = bundle.getString("status");
            intQty = bundle.getInt("qty");
            doubleDue = bundle.getDouble("due");
            date = bundle.getString("date");

            purchaserName.setText(strempName+"'s Payment Details");
            paymentDate.setText("Paid on: "+date);
            purchaseStatus.setText("Status: "+strStatus);
            purchaseQty.setText(intQty+" items, Total: ");
            purchaseDue.setText("₱ "+doubleDue);

            display_complete_purchase();

        }
    }

    private void display_complete_purchase() {

        String uniqueId = strempName+"completed"+""+date+""+doubleDue;
        databaseReference.child("purchases")
                .orderByChild("purchase_key")
                .equalTo(uniqueId)
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
                                        Toast.makeText(ViewCompleteActivity.this, "does not contain any items at AALL", Toast.LENGTH_SHORT).show();
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


}
