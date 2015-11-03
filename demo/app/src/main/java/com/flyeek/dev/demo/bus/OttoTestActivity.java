package com.flyeek.dev.demo.bus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flyeek.dev.demo.R;
import com.flyeek.dev.demo.bus.event.TestHasParamEvent;
import com.flyeek.dev.demo.bus.event.TestNoParamEvent;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OttoTestActivity extends Activity {

    @Bind(R.id.btn_otto_event_no_param)
    public Button mBtnNoParam;

    @Bind(R.id.btn_otto_event_has_param)
    public Button mBtnHasParam;

    @Bind(R.id.txt_otto_event_info)
    public TextView mTxtEventInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_otto);

        ButterKnife.bind(this);

        mBtnNoParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(new TestNoParamEvent());
            }
        });

        mBtnHasParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(new TestHasParamEvent("param from otto event"));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onTestNoParamEvent(TestNoParamEvent event) {
        mTxtEventInfo.setText("get no param event");
    }

    @Subscribe
    public void onTestHasParamEvent(TestHasParamEvent event) {
        mTxtEventInfo.setText(event.param);
    }
}
