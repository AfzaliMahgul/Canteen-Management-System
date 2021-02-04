package com.example.rty.quickmeal;

/**
 * Created by Shree on 08-01-2018.
 */

public class Item {

    private String itemId;
    private String itemImage;
    private String itemName;
    private String itemPrice;

    public Item(String itemId, String itemName, String itemImage, String itemPrice) {
        this.itemId = itemId;
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;

    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
