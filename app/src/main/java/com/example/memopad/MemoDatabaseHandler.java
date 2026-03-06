package com.example.memopad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class MemoDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "memopad.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_MEMOS = "memos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEMO = "memo";

    public MemoDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_MEMOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MEMO + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMOS);
        onCreate(db);
    }

    public void addMemo(String memoText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, memoText);
        db.insert(TABLE_MEMOS, null, values);
        db.close();
    }

    public ArrayList<memopad> getAllMemos() {
        ArrayList<memopad> memoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MEMOS, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String memo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEMO));
                memoList.add(new memopad(id, memo));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return memoList;
    }
}