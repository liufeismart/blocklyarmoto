package com.blockly.android.demo.activity;

import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liufeismart on 2018/3/23.
 */

public class AvoidanceActivity extends AbstractBlocklyActivity {
    private final String TAG = AvoidanceActivity.class.getSimpleName();

    static final List<String> TURTLE_BLOCK_DEFINITIONS = Arrays.asList(
            "turtle/blocks_electrical_machinery.json",
            "turtle/blocks_time.json",
            "turtle/blocks_logic.json",
            "turtle/blocks_avoidance.json",
            "turtle/blocks_loop.json"

    );
    static final List<String> TURTLE_BLOCK_GENERATORS = Arrays.asList(
            "turtle/generators_follow.js"
    );

    private final CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new CodeGenerationRequest.CodeGeneratorCallback() {
                @Override
                public void onFinishCodeGeneration(final String generatedCode) {
                    // Sample callback.
                    Log.i(TAG, "generatedCode:" + generatedCode);

                    try {
                        String[] statements =generatedCode.split("\n");
                        JSONObject root = new JSONObject();
                        JSONArray array = new JSONArray();
                        root.put("array", array);
                        JSONObject item;
                        int index = 0;
                        for(String statement : statements) {
                            item = new JSONObject();
                            if(statement.contains("repeat")) {
                                item.put("action", "repeat");
                                String[] strs = statement.split(":");
                                item.put("in1", Integer.parseInt(strs[1]));
                                item.put("in2", Integer.parseInt(strs[2]));
                            }
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
                            else if(statement.contains("electrical_machinery_1")) {
                                item.put("action", "electrical_machinery_1");
                                String[] strs = statement.split(":");
                                item.put("in", Integer.parseInt(strs[1]));
                            }
                            else if(statement.contains("electrical_machinery_2")) {
                                item.put("action", "electrical_machinery_2");
                                String[] strs = statement.split(":");
                                item.put("in", Integer.parseInt(strs[1]));
                            }
                            else if(statement.contains("delay")) {
                                item.put("action", "delay");
                                String[] strs = statement.split(":");
                                item.put("time", Integer.parseInt(strs[1]));
                            }
                            array.put(item);
                            index++;
                        }
                        Message msg = new Message();
                        msg.what = com.blockly.android.demo.Constants.MSG_DELIVERY;
                        msg.obj = root.toString();
//                        Toast.makeText(ForwardBackActivity.this, "length = "+ root.toString().length(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, root.toString());
                        com.blockly.android.demo.Constants.mClientThread.handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "turtle/toolbox_avoidance.xml";
    }

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        return TURTLE_BLOCK_DEFINITIONS;
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return TURTLE_BLOCK_GENERATORS;
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        return mCodeGeneratorCallback;
    }
    @Override
    protected String getWorkspaceAutosavePath() {
        return "workspace/workspace_avoidance.xml";
    }
}