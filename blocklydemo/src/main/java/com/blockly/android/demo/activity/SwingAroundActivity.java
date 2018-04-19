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

public class SwingAroundActivity extends AbstractBlocklyActivity {
    private final String TAG = SwingAroundActivity.class.getSimpleName();

    static final List<String> TURTLE_BLOCK_DEFINITIONS = Arrays.asList(
            "turtle/blocks_steering_engine.json",
            "turtle/blocks_time.json"
    );
    static final List<String> TURTLE_BLOCK_GENERATORS = Arrays.asList(
            "turtle/generators_swing_around.js",
            "turtle/generators.js"
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
                            if(statement.contains("steering_engine")) {
                                item.put("action", "steering_engine");
                                String[] strs = statement.split(":");
                                item.put("in", Integer.parseInt(strs[1]));
                            }
                            else if(statement.contains("last_time")) {
                                item.put("action", "last_time");
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
        return "turtle/toolbox_swingaround.xml";
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
        return "workspace/workspace_swingaround.xml";
    }
}
