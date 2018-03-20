package com.blockly.android.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.blockly.android.demo.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * Created by liufeismart on 18/2/28.
 */

public class QRScanActivity extends Activity implements ZXingScannerView.ResultHandler {
    public static final String TAG = "QRScanActivity";

    ZXingScannerView zxsv_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);
        zxsv_ = findViewById(R.id.zxsv_);
        zxsv_.setResultHandler(this);
    }


    @Override
    protected void onResume() {
        Log.v(TAG, "onResume");
        super.onResume();
        zxsv_.startCamera();
    }

    @Override
    protected void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
        zxsv_.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent(this, QRWaitActivity.class);
        startActivity(intent);
    }
}
