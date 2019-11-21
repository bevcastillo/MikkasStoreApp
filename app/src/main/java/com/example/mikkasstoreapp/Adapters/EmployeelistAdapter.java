package com.example.mikkasstoreapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Objects.Employeelistdata;
import com.example.mikkasstoreapp.R;

import java.util.List;

public class EmployeelistAdapter extends RecyclerView.Adapter<EmployeelistAdapter.ViewHolder> {

    Context context;
    List<Employeelistdata> list;

    public EmployeelistAdapter(Context context, List<Employeelistdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_employee_list, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employeelistdata data = list.get(position);

        holder.emp_name.setText(data.getEmp_name());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView emp_name;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emp_name = (TextView) itemView.findViewById(R.id.employee_name);
            layout = (LinearLayout) itemView.findViewById(R.id.itemlist_layout);
        }
    }

}
