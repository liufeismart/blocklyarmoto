package com.blockly.android.demo.activity;

import android.support.annotation.NonNull;

import com.blockly.util.DefaultCodeGeneratorCallback;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liufeismart on 2018/3/13.
 */

public class ForwardBackActivity extends AbstractBlocklyActivity {

    private final String TAG = ForwardBackActivity.class.getSimpleName();

    static final List<String> TURTLE_BLOCK_DEFINITIONS = Arrays.asList(
            "turtle/blocks_electrical_machinery.json",
            "turtle/blocks_time.json"
    );
    static final List<String> TURTLE_BLOCK_GENERATORS = Arrays.asList(
            "turtle/generators_electrical_machinery.js",
            "turtle/generators_time.js"
    );
    private final DefaultCodeGeneratorCallback mCodeGeneratorCallback = new DefaultCodeGeneratorCallback(TAG);



    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "turtle/toolbox_forward_back.xml";
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
        return "workspace/workspace_forward_back.xml";
    }
}
