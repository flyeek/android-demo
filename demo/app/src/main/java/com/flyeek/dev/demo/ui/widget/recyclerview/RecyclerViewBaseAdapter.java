package com.flyeek.dev.demo.ui.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyeek.dev.demo.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter for RecyclerView.
 * Created by flyeek on 7/2/15.
 */
public class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerViewBaseAdapter.MyViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater;

    protected List<String> mDatas;

    public static int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static int VERTICAL = LinearLayoutManager.VERTICAL;
    protected int mOrientation;

    protected OnItemClickListener mItemClickListener;

    public RecyclerViewBaseAdapter(Context context, List<String> datas, int orientation) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);

        this.mDatas = datas;
        this.mOrientation = orientation;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.textView.setText(mDatas.get(position));

        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(int position) {
        mDatas.add(position, "Inserted Item");

//        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public void deleteData(int position) {
        mDatas.remove(position);

//        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int positioin);

        void onItemLongClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_id)
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}