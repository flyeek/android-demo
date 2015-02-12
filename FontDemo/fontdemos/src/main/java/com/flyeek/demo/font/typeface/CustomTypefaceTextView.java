package com.flyeek.demo.font.typeface;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by flyeek on 2015/2/28.
 */
public class CustomTypefaceTextView extends TextView {

    public CustomTypefaceTextView(Context context) {
        super(context);
        initTypeface();
    }

    public CustomTypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeface();
    }

    public CustomTypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeface();
    }

    /**
     * Initialize the custom TextView with specified font typeface.
     */
    private void initTypeface() {
        Typeface customTypeface = Typeface.createFromAsset(getContext().getAssets(), "NotoSerif-BoldItalic.ttf");
        this.setTypeface(customTypeface);
    }
}
