package com.blockly.android.demo;

/**
 * Created by liufeismart on 18/2/26.
 */

public class Constants {
    public static final int LESSON_FORWARD_BACK = 0;
    public static final int LESSON_LED = 1;



    public static com.blockly.util.ClientThread mClientThread;


    public static final int MSG_BLUETOOTH_CONNECT_SUCCESS = 0;//蓝牙连接成功
    public static final int MSG_BLUETOOTH_CONNECT_FAIL = 2;//蓝牙连接失败
    public static final int MSG_DELIVERY = 1; // 消息传递

    public static final String SCAN_RESULT = "SCAN_RESULT";
    public static final int REQUEST_SCAN = 0;

    public static final String ACTION_DEVICE_INFO = "com.humax.intent.ACTION_DEVICE_INFO";//器材介绍
    public static final String EXTRA_DEVICE_INFO = "extra_device_info";

    public static final int REQUEST_ENABLE_BT = 0;
}
