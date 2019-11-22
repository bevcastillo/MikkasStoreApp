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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Objects.AllPurchasedItemsAdapter;
import com.example.mikkasstoreapp.Objects.Employee;
import com.example.mikkasstoreapp.Objects.EmployeeCart;
import com.example.mikkasstoreapp.Objects.Employeelistdata;
import com.example.mikkasstoreapp.Objects.Itemlistdata;
import com.example.mikkasstoreapp.Objects.Items;
import com.example.mikkasstoreapp.Objects.Purchase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class AllPurchasesActivity extends AppCompatActivity {

    RecyclerView recyclerViewPurchlist;
    FloatingActionButton fab_addpurch;
    TextView txtEmpName, txtItemName, txtPurchDate, txtItemQty, noData;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String purchaseId, customerCartId, selectedEmployee, selectedItem;

    int totalPurchaseQty;
    double totalPurchaseDue;

    List<Employeelistdata> empllist;
    List<Itemlistdata> itemList;
    Map<String, Object> cartMap;
    List<Items> items;


    List<EmployeeCart> cartItems;
    EmployeeCart cartItem;

    private static int itemcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_purchases);

        recyclerViewPurchlist = (RecyclerView) findViewById(R.id.recyclerview_purchlist);
        fab_addpurch = (FloatingActionButton) findViewById(R.id.fab_add_purch);
        txtEmpName = (TextView) findViewById(R.id.purch_emp_name);
        txtPurchDate = (TextView) findViewById(R.id.purch_date);
        txtItemName = (TextView) findViewById(R.id.purch_item_name);
        txtItemQty = (TextView) findViewById(R.id.purch_item_details);
        noData = (TextView) findViewById(R.id.purch_no_data);

        //
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data");

        fab_addpurch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_add_dialog();
            }
        });

        //
        cartMap = new HashMap<String, Object>();
        cartItems = new ArrayList<>();
        cartItem = new EmployeeCart();
        items = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        display_all_purchases();
    }

    private void display_all_purchases() {


        //displaying the employee names
        databaseReference.child("/purchases")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
//
                                        AllPurchasedItemsAdapter adapter = new AllPurchasedItemsAdapter(getApplicationContext(), items);
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                        recyclerViewPurchlist.setLayoutManager(layoutManager);
                                        recyclerViewPurchlist.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                        recyclerViewPurchlist.setItemAnimator(new DefaultItemAnimator());
                                        recyclerViewPurchlist.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();

                                        if (items.isEmpty()){
                                            noData.setVisibility(View.VISIBLE);
                                        }else {
                                            noData.setVisibility(View.GONE);
                                        }

                                    }else {
                                        Toast.makeText(AllPurchasesActivity.this, "does not contain anything", Toast.LENGTH_SHORT).show();


                                    }
                                }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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

//        displaying the item names
        databaseReference.child("/items")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                            Items items = dataSnapshot2.getValue(Items.class);
                            int stock = items.getItem_stock();
                            if (stock>0){
                                itemlist.add(items.getItem_name());
                            }
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

                //getting the date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY");
                final String purchaseDate = simpleDateFormat.format(new Date());

                //adding to firebase
                final String PurhcaseId = databaseReference.push().getKey();
                final String EmployeeCartId = databaseReference.push().getKey();

                final Employee employee = new Employee();
                employee.setEmp_name(selectedEmployee);

                final Purchase purchase = new Purchase();


                databaseReference.child("/purchases")
                        .orderByChild("purchase_emp_name")
                        .equalTo(selectedEmployee)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){ //employee have purchase already
                                    //we will check the purchase status
                                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                                        final String purchaseKey = dataSnapshot1.getKey();
                                        Purchase purchase1 = dataSnapshot1.getValue(Purchase.class);
                                        final String purchaseStatus = purchase1.getPurch_status();
                                        final String purchaseId = purchase1.getPurchase_key();
                                        final String empName = purchase1.getPurchase_emp_name();
                                        final String empPayDate = purchase1.getPurch_payment_date();
                                        final double purchaseDue = purchase1.getPurch_total_due();
                                        totalPurchaseQty = purchase1.getPurch_tot_qty(); //default qty
                                        totalPurchaseDue = purchase1.getPurch_total_due(); //default total due

                                        databaseReference.child("/items")
                                                .orderByChild("item_name")
                                                .equalTo(selectedItem)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                                                                String itemKey = dataSnapshot2.getKey();
                                                                Items items1 = dataSnapshot2.getValue(Items.class);
                                                                double itemPrice = items1.getItem_price();
                                                                int itemStock = items1.getItem_stock();

                                                                int itemQty = Integer.parseInt(editTextItemQty.getText().toString());

                                                                String strSubtotal = String.format("%.2f", itemPrice * itemQty);
                                                                double subtotal = Double.parseDouble(strSubtotal);

//                                                                double subtotal = itemPrice * itemQty;
                                                                int newItemStock = itemStock - itemQty;


                                                                final Items items = new Items();
                                                                items.setItem_name(selectedItem);
                                                                items.setItem_emp_purchased(selectedEmployee);
                                                                items.setItem_qty(itemQty);
                                                                items.setItem_price(itemPrice);
                                                                items.setItem_subtotal(subtotal);
                                                                items.setItem_purch_date(purchaseDate);

                                                                final EmployeeCart employeeCart = new EmployeeCart();
                                                                employeeCart.setItems(items);

                                                                cartMap.put(EmployeeCartId, employeeCart);

                                                                //
//                                                                double newTotalDue = totalPurchaseDue + subtotal;
                                                                int newTotalQty = totalPurchaseQty + itemQty;


                                                                String strNewDue = String.format("%.2f", totalPurchaseDue + subtotal);
                                                                double newTotalDue = Double.parseDouble(strNewDue);

                                                                //
                                                                final Purchase purchase = new Purchase();
                                                                purchase.setPurchase_emp_name(selectedEmployee);
                                                                purchase.setPurch_status("Pending");
                                                                purchase.setEmployee_cart(cartMap);
                                                                purchase.setPurch_total_due(newTotalDue);
                                                                purchase.setPurch_tot_qty(newTotalQty);

                                                                String strTotalDue = String.format("%.2f", totalPurchaseDue + subtotal);
                                                                double totalDue = Double.parseDouble(strTotalDue);

                                                                String uniqueId = empName+"completed"+""+empPayDate+""+purchaseDue;




                                                                if (purchaseId.equals(uniqueId)){ //status is completed and we will need to create another purchase
                                                                    if (itemStock >= itemQty) { //let's check if the stock is greater than the quantity purchased
                                                                        databaseReference.child("items/"+itemKey).child("item_stock").setValue(newItemStock); //deduct the stock count
                                                                        databaseReference.child("purchases").push().setValue(purchase); //save to firebase
                                                                        cartMap.clear();

                                                                        Toast.makeText(AllPurchasesActivity.this, "Purchase has been saved.", Toast.LENGTH_SHORT).show();

                                                                    } else {
                                                                        Toast.makeText(AllPurchasesActivity.this, "There are only " + itemStock + " left!", Toast.LENGTH_SHORT).show();
                                                                    } //end else
                                                                }else {
                                                                    if (itemStock >= itemQty) {

                                                                        databaseReference.child("items/" + itemKey).child("item_stock").setValue(newItemStock); //deduct the stock count

                                                                        databaseReference.child("purchases/"+purchaseKey).child("purch_total_due").setValue(totalDue);
                                                                        databaseReference.child("purchases/"+purchaseKey).child("purch_tot_qty").setValue(totalPurchaseQty + itemQty);

                                                                        databaseReference.child("purchases/" + purchaseKey).child("/employee_cart").updateChildren(cartMap);
                                                                        cartMap.clear();
                                                                        Toast.makeText(AllPurchasesActivity.this, "Purchase has been saved.", Toast.LENGTH_SHORT).show();

                                                                    } else {
                                                                        Toast.makeText(AllPurchasesActivity.this, "There are only " + itemStock + " left!", Toast.LENGTH_SHORT).show();
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
                                }else { //if employee does not have any transactions yet, we will create a new purchase transaction
                                    databaseReference.child("/items")
                                            .orderByChild("item_name")
                                            .equalTo(selectedItem)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()){
                                                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                                            String itemKey = dataSnapshot1.getKey();
                                                            Items items1 = dataSnapshot1.getValue(Items.class);
                                                            double itemPrice = items1.getItem_price();
                                                            int itemStock = items1.getItem_stock();

                                                            int itemQty = Integer.parseInt(editTextItemQty.getText().toString());
//                                                            double subtotal = itemPrice*itemQty;
                                                            int newItemStock = itemStock - itemQty;

                                                            String strSubtotal = String.format("%.2f", itemPrice*itemQty);
                                                            double subtotal = Double.parseDouble(strSubtotal);

                                                            final Items items = new Items();
                                                            items.setItem_name(selectedItem);
                                                            items.setItem_emp_purchased(selectedEmployee);
                                                            items.setItem_qty(itemQty);
                                                            items.setItem_price(itemPrice);
                                                            items.setItem_subtotal(subtotal);
                                                            items.setItem_purch_date(purchaseDate);

                                                            final EmployeeCart employeeCart = new EmployeeCart();
                                                            employeeCart.setItems(items);

                                                            cartMap.put(EmployeeCartId, employeeCart);

                                                            //we update the purchase quantity and total due of the employee purchase
                                                            int newPurchaseQty = totalPurchaseQty + itemQty;
//                                                            double newPurchaseDue = totalPurchaseDue + subtotal;

                                                            String strPurchaseDue = String.format("%.2f", totalPurchaseDue + subtotal);
                                                            double newPurchaseDue = Double.parseDouble(strPurchaseDue);

                                                            //
                                                            final Purchase purchase = new Purchase();
                                                            purchase.setPurchase_emp_name(selectedEmployee);
                                                            purchase.setPurch_tot_qty(newPurchaseQty);
                                                            purchase.setPurch_total_due(newPurchaseDue);
                                                            purchase.setPurch_status("Pending");
                                                            purchase.setEmployee_cart(cartMap);

                                                            //we deduct the itemStock
                                                            if (itemStock >= itemQty){ //let's check if the stock is greater than the quantity purchased
                                                                databaseReference.child("items/"+itemKey).child("item_stock").setValue(newItemStock); //deduct the stock count
                                                                databaseReference.child("purchases").push().setValue(purchase); //save to firebase
                                                                cartMap.clear();
                                                                Toast.makeText(AllPurchasesActivity.this, "Purchase has been saved.", Toast.LENGTH_SHORT).show();
                                                            }else {
                                                                Toast.makeText(AllPurchasesActivity.this, "There are only "+itemStock+" left!", Toast.LENGTH_SHORT).show();
                                                            }


                                                        }
                                                    }else {
                                                        Toast.makeText(AllPurchasesActivity.this, "Please select an item!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


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

}
