package com.example.newconverter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contentDb";
    public static final String TABLE_CURRENCY = "сourse";

    public static final String KEY_ID = "_id";
    public static final String KEY_CURRENCY_TO = "currency_to";
    public static final String KEY_CURRENCY = "currency";
    public static final String KEY_VALUE = "value";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_CURRENCY  + "(" + KEY_ID + " integer primary key," + KEY_CURRENCY_TO + " text," + KEY_CURRENCY + " text," + KEY_VALUE + " text" + ")" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);

    }
}
