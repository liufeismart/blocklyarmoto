package com.blockly.android.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blockly.android.demo.R;

/**
 * Created by liufeismart on 2018/3/14.
 */

public class IntroduceActivity extends Activity {

    private final String TAG = IntroduceActivity.class.getSimpleName();

    private TextView[] tv_introduce = new TextView[3];
    private int[] ids = {R.id.tv_structure, R.id.tv_electrocircuit, R.id.tv_video};

    private Class[] jumpActivity = {StructureActivity.class,
                        ElectrocircuitActivity.class,
                        VideoAssembleActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        for(int i=0; i<tv_introduce.length; i++) {
            tv_introduce[i] = this.findViewById(ids[i]);
        }
        for(int i=0; i<tv_introduce.length; i++) {
            final int index = i;
            tv_introduce[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "跳转到:" + jumpActivity[index].getSimpleName());
                    startActivity(new Intent(IntroduceActivity.this, jumpActivity[index]));
                }
            });

        }
    }
}
