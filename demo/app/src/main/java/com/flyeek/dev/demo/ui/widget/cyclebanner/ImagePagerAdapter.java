/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */

package com.flyeek.dev.demo.ui.widget.cyclebanner;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @author http://blog.csdn.net/finddreams
 * @Description: 图片适配器
 */
public class ImagePagerAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageIdList;
    private List<String> linkUrlArray;
    private List<String> urlTitlesList;
    private int size;
    private boolean isInfiniteLoop;

    public ImagePagerAdapter(Context context, List<String> imageIdList,
                             List<String> urllist, List<String> urlTitlesList) {
        this.context = context;
        this.imageIdList = imageIdList;
        if (imageIdList != null) {
            this.size = imageIdList.size();
        }
        this.linkUrlArray = urllist;
        this.urlTitlesList = urlTitlesList;
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new SimpleDraweeView(context);
            holder.imageView
                    .setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.imageView.setImageURI(Uri.parse(imageIdList.get(getPosition(position))));

        holder.imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String url = linkUrlArray.get(ImagePagerAdapter.this
                        .getPosition(position));
                String title = urlTitlesList.get(ImagePagerAdapter.this
                        .getPosition(position));

                Toast.makeText(context, "click " + getPosition(position), Toast.LENGTH_SHORT)
                        .show();

            }
        });

        return view;
    }

    private static class ViewHolder {

        SimpleDraweeView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

}
