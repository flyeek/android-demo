package com.flyeek.dev.demo.ui.widget.recyclerview;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.flyeek.dev.demo.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private RecyclerViewBaseAdapter mAdapter;

    private RecyclerView.ItemDecoration mItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);

        initDatas();
        initViews();

        mAdapter = new RecyclerViewLinearAdapter(this, mDatas, RecyclerViewLinearAdapter.VERTICAL);
        mRecyclerView.setAdapter(mAdapter);

        // Config layout for RecyclerView.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Config divider style between item for RecyclerView.
        /*mItemDecoration = getLinearItemDecoration();
        mRecyclerView.addItemDecoration(mItemDecoration);*/

        // Config item animator for RecyclerView.
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initActions();
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();

        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
    }

    private void initActions() {
        mAdapter.setItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "item " + position + " clicked", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "item " + position + " long clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private RecyclerViewLinearAdapter getAdapter() {
        return new RecyclerViewLinearAdapter(this, mDatas, RecyclerViewLinearAdapter.VERTICAL);
    }

    private RecyclerView.ItemDecoration getLinearItemDecoration() {
        Drawable drawableDivider = getResources().getDrawable(R.drawable
                .recycler_view_divider_vertical_bright);
        int dividerSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.0f,
                getResources().getDisplayMetrics());
        return new ItemDecorationDivider(ItemDecorationDivider.VERTICAL, drawableDivider, dividerSize);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_view_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                mAdapter.addData(1);
                break;

            case R.id.action_delete:
                mAdapter.deleteData(1);
                break;

            case R.id.action_listview:
                mAdapter = new RecyclerViewLinearAdapter(this, mDatas, RecyclerViewLinearAdapter.VERTICAL);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;

            case R.id.action_horizontal_listview:
                mAdapter = new RecyclerViewLinearAdapter(this, mDatas, RecyclerViewLinearAdapter.HORIZONTAL);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                        .HORIZONTAL, false));
                break;

            case R.id.action_gridview:
                mAdapter = new RecyclerViewLinearAdapter(this, mDatas, RecyclerViewLinearAdapter.VERTICAL);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                break;

            case R.id.action_horizontal_gridview:
                mAdapter = new RecyclerViewLinearAdapter(this, mDatas, RecyclerViewLinearAdapter.HORIZONTAL);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 6, GridLayoutManager
                        .HORIZONTAL, false));
                break;

            case R.id.action_stagger_gridview:
                mAdapter = new RecyclerViewStaggerAdapter(this, mDatas,
                        RecyclerViewStaggerAdapter.VERTICAL);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                        StaggeredGridLayoutManager.VERTICAL));
                break;

            case R.id.action_horizontal_stagger_gridview:
                mAdapter = new RecyclerViewStaggerAdapter(this, mDatas,
                        RecyclerViewStaggerAdapter.HORIZONTAL);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(6,
                        StaggeredGridLayoutManager.HORIZONTAL));
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
