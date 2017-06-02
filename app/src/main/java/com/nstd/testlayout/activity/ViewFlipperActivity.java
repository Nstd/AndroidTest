package com.nstd.testlayout.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterViewFlipper;
import android.widget.ViewFlipper;

import com.nstd.testlayout.BaseActivity;
import com.nstd.testlayout.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewFlipperActivity extends BaseActivity {
    private static final String TAG = "ViewFlipper";

    @BindView(R.id.flipper)
    ViewFlipper flipper;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scrolling;
    }

    @Override
    protected String getTitleStr() {
        return TAG;
    }

    @Override
    protected void initViews() {
        flipper.post(new Runnable() {
            @Override
            public void run() {
                int height = flipper.getMeasuredHeight();
                Animation a = new TranslateAnimation(0, 0, -height, 0);
                a.setDuration(500);
                flipper.setInAnimation(a);
                a = new TranslateAnimation(0, 0, 0, height);
                a.setDuration(200);
                flipper.setOutAnimation(a);
                flipper.startFlipping();
                flipper.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "current is : " + flipper.getDisplayedChild());
                    }
                });
            }
        });
    }
}
