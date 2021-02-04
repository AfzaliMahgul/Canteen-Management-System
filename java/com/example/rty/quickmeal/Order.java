package com.example.rty.quickmeal;

/**
 * Created by rty on 02/02/18.
 */

public class Order {

    private String itemId;
    private String itemName;
    private String itemPrice;
    private String itemDesc;
    private String itemQty;
    private String totalPrice;
    private String itemImage;

    public Order(String itemId, String itemName, String itemPrice, String itemDesc, String itemQty, String totalPrice, String itemImage) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDesc = itemDesc;
        this.itemQty = itemQty;
        this.totalPrice = totalPrice;
        this.itemImage = itemImage;
    }

    public Order() {

    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
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
