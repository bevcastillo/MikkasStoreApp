package com.example.mikkasstoreapp.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.R;

import java.util.List;

public class DetailedItemSummaryAdapter extends RecyclerView.Adapter<DetailedItemSummaryAdapter.ViewHolder> {

    Context context;
    List<Items> list;

    public DetailedItemSummaryAdapter(Context context, List<Items> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_payment_summary, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items items =  list.get(position);

        if (!items.equals(null)){
            holder.itemName.setText(items.getItem_name());
            holder.purchaseDate.setText(items.getItem_purch_date());
            holder.itemDetails.setText(""+items.getItem_qty()+" X "+items.getItem_price());
            holder.itemSubtotal.setText(""+items.getItem_subtotal());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView purchaseDate, itemName, itemDetails, itemSubtotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            purchaseDate = (TextView) itemView.findViewById(R.id.summary_purchased_item_date);
            itemName = (TextView) itemView.findViewById(R.id.summary_purchaser_item_name);
            itemDetails = (TextView) itemView.findViewById(R.id.summary_item_details);
            itemSubtotal = (TextView) itemView.findViewById(R.id.summary_item_subtotal);
        }
    }
}
