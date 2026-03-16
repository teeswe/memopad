package com.example.memopad.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.memopad.model.memopad;

import java.util.ArrayList;

public class MemoDAO {
    private final DAOFactory daoFactory;

    MemoDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void create(String memoText) {
        SQLiteDatabase db = daoFactory.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("memo", memoText);
        db.insert("memos", null, values);
        db.close();
    }
    // delete method
    public void delete(int id) {
        SQLiteDatabase db = daoFactory.getWritableDatabase();
        db.delete("memos", "_id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public memopad find(int id) {
        SQLiteDatabase db = daoFactory.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM memos WHERE _id=?", new String[]{String.valueOf(id)});
        memopad memo = null;
        if (cursor.moveToFirst()) {
            String memoText = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
            memo = new memopad(id, memoText);
        }
        cursor.close();
        return memo;
    }

    public ArrayList<memopad> list() {
        ArrayList<memopad> memoList = new ArrayList<>();
        SQLiteDatabase db = daoFactory.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM memos", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String memo = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
                memoList.add(new memopad(id, memo));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return memoList;
    }
}
