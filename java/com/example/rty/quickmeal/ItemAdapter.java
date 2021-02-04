package com.example.rty.quickmeal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private List<Item> mItemList;

    public ItemAdapter(List<Item> mItemList) {
        this.mItemList = mItemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ItemViewHolder(v);
    }

    // ------- Show drink item details inside Recycler view --------
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final Context context = holder.mView.getContext();
        Item i = mItemList.get(position);
        String itemName = i.getItemName();
        String itemPrice = i.getItemPrice();
        String itemImage = i.getItemImage();
        final String itemId = i.getItemId();

        holder.itemPrice.setText("₹ "+ itemPrice);
        holder.itemSelected.setText(itemName);
        Picasso.with(holder.itemImage.getContext()).load(itemImage).into(holder.itemImage);

        // --------- Go to item specification activity ----------
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent specificItem = new Intent(view.getContext(), DrinkSpecifcItem.class);
                specificItem.putExtra("ItemId", itemId);

                context.startActivity(specificItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    //--------- Get the data feilds inside Recyclerview using ViewHolder -----------
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public String itemId;
        public TextView itemPrice;
        public TextView itemSelected;
        public ImageView itemImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemSelected = (TextView) itemView.findViewById(R.id.tv);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
        }
    }

    public void setFilter(List<Item> newList){

        mItemList=new ArrayList<>();
        mItemList.addAll(newList);
        notifyDataSetChanged();
    }
}
