package com.flyeek.dev.demo.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by flyeek on 6/8/15.
 */
public class Facebook {

    public static void shareToFaceBook(Activity activity) {
        ShareDialog shareDialog = new ShareDialog(activity);
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/DCIM/phototest.jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (fis != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                shareDialog.show(content);
            }
        }
        /*ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle("Hello Facebook")
                .setContentDescription(
                        "The 'Hello Facebook' sample  showcases simple Facebook integration")
                .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                .build();
        shareDialog.show(linkContent);*/
    }
}
