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
import java.io.OutputStream;

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
                        break;
                    case Constants.MSG_DELIVERY:
                        content = (String)msg.obj;
                        Log.e(BluetoothActivity.TAG, "发送消息"+ content);
                        Log.e(BluetoothActivity.TAG,  Thread.currentThread().getName());
                        synchronized (lock) {
                            lock.notify();
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
            while(true) {
                synchronized (lock) {
                    Log.e(BluetoothActivity.TAG, "等待接收消息");
                    Log.e(BluetoothActivity.TAG,  Thread.currentThread().getName());
                    lock.wait();
//                    Thread.sleep(1000);

//                    Thread.sleep(1000);
                    if(content!=null && !"".equals(content)) {
                        Log.e(BluetoothActivity.TAG, "接收消息长度: " + content.getBytes().length);
//                        content = "<"+ content + ">";
                        out.write(content.getBytes());
                    }
                    Log.e(BluetoothActivity.TAG, "接收消息: " + content);
                }

            }
        } catch (IOException e) {
            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_FAIL);
            e.printStackTrace();
        } catch (InterruptedException e) {
            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_FAIL);
            e.printStackTrace();
        } catch (Exception e) {
            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_FAIL);
            e.printStackTrace();
        }

    }


}
