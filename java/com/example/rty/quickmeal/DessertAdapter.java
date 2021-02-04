package com.example.rty.quickmeal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DessertAdapter extends RecyclerView.Adapter<DessertAdapter.DessertViewHolder> {

    private List<Item> mDessertList;
    Context context;

    public DessertAdapter(List<Item> mDessertList) {
        this.mDessertList = mDessertList;
    }

    @Override
    public DessertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new DessertViewHolder(v);
    }


    // ------- Show dessert item details inside Recycler view --------
    @Override
    public void onBindViewHolder(DessertViewHolder holder, int position) {
        Item i = mDessertList.get(position);
        final Context ctx=holder.mView.getContext();

        String itemName = i.getItemName();
        String itemPrice = i.getItemPrice();
        String itemImage = i.getItemImage();
        final String itemId = i.getItemId();

        holder.itemPrice.setText("₹ "+ itemPrice);
        holder.itemChecked.setText(itemName);
        Picasso.with(holder.itemImage.getContext()).load(itemImage).into(holder.itemImage);

        // --------- Go to item specification activity ----------
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent DspecificItem = new Intent(view.getContext(), DessertSpecificItem.class);
                DspecificItem.putExtra("ItemId", itemId);

                ctx.startActivity(DspecificItem);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDessertList.size();
    }

    //--------- Get the data feilds inside Recyclerview using ViewHolder -----------
    public class DessertViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView itemPrice;
        public TextView itemChecked;
        public ImageView itemImage;
        public ImageView deleteIcon;

        public DessertViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemChecked = (TextView) itemView.findViewById(R.id.tv);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);

            deleteIcon = (ImageView) mView.findViewById(R.id.delete_item);
        }
    }

    // ----------search filter----------
    public void setFilter(List<Item> newList){

        mDessertList=new ArrayList<>();
        mDessertList.addAll(newList);
        notifyDataSetChanged();
    }
}
