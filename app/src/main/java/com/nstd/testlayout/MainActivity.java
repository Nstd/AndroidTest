package com.nstd.testlayout;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nstd.testlayout.activity.AdapterViewFlipperActivity;
import com.nstd.testlayout.activity.GradientArgbEvaluateActivity;
import com.nstd.testlayout.activity.ViewFlipperActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Nstd on 17/6/1.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;

    String[] data = {
            "ViewFlipper",
            "AdapterViewFlipper",
            "GradientArgbEvaluate"
    };

    Class<?>[] actClazz = {
            ViewFlipperActivity.class,
            AdapterViewFlipperActivity.class,
            GradientArgbEvaluateActivity.class
    };

    Map<Integer, Class<?>> map = new HashMap<>();

    {
        for(int i = 0; i < actClazz.length; i++) {
            map.put(i, actClazz[i]);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleStr() {
        return "Demo";
    }

    @Override
    protected void initViews() {
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(MainActivity.this, map.get(i));
                startActivity(it);
            }
        });
    }
}
