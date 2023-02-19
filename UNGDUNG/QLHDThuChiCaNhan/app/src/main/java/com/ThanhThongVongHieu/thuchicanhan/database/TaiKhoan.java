package com.ThanhThongVongHieu.thuchicanhan.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaiKhoan extends SQLiteOpenHelper {

    public TaiKhoan(Context context) {
        super(context, "QUANLYTHUCHIDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE TAIKHOAN(TenTaiKhoan text primary key, MatKhau text)";
        db.execSQL(sql);
        sql = "INSERT INTO TAIKHOAN VALUES('admin','admin')";
        db.execSQL(sql);
        sql = "INSERT INTO TAIKHOAN VALUES('admin1','admin1')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TAIKHOAN");
        onCreate(db);
    }
}
