package com.example.rty.quickmeal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SnacksAdapter extends RecyclerView.Adapter<SnacksAdapter.SnacksViewHolder> {

    private List<Item> mSnacksList;

    public SnacksAdapter(List<Item> mSnacksList) {
        this.mSnacksList = mSnacksList;
    }

    @Override
    public SnacksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new SnacksViewHolder(v);
    }

    // ------- Show Snacks item details inside Recycler view --------
    @Override
    public void onBindViewHolder(SnacksViewHolder holder, int position) {

        Item i = mSnacksList.get(position);
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

                Intent DspecificItem = new Intent(view.getContext(), SnacksSpecificItem.class);
                DspecificItem.putExtra("ItemId", itemId);

                ctx.startActivity(DspecificItem);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mSnacksList.size();
    }

    //--------- Get the data feilds inside Recyclerview using ViewHolder -----------
    public class SnacksViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView itemPrice;
        public TextView itemChecked;
        public ImageView itemImage;

        public SnacksViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemChecked = (TextView) itemView.findViewById(R.id.tv);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
        }
    }

    public void setFilter(List<Item> newList){

        mSnacksList=new ArrayList<>();
        mSnacksList.addAll(newList);
        notifyDataSetChanged();
    }
}
