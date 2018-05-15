package com.blockly.android.demo.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blockly.util.DefaultCodeGeneratorCallback;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liufeismart on 2018/3/23.
 */

public class TrackingActivity extends AbstractBlocklyActivity {
    private final String TAG = TrackingActivity.class.getSimpleName();

    static final List<String> TURTLE_BLOCK_DEFINITIONS = Arrays.asList(
            "turtle/blocks_loop.json",
            "turtle/blocks_logic.json",
            "turtle/blocks_tracking.json",
            "turtle/blocks_electrical_machinery.json",
            "turtle/blocks_time.json"
    );
    static final List<String> TURTLE_BLOCK_GENERATORS = Arrays.asList(
            "turtle/generators_loop.js",
            "turtle/generators_logic.js",
            "turtle/generators_tracking.js",
            "turtle/generators_electrical_machinery.js",
            "turtle/generators_time.js"
    );
//    private final DefaultCodeGeneratorCallback mCodeGeneratorCallback = new DefaultCodeGeneratorCallback(TAG,
//            new SoftReference<Context>(this));

    String track = "repeat:-1:42\n" +
            "  if:3:18:20\n" +
            "    logic_compare:EQ:1:1\n" +
            "      tracking_left\n" +
            "      tracking_result:0\n" +
            "    if:3:8:6\n" +
            "      logic_compare:EQ:1:1\n" +
            "        tracking_middle\n" +
            "        tracking_result:0\n" +
            "      if:3:2:2\n" +
            "        logic_compare:EQ:1:1\n" +
            "          tracking_right\n" +
            "          tracking_result:0\n" +
            "        electrical_machinery_1:100\n" +
            "        electrical_machinery_2:100\n" +
            "        electrical_machinery_1:50\n" +
            "        electrical_machinery_2:100\n" +
            "      if:3:2\n" +
            "        logic_compare:EQ:1:1\n" +
            "          tracking_right\n" +
            "          tracking_result:1\n" +
            "        electrical_machinery_1:0\n" +
            "        electrical_machinery_2:100\n" +
            "    if:3:8:8\n" +
            "      logic_compare:EQ:1:1\n" +
            "        tracking_middle\n" +
            "        tracking_result:0\n" +
            "      if:3:2:2\n" +
            "        logic_compare:EQ:1:1\n" +
            "          tracking_right\n" +
            "          tracking_result:0\n" +
            "        electrical_machinery_1:100\n" +
            "        electrical_machinery_2:50\n" +
            "        electrical_machinery_1:100\n" +
            "        electrical_machinery_2:100\n" +
            "      if:3:2:2\n" +
            "        logic_compare:EQ:1:1\n" +
            "          tracking_right\n" +
            "          tracking_result:0\n" +
            "        electrical_machinery_1:100\n" +
            "        electrical_machinery_2:0\n" +
            "        electrical_machinery_1:-50\n" +
            "        electrical_machinery_2:-50\n";

    private final MyCodeGeneratorCallback mCodeGeneratorCallback = new MyCodeGeneratorCallback(TAG,
            new SoftReference<Context>(this));

    class MyCodeGeneratorCallback extends DefaultCodeGeneratorCallback {
            public MyCodeGeneratorCallback(String tag, SoftReference<Context> contextRef) {
                super(tag, contextRef);
            }
        @Override
        public void onFinishCodeGeneration(String generatedCode) {
//            String str = ss;
            if(track.equals(generatedCode)) {
                generatedCode = "tracking_self\n";
            }
            super.onFinishCodeGeneration(generatedCode);
        }
    }
    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "turtle/toolbox_tracking.xml";
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
        return "workspace/workspace_tracking.xml";
    }
}

