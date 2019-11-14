package com.example.mikkasstoreapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mikkasstoreapp.Objects.Items;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText editTextItemName, editTextPrice, editTextQty;
    String strSelectedCategory;
    String itemId;
    Spinner spinnerCategory;
    Button btnAddItem;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editTextItemName = findViewById(R.id.edit_item_name);
        editTextPrice = findViewById(R.id.edit_item_price);
        editTextQty = findViewById(R.id.edit_item_quantity);
        btnAddItem = findViewById(R.id.btn_add_item);

        spinnerCategory = findViewById(R.id.spinner_categories);

        btnAddItem.setOnClickListener(this);
        spinnerCategory.setOnItemSelectedListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data");
        itemId = databaseReference.push().getKey();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_add_item:
                insert_items();
                break;
        }
    }

    private void add_items(String item_name, String item_category, double item_price, int item_qty){
        final Items items = new Items(item_name, item_category, item_price, item_qty);

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
        final String username = (userPref.getString("user_username",""));

        items.setItem_name(item_name);
        items.setItem_category(strSelectedCategory);
        items.setItem_price(item_price);
        items.setItem_qty(item_qty);

        databaseReference.child("users")
                .orderByChild("username")
                .equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        databaseReference.child("items").child(itemId).setValue(items);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void insert_items() {
        String itemName = editTextItemName.getText().toString();
        double itemPrice = Double.parseDouble(editTextPrice.getText().toString());
        int itemQty = Integer.parseInt(editTextQty.getText().toString());

        add_items(itemName, strSelectedCategory, itemPrice, itemQty);
        Toast.makeText(this, "Item successfully added.", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int sid = parent.getId();

            switch (sid){
                case R.id.spinner_categories:
                    strSelectedCategory = this.spinnerCategory.getItemAtPosition(position).toString();
                    break;
            }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
