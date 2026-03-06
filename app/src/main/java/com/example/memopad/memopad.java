package com.example.memopad;

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
        // Change this line to add the hashtag before the ID
        return "#" + id + ": " + memo;
    }
}