package com.example.rty.quickmeal.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.rty.quickmeal.Order;
import com.example.rty.quickmeal.Unique;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rty on 02/02/18.
 */

public class Database extends SQLiteAssetHelper{

    private static final String DB_NAME = "DB.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"itemId", "itemName", "itemPrice", "itemImage", "itemDesc" , "itemQty", "totalPrice", "itemImage"};
        String sqlTable = "OrderDetails";

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();
        if(cursor.moveToFirst()) {

            do {
                result.add(new Order(cursor.getString(cursor.getColumnIndex("itemId")),
                        cursor.getString(cursor.getColumnIndex("itemName")),
                        cursor.getString(cursor.getColumnIndex("itemPrice")),
                        cursor.getString(cursor.getColumnIndex("itemDesc")),
                        cursor.getString(cursor.getColumnIndex("itemQty")),
                        cursor.getString(cursor.getColumnIndex("totalPrice")),
                        cursor.getString(cursor.getColumnIndex("itemImage"))
                ));
            }while (cursor.moveToNext());
        }

        return result;
    }





    public void addToCart(Order order) {

        SQLiteDatabase db = getReadableDatabase();
        //SQLiteDatabase db = getWritableDatabase();
        String query = String.format("INSERT INTO OrderDetails(itemId, itemName, itemPrice,itemDesc,itemQty, totalPrice, itemImage) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                order.getItemId(),
                order.getItemName(),
                order.getItemPrice(),
                order.getItemDesc(),
                order.getItemQty(),
                order.getTotalPrice(),
                order.getItemImage());



        db.execSQL(query);
    }


    public void clearCart() {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetails");

        db.execSQL(query);
    }


    public List<Unique> getUnique() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"itemId"};
        String sqlTable = "OrderDetails";

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<Unique> result = new ArrayList<>();
        if(cursor.moveToFirst()) {

            do {
                result.add(new Unique(cursor.getString(cursor.getColumnIndex("itemId"))
                ));
            }while (cursor.moveToNext());
        }

        return result;
    }

    /*
    public String selectCart(CartItems cartItems) {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteDatabase dbw = getWritableDatabase();
        //String query = String.format("INSERT INTO OrderDetails(itemId, itemName, itemPrice,itemDesc, itemImage) VALUES('%s', '%s', '%s', '%s', '%s');",
        String query= "Select *from OrderDetails";
        Cursor cursor = db.rawQuery(query, null);


        db.execSQL(query);
        return query;
    }

    */
}
