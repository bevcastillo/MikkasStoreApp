package com.example.mikkasstoreapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mikkasstoreapp.Objects.Itemlistdata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class EditItemActivity extends AppCompatActivity implements View.OnClickListener {

    EditText updateItemName, updateItemStock, updateItemPrice;
    String strItemName;
    int inteItemStock;
    double doubleItemPrice;

    Button btnUpdate, btnDelete;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Itemlistdata> itemlistdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        updateItemName = (EditText) findViewById(R.id.update_item_name);
        updateItemPrice = (EditText) findViewById(R.id.update_item_price);
        updateItemStock = (EditText) findViewById(R.id.update_item_stock);
        btnUpdate = (Button) findViewById(R.id.btn_edit);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data");

        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        getSelectedItemDetails();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle!=null){
            strItemName = bundle.getString("item_name");
            doubleItemPrice = bundle.getDouble("item_price");
            inteItemStock = bundle.getInt("item_stock");

            updateItemName.setText(strItemName);
            updateItemPrice.setText(Double.toString(doubleItemPrice));
            updateItemStock.setText(Integer.toString(inteItemStock));


        }
    }

    private void getSelectedItemDetails() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_edit:
                if (checkEmptyFields()){
                    edit_items();
                }
                break;
            case R.id.btn_delete:
                delete_items();
                break;
        }
    }

    private void edit_items() {

        databaseReference.child("items")
                .orderByChild("item_name")
                .equalTo(strItemName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                String itemKey = dataSnapshot1.getKey();

                                //updating from items node
                                databaseReference.child("items/"+itemKey+"/item_name").setValue(updateItemName.getText().toString());
                                databaseReference.child("items/"+itemKey+"/item_price").setValue(Double.valueOf(updateItemPrice.getText().toString()));
                                databaseReference.child("items/"+itemKey+"/item_stock").setValue(Integer.valueOf(updateItemStock.getText().toString()));

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        Toast.makeText(this, "Item has been updated.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void delete_items() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                databaseReference.child("items")
                        .orderByChild("item_name")
                        .equalTo(strItemName)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                        String itemKey = dataSnapshot1.getKey();

                                        //query to delete
                                        databaseReference.child("items/"+itemKey).setValue(null);

                                    }
                                }else {

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                Toast.makeText(EditItemActivity.this, "Item has been deleted.", Toast.LENGTH_SHORT).show();
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

    private boolean checkEmptyFields(){
        String itemName = updateItemName.getText().toString();
        String itemStock = updateItemStock.getText().toString();
        String itemPrice = updateItemPrice.getText().toString();


        if (itemName.equals("")){
            Toast.makeText(this, "Item name can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (itemStock.equals("")){
            Toast.makeText(this, "Item stock can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (itemPrice.equals("")){
            Toast.makeText(this, "Item price can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
