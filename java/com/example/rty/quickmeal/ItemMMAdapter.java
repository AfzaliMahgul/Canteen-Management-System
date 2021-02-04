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


public class ItemMMAdapter extends RecyclerView.Adapter<ItemMMAdapter.ItemMMViewHolder> {

    private List<Item> mItemMMList;

    public ItemMMAdapter(List<Item> mItemMMList) {
        this.mItemMMList = mItemMMList;
    }

    @Override
    public ItemMMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ItemMMViewHolder(v);
    }

    // ------- Show Main Meal item details inside Recycler view --------
    @Override
    public void onBindViewHolder(ItemMMViewHolder holder, int position) {

        Item i = mItemMMList.get(position);
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

                Intent DspecificItem = new Intent(view.getContext(), MainMealSpecificItem.class);
                DspecificItem.putExtra("ItemId", itemId);

                ctx.startActivity(DspecificItem);

            }
        });
    }

    //--------- Get the data feilds inside Recyclerview using ViewHolder -----------
    @Override
    public int getItemCount() {
        return mItemMMList.size();
    }


    public class ItemMMViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView itemPrice;
        public TextView itemChecked;
        public ImageView itemImage;

        public ItemMMViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemChecked = (TextView) itemView.findViewById(R.id.tv);

            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
        }
    }

    public void setFilter(List<Item> newList){

        mItemMMList=new ArrayList<>();
        mItemMMList.addAll(newList);
        notifyDataSetChanged();
    }
}
