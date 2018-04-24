package com.blockly.util;

import android.os.Message;
import android.util.Log;

import com.blockly.android.demo.activity.SkillMovesActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

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
        Log.i(tag, "generatedCode:\n" + generatedCode);

        try {
            String[] statements =generatedCode.split("\n");
            JSONObject root = new JSONObject();
            JSONArray array = new JSONArray(

            );
            root.put("array", array);
            JSONObject item;

            for(int i=0; i< statements.length; i++) {
                String statement = statements[i];
                i = parseStatement(statements, i, array).index;
            }
            Message msg = new Message();
            msg.what = com.blockly.android.demo.Constants.MSG_DELIVERY;
            msg.obj = root.toString();
            com.blockly.android.demo.Constants.mClientThread.handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bean parseStatement(String statements[], int i, JSONArray array) {
        JSONObject item = new JSONObject();
        String statement = statements[i];
        try {
            //循环
            if (statement.contains("repeat")) {
                item.put("action", "repeat");
                String[] strs = statement.split(":");
                item.put("in1", Integer.parseInt(strs[1]));
                item.put("in2", Integer.parseInt(strs[2]));
            }
            //逻辑
            else if(statement.contains("if")) {
                item.put("action", "if");
                String[] strs = statement.split(":");
                item.put("in1", Integer.parseInt(strs[1]));
                item.put("in2", Integer.parseInt(strs[2]));
                if(strs.length>3) {
                    item.put("in3", Integer.parseInt(strs[3]));
                }
                else {
                    item.put("in3", 0);
                }

            }
            else if(statement.contains("logic_compare")) {
                item.put("action", "logic_compare");
                String[] strs = statement.split(":");
                item.put("in1", strs[1]);
                item.put("in2", Integer.parseInt(strs[2]));
                item.put("in3", Integer.parseInt(strs[3]));
            }
            //红外避障
            else if(statement.contains("avoidance_left")) {
                item.put("action", "avoidance_left");
            }
            else if(statement.contains("avoidance_right")) {
                item.put("action", "avoidance_right");
            }
            else if(statement.contains("avoidance_result")) {
                item.put("action", "avoidance_result");
                String[] strs = statement.split(":");
                item.put("in", Integer.parseInt(strs[1]));
            }
            //红外循迹
            else if(statement.contains("tracking_left")) {
                item.put("action", "tracking_left");
            }
            else if(statement.contains("tracking_middle")) {
                item.put("action", "tracking_middle");
            }
            else if(statement.contains("tracking_right")) {
                item.put("action", "tracking_right");
            }
            else if(statement.contains("tracking_result")) {
                item.put("action", "tracking_result");
                String[] strs = statement.split(":");
                item.put("in", Integer.parseInt(strs[1]));
            }
            //舵机
            else if(statement.contains("steering_engine")) {
                item.put("action", "steering_engine");
                String[] strs = statement.split(":");
                item.put("in", Integer.parseInt(strs[1]));
            }
            //超声波
            else if(statement.contains("ultrasonic")) {
                item.put("action", "ultrasonic");
            }
            else if(statement.contains("ultrasonic_result")) {
                item.put("action", "ultrasonic_result");
                String[] strs = statement.split(":");
                item.put("in", Integer.parseInt(strs[1]));
            }
            //变量
            else if(statement.contains("variate_front_direction")) {
                item.put("action", "variate");
                item.put("in", "variate_front_direction");
            }
            else if(statement.contains("variate_left_direction")) {
                item.put("action", "variate");
                item.put("in", "variate_left_direction");
            }
            else if(statement.contains("variate_right_direction")) {
                item.put("action", "variate");
                item.put("in", "variate_right_direction");
            }
            else if(statement.contains("variate_set")) {
                item.put("action", "variate_set");
                String[] strs = statement.split(":");
                item.put("in1", Integer.parseInt(strs[1]));
                item.put("in2", Integer.parseInt(strs[2]));
            }
            //LED灯
            else if(statement.contains("Light.lightUp")) {
                item.put("action", "lightUp");
                String[] strs = statement.split(":");
                item.put("pin", Integer.parseInt(strs[1]));
            }
            else if(statement.contains("Light.lightdown")) {
                item.put("action", "lightDown");
                String[] strs = statement.split(":");
                item.put("pin", Integer.parseInt(strs[1]));
            }
            //电机
            else if (statement.contains("electrical_machinery_1")) {
                item.put("action", "electrical_machinery_1");
                String[] strs = statement.split(":");
                item.put("in", parseInt(strs[1]));
            } else if (statement.contains("electrical_machinery_2")) {
                item.put("action", "electrical_machinery_2");
                String[] strs = statement.split(":");
                item.put("in", parseInt(strs[1]));
            } else if (statement.contains("last_time")) {
                item.put("action", "last_time");
                String[] strs = statement.split(":");
                item.put("in1", parseInt(strs[1]));
                item.put("in2", parseInt(strs[2]));
                i++;
                for(int j=0; j< parseInt(strs[2]); j++) {
                    JSONObject item_temp = new JSONObject();
                    parseStatement(statements, i+j, array);
                }
                array.put(item);
                i++;
                return new Bean(item, i);
            }
            array.put(item);
            i++;
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return new Bean(item, i);
    }


    class Bean {
        JSONObject item;
        int index;

        public Bean(JSONObject item, int index) {
            this.item = item;
            this.index = index;
        }
    }

}
