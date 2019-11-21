package com.example.mikkasstoreapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Objects.Purchaselistdata;
import com.example.mikkasstoreapp.PaymentDetailsActivity;
import com.example.mikkasstoreapp.R;

import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolder> {

    Context context;
    List<Purchaselistdata> list;

    public PaymentsAdapter(Context context, List<Purchaselistdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_payments, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String purchaserName = list.get(viewHolder.getAdapterPosition()).getPurchase_emp_name();
                String purchaseStatus = list.get(viewHolder.getAdapterPosition()).getPurch_status();
                double purchaseDue = list.get(viewHolder.getAdapterPosition()).getPurch_total_due();

                Intent intent = new Intent(v.getContext(), PaymentDetailsActivity.class);
                intent.putExtra("purchaser_name", purchaserName);
                intent.putExtra("purchase_status", purchaseStatus);
                intent.putExtra("purchase_due", purchaseDue);
                v.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Purchaselistdata data = list.get(position);

        holder.empName.setText(data.getPurchase_emp_name());
        holder.purchaseStatus.setText("Status: "+data.getPurch_status());
        holder.purchaseDue.setText(""+data.getPurch_total_due());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView empName, purchaseDue, purchaseStatus;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            empName = (TextView) itemView.findViewById(R.id.purch_emp_name);
            purchaseStatus = (TextView) itemView.findViewById(R.id.purchase_status);
            purchaseDue = (TextView) itemView.findViewById(R.id.purch_item_due);
            layout = (LinearLayout) itemView.findViewById(R.id.layout_purchlist);
        }
    }
}
