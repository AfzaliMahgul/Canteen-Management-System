package com.example.rty.quickmeal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.DessertViewHolder> {

    private List<History> mOrderHistory;
    private Calendar calendar;

    public OrderHistoryAdapter(List<History> mHistory) {
        this.mOrderHistory = mHistory;
    }

    @Override
    public DessertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history, parent, false);
        return new DessertViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DessertViewHolder holder, int position) {

        History i = mOrderHistory.get(position);
        final Context ctx=holder.mView.getContext();

        //calendar= Calendar.getInstance();
        //String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        String itemName = i.getItemName();
        String itemPrice = i.getItemPrice();
        String itemImage = i.getItemImage();
        final String itemId = i.getItemId();
        String itemQty= i.getItemQty();
        String totalPrice= i.getTotalPrice();

        //Toast.makeText(ctx.getApplicationContext(), itemPrice, Toast.LENGTH_SHORT).show();
        holder.itemPrice.setText("Price: ₹ "+itemPrice);
        holder.itemName.setText(itemName);
        holder.itemQty.setText("Quantity: "+itemQty);
        holder.totalPrice.setText("Total: ₹ "+totalPrice);
        Picasso.with(holder.itemImage.getContext()).load(itemImage).into(holder.itemImage);




    }

    @Override
    public int getItemCount() {
        return mOrderHistory.size();
    }


    // ------- Creating History ---------
    public class DessertViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView itemPrice;
        public TextView itemName;
        public ImageView itemImage;
        public TextView itemQty;
        public TextView totalPrice;

        public DessertViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemQty= (TextView) itemView.findViewById(R.id.qty1);
            totalPrice= (TextView) itemView.findViewById(R.id.total);
        }
    }
}
