package com.blockly.android.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blockly.android.demo.R;

/**
 * Created by liufeismart on 2018/3/15.
 */

public class QRWaitActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrwait);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(QRWaitActivity.this, QRResultActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
