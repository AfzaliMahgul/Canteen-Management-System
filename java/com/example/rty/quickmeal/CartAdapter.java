package com.example.rty.quickmeal;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rty on 02/02/18.
 */

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView item_cart_name;
    public TextView item_cart_price;
    public ImageView item_cart_count;
    public ImageView item_delete;


    public void setItem_cart_name(TextView item_cart_name) {
        this.item_cart_name = item_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);

        item_cart_name = (TextView) itemView.findViewById(R.id.cart_item_name);
        item_cart_price = (TextView) itemView.findViewById(R.id.cart_item_price);
        item_cart_count = (ImageView) itemView.findViewById(R.id.cart_item_count);

        item_delete = (ImageView) itemView.findViewById(R.id.delete_item);

    }

    @Override
    public void onClick(View view) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }




    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);

        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {

        TextDrawable drawable = TextDrawable.builder()
                .buildRound("" + listData.get(position).getItemQty(), Color.RED);
        holder.item_cart_count.setImageDrawable(drawable);

        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getTotalPrice()));

        //holder.item_cart_price.setText(format.format(price));

        holder.item_cart_price.setText("₹ "+ price);

        //holder.item_cart_price.setText(listData.get(position).getTotalPrice());
        holder.item_cart_name.setText(listData.get(position).getItemName());


        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
