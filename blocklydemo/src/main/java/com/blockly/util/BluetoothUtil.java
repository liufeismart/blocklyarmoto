package com.blockly.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.ref.SoftReference;

/**
 * Created by humax on 18/4/28
 */

public class BluetoothUtil {

    public static boolean isConnected(SoftReference<Context> mContextRef) {
          return true;
//        BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
//        if(mAdapter.isEnabled()){
//            int a2dp = mAdapter.getProfileConnectionState(BluetoothProfile.A2DP); // 可操控蓝牙设备，如带播放暂停功能的蓝牙耳机
//            int headset = mAdapter.getProfileConnectionState(BluetoothProfile.HEADSET); // 蓝牙头戴式耳机，支持语音输入输出
//            int health = mAdapter.getProfileConnectionState(BluetoothProfile.HEALTH); // 蓝牙穿戴式设备
//            int GATT = mAdapter.getProfileConnectionState(BluetoothProfile.GATT);
//            // 查看是否蓝牙是否连接到三种设备的一种，以此来判断是否处于连接状态还是打开并没有连接的状态
//            int flag = -1;
//            if (a2dp == BluetoothProfile.STATE_CONNECTED) {
//                flag = a2dp;
//            } else if (headset == BluetoothProfile.STATE_CONNECTED) {
//                flag = headset;
//            } else if (health == BluetoothProfile.STATE_CONNECTED) {
//                flag = health;
//            }
//            if (flag != -1) {
//                return true;
//            }
//            else if (flag == -1) {
//                //蓝牙手机相互配对连接
//                NetworkInfo netInfo = ((ConnectivityManager) mContextRef.get().getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
//                netInfo.getState();
//                if (netInfo == null) {
//                    return false;
//                } else {
//                    if(netInfo.isConnected()) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
    }
}
