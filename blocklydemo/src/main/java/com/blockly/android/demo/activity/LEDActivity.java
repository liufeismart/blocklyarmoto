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
 * Created by liufeismart on 18/1/19.
 */

public class LEDActivity extends AbstractBlocklyActivity {

    public static final String TAG = "LEDActivity";




    static final List<String> TURTLE_BLOCK_DEFINITIONS = Arrays.asList(
            "turtle/blocks_light.json",
            "turtle/blocks_time.json"
    );
    static final List<String> TURTLE_BLOCK_GENERATORS = Arrays.asList(
            "turtle/generators_led.js",
            "turtle/generators_time.js"
    );

    private final CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback = new DefaultCodeGeneratorCallback(TAG,
            new SoftReference<Context>(this));

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "turtle/toolbox_led.xml";
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
        return "workspace/workspace_led.xml";
    }


}
