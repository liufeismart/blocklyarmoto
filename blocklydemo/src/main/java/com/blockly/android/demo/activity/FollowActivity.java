package com.blockly.android.demo.activity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.blockly.util.DefaultCodeGeneratorCallback;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liufeismart on 2018/3/23.
 */

public class FollowActivity extends AbstractBlocklyActivity {
    private final String TAG = FollowActivity.class.getSimpleName();

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
//    private final DefaultCodeGeneratorCallback mCodeGeneratorCallback = new DefaultCodeGeneratorCallback(TAG,
//            new SoftReference<Context>(this));

    private final MyCodeGeneratorCallback mCodeGeneratorCallback = new MyCodeGeneratorCallback(TAG,
            new SoftReference<Context>(this));
    String follow = "repeat:-1:20\n" +
            "  if:3:8:8\n" +
            "    logic_compare:EQ:1:1\n" +
            "      avoidance_left\n" +
            "      avoidance_result:0\n" +
            "    if:3:2:2\n" +
            "      logic_compare:EQ:1:1\n" +
            "        avoidance_right\n" +
            "        avoidance_result:0\n" +
            "      electrical_machinery_1:200\n" +
            "      electrical_machinery_2:200\n" +
            "      electrical_machinery_1:0\n" +
            "      electrical_machinery_2:200\n" +
            "    if:3:2:2\n" +
            "      logic_compare:EQ:1:1\n" +
            "        avoidance_right\n" +
            "        avoidance_result:0\n" +
            "      electrical_machinery_1:200\n" +
            "      electrical_machinery_2:0\n" +
            "      electrical_machinery_1:-200\n" +
            "      electrical_machinery_2:-200\n";
    class MyCodeGeneratorCallback extends DefaultCodeGeneratorCallback {
        public MyCodeGeneratorCallback(String tag, SoftReference<Context> contextRef) {
            super(tag, contextRef);
        }
        @Override
        public void onFinishCodeGeneration(String generatedCode) {
//            String str = ss;
            if(follow.equals(generatedCode)) {
                generatedCode = "follow_self\n";
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
        return "workspace/workspace_follow.xml";
    }
}