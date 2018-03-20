package com.blockly.android.demo.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.blockly.android.demo.R;

/**
 * Created by liufeismart on 2018/3/15.
 */

public abstract class BackActivity extends Activity {
    private ImageButton ib_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateSelf(savedInstanceState);
        ib_back = this.findViewById(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected abstract void onCreateSelf(Bundle savedInstanceState);
}
