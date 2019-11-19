package com.example.mikkasstoreapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Adapters.EmployeelistAdapter;
import com.example.mikkasstoreapp.Objects.Employee;
import com.example.mikkasstoreapp.Objects.Employeelistdata;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList2Activity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerEmpList;
    FloatingActionButton fab_add;
    String employeeId;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    List<Employeelistdata> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list2);

        recyclerEmpList = findViewById(R.id.recyclerview_emplist);
        fab_add = findViewById(R.id.fab_add_emp);

        fab_add.setOnClickListener(this);

        //
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/store_data");
        employeeId = databaseReference.push().getKey();
    }

    @Override
    protected void onStart() {
        super.onStart();

        display_all_emp();
    }

    private void display_all_emp() {
        databaseReference.child("employee")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            Employee employee = dataSnapshot1.getValue(Employee.class);
                            Employeelistdata employeelistdata = new Employeelistdata();
                            String emp_name = employee.getEmp_name();
                            employeelistdata.setEmp_name(emp_name);
                            list.add(employeelistdata);
                        }
                        EmployeelistAdapter adapter = new EmployeelistAdapter(getApplicationContext(), list);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerEmpList.setLayoutManager(layoutManager);
                        recyclerEmpList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
                        recyclerEmpList.setItemAnimator(new DefaultItemAnimator());
                        recyclerEmpList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
                add_employee(textEmpName.getText().toString());
                Toast.makeText(EmployeeList2Activity.this, "New employee has been added.", Toast.LENGTH_SHORT).show();
//                finish();

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


    private void add_employee(final String emp_name) {
        final Employee employee = new Employee(emp_name);

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
        final String username = (userPref.getString("user_username",""));

        employee.setEmp_name(emp_name);

        databaseReference.child("users")
                .orderByChild("username")
                .equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        databaseReference.child("employee").push().setValue(employee);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
