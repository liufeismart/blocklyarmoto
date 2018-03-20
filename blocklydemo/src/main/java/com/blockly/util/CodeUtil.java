package com.blockly.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liufeismart on 18/2/2.
 */

public class CodeUtil {

    private String fileName = Environment.getDataDirectory().getAbsolutePath()+"/Test.java";
    private String className= Environment.getDataDirectory().getAbsolutePath()+"/Test.class";
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CodeUtil(Context context) {
        fileName = context.getDataDir().getAbsolutePath()+"/Test.java";
        className = context.getDataDir().getAbsolutePath()+"/Test.class";
        File f = new File(fileName);
        if(f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        f = new File(className);
        if(f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建java文件
     */
    public void createJavaFile(String body) {
        String head = "public class Test{/r/n  public static void runCode(){";
        String end = "/r/n  }/r/n}";
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(
                    fileName));
            dos.writeBytes(head);
            dos.writeBytes(body);
            dos.writeBytes(end);
            dos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 编译
     */
    public int makeJavaFile() {
        int ret = 0;
        try {
            Runtime rt = Runtime.getRuntime();
            Process ps = rt.exec("cmd /c javac " + fileName);
            ps.waitFor();
            byte[] out = new byte[1024];
            DataInputStream dos = new DataInputStream(ps.getInputStream());
            dos.read(out);
            String s = new String(out);
            Log.e("CodeUtil", "makeJavaFile: "+ s);
            if (s.indexOf("Exception") > 0) {
                ret = -1;
            }
        }
        catch (Exception e) {
            ret = -1;
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 反射执行
     */
    public void run() {
        try {
            Log.e("CodeUtil", className + "是否存在?" + new File(className).exists());
            Class.forName("Test").getMethod("runCode", new Class[] {}).invoke(null, new Object[]{});
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
