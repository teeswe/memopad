package com.example.memopad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memopad.model.memopad;
import com.example.memopad.model.dao.DAOFactory;
import com.example.memopad.model.dao.MemoDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DAOFactory daoFactory;
    private MemoDAO memoDao;
    private ArrayList<memopad> memoArrayList;
    private memoAdapter adapter;
    private EditText memoInput;
    private RecyclerView recyclerView;

    private int selectedMemoId = -1;

    private class MemoPadItemClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = recyclerView.getChildLayoutPosition(v);
            if (position != RecyclerView.NO_ID && position < memoArrayList.size()) {
                memopad memo = adapter.getItem(position);
                selectedMemoId = memo.getId();
                Toast.makeText(v.getContext(), "Selected memo #" + selectedMemoId, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final MemoPadItemClickHandler itemClick = new MemoPadItemClickHandler();

    public MemoPadItemClickHandler getItemClick() {
        return itemClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daoFactory = new DAOFactory(this);//DAOFactory with open the SQLite database
        memoDao = daoFactory.getMemoDao();
        memoArrayList = new ArrayList<>();

        memoInput = findViewById(R.id.memoInput);
        Button addMemoButton = findViewById(R.id.addMemoButton);
        Button deleteMemoButton = findViewById(R.id.deleteMemoButton);
        recyclerView = findViewById(R.id.memoRecyclerView);

        memoArrayList.addAll(memoDao.list());
        adapter = new memoAdapter(this, memoArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = memoInput.getText().toString();
                if (!text.trim().isEmpty()) {
                    memoDao.create(text);
                    memoInput.setText("");
                    selectedMemoId = -1;
                    refreshMemos();
                }
            }
        });
        // Deletes the memo that is currently on the database
        deleteMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMemoId != -1) {
                    memoDao.delete(selectedMemoId);
                    selectedMemoId = -1;
                    refreshMemos();
                } else {//select a memo before deleting
                    Toast.makeText(MainActivity.this, "tap a memo first to selectit", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshMemos() {
        memoArrayList.clear();
        memoArrayList.addAll(memoDao.list());
        adapter.notifyDataSetChanged();
    }
}