package com.example.mikkasstoreapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmployeeList2Activity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerEmpList;
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list2);

        recyclerEmpList = findViewById(R.id.recyclerview_emplist);
        fab_add = findViewById(R.id.fab_add_emp);

        fab_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.fab_add_emp:
                    show_add_emp_dialog();
                break;
        }
    }

    private void show_add_emp_dialog() {
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_add_emp_layout, null);
        dialogbuilder.setView(dialogView);

        final EditText textEmpName = (EditText) dialogView.findViewById(R.id.edit_empnickname);

        dialogbuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //saving to firebase database
            }
        });

        dialogbuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //closing the alert dialog
            }
        });

        AlertDialog build = dialogbuilder.create();
        build.show();
    }
}
