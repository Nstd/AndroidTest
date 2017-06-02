package com.nstd.testlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Nstd on 17/6/1.
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected abstract int getLayoutResId();
    protected abstract String getTitleStr();
    protected abstract void initViews();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);

        setTitle(getTitleStr());

        initViews();
    }


}
