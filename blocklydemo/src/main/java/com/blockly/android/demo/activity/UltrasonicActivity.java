package com.blockly.android.demo.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.blockly.util.DefaultCodeGeneratorCallback;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liufeismart on 2018/4/18.
 * 超生波避障
 */

public class UltrasonicActivity extends AbstractBlocklyActivity {

    private final String TAG = SwingAroundActivity.class.getSimpleName();



    static final List<String> TURTLE_BLOCK_DEFINITIONS = Arrays.asList(
            "turtle/blocks_loop.json",
            "turtle/blocks_logic.json",
            "turtle/blocks_steering_engine.json",
            "turtle/blocks_ultrasonic.json",
            "turtle/blocks_electrical_machinery.json",
            "turtle/blocks_time.json",
            "turtle/blocks_variate.json"

    );
    static final List<String> TURTLE_BLOCK_GENERATORS = Arrays.asList(
            "turtle/generators_loop.js",
            "turtle/generators_logic.js",
            "turtle/generators_steering_engine.js",
            "turtle/generators_ultrasonic.js",
            "turtle/generators_electrical_machinery.js",
            "turtle/generators_time.js",
            "turtle/generators_variate.js"
    );

    String avoid2 = "repeat:-1:48\n" +
            "  steering_engine:90\n" +
            "  variate_set:1:1\n" +
            "    variate_front_distance\n" +
            "    ultrasonic\n" +
            "  steering_engine:180\n" +
            "  variate_set:1:1\n" +
            "    variate_left_distance\n" +
            "    ultrasonic\n" +
            "  steering_engine:0\n" +
            "  variate_set:1:1\n" +
            "    variate_right_distance\n" +
            "    ultrasonic\n" +
            "  if:3:3\n" +
            "    logic_compare:VA:1:1\n" +
            "      variate_front_distance\n" +
            "      ultrasonic_result:15\n" +
            "    last_time:1:2\n" +
            "      electrical_machinery_1:-200\n" +
            "      electrical_machinery_2:-200\n" +
            "  if:3:22:3\n" +
            "    logic_compare:VA:1:1\n" +
            "      variate_front_distance\n" +
            "      ultrasonic_result:30\n" +
            "    if:3:9:9\n" +
            "      logic_compare:GTR:1:1\n" +
            "        variate_left_distance\n" +
            "        variate_right_distance\n" +
            "      if:3:3:2\n" +
            "        logic_compare:VA:1:1\n" +
            "          variate_left_distance\n" +
            "          ultrasonic_result:20\n" +
            "        last_time:1:2\n" +
            "          electrical_machinery_1:-200\n" +
            "          electrical_machinery_2:-200\n" +
            "        last_time:1:1\n" +
            "          electrical_machinery_2:200\n" +
            "      if:3:3:2\n" +
            "        logic_compare:VA:1:1\n" +
            "          variate_right_distance\n" +
            "          ultrasonic_result:20\n" +
            "        last_time:1:2\n" +
            "          electrical_machinery_1:-200\n" +
            "          electrical_machinery_2:-200\n" +
            "        last_time:1:1\n" +
            "          electrical_machinery_1:200\n" +
            "    last_time:1:2\n" +
            "      electrical_machinery_1:200\n" +
            "      electrical_machinery_2:200\n";

    class MyCodeGeneratorCallback extends DefaultCodeGeneratorCallback {
        public MyCodeGeneratorCallback(String tag, SoftReference<Context> contextRef) {
            super(tag, contextRef);
        }
        @Override
        public void onFinishCodeGeneration(String generatedCode) {
//            String str = ss;
            if(avoid2.equals(generatedCode)) {
                generatedCode = "avoid_self_2\n";
            }
            super.onFinishCodeGeneration(generatedCode);
        }
    }
    private final MyCodeGeneratorCallback mCodeGeneratorCallback = new MyCodeGeneratorCallback(TAG,
            new SoftReference<Context>(this));

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "turtle/toolbox_ultrasonic.xml";
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
//        String cmd = "Log.e(\"CodeUtil\",\"usage:java TestRun int i=1; System.out.println(i+100);\");";
//
//        CodeUtil t = new CodeUtil(this.getApplicationContext());
//        t.createJavaFile(cmd);
//        if (t.makeJavaFile() == 0) {
//            t.run();
//        }
//        if (Build.VERSION.SDK_INT >= 23) {
//            int REQUEST_CODE_CONTACT = 101;
//            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            //验证是否许可权限
//            for (String str : permissions) {
//                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                    //申请权限
//                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                    return;
//                }
//            }
//        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

// Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        101);

                // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    @Override
    protected String getWorkspaceAutosavePath() {
        return "workspace/workspace_ultrasonic.xml";
    }




}

