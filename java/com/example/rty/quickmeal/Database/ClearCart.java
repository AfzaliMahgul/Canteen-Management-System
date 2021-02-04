package com.example.rty.quickmeal.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by rty on 02/27/18.
 */

public class ClearCart extends SQLiteAssetHelper {

    private static final String DB_NAME = "database.db";
    private static final int DB_VER = 1;

    public ClearCart(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }




    public void clearCart() {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetails");

        db.execSQL(query);
    }
}