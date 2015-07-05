package com.flyeek.dev.demo.ui.widget.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Simple divider between items in RecyclerView.
 * Created by flyeek on 7/3/15.
 */
public class ItemDecorationDivider extends RecyclerView.ItemDecoration{

    public static int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static int VERTICAL = LinearLayoutManager.VERTICAL;
    private int mOrientation;

    private Drawable mDivider;
    private int mDividerSize;

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager.
     *
     * @param divider A divider Drawable to be drawn on the RecyclerView
     */
    public ItemDecorationDivider(int orientation, Drawable divider) {
        mOrientation = orientation;
        mDivider = divider;

        if (orientation == HORIZONTAL) {
            mDividerSize = divider.getIntrinsicWidth();
        } else {
            mDividerSize = divider.getIntrinsicHeight();
        }
    }

    public ItemDecorationDivider(int orientation, Drawable divider, int dividerSpan) {
        mOrientation = orientation;
        mDivider = divider;
        mDividerSize = dividerSpan;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONTAL) {
            drawHorizontalDividers(canvas, parent);
        } else if (mOrientation == VERTICAL) {
            drawVerticalDividers(canvas, parent);
        }
    }

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager (or its
     * subclass) oriented horizontally.
     */
    private void drawHorizontalDividers(Canvas canvas, RecyclerView parent) {
        int parentTop = parent.getPaddingTop();
        int parentBottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentLeft = child.getRight() + params.rightMargin;
            int parentRight = parentLeft + mDividerSize;

            mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            mDivider.draw(canvas);
        }
    }

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager (or its
     * subclass) oriented vertically.
     */
    private void drawVerticalDividers(Canvas canvas, RecyclerView parent) {
        int parentLeft = parent.getPaddingLeft();
        int parentRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentTop = child.getBottom() + params.bottomMargin;
            int parentBottom = parentTop + mDividerSize;

            mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            mDivider.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }

        if (mOrientation == HORIZONTAL) {
            outRect.left = mDividerSize;
        } else if (mOrientation == VERTICAL) {
            outRect.top = mDividerSize;
        }
    }
}
