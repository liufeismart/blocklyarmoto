package com.blockly.android.demo.activity;

import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Created by liufeismart on 2018/3/23.
 */

public class SkillMovesActivity extends AbstractBlocklyActivity {
    private final String TAG = SkillMovesActivity.class.getSimpleName();

    static final List<String> TURTLE_BLOCK_DEFINITIONS = Arrays.asList(
            "turtle/blocks_electrical_machinery.json",
            "turtle/blocks_time.json",
            "turtle/turtle_blocks.json"
    );
    static final List<String> TURTLE_BLOCK_GENERATORS = Arrays.asList(
            "turtle/generators_skillmoves.js"
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

                        for(int i=0; i< statements.length; i++) {
                            String statement = statements[i];
                            i = parseStatement(statements, i, array).index;
                        }
                        String msgStr = root.toString();
//                        Message msgStart = new Message();
//                        msgStart.what = com.blockly.android.demo.Constants.MSG_DELIVERY;
//                        msgStart.obj = "start";
//                        com.blockly.android.demo.Constants.mClientThread.handler.sendMessage(msgStart);
//                        int end = 0;
//                        int index = 1;
////
//                        for(int i=0; i< msgStr.length()-1; i=end) {
//                            Message msg = new Message();
//                            msg.what = com.blockly.android.demo.Constants.MSG_DELIVERY;
//                            end = i + 63;
//                            if(end > msgStr.length()-1)  {
//                                end = msgStr.length()-1;
//                            }
//                            msg.obj = msgStr.substring(i, end);
//
////                        Toast.makeText(ForwardBackActivity.this, "length = "+ root.toString().length(), Toast.LENGTH_LONG).show();
//                                    Log.e(TAG, (String)(msg.obj));
//                            Log.e(TAG, "index = " + index);
//                            com.blockly.android.demo.Constants.mClientThread.handler.sendMessageDelayed(msg, index*1000);
//                            index++;
//                        }
                        Message msg = new Message();
                        msg.what = com.blockly.android.demo.Constants.MSG_DELIVERY;
//                        msg.obj = "<{\"array\":[{\"action\":\"electrical_machinery_2\",\"in\":200},{\"action\":\"delay\",\"time\":3},{\"action\":\"electrical_machinery_1\",\"in\":200},{\"action\":\"delay\",\"time\":3}]}>";
                        msg.obj = "("+root.toString()+")";
                        com.blockly.android.demo.Constants.mClientThread.handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

    private Bean parseStatement(String statements[], int i, JSONArray array) {
        JSONObject item = new JSONObject();
        String statement = statements[i];
        try {
            if (statement.contains("repeat")) {
                String[] strs = statement.split(":");
                int count = Integer.parseInt(strs[1]);
                int stCount = Integer.parseInt(strs[2]);
                List<JSONObject> items = new ArrayList<>();
                for (int stCountIndex = 0; stCountIndex < stCount; stCountIndex++) {
                    item = new JSONObject();
                    i++;
                    Bean bean = parseStatement(statements, i, array);
                    items.add(bean.item);
                }
                for (int countIndex = 0; countIndex < count; countIndex++) {
                    for (JSONObject item2 : items) {
                        array.put(item2);
                    }
                }
            } else if (statement.contains("electrical_machinery_1")) {
                item.put("action", "electrical_machinery_1");
                String[] strs = statement.split(":");
                item.put("in", parseInt(strs[1]));
            } else if (statement.contains("electrical_machinery_2")) {
                item.put("action", "electrical_machinery_2");
                String[] strs = statement.split(":");
                item.put("in", parseInt(strs[1]));
            } else if (statement.contains("delay")) {
                item.put("action", "delay");
                String[] strs = statement.split(":");
                item.put("time", parseInt(strs[1]));
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return new Bean(item, i);
    }



    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "turtle/toolbox_skillmoves.xml";
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
        return "workspace/workspace_skillmoves.xml";
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


