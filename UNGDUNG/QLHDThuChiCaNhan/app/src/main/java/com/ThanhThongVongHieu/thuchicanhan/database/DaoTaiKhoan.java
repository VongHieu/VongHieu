package com.ThanhThongVongHieu.thuchicanhan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.ThanhThongVongHieu.thuchicanhan.mdel.TaikhoanMatKhau;

import java.util.ArrayList;

public class DaoTaiKhoan {
    TaiKhoan dtbRegister;

    public DaoTaiKhoan(Context context) {

        dtbRegister = new TaiKhoan(context);

    }

    public TaikhoanMatKhau getUserByUserName(String tk) {
        SQLiteDatabase dtb = dtbRegister.getReadableDatabase();
        Cursor cs = dtb.rawQuery("SELECT * FROM TAIKHOAN WHERE TenTaiKhoan='" + tk + "'", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            try {
                String username = cs.getString(0);
                String mk = cs.getString(1);
                cs.close();
                return new TaikhoanMatKhau(username, mk);
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
        return new TaikhoanMatKhau("lksdfjal!@_sdfjalksf", "sfjkasdf_@!ljasldfj");
    }

    public ArrayList<TaikhoanMatKhau> getALl() {
        ArrayList<TaikhoanMatKhau> listTK = new ArrayList<>();
        SQLiteDatabase dtb = dtbRegister.getReadableDatabase();
        Cursor cs = dtb.rawQuery("SELECT * FROM TAIKHOAN", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            try {
                String tk = cs.getString(0);
                String mk = cs.getString(1);
                TaikhoanMatKhau t = new TaikhoanMatKhau(tk, mk);
                listTK.add(t);
                cs.moveToNext();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
        cs.close();
        return listTK;
    }

    public boolean Them(TaikhoanMatKhau tk) {
        SQLiteDatabase db = dtbRegister.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenTaiKhoan", tk.getTenTaiKhoan());
        values.put("MatKhau", tk.getMatKhau());
        long r = db.insert("TAIKHOAN", null, values);
        if (r <= 0) {
            return false;
        }
        return true;
    }

    public boolean doiMk(TaikhoanMatKhau tk) {
        SQLiteDatabase db = dtbRegister.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MatKhau", tk.getMatKhau());
        int r = db.update("TAIKHOAN", values, "TenTaiKhoan=?", new String[]{tk.getTenTaiKhoan()});
        if (r <= 0) {
            return false;
        }
        return true;
    }

}
