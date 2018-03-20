package com.blockly.android.demo.activity;

import com.blockly.android.demo.R;
import com.blockly.android.demo.activity.base.ViewPagerActivity;

/**
 * Created by liufeismart on 2018/3/14.
 */

public class StructureActivity extends ViewPagerActivity {

    @Override
    protected int[] getImage() {
        return new int[]{R.mipmap.structure_1,
                R.mipmap.structure_2,
                R.mipmap.structure_3,
                R.mipmap.structure_4,
                R.mipmap.structure_5,};
    }
}
