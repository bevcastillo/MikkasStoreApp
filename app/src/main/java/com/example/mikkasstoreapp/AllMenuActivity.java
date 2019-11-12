package com.example.mikkasstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AllMenuActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardViewEmplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_menu);

        cardViewEmplist = findViewById(R.id.card_emplist);

        cardViewEmplist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.card_emplist:
                    Intent intent = new Intent(AllMenuActivity.this, EmployeeList2Activity.class);
                    startActivity(intent);
                break;
        }

    }
}
