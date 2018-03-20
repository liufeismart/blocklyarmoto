package com.blockly.android.demo.activity;

import com.blockly.android.demo.R;
import com.blockly.android.demo.activity.base.ViewPagerActivity;

/**
 * Created by liufeismart on 2018/3/14.
 */

public class ElectrocircuitActivity  extends ViewPagerActivity {

    @Override
    protected int[] getImage() {
        return new int[]{R.mipmap.electrocircuit_1,
                R.mipmap.electrocircuit_2,
                R.mipmap.electrocircuit_3,
                R.mipmap.electrocircuit_4,
                R.mipmap.electrocircuit_5,};
    }
}
