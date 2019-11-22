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
import com.example.mikkasstoreapp.R;
import com.example.mikkasstoreapp.ViewCompleteActivity;

import java.util.List;

public class CompletedPaymentAdapter extends RecyclerView.Adapter<CompletedPaymentAdapter.ViewHolder> {

    Context context;
    List<Purchaselistdata> list;

    public CompletedPaymentAdapter(Context context, List<Purchaselistdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_complete_payment, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String purchaserName = list.get(viewHolder.getAdapterPosition()).getPurchase_emp_name();
                String purchaseStatus = list.get(viewHolder.getAdapterPosition()).getPurch_status();
                double purchaseDue = list.get(viewHolder.getAdapterPosition()).getPurch_total_due();
                int purchaseQty = list.get(viewHolder.getAdapterPosition()).getPurch_tot_qty();
                String paymentDate = list.get(viewHolder.getAdapterPosition()).getPurch_payment_date();

                Intent intent = new Intent(v.getContext(), ViewCompleteActivity.class);
                intent.putExtra("employee_name", purchaserName);
                intent.putExtra("status", purchaseStatus);
                intent.putExtra("due", purchaseDue);
                intent.putExtra("qty", purchaseQty);
                intent.putExtra("date", paymentDate);
                v.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Purchaselistdata data = list.get(position);

        holder.empName.setText(data.getPurchase_emp_name());
        holder.purchaseDue.setText("₱ "+data.getPurch_total_due());
        holder.paymentStatus.setText(data.getPurch_status());
        holder.purchaseQty.setText(data.getPurch_tot_qty()+" items, Total: ");
        holder.paymentDate.setText("Paid on: "+data.getPurch_payment_date());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView empName, purchaseQty, purchaseDue, paymentDate, paymentStatus, userName;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            empName = (TextView) itemView.findViewById(R.id.payment_purchaser);
            paymentDate = (TextView) itemView.findViewById(R.id.payment_date);
            purchaseQty = (TextView) itemView.findViewById(R.id.purchItemDetails);
            purchaseDue = (TextView) itemView.findViewById(R.id.purch_item_due);
            paymentStatus = (TextView) itemView.findViewById(R.id.payment_status);
            userName = (TextView) itemView.findViewById(R.id.payment_empname);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);

        }
    }
}
