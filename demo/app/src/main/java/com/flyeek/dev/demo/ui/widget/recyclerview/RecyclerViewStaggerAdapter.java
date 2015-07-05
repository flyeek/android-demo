package com.flyeek.dev.demo.ui.widget.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for RecyclerView.
 * Created by flyeek on 7/2/15.
 */
public class RecyclerViewStaggerAdapter extends RecyclerViewBaseAdapter {

    private List<Integer> mLength;

    public RecyclerViewStaggerAdapter(Context context, List<String> datas, int orientation) {
        super(context, datas, orientation);

        this.mLength = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++) {
            mLength.add((int) (100 + Math.random() * 200));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (mOrientation == HORIZONTAL) {
            lp.width = mLength.get(position);
        } else {
            lp.height = mLength.get(position);
        }

        holder.itemView.setLayoutParams(lp);

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

}