package com.example.mikkasstoreapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mikkasstoreapp.Objects.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllMenuActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardViewEmplist, cardViewItemlist, cardViewPurchList;
    TextView txtFirstname;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_menu);

        cardViewEmplist = findViewById(R.id.card_emplist);
        cardViewItemlist = findViewById(R.id.card_items);
        cardViewPurchList = findViewById(R.id.card_purchases);
        txtFirstname = findViewById(R.id.txt_firstname);

        cardViewEmplist.setOnClickListener(this);
        cardViewItemlist.setOnClickListener(this);
        cardViewPurchList.setOnClickListener(this);


        //
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data/users");


    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
        final String username = (userPref.getString("user_username",""));

        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        String userKey = dataSnapshot1.getKey();
                        Users users = dataSnapshot1.getValue(Users.class);

                        String userFirstname = users.getFirstname();

                        txtFirstname.setText(userFirstname);
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

        switch (id) {
            case R.id.card_emplist:
                    Intent intent = new Intent(AllMenuActivity.this, EmployeeList2Activity.class);
                    startActivity(intent);
                break;
            case R.id.card_items:
                    Intent intent1 = new Intent(AllMenuActivity.this, ItemlistActivity.class);
                    startActivity(intent1);
                break;
            case R.id.card_purchases:
                    Intent intent2 = new Intent(AllMenuActivity.this, AllPurchasesActivity.class);
                    startActivity(intent2);
                break;
        }

    }
}
