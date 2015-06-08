package com.flyeek.dev.demo.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by flyeek on 6/8/15.
 */
public class Instagram {

    public static Intent getshareImageIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        File image = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/DCIM/phototest.jpg");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
        intent.setPackage("com.instagram.android");

        return intent;
    }
}
