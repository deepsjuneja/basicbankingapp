package com.example.basicbankingapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BankDBHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    private static String DB_NAME = "bank.db";
    private static int DB_VERSION = 2;
    private SQLiteDatabase bankDatabase;
//    private final Context mContext;
    private SQLiteOpenHelper sqLiteOpenHelper;
    public static final String TABLE_NAME = "customers";
    public static final String TABLE2_NAME = "transactions";

    public BankDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "account TEXT NOT NULL UNIQUE, " +
                "balance REAL NOT NULL)";

        db.execSQL(query1);

        String query2 = "CREATE TABLE " + TABLE2_NAME +
                "(trans_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT NOT NULL, " +
                "sender TEXT NOT NULL, " +
                "receiver TEXT NOT NULL, " +
                "amount REAL NOT NULL, " +
                "status TEXT NOT NULL)";

        db.execSQL(query2);

        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES(1001,'Monica','chefgeller@gmail.com','234156784379',700500.0)");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES(1002,'Chandler','sarcasticbing@gmail.com','256478123902',998760.0)");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES(1003,'Phoebe','smellycatbuffay@gmail.com','264925361078',89037.0)");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES(1004,'Rachel','rachelgreen@gmail.com','265785301275',600000.0)");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES(1005,'Ross','dinoross@gmail.com','234215876903',832400.0)");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES(1006,'Joey','drdrakeramoray@gmail.com','209876543127',70760.0)");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES(1007,'Richard','drburke@gmail.com','216543879010',252000.0)");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES(1008,'Gunther','manager@centralperk.org','296780345671',560760.0)");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES(1009,'Janice','omgjanice@gmail.com','253412097865',552000.0)");
        db.execSQL("INSERT INTO " + TABLE_NAME +
                " VALUES(1010,'Paolo','theitalianguy@gmail.com','234165931902',800.0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
            onCreate(db);
        }
    }

    public Cursor readCustomersList(Activity activity) {
        sqLiteOpenHelper = new BankDBHelper(activity);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
//        Log.i("DB_PATH", String.valueOf(mContext.getDatabasePath(DB_NAME)));
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor readCustomerData(Activity activity, int customerID){
        sqLiteOpenHelper = new BankDBHelper(activity);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id="+customerID;
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }

    public Cursor readReceivers(Activity activity, int customerID){
        sqLiteOpenHelper = new BankDBHelper(activity);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " EXCEPT SELECT * FROM " + TABLE_NAME + " WHERE id="+customerID;
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }

    public void updateBalance(Activity activity, String cust_id, double cust_balance) {
        sqLiteOpenHelper = new BankDBHelper(activity);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET balance = " + cust_balance + " WHERE id="+cust_id;
        db.execSQL(query);
    }

    public Cursor readTransactions(Activity activity) {
        sqLiteOpenHelper = new BankDBHelper(activity);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE2_NAME;
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }

    public boolean addTransaction(Activity activity, String date, String sender, String receiver, double amount, String status) {
        sqLiteOpenHelper = new BankDBHelper(activity);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("sender", sender);
        contentValues.put("receiver", receiver);
        contentValues.put("amount", amount);
        contentValues.put("status", status);
        long transStatus = db.insert(TABLE2_NAME, null, contentValues);

        if(transStatus != -1)
            return true;
        else
            return false;
    }
}
