package com.example.mikkasstoreapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mikkasstoreapp.Objects.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtSignUp;
    Button btnSignin;
    EditText editUsername;
    EditText editPassw;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSignUp = findViewById(R.id.text_sign_up);
        btnSignin = findViewById(R.id.btn_sign_in);
        editUsername = findViewById(R.id.edit_username);
        editPassw = findViewById(R.id.edit_password);

        btnSignin.setOnClickListener(this);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        //
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data/users");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        
        switch (id){
            case R.id.btn_sign_in: 
                user_login();
                break;
        }
    }

    private void user_login() {
        SharedPreferences userPref = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = userPref.edit();

        final Gson gson = new Gson();

        final String username = editUsername.getText().toString();
        final String password = editPassw.getText().toString();

        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    editor.putString("user_username", username);

                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        Users users = dataSnapshot1.getValue(Users.class);

                        if (users.getPassword().equals(password)){
                            editor.commit();
                            Intent intent = new Intent(MainActivity.this, AllMenuActivity.class);
                            Toast.makeText(MainActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }

                }else {
                    Toast.makeText(MainActivity.this, username+" does not exist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
