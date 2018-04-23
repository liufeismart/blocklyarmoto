package com.blockly.android.demo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liufeismart on 2018/4/18.
 * 超生波避障
 */

public class UltrasonicActivity extends AbstractBlocklyActivity {

    private final String TAG = SwingAroundActivity.class.getSimpleName();



    static final List<String> TURTLE_BLOCK_DEFINITIONS = Arrays.asList(
            "turtle/blocks_ultrasonic.json",
            "turtle/blocks_time.json",
            "turtle/blocks_steering_engine.json",
            "turtle/blocks_time.json",
            "turtle/blocks_electrical_machinery.json",
            "turtle/blocks_loop.json",
<<<<<<< HEAD
            "turtle/blocks_logic.json"
    );
    static final List<String> TURTLE_BLOCK_GENERATORS = Arrays.asList(
            "turtle/generators_bluetooth.js"
=======
            "turtle/blocks_logic.json",
            "turtle/blocks_variate.json"

    );
    static final List<String> TURTLE_BLOCK_GENERATORS = Arrays.asList(
            "turtle/generators_variate.js",
            "turtle/generators_ultrasonic.js",
            "turtle/generators_time",
            "turtle/generators_time"
>>>>>>> test
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
                            if(statement.contains("Light.lightUp")) {
                                item.put("action", "lightUp");
                                String[] strs = statement.split(":");
                                item.put("pin", Integer.parseInt(strs[1]));
                            }
                            else if(statement.contains("Light.lightdown")) {
                                item.put("action", "lightDown");
                                String[] strs = statement.split(":");
                                item.put("pin", Integer.parseInt(strs[1]));
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
//                        Toast.makeText(LEDActivity.this, "length = "+ root.toString().length(), Toast.LENGTH_LONG).show();
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


<<<<<<< HEAD
=======
    @Override
    protected String getWorkspaceAutosavePath() {
        return "workspace/workspace_ultrasonic.xml";
    }
>>>>>>> test


}

