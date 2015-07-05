package com.flyeek.dev.demo.ui.widget.recyclerview;

import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;

import java.util.List;

/**
 * Adapter for RecyclerView.
 * Created by flyeek on 7/2/15.
 */
public class RecyclerViewLinearAdapter extends RecyclerViewBaseAdapter {

    public RecyclerViewLinearAdapter(Context context, List<String> datas, int orientation) {
        super(context, datas, orientation);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (mOrientation == HORIZONTAL) {
            lp.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 72,
                    mContext.getResources().getDisplayMetrics());
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 72,
                    mContext.getResources().getDisplayMetrics());
        }
        holder.itemView.setLayoutParams(lp);

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

}