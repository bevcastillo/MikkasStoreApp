package com.example.mikkasstoreapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Objects.Employee;
import com.example.mikkasstoreapp.Objects.Items;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllPurchasesActivity extends AppCompatActivity {

    RecyclerView recyclerViewPurchlist;
    FloatingActionButton fab_addpurch;
    TextView txtEmpName, txtItemName, txtPurchDate, txtItemQty;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String purchaseId, selectedEmployee, selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_purchases);

        recyclerViewPurchlist = (RecyclerView) findViewById(R.id.recyclerview_purchlist);
        fab_addpurch = (FloatingActionButton) findViewById(R.id.fab_add_purch);
        txtEmpName = (TextView) findViewById(R.id.purch_emp_name);
        txtPurchDate = (TextView) findViewById(R.id.purch_date);
        txtItemName = (TextView) findViewById(R.id.purch_item_name);
        txtItemQty = (TextView) findViewById(R.id.purch_item_qty);

        //
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data");

        fab_addpurch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_add_dialog();
            }
        });
    }

    private void open_add_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_add_purchase, null);
        builder.setView(dialogView);

        final Spinner spinnerEmpName = (Spinner) dialogView.findViewById(R.id.spinner_empName);
        final Spinner spinnerItemName = (Spinner) dialogView.findViewById(R.id.spinner_itemName);
        final EditText editTextItemQty = (EditText) dialogView.findViewById(R.id.edit_item_name);

        final ArrayList<String> employees = new ArrayList<String>();
        final ArrayList<String> itemlist = new ArrayList<String>();

        //displaying the employee names
        databaseReference.child("/employee")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Employee employee = dataSnapshot1.getValue(Employee.class);
                    employees.add(employee.getEmp_name());
                }
                employees.add("Create Employee");
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AllPurchasesActivity.this, R.layout.spinner_emp_name, employees);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_emp_name);
                spinnerEmpName.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //displaying the item names
        databaseReference.child("/items")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                            Items items = dataSnapshot2.getValue(Items.class);
                            itemlist.add(items.getItem_name());
                        }
                        itemlist.add("Create Items");
                        ArrayAdapter<String> spinnerItemArrAdapter = new ArrayAdapter<String>(AllPurchasesActivity.this, R.layout.spinner_item_name, itemlist);
                        spinnerItemArrAdapter.setDropDownViewResource(R.layout.spinner_item_name);
                        spinnerItemName.setAdapter(spinnerItemArrAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



        spinnerEmpName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEmployee = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerItemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //adding to firebase

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //closing the alert dialog
            }
        });

        AlertDialog build = builder.create();
        build.show();
    }

    private void add_purchased_items(){

    }

}
