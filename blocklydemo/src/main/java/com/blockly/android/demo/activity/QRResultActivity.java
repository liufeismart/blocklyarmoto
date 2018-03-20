package com.blockly.android.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.blockly.android.demo.R;

import static com.blockly.android.demo.Constants.ACTION_DEVICE_INFO;
import static com.blockly.android.demo.Constants.EXTRA_DEVICE_INFO;

/**
 * Created by liufeismart on 2018/3/15.
 */

public class QRResultActivity extends Activity {

    private String TAG = QRResultActivity.class.getSimpleName();

    private long downTime;
    private float downX, downY;
    private boolean isClick = true;


    private RectF[] rects = new RectF[]{
            new RectF(226f, 183f, 386, 259f),
            new RectF(142f, 275f, 300f, 350f),
            new RectF(57f, 427f, 342f, 512f),
            new RectF(85f, 568f, 328f, 653f),
            new RectF(929f, 178f, 1160f, 258f),
            new RectF(970f, 275f, 1194f, 353f),
            new RectF(950f, 508f, 1170f, 591f),
            new RectF(936f, 602f, 1165f, 683f),
        };
    private String[] hints = {
           "复位键,按下会清空之前汆熟的程序",
            "USB口, 可以通过USB线连接电脑，传输程序",
            "通讯信号指示灯,传输程序时,灯会闪烁",
            "DC电源接口，连接电源为ARDUINO板进行供电",
            "数字输入口,用于传输数字信号",
            "电源指示灯,当ARDUINO板通电后,灯会亮起",
            "单片机芯片, 相当于这个ARDUINO板的大脑，执行程序命令",
            "模拟输入口, 用于传输模拟信号"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrresult);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                downTime = System.currentTimeMillis();
                isClick = true;
                break;
            case MotionEvent.ACTION_UP:
                long currTime = System.currentTimeMillis();
                float upX = event.getRawX();
                float upY = event.getRawY();
                Log.v(TAG, "x = " +upX+", y =" + upY);
                if((currTime-downTime) < 1000 && Math.abs(upX-downX) < 10 && Math.abs(upY-downY) < 10) {
                    int index = 0;
                    for(RectF rect : rects) {
//                        Log.v(TAG, "rect = ("+ rect.top+", " +rect.left +", "+rect.bottom+", "+rect.right);
                        if(rect.contains(upX, upY)) {
                            Intent intent = new Intent();
                            intent.setAction(ACTION_DEVICE_INFO);
                            intent.putExtra(EXTRA_DEVICE_INFO, hints[index]);
                            sendBroadcast(intent);
                            break;
                        }
                        index++;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
