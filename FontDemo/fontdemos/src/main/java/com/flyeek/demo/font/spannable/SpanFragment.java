package com.flyeek.demo.font.spannable;


import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.DrawableMarginSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.IconMarginSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TabStopSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flyeek.demo.font.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpanFragment extends Fragment {
    private final String SPAN_TEXT_BACKGROUNDCOLOR = "BackgroundColorSpan";
    private final String SPAN_TEXT_FOREGROUNDCOLOR = "ForegroundColorSpan";
    private final String SPAN_TEXT_ABSOLUTESIZE = "AbsoluteSizeSpan";
    private final String SPAN_TEXT_RELATIVESIZE = "RelativeSizeSpan";
    private final String SPAN_TEXT_SCALEX = "ScaleXSpan";
    private final String SPAN_TEXT_STYLE = "StyleSpan";
    private final String SPAN_TEXT_TYPEFACE = "TypefaceSpan";
    private final String SPAN_TEXT_TEXTAPPEARANCE = "TextAppearanceSpan";
    private final String SPAN_TEXT_UNDERLINE = "UnderlineSpan";
    private final String SPAN_TEXT_STRIKETHROUGH = "StrikeThroughSpan";
    private final String SPAN_TEXT_SUPERSCRIPT = "SuperscriptSpan";
    private final String SPAN_TEXT_SUBSCRIPT = "SubscriptSpan";
    private final String SPAN_TEXT_CLICKABLE = "ClickableSpan";
    private final String SPAN_TEXT_URL = "URLSpan";
    private static final String SPAN_TEXT_IMAGE = "ImageSpan";
    private static final String SPAN_TEXT_BULLET = "BulletSpan";
    private static final String SPAN_TEXT_LEADINGMARGIN = "LeadingMarginSpan";
    private static final String SPAN_TEXT_ICONMARGIN = "IconMarginSpan";
    private static final String SPAN_TEXT_DRAWABLEMARGIN = "DrawableMarginSpan";
    private static final String SPAN_TEXT_ALIGNMENT = "AlignmentSpan";
    private static final String SPAN_TEXT_TAPSTOP = "TapStop\tSpan";
    private static final String SPAN_TEXT_QUOTE = "QuoteSpan";
    private static final String SPAN_TEXT_MASKFILTER = "MaskFilterSpan";
    private static final String SPAN_TEXT_REPLACEMENT_ROUNDCORNER = "RoundCornerBackgroundSpan";
    private static final String SPAN_TEXT_REPLACEMENT_DRAWABLE = "SuperscriptDrawableSpan";


    private SpannableStringBuilder mSpannableBuilder;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SpanFragment.
     */
    public static SpanFragment newInstance() {
        return new SpanFragment();
    }

    public SpanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_span, container, false);
        Button showButton = (Button) rootView.findViewById(R.id.span_show);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView sampleTxtView = (TextView) getActivity().findViewById(R.id.span_sample);
                showSpannableText(sampleTxtView);
            }
        });

        return rootView;
    }

    private void showSpannableText(TextView targetTxtView) {
        mSpannableBuilder = new SpannableStringBuilder();

        // Set BackgroundColor
        BackgroundColorSpan spanBgColor = new BackgroundColorSpan(Color.GREEN);
        appendSpannableText(spanBgColor, SPAN_TEXT_BACKGROUNDCOLOR);

        // Set ForegroundColor
        ForegroundColorSpan spanFgColor = new ForegroundColorSpan(Color.BLUE);
        appendSpannableText(spanFgColor, SPAN_TEXT_FOREGROUNDCOLOR);

        // Set AbsoluteTextSize
        AbsoluteSizeSpan spanAbsoluteSize = new AbsoluteSizeSpan(30, true);
        appendSpannableText(spanAbsoluteSize, SPAN_TEXT_ABSOLUTESIZE);

        // Set RelativeTextSize
        RelativeSizeSpan spanRelativeSize = new RelativeSizeSpan(0.5f);
        appendSpannableText(spanRelativeSize, SPAN_TEXT_RELATIVESIZE);

        // Set HorizontalScale
        ScaleXSpan spanScaleX = new ScaleXSpan(3.0f);
        appendSpannableText(spanScaleX, SPAN_TEXT_SCALEX);

        // Set FontStyle
        StyleSpan spanStyle = new StyleSpan(Typeface.BOLD_ITALIC);
        appendSpannableText(spanStyle, SPAN_TEXT_STYLE);

        // Set Typeface
        TypefaceSpan spanTypeface = new TypefaceSpan("serif");
        appendSpannableText(spanTypeface, SPAN_TEXT_TYPEFACE);

        // Set TextAppearance
        TextAppearanceSpan spanTextAppearance = new TextAppearanceSpan(getActivity(),
                android.R.color.tertiary_text_dark);
        appendSpannableText(spanTextAppearance, SPAN_TEXT_TEXTAPPEARANCE);

        // Set Underline
        UnderlineSpan spanUnderline = new UnderlineSpan();
        appendSpannableText(spanUnderline, SPAN_TEXT_UNDERLINE);

        // Set StrikeThrough
        StrikethroughSpan spanStrikeThrough = new StrikethroughSpan();
        appendSpannableText(spanStrikeThrough, SPAN_TEXT_STRIKETHROUGH);

        // Set Superscript
        SuperscriptSpan spanSuperscript = new SuperscriptSpan();
        appendSpannableText(spanSuperscript, SPAN_TEXT_SUPERSCRIPT);

        // Set Subscript
        SubscriptSpan spanSubscript = new SubscriptSpan();
        appendSpannableText(spanSubscript, SPAN_TEXT_SUBSCRIPT);

        // Set Clickable
        ClickableSpan spanClickable = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // The text holding this span is clicked by user.
                Toast.makeText(getActivity(), "text is clicked", Toast.LENGTH_SHORT).show();
            }
        };
        appendSpannableText(spanClickable, SPAN_TEXT_CLICKABLE);

        // Set URL
        URLSpan spanURL = new URLSpan("http://www.baidu.com");
        appendSpannableText(spanURL, SPAN_TEXT_URL);

        // Set Image
        ImageSpan spanImage = new ImageSpan(getActivity(), R.mipmap.ic_launcher);
        appendSpannableText(spanImage, SPAN_TEXT_IMAGE);

        // Set Bullet
        mSpannableBuilder.append("\n");
        BulletSpan spanBullet = new BulletSpan(BulletSpan.STANDARD_GAP_WIDTH);
        appendSpannableText(spanBullet, SPAN_TEXT_BULLET);

        // Set LeadingMargin of paragraph
        mSpannableBuilder.append("\n");
        LeadingMarginSpan.Standard spanLeadingMargin = new LeadingMarginSpan.Standard(60, 0);
        appendSpannableText(spanLeadingMargin, SPAN_TEXT_LEADINGMARGIN);

        // Set LeadingMargin and add a bitmap to paragraph
        mSpannableBuilder.append("\n");
        IconMarginSpan spanIconMargin = new IconMarginSpan(BitmapFactory.decodeResource
                (getActivity().getResources(), R.mipmap.ic_launcher));
        appendSpannableText(spanIconMargin, SPAN_TEXT_ICONMARGIN);

        // Set LeadingMargin and add a drawable to paragraph
        mSpannableBuilder.append("\n\n\n\n");
        DrawableMarginSpan spanDrawableMargin = new DrawableMarginSpan(getActivity().getResources
                ().getDrawable(R.mipmap.ic_launcher));
        appendSpannableText(spanDrawableMargin, SPAN_TEXT_DRAWABLEMARGIN);

        // Set Alignment of paragraph
        mSpannableBuilder.append("\n");
        AlignmentSpan.Standard spanAlignment = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
        appendSpannableText(spanAlignment, SPAN_TEXT_ALIGNMENT);

        // Set TapStop of paragraph
        mSpannableBuilder.append("\n\n");
        TabStopSpan.Standard spanTabStop = new TabStopSpan.Standard(300);
        appendSpannableText(spanTabStop, SPAN_TEXT_TAPSTOP);

        // Set Quote of paragraph
        mSpannableBuilder.append("\n");
        QuoteSpan spanQuote = new QuoteSpan();
        appendSpannableText(spanQuote, SPAN_TEXT_QUOTE);

        // Set MaskFilter, hardware acceleration must be turned off.
        targetTxtView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        MaskFilterSpan spanMaskFilter = new MaskFilterSpan(new BlurMaskFilter(3, BlurMaskFilter.Blur.OUTER));
        appendSpannableText(spanMaskFilter, SPAN_TEXT_MASKFILTER);

        // Set Replacement, that is to say, you can make some customization.
        mSpannableBuilder.append("\n");
        RoundBackgroundSpan spanRoundCornerBg = new RoundBackgroundSpan(Color.WHITE, Color.BLUE);
        appendSpannableText(spanRoundCornerBg, SPAN_TEXT_REPLACEMENT_ROUNDCORNER);
        mSpannableBuilder.append("\n");
        mSpannableBuilder.append(SPAN_TEXT_REPLACEMENT_DRAWABLE);
        SuperscriptDrawableSpan spanSuperscriptDeawable = new SuperscriptDrawableSpan(getActivity
                ().getResources().getDrawable(R.mipmap.ic_launcher), 24, 24);
        appendSpannableText(spanSuperscriptDeawable, "^");

        // Set movement and text.
        targetTxtView.setMovementMethod(LinkMovementMethod.getInstance());
        targetTxtView.setText(mSpannableBuilder);
    }

    private void appendSpannableText(Object span, String text) {
        mSpannableBuilder.append(text);
        mSpannableBuilder.setSpan(span, mSpannableBuilder.length() - text.length(),
                mSpannableBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mSpannableBuilder.append("-");
    }

    /**
     * Make rounded corner background of text.
     */
    private class RoundBackgroundSpan extends ReplacementSpan {
        private int mTextColor;
        private int mBgColor;

        public RoundBackgroundSpan(int textColor, int bgColor) {
            this.mTextColor = textColor;
            this.mBgColor = bgColor;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return Math.round(paint.measureText(text, start, end));
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            RectF bgRect = new RectF(x, top, x + paint.measureText(text, start, end), bottom);
            paint.setColor(mBgColor);
            canvas.drawRoundRect(bgRect, 10f, 10f, paint);
            paint.setColor(mTextColor);
            canvas.drawText(text, start, end, x, y, paint);
        }
    }

    /**
     * Make drawable as the superscript
     */
    private class SuperscriptDrawableSpan extends ReplacementSpan {
        private Drawable mDrawable;
        private int mWidth;
        private int mHeight;

        public SuperscriptDrawableSpan(Drawable drawable, int width, int height) {
            this.mDrawable = drawable;
            this.mWidth = width;
            this.mHeight = height;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return mWidth;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            mDrawable.setBounds((int) x, top, ((int) x) + mWidth, top + mHeight);
            mDrawable.draw(canvas);
        }
    }
}
