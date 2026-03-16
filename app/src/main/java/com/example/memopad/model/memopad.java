package com.example.memopad.model;

public class memopad {
    private final int id;
    private final String memo;

    public memopad(int id, String memo) {
        this.id = id;
        this.memo = memo;
    }

    public int getId() { return id; }
    public String getMemo() { return memo; }

    @Override
    public String toString() {
        // added the hashhtag
        return "#" + id + ": " + memo;
    }
}