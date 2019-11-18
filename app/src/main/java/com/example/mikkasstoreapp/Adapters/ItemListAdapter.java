package com.example.mikkasstoreapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikkasstoreapp.Objects.Itemlistdata;
import com.example.mikkasstoreapp.R;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    Context context;
    List<Itemlistdata> list;

    public ItemListAdapter(Context context, List<Itemlistdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem_list, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Itemlistdata data = list.get(position);

        holder.itemName.setText(data.getItem_name());
        holder.itemQty.setText("Qty: "+data.getItem_qty());
        holder.itemPrice.setText(""+data.getItem_price());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, itemQty, itemPrice;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.card_item_name);
            itemQty = (TextView) itemView.findViewById(R.id.card_item_qty);
            itemPrice = (TextView) itemView.findViewById(R.id.card_item_price);
            cardView = (CardView) itemView.findViewById(R.id.card_emplist);
        }
    }
}
