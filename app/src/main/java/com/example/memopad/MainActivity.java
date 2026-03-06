package com.example.memopad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MemoDatabaseHandler dbHandler;
    private ArrayList<memopad> memoArrayList;
    private memoAdapter adapter;
    private EditText memoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new MemoDatabaseHandler(this);
        memoArrayList = new ArrayList<>();

        memoInput = findViewById(R.id.memoInput);
        Button addMemoButton = findViewById(R.id.addMemoButton);

        // delete button to the Java code
        Button deleteMemoButton = findViewById(R.id.deleteMemoButton);

        RecyclerView recyclerView = findViewById(R.id.memoRecyclerView);

        memoArrayList.addAll(dbHandler.getAllMemos());
        adapter = new memoAdapter(memoArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = memoInput.getText().toString();
                if (!text.trim().isEmpty()) {
                    dbHandler.addMemo(text);
                    memoInput.setText("");
                    refreshMemos();
                }
            }
        });


        deleteMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic for deletion will be added in Lab 4B
            }
        });
    }

    private void refreshMemos() {
        memoArrayList.clear();
        memoArrayList.addAll(dbHandler.getAllMemos());
        adapter.notifyDataSetChanged();
    }
}