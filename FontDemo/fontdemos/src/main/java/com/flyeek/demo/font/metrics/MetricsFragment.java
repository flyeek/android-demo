package com.flyeek.demo.font.metrics;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyeek.demo.font.R;


/**
 * A simple {@link Fragment} subclass for demon of FontMetrics.
 */
public class MetricsFragment extends Fragment {
    private final String SAMPLE_TEXT = "Metrics";
    private float baseLineY;

    public MetricsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_metrics, container, false);

        Button showButton = (Button) rootView.findViewById(R.id.metrics_show);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFontInfo();
            }
        });

        return rootView;
    }

    private void showFontInfo() {
        ImageView sampleTextImgView = (ImageView) getActivity().findViewById(R.id.metrics_sample_buildin);
        Paint textPaint = drawText(sampleTextImgView, SAMPLE_TEXT);
        TextView showPaintFontTxtView = (TextView) getActivity().findViewById(R.id.metrics_info_buildin);
        showPaintFontInfo(showPaintFontTxtView, textPaint, SAMPLE_TEXT);

        TextView sampleTxtView = (TextView) getActivity().findViewById(R.id.metrics_sample_custom);
        writeText(sampleTxtView, SAMPLE_TEXT);
        TextView showWriteFontTxtView = (TextView) getActivity().findViewById(R.id.metrics_info_custom);
        showWriteFontInfo(showWriteFontTxtView, sampleTxtView);
    }

    private Paint drawText(final ImageView targetImgView, String sourceText) {
        int imgWidth = targetImgView.getWidth();
        int imgHeight = targetImgView.getHeight();
        Bitmap textImg = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(textImg);

        // Draw sample text
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(this.getResources().getDimensionPixelSize(R.dimen.metrics_sampletext_fontsize));
        Paint.FontMetrics textFontMetrics = textPaint.getFontMetrics();
        float textBaseX = (imgWidth - textPaint.measureText(sourceText)) / 2;
        baseLineY = (imgHeight + Math.abs(textFontMetrics.ascent) - textFontMetrics.descent) / 2;
        canvas.drawText(sourceText, textBaseX, baseLineY, textPaint);

        // Draw base line
        Paint baseLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        baseLinePaint.setColor(Color.RED);
        canvas.drawLine(0, baseLineY, imgWidth, baseLineY, baseLinePaint);

        // Draw ascent line
        Paint ascentLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ascentLinePaint.setColor(Color.BLUE);
        float ascentLineY = baseLineY + textFontMetrics.ascent;
        canvas.drawLine(0, ascentLineY, imgWidth, ascentLineY, ascentLinePaint);

        // Draw top line
        Paint topLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ascentLinePaint.setColor(Color.GREEN);
        float topLineY = baseLineY + textFontMetrics.top;
        canvas.drawLine(0, topLineY, imgWidth, topLineY, topLinePaint);

        // Draw descent line
        Paint descentLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        descentLinePaint.setColor(Color.MAGENTA);
        float descentLineY = baseLineY + textFontMetrics.descent;
        canvas.drawLine(0, descentLineY, imgWidth, descentLineY, descentLinePaint);

        // Draw bottom line
        Paint bottomLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bottomLinePaint.setColor(Color.MAGENTA);
        float bottomLineY = baseLineY + textFontMetrics.bottom;
        canvas.drawLine(0, bottomLineY, imgWidth, bottomLineY, bottomLinePaint);

        // Draw textbound
        Paint textBoundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textBoundPaint.setColor(Color.BLACK);
        textBoundPaint.setStrokeWidth(2.0F);
        Rect textBound = new Rect();
        textPaint.getTextBounds(sourceText, 0, sourceText.length(), textBound);
        float[] textBoundPints = {textBaseX + textBound.left, baseLineY + textBound.bottom,
                textBaseX + textBound.left, baseLineY + textBound.top,
                textBaseX + textBound.left, baseLineY + textBound.top, textBaseX + textBound.right,
                baseLineY + textBound.top, textBaseX + textBound.right,
                baseLineY + textBound.top, textBaseX + textBound.right,
                baseLineY + textBound.bottom, textBaseX + textBound.right,
                baseLineY + textBound.bottom, textBaseX + textBound.left,
                baseLineY + textBound.bottom};
        canvas.drawLines(textBoundPints, textBoundPaint);

        // Set bitmap to ImageView
        targetImgView.setImageBitmap(textImg);

        return textPaint;
    }

    private void showPaintFontInfo(TextView targetTxtView, Paint sourcePaint, String sourceText) {
        Paint.FontMetrics fontMetrics = sourcePaint.getFontMetrics();
        Rect textBound = new Rect();
        sourcePaint.getTextBounds(sourceText, 0, sourceText.length(), textBound);
        float ascent = Math.abs(fontMetrics.ascent);
        float top = Math.abs(fontMetrics.top);
        float descent = fontMetrics.descent;
        float bottom = fontMetrics.bottom;
        float leading = fontMetrics.leading;
        float lineHeight = ascent + descent + leading;

        // TODO refine string concatenation
        String fontInfoString = "";
        fontInfoString += "font size: " + getActivity().getResources().getDimensionPixelSize(R.dimen
                .metrics_sampletext_fontsize) / getActivity().getResources().getDisplayMetrics().density +
                "sp\n";
        fontInfoString += "font scaled density: " + getActivity().getResources().getDisplayMetrics()
                .scaledDensity + "\n";
        fontInfoString += "baseline pos: " + baseLineY + "\n";
        fontInfoString += "font ascent: " + ascent + "\n";
        fontInfoString += "font top: " + top + "\n";
        fontInfoString += "font descent: " + descent + "\n";
        fontInfoString += "font bottom: " + bottom + "\n";
        fontInfoString += "font leading: " + leading + "\n";
        fontInfoString += "textBox width: " + textBound.width() + "\n";
        fontInfoString += "textBox height: " + textBound.height() + "\n";
        fontInfoString += "line height: " + lineHeight + "\n";
        fontInfoString += "line width: " + sourcePaint.measureText(sourceText);
        targetTxtView.setText(fontInfoString);
    }

    private void writeText(TextView targetTxtView, String sourceText) {
        targetTxtView.setText(sourceText);
    }

    private void showWriteFontInfo(TextView targetTxtView, TextView sourceTxtView) {
        Paint sourcePaint = sourceTxtView.getPaint();
        Paint.FontMetrics fontMetrics = sourcePaint.getFontMetrics();
        Rect textBound = new Rect();
        sourceTxtView.getLineBounds(0, textBound);
        String sourceText = sourceTxtView.getText().toString();
        sourcePaint.getTextBounds(sourceText, 0, sourceText.length(), textBound);
        float baseLine = sourceTxtView.getBaseline();
        float ascent = Math.abs(fontMetrics.ascent);
        float top = Math.abs(fontMetrics.top);
        float descent = fontMetrics.descent;
        float bottom = fontMetrics.bottom;
        float leading = sourceTxtView.getLineHeight() * (sourceTxtView.getLineSpacingMultiplier()
                - 1) + sourceTxtView.getLineSpacingExtra();

        // TODO refine string concatenation
        String fontInfoString = "";
        fontInfoString += "font size: " + getActivity().getResources().getDimensionPixelSize(R.dimen
                .metrics_sampletext_fontsize) / getActivity().getResources().getDisplayMetrics().density +
                "sp\n";
        fontInfoString += "font scaled density: " + getActivity().getResources().getDisplayMetrics()
                .scaledDensity + "\n";
        fontInfoString += "baseline pos: " + baseLine + "\n";
        fontInfoString += "font ascent: " + ascent + "\n";
        fontInfoString += "font top: " + top + "\n";
        fontInfoString += "font descent: " + descent + "\n";
        fontInfoString += "font bottom: " + bottom + "\n";
        fontInfoString += "font leading: " + leading + "\n";
        fontInfoString += "textBox width: " + textBound.width() + "\n";
        fontInfoString += "textBox height: " + textBound.height() + "\n";
        fontInfoString += "line height: " + sourceTxtView.getLineHeight() + "\n";
        fontInfoString += "line width: " + sourcePaint.measureText(sourceText);
        targetTxtView.setText(fontInfoString);
    }

}
