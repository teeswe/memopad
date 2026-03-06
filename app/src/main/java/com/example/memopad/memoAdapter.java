package com.example.memopad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class memoAdapter extends RecyclerView.Adapter<memoAdapter.ViewHolder> {
    private ArrayList<memopad> memoList;

    public memoAdapter(ArrayList<memopad> memoList) {
        this.memoList = memoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        memopad item = memoList.get(position);
        holder.memoTextView.setText(item.toString());
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView memoTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            memoTextView = itemView.findViewById(R.id.memoText);
        }
    }
}