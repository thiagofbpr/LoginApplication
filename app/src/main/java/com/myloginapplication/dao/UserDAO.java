package com.myloginapplication.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import androidx.annotation.NonNull;
import com.myloginapplication.model.User;

public class UserDAO extends MainDAO {

    public UserDAO(Context context) {
        super(context);
    }

    public void addUser(@NonNull User user) throws SQLiteException {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = new ContentValues();

        data.put("fullname", user.get_fullName());
        data.put("username", user.get_userName());
        data.put("password", user.get_password());

        db.insertOrThrow("user", null, data);
        db.close();
    }

    @SuppressLint("Range")
    public User selectUser(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from user where username like '" + username + "'";
        Cursor cursor = db.rawQuery(sql, null);

        User user = null;
        while (cursor.moveToNext()) {
            user = new User();
            user.set_fullName(cursor.getString(cursor.getColumnIndex("fullname")));
            user.set_userName(cursor.getString(cursor.getColumnIndex("username")));
            user.set_password(cursor.getString(cursor.getColumnIndex("password")));
        }
        db.close();
        return user;
    }


}
