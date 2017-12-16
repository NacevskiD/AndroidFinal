package com.david.capstonefinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;

import java.util.ArrayList;

public class LocalDB {

    private Context context;
    private SQLHelper helper;
    private SQLiteDatabase db;
    protected static final String DB_NAME = "items.db";

    protected static final int DB_VERSION = 1;
    protected static final String DB_TABLE = "data";

    private static final String DATE_COL = "_date";
    protected static final String DESC_COL = "description";
    protected static final String TITLE_COL = "title";
    protected static final String RATING_COL = "rating";
    protected static final String PICTURE_COL = "pic";
    protected static final String COMM_RATING_COL = "commRating";
    protected static final String YOUR_RATING = "yourRating";
    protected static final String YTLINK_COL = "ytLink";
    protected static final String ACTOR_COL = "actor";


    private static final String DB_TAG = "DatabaseManager" ;
    private static final String SQL_TAG = "SQLHelper" ;

    public LocalDB(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close(); // Closes the database - very important to ensure all data is saved!
    }


    public void addItem(String title,String date, String desc,String rating,String actor, String commRating,String blob,String ytLink, String yourRating){
        ContentValues newProduct = new ContentValues();
        newProduct.put(DATE_COL,date);
        newProduct.put(DESC_COL,desc);
        newProduct.put(PICTURE_COL,blob);
        newProduct.put(TITLE_COL,title);
        newProduct.put(RATING_COL,rating);
        newProduct.put(COMM_RATING_COL,commRating);
        newProduct.put(YOUR_RATING,yourRating);
        newProduct.put(YTLINK_COL,ytLink);
        newProduct.put(ACTOR_COL,actor);


        try {
            db.insertOrThrow(DB_TABLE,null,newProduct);
        }
        catch (SQLiteConstraintException sqlce){
            Log.e("Database","Error inserting data");
        }
    }

    public void deleteItem(String name){
        String[] whereArgs = {name};
        String where = DATE_COL + " = ?";
        int rowsDeleted = db.delete(DB_TABLE,where,whereArgs);
        Log.i("Database","Deleted");
    }

    void dropDB() {
        String dropTable = "DROP TABLE " + DB_TABLE;
        db.execSQL(dropTable);
    }

    void createTable(){
        String createTable = "CREATE TABLE " + DB_TABLE +
                " (" + DATE_COL + " TEXT PRIMARY KEY, " +
                TITLE_COL + " TEXT ," + DESC_COL + " TEXT ," + RATING_COL + " TEXT ," +
                COMM_RATING_COL + " TEXT ," + YOUR_RATING + " TEXT ," + ACTOR_COL + " TEXT ," +
                YTLINK_COL +" TEXT, " + PICTURE_COL +" TEXT);";

        db.execSQL(createTable);
    }

    public ArrayList<FirebaseData> fetchAllProducts() {

        ArrayList<FirebaseData> products = new ArrayList<>();

        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, DATE_COL);

        while (cursor.moveToNext()) {
            String date = cursor.getString(0);
            String desc = cursor.getString(2);
            String image = cursor.getString(8);
            String title = cursor.getString(1);
            String rating = cursor.getString(3);
            String commRating = cursor.getString(4);
            String yourRating = cursor.getString(5);
            String ytLink = cursor.getString(7);
            String actor = cursor.getString(6);
            FirebaseData listItem = new FirebaseData();
            listItem.setDate(date);
            listItem.setDesc(desc);
            listItem.setPicture(image);
            listItem.setTitle(title);
            listItem.setRating(rating);
            listItem.setCommRating(Float.parseFloat(commRating));
            Float test = Float.parseFloat("5");
            listItem.setYourRating(test);
            listItem.setYtLink(ytLink);

            products.add(listItem);
        }

        Log.i(DB_TAG, products.toString());

        cursor.close();
        return products;

    }



    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c){
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //String dropTable = "DROP TABLE " + DB_TABLE;

            String createTable = "CREATE TABLE " + DB_TABLE +
                    " (" + DATE_COL + " TEXT PRIMARY KEY, " +
                    TITLE_COL + " TEXT ," + DESC_COL + " TEXT ," + RATING_COL + " TEXT ," +
                    COMM_RATING_COL + " TEXT ," + YOUR_RATING + " TEXT ," + ACTOR_COL + " TEXT ," +
                    YTLINK_COL +" TEXT, " + PICTURE_COL +" TEXT);";

            Log.d(SQL_TAG, createTable);
            //db.execSQL(dropTable);
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
            Log.w(SQL_TAG, "Upgrade table - drop and recreate it");
        }
    }

}