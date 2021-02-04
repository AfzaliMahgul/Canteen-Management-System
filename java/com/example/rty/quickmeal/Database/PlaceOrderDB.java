package com.example.rty.quickmeal.Database;


import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteQueryBuilder;
        import android.util.Log;
        import android.widget.Toast;

        import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by rty on 02/02/18.
 */

public class PlaceOrderDB extends SQLiteAssetHelper{

    private static final String DB_NAME = "database.db";
    private static final int DB_VER = 1;

    public PlaceOrderDB(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }




    public List<CartItems> getItemId(CartItems cartItems) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"itemId"};
        String sqlTable = "OrderDetails";

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db, sqlSelect, null,null,null,null,null);

        final List<CartItems> result = new ArrayList<>();
        if(cursor.moveToFirst()) {

            do {
                result.add(new CartItems(cursor.getString(cursor.getColumnIndex("itemId"))));

            }while (cursor.moveToNext());
        }

        return result;
    }

    public void SelectCart(CartItems item) {

        SQLiteDatabase db = getReadableDatabase();
        //SQLiteDatabase dbw = getWritableDatabase();
        //String query = String.format("INSERT INTO OrderDetails(itemId, itemName, itemPrice,itemDesc, itemImage) VALUES('%s', '%s', '%s', '%s', '%s');",
        String query= "Select *from OrderDetails";
        Cursor cursor = db.rawQuery(query, null);


        db.execSQL(query);
    }
}

