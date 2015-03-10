package com.flyeek.gettingstarted;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FullScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        setContentView(R.layout.activity_fullscreen);

        TextView textView = (TextView) findViewById(R.id.message_displayed);
        textView.setTextSize(40);
        textView.setText(message);
    }
}
