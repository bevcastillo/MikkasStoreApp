package com.example.mikkasstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mikkasstoreapp.Objects.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText userLname, userfname, userUsername, userPassw;
    Button btnGetStarted;

    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userLname = findViewById(R.id.edit_user_lastname);
        userfname = findViewById(R.id.edit_user_firstname);
        userUsername = findViewById(R.id.edit_user_username);
        userPassw = findViewById(R.id.edit_user_password);
        btnGetStarted = findViewById(R.id.btn_user_get_started);


        btnGetStarted.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("store_data");
        storeId = databaseReference.push().getKey();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_user_get_started:
                if (validate_fields()){
                    user_register();
                }
                break;
        }
    }

    private boolean validate_fields(){
        String txtlname = userLname.getText().toString();
        String txtfname = userfname.getText().toString();
        String txtusername = userUsername.getText().toString();
        String txtpassword = userPassw.getText().toString();
        boolean ok = true;

        if (txtlname.isEmpty()){
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            ok = false;
        }

        if (txtfname.isEmpty()){
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            ok = false;
        }

        if (txtusername.isEmpty()){
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            ok = false;
        }

        if (txtpassword.isEmpty()){
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            ok = false;
        }

        return ok;

    }

    private void create_user(String lastname, String firstname, String username, String password) {
        Users users = new Users(lastname, firstname, username, password);
        databaseReference.child("users").child(storeId).setValue(users);
    }

    private void user_register() {
        String txtlname = userLname.getText().toString();
        String txtfname = userfname.getText().toString();
        String txtusername = userUsername.getText().toString();
        String txtpassword = userPassw.getText().toString();

        create_user(txtlname, txtfname, txtusername, txtpassword);
        Intent intent = new Intent(SignupActivity.this, AllMenuActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Successfully registered.", Toast.LENGTH_SHORT).show();

    }

}
