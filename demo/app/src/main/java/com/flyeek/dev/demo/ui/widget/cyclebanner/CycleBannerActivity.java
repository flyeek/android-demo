package com.flyeek.dev.demo.ui.widget.cyclebanner;

import android.app.Activity;
import android.os.Bundle;

import com.flyeek.dev.demo.R;

import java.util.ArrayList;

/**
 * @author http://blog.csdn.net/finddreams
 * @Description:显示广告条的主页
 */
public class CycleBannerActivity extends Activity {

    private ViewFlow mViewFlow;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray = new ArrayList<String>();
    ArrayList<String> titleList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclebanner);
        initView();
        imageUrlList
                .add("http://b.hiphotos.baidu.com/image/pic/item/d01373f082025aaf95bdf7e4f8edab64034f1a15.jpg");
        imageUrlList
                .add("http://g.hiphotos.baidu.com/image/pic/item/6159252dd42a2834da6660c459b5c9ea14cebf39.jpg");
        imageUrlList
                .add("http://d.hiphotos.baidu.com/image/pic/item/adaf2edda3cc7cd976427f6c3901213fb80e911c.jpg");
        imageUrlList
                .add("http://g.hiphotos.baidu.com/image/pic/item/b3119313b07eca80131de3e6932397dda1448393.jpg");

        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/44301359");
        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/43486527");
        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/44648121");
        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/44619589");
        titleList.add("常见Android进阶笔试题");
        titleList.add("GridView之仿支付宝钱包首页");
        titleList.add("仿手机QQ网络状态条的显示与消失 ");
        titleList.add("Android循环滚动广告条的完美实现 ");
        initBanner(imageUrlList);
    }

    private void initView() {
        mViewFlow = (ViewFlow) findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);

    }

    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImagePagerAdapter(this, imageUrlList,
                linkUrlArray, titleList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

}
