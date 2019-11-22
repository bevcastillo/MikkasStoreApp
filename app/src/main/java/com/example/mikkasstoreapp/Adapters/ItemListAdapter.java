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

import com.example.mikkasstoreapp.EditItemActivity;
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

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = list.get(viewHolder.getAdapterPosition()).getItem_name();
                int itemStock = list.get(viewHolder.getAdapterPosition()).getItem_stock();
                double itemPrice = list.get(viewHolder.getAdapterPosition()).getItem_price();

                Intent intent = new Intent(v.getContext(), EditItemActivity.class);
                intent.putExtra("item_name", itemName);
                intent.putExtra("item_stock", itemStock);
                intent.putExtra("item_price", itemPrice);
                v.getContext().startActivity(intent);

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Itemlistdata data = list.get(position);

        holder.itemName.setText(data.getItem_name());
        holder.itemQty.setText("Stock: "+data.getItem_stock());
        holder.itemPrice.setText(""+data.getItem_price());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, itemQty, itemPrice;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.card_item_name);
            itemQty = (TextView) itemView.findViewById(R.id.card_item_qty);
            itemPrice = (TextView) itemView.findViewById(R.id.card_item_price);
            layout = (LinearLayout) itemView.findViewById(R.id.layout_items);
        }
    }
}
