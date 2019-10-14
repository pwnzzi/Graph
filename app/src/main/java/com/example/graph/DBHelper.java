package com.example.graph;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {




    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE CHART ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " date INTEGER, cal INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS CHART;";
        db.execSQL(sql);
        onCreate(db);

    }
    public void insert(int date, int cal){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("INSERT INTO CHART VALUES(null,'"+date+"','"+ cal +"')");
        db.close();
    }
    public void delete(int _date){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM CHART WHERE date = '" + _date +"'");
        db.close();
    }
    public void update(int date, int cal){
        SQLiteDatabase db =getWritableDatabase();
        db.execSQL("UPDATE CHART SET cal=" + cal + " WEHER date='" + date +"'");
        db.close();

    }

}
