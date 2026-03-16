package com.example.memopad.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAOFactory extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "memopad.db";
    private static final int DATABASE_VERSION = 1;

    public DAOFactory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE memos (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "memo TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS memos");
        onCreate(db);
    }

    public MemoDAO getMemoDao() {
        return new MemoDAO(this);
    }
}