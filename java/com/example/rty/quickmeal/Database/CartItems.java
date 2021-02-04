package com.example.rty.quickmeal.Database;

/**
 * Created by rty on 02/27/18.
 */
public class CartItems {

    private String itemId;
    private String itemName;
    private String itemPrice;
    private String itemImage;
    private String itemDesc;

    public CartItems(String itemId) {
        this.itemId = itemId;

    }

    public CartItems() {

    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}