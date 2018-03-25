package com.example.gokulkrishnam.stepcounterapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by GOKULKRISHNA M on 16-07-2017.
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    String[] mDataset;

    public MyAdapter(String myDataset[])
    {
        Log.d("vivz", "onBindViewHolder: foasdjfoi");
        this.mDataset = myDataset;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        ViewHolder(CardView v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d("vivz", "onBindViewHolder: f");
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        Log.d("vivz", "onBindViewHolder: jakkas");
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position)
    {
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }



}
