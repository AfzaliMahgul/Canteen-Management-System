package com.example.rty.quickmeal;

public class History {
    private String itemId;
    private String itemImage;
    private String itemName;
    private String itemPrice;
    private String totalPrice;
    private String itemQty;

    public History(String itemName, String itemImage, String itemPrice, String itemQty, String totalPrice) {
        this.itemId = itemId;
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.totalPrice = totalPrice;

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

    public String getItemQty() {
        return itemQty;
    }
    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }



}
