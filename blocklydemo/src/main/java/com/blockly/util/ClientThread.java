package com.blockly.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.blockly.android.demo.Constants;
import com.blockly.android.demo.activity.BluetoothActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private boolean isRun;
    private List<Integer> record_execute = new ArrayList<>();
    private HashMap<String, Integer>  map_variate = new HashMap<>();

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
                        if(isRun) {
                            isRun = false;
                        }
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
                    record_execute.clear();
                    Log.e(BluetoothActivity.TAG, "等待接收消息2");
                    Log.e(BluetoothActivity.TAG,  Thread.currentThread().getName());
                    isRun = false;
                    lock.wait();
                    isRun = true;
                    if(content!=null && !"".equals(content)) {
                        JSONObject root = new JSONObject(content);
                        JSONArray array = root.getJSONArray("array");
                        int count = array.length();
                        for(int i=0;count!=-1 && i<count; i++) {
                            Result_Statement st = parseStatement(array, i, count,in , out);
                            i = st.index;
                            count = st.count;
                        }
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

    private Result_Statement parseStatement(JSONArray array, int i, int count, InputStream in, OutputStream out)  throws Exception{
        JSONObject obj = array.getJSONObject(i);
        String action = obj.getString("action");
        if(action.equals("repeat")) {
            int in1 = obj.getInt("in1");
            int in2  = obj.getInt("in2");
            Log.e(BluetoothActivity.TAG, "repeat: " + in1);
            i++;
            int stCount = count;
            int stIndex = i;
            if(in1!=-1) {
                stCount = i+in2;
                for(int forCount=0; forCount<in1; forCount++) {
                    for(stIndex=i;  stCount!=-1 && stIndex < stCount ; stIndex++) {
                        if(!isRun) {
                            stCount = -1;
                        }
                        else {
                            Result_Statement st = parseStatement(array, stIndex, count,in , out);
                            if(!isRun) {
                                st.count = -1;
                            }
                            stIndex = st.index;
                            stCount = st.count;
                        }

                    }
                }
                return new Result_Statement(stCount, i);
            }
            else {
                while(true) {
                    for(stIndex=i; stIndex < stCount ; stIndex++) {
                        if(!isRun) {
                            break;
                        }
                        Result_Statement st = parseStatement(array, stIndex, count,in , out);
                        stIndex = st.index;
                        stCount = st.count;
                        if(!isRun) {
                            break;
                        }
                    }
                    if(!isRun) {
                        break;
                    }
                }
                return new Result_Statement(-1, i);
            }
        } else if(action.equals("if")) {
            i++;
            JSONObject obj_cp = array.getJSONObject(i);
            Result_Compare result_if = parseCompare(obj_cp, array, i, out, in);
            int in1 = obj.getInt("in1");
            int in2 = obj.getInt("in2");
            int in3 = obj.getInt("in3");
            if(!result_if.result.equals("0")) {
                i = result_if.index;
                Log.v(BluetoothActivity.TAG, "if:"+result_if.index);
                count=i+in2+1;
            } else {

                i = result_if.index+in2;
                Log.v(BluetoothActivity.TAG, "else:"+result_if.index+":"+in2+":"+i);
                count=i+in3+1;
            }
        } else if(action.equals("last_time")) {
            int in1 = obj.getInt("in1");
            int in2 = obj.getInt("in2");
            JSONObject obj_delay = new JSONObject();
            obj_delay.put("action","delay");
            obj_delay.put("time", 3);
            String objStr = obj_delay.toString();
            uploadStatement(out, objStr, in);
            for(int index= record_execute.size()-1; index>= (i-in2); index--) {
                JSONObject obj_execute = array.getJSONObject(record_execute.get(index));
                String action_execute  = obj_execute.getString("action");
                if("electrical_machinery_1".equals(action_execute) ||
                        "electrical_machinery_2".equals(action_execute)) {
                    obj_execute.put("in", 0);
                    objStr = obj_execute.toString();
                    uploadStatement(out, objStr, in);
                }
            }
        }
        else if(action.equals("variate_set")) {
            int in1 = obj.getInt("in1");
            int in2 = obj.getInt("in2");
            if(in1 == 1) {
                i++;
                JSONObject obj_variate = array.getJSONObject(i);
                if("variate".equals(obj_variate.getString("action"))) {
                    i++;
                    int count_set_in2 = i+in2;
                    Result_Compare result_set_in2 = null;
                    for(; i<count_set_in2; i++) {
                        JSONObject obj_cp_in3 = array.getJSONObject(i);
                        result_set_in2 = parseCompare(obj_cp_in3, array, i, out, in);
                    }
                    map_variate.put(obj_variate.getString("in"), Integer.parseInt(result_set_in2.result));
                    return new Result_Statement(count, i);
                }
            }
            i = i+ in1+in2;
        }
        else {
            String objStr = obj.toString();
            Log.e(BluetoothActivity.TAG, "index = " + i);
            uploadStatement(out, objStr, in);
            record_execute.add(i);
        }
        if(!isRun) {
            count = -1;
        }
        return new Result_Statement(count, i);
    }



    private Result_Compare parseCompare(JSONObject obj, JSONArray array, int i,
                                        OutputStream out,
                                        InputStream in) throws Exception {
        String action = obj.getString("action");
        if(action.equals("logic_compare")) {
            String in1_cp = obj.getString("in1");
            int in2_cp = obj.getInt("in2");

            int count_cp_in2 = i+in2_cp;
            Result_Compare result_cp_in2 = null;
            i++;
            for(; i<=count_cp_in2; i++) {
                JSONObject obj_cp_in2 = array.getJSONObject(i);
                result_cp_in2 = parseCompare(obj_cp_in2, array, i, out, in);
            }
            int in3_cp = obj.getInt("in3");
            int count_cp_in3 = result_cp_in2.index+in3_cp;
            Result_Compare result_cp_in3 = null;
            i = result_cp_in2.index+1;
            for(; i<=count_cp_in3; i++) {
                JSONObject obj_cp_in3 = array.getJSONObject(i);
                result_cp_in3 = parseCompare(obj_cp_in3, array, i, out, in);
            }
            String result = "";

            int result_int_1 = Integer.parseInt(result_cp_in2.result);
            int result_int_2 = Integer.parseInt(result_cp_in3.result);
            boolean result_bool = false;
            if("EQ".equals(in1_cp)) {
                result_bool = (result_int_1 == result_int_2);
            } else if("NEQ".equals(in1_cp)) {
                result_bool = (result_int_1 != result_int_2);
            } else if("GTR".equals(in1_cp)) {
                result_bool = (result_int_1 > result_int_2);
            } else if("VA".equals(in1_cp)) {
                result_bool = (result_int_1 < result_int_2);
            } else if("GTE".equals(in1_cp)) {
                result_bool = (result_int_1 >= result_int_2);
            } else if("EQ".equals(in1_cp)) {
                result_bool = (result_int_1 <= result_int_2);
            }
            if(result_bool) {
                result = "1";
            } else {
                result = "0";
            }
            Log.v("ClientThread", "A:"+result_cp_in2.toString());
            Log.v("ClientThread", "B:"+result_cp_in3.toString());
            Log.v("ClientThread", "result:"+result);
            return new Result_Compare(result,result_cp_in3.index);
        }
        else if(action.equals("tracking_right") ||
                action.equals("tracking_left") ||
                action.equals("tracking_middle") ||
                action.equals("avoidance_left") ||
                action.equals("avoidance_right") ||
                action.equals("ultrasonic")) {
            String result = uploadStatement(out, obj.toString(), in);
            return new Result_Compare(result,i);
        } else if(action.equals("tracking_result") ||
                action.equals("avoidance_result") ||
                action.equals("ultrasonic_result")) {
            return new Result_Compare(String.valueOf(obj.getInt("in")),i);
        }
        return null;
    }

    private String uploadStatement(OutputStream out, String objStr, InputStream in) throws Exception {
        Log.e(BluetoothActivity.TAG, "发送的消息: " + objStr);
        out.write(objStr.getBytes());
        String str = "";
        while(true) {
            byte[] buffer = new byte[128];
            int count = in.read(buffer);
            if(count<=0) {
                break;
            }
            str += new String(buffer, 0, count, Charset.forName("utf-8"));
            int start = str.lastIndexOf("<");
            int end = str.lastIndexOf(">");
            if(start>=0 && end >start ) {
                Log.e(BluetoothActivity.TAG, "收到的字符串:"+str);
                str = str.substring(start,end);
                if(str.equals("success")) {
                    break;
                }
                if(str.contains(":")) {
                    String[] splitStr = str.split(":");
                    str = splitStr[1];
                }

                break;
            }
            else {
//                Log.e(BluetoothActivity.TAG, "收到的字符串:"+str);
            }
            Thread.sleep(50);

        }
        return str;
    }

//    @Override
//    public void run() {
//        try {
//            Log.e(BluetoothActivity.TAG, "Client连接");
////            mmSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(SERVICE_UUID));
//            mmSocket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
//            //请求连接
//            mmSocket.connect();
//            Log.e(BluetoothActivity.TAG, "Client连接建立成功");
//            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_SUCCESS);
//            OutputStream out = mmSocket.getOutputStream();
//            InputStream in = mmSocket.getInputStream();
//
//            while(true) {
//                Log.e(BluetoothActivity.TAG, "等待接收消息1");
//                synchronized (lock) {
//                    Log.e(BluetoothActivity.TAG, "等待接收消息2");
//                    Log.e(BluetoothActivity.TAG,  Thread.currentThread().getName());
//                    lock.wait();
//                    if(content!=null && !"".equals(content)) {
//                        JSONObject root = new JSONObject(content);
//                        JSONArray array = root.getJSONArray("array");
//                        for(int i=0; i<array.length(); i++) {
//                            JSONObject obj = array.getJSONObject(i);
//                            String objStr = obj.toString();
//                            Log.e(BluetoothActivity.TAG, "发送的消息: " + objStr);
//                            out.write(objStr.getBytes());
//                            String str = "";
//                            while(true) {
//                                byte[] buffer = new byte[128];
//                                int count = in.read(buffer);
//                                str += new String(buffer, 0, count, Charset.forName("utf-8"));
//                                int start = str.lastIndexOf("<");
//                                int end = str.lastIndexOf(">");
//                                if(start>=0 && end >start ) {
//                                    Log.e(BluetoothActivity.TAG, "收到的字符串:"+str);
//                                    str = str.substring(start,end);
//                                    if(str.equals("success")) {
//                                        break;
//                                    }
//
//                                    break;
//                                }
//                                else {
//                                    Log.e(BluetoothActivity.TAG, "收到的字符串:"+str);
//                                }
//                                Thread.sleep(50);
//                            }
//                        }
//                    }
//                }
//
//            }
//        } catch (IOException e) {
//            Log.e(BluetoothActivity.TAG, "IOException");
//            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_FAIL);
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            Log.e(BluetoothActivity.TAG, "InterruptedException");
//            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_FAIL);
//            e.printStackTrace();
//        } catch (Exception e) {
//            Log.e(BluetoothActivity.TAG, "Exception");
//            handler.sendEmptyMessage(MSG_BLUETOOTH_CONNECT_FAIL);
//            e.printStackTrace();
//        }
//
//    }

    class Result_Compare {
        public String result;
        public int index;
        public Result_Compare(String result, int index) {
            this.result = result;
            this.index = index;
        }

        @Override
        public String toString() {
            return result+":"+ index;
        }
    }

    class Result_Statement {
        public int count;
        public int index;
        public Result_Statement(int count, int index) {
            this.count = count;
            this.index = index;
        }
    }

}
