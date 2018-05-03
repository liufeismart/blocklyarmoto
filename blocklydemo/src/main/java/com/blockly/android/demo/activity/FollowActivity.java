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
    private final DefaultCodeGeneratorCallback mCodeGeneratorCallback = new DefaultCodeGeneratorCallback(TAG,
            new SoftReference<Context>(this));


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