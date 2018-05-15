package com.blockly.android.demo.activity;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blockly.util.DefaultCodeGeneratorCallback;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.SoftReference;
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
            "turtle/generators_loop.js",
            "turtle/generators_logic.js",
            "turtle/generators_avoidance.js",
            "turtle/generators_electrical_machinery.js",
            "turtle/generators_time.js"
    );

//    private final DefaultCodeGeneratorCallback mCodeGeneratorCallback = new DefaultCodeGeneratorCallback(TAG, new SoftReference<Context>(this));
    String avoid = "repeat:-1:20\n" +
        "  if:3:8:8\n" +
        "    logic_compare:EQ:1:1\n" +
        "      avoidance_left\n" +
        "      avoidance_result:0\n" +
        "    if:3:2:2\n" +
        "      logic_compare:EQ:1:1\n" +
        "        avoidance_right\n" +
        "        avoidance_result:0\n" +
        "      electrical_machinery_1:-200\n" +
        "      electrical_machinery_2:-200\n" +
        "      electrical_machinery_1:200\n" +
        "      electrical_machinery_2:0\n" +
        "    if:3:2:2\n" +
        "      logic_compare:EQ:1:1\n" +
        "        avoidance_right\n" +
        "        avoidance_result:0\n" +
        "      electrical_machinery_1:0\n" +
        "      electrical_machinery_2:200\n" +
        "      electrical_machinery_1:200\n" +
        "      electrical_machinery_2:200\n";
    private final MyCodeGeneratorCallback mCodeGeneratorCallback = new MyCodeGeneratorCallback(TAG,
            new SoftReference<Context>(this));

    class MyCodeGeneratorCallback extends DefaultCodeGeneratorCallback {
        public MyCodeGeneratorCallback(String tag, SoftReference<Context> contextRef) {
            super(tag, contextRef);
        }
        @Override
        public void onFinishCodeGeneration(String generatedCode) {
//            String str = ss;
            if(avoid.equals(generatedCode)) {
                generatedCode = "avoid_self_1\n";
            }
            super.onFinishCodeGeneration(generatedCode);
        }
    }

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