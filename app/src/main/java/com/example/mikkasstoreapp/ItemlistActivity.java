package com.example.mikkasstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Adapters.ItemListAdapter;
import com.example.mikkasstoreapp.Objects.Itemlistdata;
import com.example.mikkasstoreapp.Objects.Items;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemlistActivity extends AppCompatActivity {

    FloatingActionButton fab_additem;
    RecyclerView recyclerViewItemList;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    List<Itemlistdata> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        fab_additem = findViewById(R.id.fab_add_items);
        recyclerViewItemList = findViewById(R.id.recycler_itemlist);

        fab_additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemlistActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        //
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data");
    }

    @Override
    protected void onStart() {
        super.onStart();

        display_all_item();
    }

    private void display_all_item() {
        databaseReference.child("items")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            Items items = dataSnapshot1.getValue(Items.class);
                            Itemlistdata itemlistdata = new Itemlistdata();
                            String itemName = items.getItem_name();
                            int itemQty = items.getItem_qty();
                            double itemPrice = items.getItem_price();
                            itemlistdata.setItem_name(itemName);
                            itemlistdata.setItem_qty(itemQty);
                            itemlistdata.setItem_price(itemPrice);
                            list.add(itemlistdata);
                        }
                            ItemListAdapter adapter = new ItemListAdapter(getApplicationContext(), list);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerViewItemList.setLayoutManager(layoutManager);
                            recyclerViewItemList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
                            recyclerViewItemList.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewItemList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

//    private void display_all_item() {
//        SharedPreferences userPref = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
//        final String username = (userPref.getString("user_username",""));
//
//        databaseReference.child("users")
//                .orderByChild("username")
//                .equalTo(username)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()){
//                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                                databaseReference.child("items")
//                                        .addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                list = new ArrayList<>();
//                                                for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
//                                                    Items items = dataSnapshot2.getValue(Items.class);
//                                                    Itemlistdata itemlistdata = new Itemlistdata();
//
//                                                    String itemName = items.getItem_name();
//                                                    int itemQty = items.getItem_qty();
//                                                    double itemPrice = items.getItem_price();
//
//                                                    itemlistdata.setItem_name(itemName);
//                                                    itemlistdata.setItem_qty(itemQty);
//                                                    itemlistdata.setItem_price(itemPrice);
//                                                    list.add(itemlistdata);
//                                                }
//                                                ItemListAdapter adapter = new ItemListAdapter(getApplicationContext(), list);
//                                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                                                recyclerViewItemList.setLayoutManager(layoutManager);
//                                                recyclerViewItemList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
//                                                recyclerViewItemList.setItemAnimator(new DefaultItemAnimator());
//                                                recyclerViewItemList.setAdapter(adapter);
//                                                adapter.notifyDataSetChanged();
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                            }
//                                        });
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//    }
}
