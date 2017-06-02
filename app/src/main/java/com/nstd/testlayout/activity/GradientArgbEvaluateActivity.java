package com.nstd.testlayout.activity;

import android.animation.TypeEvaluator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nstd.testlayout.BaseActivity;
import com.nstd.testlayout.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Nstd on 17/6/1.
 *
 * https://www.zybuluo.com/TryLoveCatch/note/725449
 */

public class GradientArgbEvaluateActivity extends BaseActivity {
    private static final String TAG = "GradientArgbEvaluate";

    @BindView(R.id.pager)
    ViewPager pager;
    List<View> views = new ArrayList<>();
    MyColor[] colors = new MyColor[] {
            new MyColor(Color.parseColor("#ff2300"), Color.parseColor("#ff8100")),
            new MyColor(Color.parseColor("#ff8100"), Color.parseColor("#fbff00")),
            new MyColor(Color.parseColor("#ffeb00"), Color.parseColor("#32a702")),
    };

    MyColorEvaluator evaluator = new MyColorEvaluator();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gradient_argb_evaluate;
    }

    @Override
    protected String getTitleStr() {
        return TAG;
    }

    @Override
    protected void initViews() {
        for(char a = 'A'; a < 'D'; a++) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);
            tv.setText(a + "");
            views.add(tv);
        }

        pager.setAdapter(new MyPagerAdapter());
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                handleBackground(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void handleBackground(int position, float positionOffset) {
        int count = views.size() - 1;
        if(count != 0) {
            float length = (position + positionOffset) / count;

            GradientDrawable gd;
            if(pager.getBackground() == null) {
                gd = new GradientDrawable();
                gd.setOrientation(GradientDrawable.Orientation.TL_BR);
            } else {
                gd = (GradientDrawable) pager.getBackground();
            }

//            MyColor color = evaluator.evaluate(length, colors[0], colors[1]);
            MyColor color = evaluator.evaluate(positionOffset, colors[position], colors[position + 1 == colors.length ? position : position + 1]);
            gd.setColors(new int[]{color.startColor, color.endColor});
            pager.setBackground(gd);
        }
    }


    private class MyColor {
        int startColor;
        int endColor;

        public MyColor() {}

        public MyColor(int startColor, int endColor) {
            this.startColor = startColor;
            this.endColor = endColor;
        }
    }

    private class MyColorEvaluator implements TypeEvaluator<MyColor> {

        @Override
        public MyColor evaluate(float v, MyColor start, MyColor end) {
            MyColor target = new MyColor();
            target.startColor = createColor(v, start.startColor, end.startColor);
            target.endColor = createColor(v, start.endColor, end.endColor);
            return target;
        }

        private int createColor(float fraction, int pStartColor, int pEndColor) {
            int startA = (pStartColor >> 24) & 0xff;
            int startR = (pStartColor >> 16) & 0xff;
            int startG = (pStartColor >> 8) & 0xff;
            int startB = pStartColor & 0xff;
            int endA = (pEndColor >> 24) & 0xff;
            int endR = (pEndColor >> 16) & 0xff;
            int endG = (pEndColor >> 8) & 0xff;
            int endB = pEndColor & 0xff;
            return ((startA + (int) (fraction * (endA - startA))) << 24)
                    | ((startR + (int) (fraction * (endR - startR))) << 16)
                    | ((startG + (int) (fraction * (endG - startG))) << 8)
                    | ((startB + (int) (fraction * (endB - startB))));
        }
    }


    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "" + ('A' + position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }
    }
}
