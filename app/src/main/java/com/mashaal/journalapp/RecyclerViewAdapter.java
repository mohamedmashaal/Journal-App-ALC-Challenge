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
    final private ListItemClickListener mOnClickListener;

    public RecyclerViewAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
    }

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
        String DayMonth = mDiaryDataSet.get(position).getDayMonth();
        String year = Integer.toString(mDiaryDataSet.get(position).getYear());
        holder.setItemDayMonth(DayMonth);
        holder.setItemTitle(title);
        holder.setItemYear(year);
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



    public class DiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mDairyItemYear;
        private TextView mDairyItemTitle;
        private TextView mDairyItemDayMonth;
        public DiaryViewHolder(View itemView) {
            super(itemView);
            mDairyItemYear = (TextView)itemView.findViewById(R.id.diary_item_year);
            mDairyItemTitle = (TextView)itemView.findViewById(R.id.diary_item_title);
            mDairyItemDayMonth = (TextView)itemView.findViewById(R.id.diary_item_day_month);
        }

        public void setItemTitle(String text){
            mDairyItemTitle.setText(text);
        }
        public void setItemYear(String text){
            mDairyItemYear.setText(text);
        }
        public void setItemDayMonth(String text){
            mDairyItemDayMonth.setText(text);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.itemClicked(clickedPosition);
        }
    }

    interface ListItemClickListener{
        void itemClicked(int position);
    }
}
