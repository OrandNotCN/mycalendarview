package com.lacuz.cherish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lacuz.cherish.R;
import com.lacuz.cherish.greendao.gen.GreenDaoUtils;
import com.lacuz.cherish.ui.adapter.BirthListAdapter;
import com.lacuz.cherish.view.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerParallaxSnap extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    BirthListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_parallax_snap);
        ButterKnife.bind(this);

        mToolbar.setTitle("风景旧曾谙");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initRecyclerView();

    }
    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL,1,R.color.base_grey));
        mAdapter = new BirthListAdapter(null, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.updateData(GreenDaoUtils.getSession(this).getBirthDao().queryBuilder().list());
    }

    public void fabClick(View view) {
        startActivity(new Intent(this, AddActivity.class));
    }
}
