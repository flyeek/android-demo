package com.flyeek.dev.demo.graphic;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Split image to row * colume pieces
 * Created by flyeek on 6/10/15.
 */
public class ImageSplitter {

    public static List<Bitmap> split(Bitmap bitmap, int xPiece, int yPiece) {
        List<Bitmap> pieces = new ArrayList<Bitmap>(xPiece * yPiece);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth = width / 3;
        int pieceHeight = height / 3;
        for (int i = 0; i < yPiece; i++) {
            for (int j = 0; j < xPiece; j++) {
                Bitmap piece = null;
                int xValue = j * pieceWidth;
                int yValue = i * pieceHeight;
                piece = Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceHeight);
                pieces.add(piece);
            }
        }

        return pieces;
    }
}
