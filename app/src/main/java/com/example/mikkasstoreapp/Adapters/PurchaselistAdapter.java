package com.example.mikkasstoreapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Objects.Purchaselistdata;
import com.example.mikkasstoreapp.R;

import java.util.List;

public class PurchaselistAdapter extends RecyclerView.Adapter<PurchaselistAdapter.ViewHolder> {

    Context context;
    List<Purchaselistdata> list;

    public PurchaselistAdapter(Context context, List<Purchaselistdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_purchase_list, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Purchaselistdata data = list.get(position);

        holder.purch_emp_name.setText(data.getPurchase_emp_name());
        holder.purch_item.setText(data.getItems().getItem_name());
        holder.purch_date.setText(data.getItems().getItem_purch_date());
        holder.purch_details.setText(data.getItems().getItem_price()+" x "+data.getItems().getItem_qty()+" =");
        holder.purch_subtotal.setText(" "+data.getItems().getItem_subtotal());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout purchlist_layout;
        TextView purch_emp_name, purch_date, purch_item, purch_details, purch_subtotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            purchlist_layout = (LinearLayout) itemView.findViewById(R.id.layout_purchlist);
            purch_emp_name = (TextView) itemView.findViewById(R.id.purch_emp_name);
            purch_date = (TextView) itemView.findViewById(R.id.purch_date);
            purch_item = (TextView) itemView.findViewById(R.id.purch_item_name);
            purch_details = (TextView) itemView.findViewById(R.id.purch_item_details);
            purch_subtotal = (TextView) itemView.findViewById(R.id.purch_item_subtotal);
        }
    }
}
