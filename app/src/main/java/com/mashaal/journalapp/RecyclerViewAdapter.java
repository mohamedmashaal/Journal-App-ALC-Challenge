package com.mashaal.journalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mohamed Mashaal on 6/30/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DiaryViewHolder> {
    ArrayList<DiaryItem> mDiaryDataSet;


    @NonNull
    @Override
    public RecyclerViewAdapter.DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        DiaryViewHolder viewHolder = new DiaryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.DiaryViewHolder holder, int position) {
        String title = mDiaryDataSet.get(position).getTitle();
        String content = mDiaryDataSet.get(position).getDairyContent();
        holder.setItemText(title + " \n" + content);
    }

    @Override
    public int getItemCount() {
        if (mDiaryDataSet == null)
            return 0;
        return mDiaryDataSet.size();
    }

    public void updateDataSet(ArrayList<DiaryItem> mDiaryDataSet){
        this.mDiaryDataSet = mDiaryDataSet;
        notifyDataSetChanged();
    }



    public class DiaryViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        public DiaryViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.diary_item);
        }

        public void setItemText(String text){
            mTextView.setText(text);
        }
    }
}
