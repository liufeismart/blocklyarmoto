package com.blockly.android.demo.activity;

import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blockly.util.DefaultCodeGeneratorCallback;
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
    private final DefaultCodeGeneratorCallback mCodeGeneratorCallback = new DefaultCodeGeneratorCallback(TAG);



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
