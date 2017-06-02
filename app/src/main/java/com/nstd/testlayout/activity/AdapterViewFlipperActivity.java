package com.nstd.testlayout.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nstd.testlayout.BaseActivity;
import com.nstd.testlayout.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nstd on 17/6/1.
 */

public class AdapterViewFlipperActivity extends BaseActivity {
    private static final String TAG = "AdapterViewFlipper";

    @BindView(R.id.adapter_view_flipper)
    AdapterViewFlipper flipper;

    String[] data = {
            "1111111111",
            "2222222222",
            "3333333333",
            "4444444444",
            "5555555555"
    };


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_adapter_view_flipper;
    }

    @Override
    protected String getTitleStr() {
        return TAG;
    }

    @Override
    protected void initViews() {
        flipper.setFlipInterval(2000);
        flipper.setAdapter(new MyAdapter());
        flipper.post(new Runnable() {
            @Override
            public void run() {
                int height = flipper.getMeasuredHeight();
                ObjectAnimator a = new ObjectAnimator().setDuration(500);
                a.setPropertyName("translationY");
                a.setFloatValues(-height, 0);
                flipper.setInAnimation(a);
                a = new ObjectAnimator().setDuration(200);
                a.setPropertyName("translationY");
                a.setFloatValues(0, height);
                flipper.setOutAnimation(a);
                flipper.startFlipping();
            }
        });
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int i) {
            return data[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView tv;
            if(view == null) {
                tv = new TextView(viewGroup.getContext());
            } else {
                tv = (TextView) view;
            }

            tv.setText(data[i]);

            return tv;
        }
    }
}
