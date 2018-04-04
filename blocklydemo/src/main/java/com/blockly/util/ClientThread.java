package com.blockly.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.blockly.android.demo.Constants;
import com.blockly.android.demo.activity.BluetoothActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import static com.blockly.android.demo.Constants.MSG_BLUETOOTH_CONNECT_FAIL;
import static com.blockly.android.demo.Constants.MSG_BLUETOOTH_CONNECT_SUCCESS;



/**
 * Created by liufeismart on 18/1/24.
 */

public class ClientThread extends HandlerThread {

    private BluetoothDevice device;
    private BluetoothSocket mmSocket;

    public static final String SERVICE_UUID = "00001125-0000-1000-8000-00805F9B34FB";//"00001101-0000-1000-8000-00805F9B34FB";

    public Handler handler;
    private static String content;
    private static Object lock = new Object();

    public ClientThread(final BluetoothActivity activity, BluetoothDevice bluetoothDevice) {
        super("Hanler");
        device = bluetoothDevice;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch(msg.what) {
                    case Constants.MSG_BLUETOOTH_CONNECT_SUCCESS:
                        activity.changeLinkState(true);
                        break;
                    case MSG_BLUETOOTH_CONNECT_FAIL:
                        activity.changeLinkState(false);
                        Log.e(BluetoothActivity.TAG, "MSG_BLUETOOTH_CONNECT_FAIL");
                        break;
                    case Constants.MSG_DELIVERY:
                        content = (String)msg.obj;
                        Log.e(BluetoothActivity.TAG, "发送消息"+ content);
                        Log.e(BluetoothActivity.TAG,  Thread.currentThread().getName());
                        synchronized (lock) {
                            Log.e(BluetoothActivity.TAG, "唤醒");
                            lock.notifyAll();

                        }
                        break;
                }

            }
        };
    }

    @Override
    public void run() {
        try {
            Log.e(BluetoothActivity.TAG, "Client连接");
//            mmSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(SERVICE_UUID));
            mmSocket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
            //请求连接
            mmSocket.connect();
            Log.e(BluetoothActivity.TAG, "Client连接建立成功");
            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_SUCCESS);
            OutputStream out = mmSocket.getOutputStream();
            InputStream in = mmSocket.getInputStream();
            while(true) {
                Log.e(BluetoothActivity.TAG, "等待接收消息1");
                synchronized (lock) {
                    Log.e(BluetoothActivity.TAG, "等待接收消息2");
                    Log.e(BluetoothActivity.TAG,  Thread.currentThread().getName());
                    lock.wait();
//                    Thread.sleep(1000);

//                    Thread.sleep(1000);
//                    if(content!=null && !"".equals(content)) {
//                        Log.e(BluetoothActivity.TAG, "接收消息长度: " + content.getBytes().length);
////                        content = "<"+ content + ">";
//                        out.write(content.getBytes());
//                    }
//                    Log.e(BluetoothActivity.TAG, "接收消息: " + content);
                    if(content!=null && !"".equals(content)) {
                        int index = 0;
                        int totalLen = content.length();
                        while(index<totalLen) {
                            int len = 50;
                            int temp = index+len;
                            if(temp >= content.length()) {
                                len = content.length() - index;
                            }
                            String tempStr = content.substring(index, index+len);
                            Log.e(BluetoothActivity.TAG, "发送的消息: " + tempStr);
//                        content = "<"+ content + ">";
                            out.write(tempStr.getBytes());
                            index+=len;

                            String str = "";
                            while(true) {
                                byte[] buffer = new byte[128];
                                int count = in.read(buffer);
                                str += new String(buffer, 0, count, Charset.forName("utf-8"));
                                if(str.length() == 50) {
                                    Log.e(BluetoothActivity.TAG, "收到的字符串:"+str);
                                    Log.e(BluetoothActivity.TAG, "index:"+index+", totalLen = " + totalLen);
                                    break;
                                }
                                Log.e(BluetoothActivity.TAG, "收到的字符串:"+str);

                            }

                            Thread.sleep(500);
                        }
                        Log.e(BluetoothActivity.TAG, "index:"+index+", totalLen = " + totalLen);
                    }
                }

            }
        } catch (IOException e) {
            Log.e(BluetoothActivity.TAG, "IOException");
            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_FAIL);
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e(BluetoothActivity.TAG, "InterruptedException");
            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_FAIL);
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(BluetoothActivity.TAG, "Exception");
            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_FAIL);
            e.printStackTrace();
        }

    }


}
