package com.myloginapplication.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainDAO extends SQLiteOpenHelper {

    public MainDAO(Context context) {
        super(context, "login_app_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table user (" +
                "id integer primary key autoincrement," +
                "fullname varchar(45) not null," +
                "username varchar(45) not null," +
                "password varchar(45) not null," +
                "constraint uq_username unique(username));";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table if exists user";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }


}
