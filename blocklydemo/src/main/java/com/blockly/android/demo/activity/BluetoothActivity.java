package com.blockly.android.demo.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blockly.android.demo.R;
import com.blockly.util.ClientThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by liufeismart on 18/2/26.
 */

public class BluetoothActivity extends Activity {

    public static final String TAG = "BluetoothActivity";

    //RecyclerView
    private RecyclerView rv_bluetooth;
    private BTAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<BluetoothDevice> devices = new ArrayList<>();

    //Button
    private ImageView btn_search;
    private ViewGroup ll_link_state;
    private TextView tv_bluetooth_name, tv_link_state;
    private ProgressBar pb_;

    //Bluetooth
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothActivity.BlueToothReceiver mBlueToothReceiver;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.activity_bluetooth);
        initView();
        initBluetooth();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBlueToothReceiver);
    }

    private void initView() {
        rv_bluetooth = this.findViewById(R.id.rv_bluetooth);
        btn_search = this.findViewById(R.id.btn_search);
        ll_link_state = this.findViewById(R.id.ll_link_state);
        tv_bluetooth_name = this.findViewById(R.id.tv_bluetooth_name);
        tv_link_state = this.findViewById(R.id.tv_link_state);
        pb_ = this.findViewById(R.id.pb_);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mBluetoothAdapter!=null) {
                    Log.v(TAG, "mBluetoothAdapter.isEnabled() = " + mBluetoothAdapter.isEnabled());
                    Log.v(TAG, "mBluetoothAdapter.isDiscovering() = " + mBluetoothAdapter.isDiscovering());
                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }
                    boolean isDiscovering = mBluetoothAdapter.startDiscovery();
                    Log.v(TAG,"蓝牙是否正在搜索?"+ isDiscovering);
                }

            }
        });
        mAdapter = new BTAdapter(devices);
        mLayoutManager = new GridLayoutManager(this, 3);
        rv_bluetooth.setAdapter(mAdapter);
        rv_bluetooth.setLayoutManager(mLayoutManager);
    }

    private void initBluetooth() {
        //获取蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //打开蓝牙
        Log.v(TAG, "mBluetoothAdapter.isEnabled() = " + mBluetoothAdapter.isEnabled());
        if(!mBluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            mBluetoothAdapter.enable();

        }
        //获取本机蓝牙名称
        String name = mBluetoothAdapter.getName();
        //获取本机蓝牙地址
        String address = mBluetoothAdapter.getAddress();
        Log.v(TAG,"bluetooth name ="+name+" address ="+address);
        //获取已绑定蓝牙
        Set<BluetoothDevice> bondedDevices =  mBluetoothAdapter.getBondedDevices();
        Log.v(TAG,"bondedDevices.size() = " + bondedDevices.size());
        for(BluetoothDevice device : bondedDevices) {
            devices.add(device);
        }

        //
        mBlueToothReceiver = new BlueToothReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);//搜索发现设备
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//状态改变
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);//行动扫描模式改变了
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//动作状态发生了变化
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        Log.v(TAG, "registerReceiver");
        registerReceiver(mBlueToothReceiver, filter);
        boolean isDiscovering = mBluetoothAdapter.startDiscovery();
        Log.v(TAG,"蓝牙是否正在搜索?"+ isDiscovering);
    }

    public void changeLinkState(boolean isSuccess) {
        if(isSuccess) {
            tv_link_state.setText("连接成功！");
        } else {
            tv_link_state.setText("连接失败！");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                   showLinkState(false);
                }
            }, 2000);
        }
        pb_.setVisibility(View.GONE);
        tv_link_state.setVisibility(View.VISIBLE);

    }


    class BTAdapter extends RecyclerView.Adapter<BTAdapter.ViewHolder> {

        private List<BluetoothDevice> devices;

        public BTAdapter(List<BluetoothDevice> devices) {
            this.devices = devices;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth, parent, false);
            ViewHolder viewholder = new ViewHolder(itemView);
            return viewholder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            BluetoothDevice device = devices.get(position);
            String name = device.getName();
            if(name == null || "".equals(name)) {
                name = device.getAddress();
            }
            holder.tv_bluetooth_name.setText(name);
            final int index = position;
            final String deviceName = name;
            holder.tv_bluetooth_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BluetoothDevice device = devices.get(index);
                    com.blockly.android.demo.Constants.mClientThread = new ClientThread(BluetoothActivity.this, device);
                    com.blockly.android.demo.Constants.mClientThread.start();
                    tv_bluetooth_name.setText(deviceName);
                    showLinkState(true);
                }
            });
        }

        @Override
        public int getItemCount() {
            return devices.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tv_bluetooth_name;
            public View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_bluetooth_name = itemView.findViewById(R.id.tv_bluetooth_name);
                this.itemView = itemView;
            }
        }
    }

    private void showLinkState(boolean show) {
        if(show) {
            ll_link_state.setVisibility(View.VISIBLE);
            tv_link_state.setVisibility(View.GONE);
            pb_.setVisibility(View.VISIBLE);
            rv_bluetooth.setVisibility(View.GONE);
        } else {
            ll_link_state.setVisibility(View.GONE);
            rv_bluetooth.setVisibility(View.VISIBLE);
        }

    }


    class BlueToothReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "onReceive :" + action);
            if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
                Log.e(TAG, "开始蓝牙搜索");
            }
            else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Log.e(TAG, "结束蓝牙搜索");
            }
            else if(action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(!devices.contains(device)) {
                    devices.add(device);

                    mAdapter.notifyDataSetChanged();
                    Log.e(TAG, "搜索到设备: " + device.getName());
                }

            }
        }
    }
}
