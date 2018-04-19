package com.blockly.util;

import android.os.Message;
import android.util.Log;

import com.google.blockly.android.codegen.CodeGenerationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liufeismart on 2018/4/19.
 */

public class DefaultCodeGeneratorCallback implements CodeGenerationRequest.CodeGeneratorCallback {

    private String tag = "";

    public DefaultCodeGeneratorCallback(String tag) {
        this.tag = tag;
    }

    @Override
    public void onFinishCodeGeneration(String generatedCode) {
        Log.i(tag, "generatedCode:" + generatedCode);

        try {
            List<String> statements = Arrays.asList(generatedCode.split("\n"));
            JSONObject root = new JSONObject();
            JSONArray array = new JSONArray();
            root.put("array", array);
            JSONObject item;
            int index = 0;
            for(String statement : statements) {
                item = new JSONObject();
                if(statement.contains("electrical_machinery_1")) {
                    item.put("action", "electrical_machinery_1");
                    String[] strs = statement.split(":");
                    item.put("in", Integer.parseInt(strs[1]));
                }
                else if(statement.contains("electrical_machinery_2")) {
                    item.put("action", "electrical_machinery_2");
                    String[] strs = statement.split(":");
                    item.put("in", Integer.parseInt(strs[1]));
                }
                else if(statement.contains("last_time")) {
                    item.put("action", "last_time");
                    String[] strs = statement.split(":");
                    item.put("in1", Integer.parseInt(strs[1]));
                    item.put("in2", Integer.parseInt(strs[2]));
                }
                array.put(item);
                index++;
            }
            Message msg = new Message();
            msg.what = com.blockly.android.demo.Constants.MSG_DELIVERY;
            msg.obj = root.toString();
            Log.e(tag, root.toString());
            com.blockly.android.demo.Constants.mClientThread.handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
