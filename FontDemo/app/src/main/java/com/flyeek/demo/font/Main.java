package com.flyeek.demo.font;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;


public class Main extends ActionBarActivity {

    private final String SAMPLE_TEXT = "Font Metrics";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView sampleTextImgView = (ImageView) this.findViewById(R.id.main_sample_paint);
        Paint textPaint = drawText(sampleTextImgView, SAMPLE_TEXT);
        TextView showPaintFontTxtView = (TextView) this.findViewById(R.id.main_info_paint);
        showPaintFontInfo(showPaintFontTxtView, textPaint, SAMPLE_TEXT);

        TextView sampleTxtView = (TextView) this.findViewById(R.id.main_sample_write);
        writeText(sampleTxtView, SAMPLE_TEXT);

    }

    private Paint drawText(final ImageView targetImgView, String sourceText) {
        int imgWidth = (this.getWindowManager().getDefaultDisplay().getWidth()
            - 2 * this.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin)) / 2;
        int imgHeight = this.getResources().getDimensionPixelSize(R.dimen.dimen_main_sampletext_height);
        Bitmap textImg = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(textImg);

        // Draw sample text
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint.FontMetrics textFontMetrics = textPaint.getFontMetrics();
        float textBaseX = 0;
        float baseLineY = 100;
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
        float topLineY = baseLineY + textFontMetrics.ascent;
        canvas.drawLine(0, topLineY, imgWidth, topLineY, topLinePaint);

        // Draw descent line
        Paint descentLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        descentLinePaint.setColor(Color.MAGENTA);
        float descentLineY = baseLineY + textFontMetrics.ascent;
        canvas.drawLine(0, descentLineY, imgWidth, descentLineY, descentLinePaint);

        // Draw bottom line
        Paint bottomLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bottomLinePaint.setColor(Color.MAGENTA);
        float bottomLineY = baseLineY + textFontMetrics.ascent;
        canvas.drawLine(0, bottomLineY, imgWidth, bottomLineY, bottomLinePaint);

        return textPaint;
    }

    private void showPaintFontInfo(TextView targetTxtView, Paint sourcePaint, String sourceText) {
        Paint.FontMetrics fontMetrics = sourcePaint.getFontMetrics();
        Rect textBound = new Rect();
        sourcePaint.getTextBounds(sourceText, 0, sourceText.length(), textBound);
        float baseLine = textBound.bottom - fontMetrics.descent;
        float ascent = Math.abs(fontMetrics.ascent);
        float top = Math.abs(fontMetrics.top);
        float descent = fontMetrics.descent;
        float bottom = fontMetrics.bottom;
        float textWidth = textBound.width();
        float textHeight = textBound.height();

        // TODO refine string concatenation
        String fontInfoString = "";
        fontInfoString += "baseline: " + baseLine + "\n";
        fontInfoString += "ascent: " + ascent + "\n";
        fontInfoString += "top: " + top + "\n";
        fontInfoString += "descent: " + descent + "\n";
        fontInfoString += "bottom: " + bottom + "\n";
        fontInfoString += "text width: " + textWidth + "\n";
        fontInfoString += "text height: " + textHeight + "\n";
        targetTxtView.setText(fontInfoString);

    }

    private void writeText(TextView targetTxtView, String sourceText) {
        targetTxtView.setText(sourceText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
