package com.blockly.android.demo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockly.android.demo.R;

import java.util.ArrayList;
import java.util.List;

import static com.blockly.android.demo.Constants.ACTION_DEVICE_INFO;
import static com.blockly.android.demo.Constants.EXTRA_DEVICE_INFO;
import static com.blockly.android.demo.Constants.REQUEST_SCAN;
import static com.blockly.android.demo.Constants.SCAN_RESULT;

/**
 * Created by liufeismart on 18/2/26.
 * 课程列表
 */

public class LessonListActivity extends Activity {
    //RecyclerView
    private RecyclerView rv_lessons;
    private RecyclerView.LayoutManager mLaoyutManager;
    private RecyclerView.Adapter mAdapter;
    private List<String> data_lessons;

    //
    private TextView tv_bluetooth;
    private TextView tv_device_info;

    public static final String TAG = LessonListActivity.class.getSimpleName();

    private String[] lesson_name = {"第一课\n总体介绍",
                        "第二课\n前进后退", "第三课\n左转右转",
                        "第四课\n花式动作", "第五课\n循迹",
                        "第六课\n避障","第七课\n跟随",
                        "第八课\n摇头晃脑"};

    private Class[] jumpActivity = {IntroduceActivity.class, ForwardBackActivity.class, LeftRightActivity.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        initView();
    }

    private void initView() {
        rv_lessons = this.findViewById(R.id.rv_lessons);
        mLaoyutManager = new GridLayoutManager(this, 4);
        data_lessons = new ArrayList<String>();
        for(String name :lesson_name) {
            data_lessons.add(name);
        }
        mAdapter = new LessonAdapter(data_lessons);
        rv_lessons.setAdapter(mAdapter);
        rv_lessons.setLayoutManager(mLaoyutManager);

        //
        tv_bluetooth = this.findViewById(R.id.tv_bluetooth);
        tv_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LessonListActivity.this, BluetoothActivity.class));
            }
        });
        tv_device_info = this.findViewById(R.id.tv_device_info);
        tv_device_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LessonListActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(LessonListActivity.this,new String[]{Manifest.permission.CAMERA},1);
                }else {
                    startActivityForResult(new Intent(LessonListActivity.this, QRScanActivity.class), REQUEST_SCAN);
                }
            }
        });
    }

    class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder>{
        private List<String> data_lessons;
        public LessonAdapter(List<String> data_lessons) {
            this.data_lessons = data_lessons;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv_lesson_name.setText(data_lessons.get(position));
            final int index = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(index<3) {
                        startActivity(new Intent(LessonListActivity.this, jumpActivity[index]));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data_lessons==null ? 0 : data_lessons.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_lesson_name;
            View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_lesson_name = itemView.findViewById(R.id.tv_lesson_name);
                this.itemView = itemView;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "onActivityResult");

        if(resultCode == RESULT_OK && requestCode == REQUEST_SCAN) {
            String result = data.getStringExtra(SCAN_RESULT);
//            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            Log.v(TAG, "result = " + result);
            Intent intent = new Intent();
            intent.setAction(ACTION_DEVICE_INFO);
            intent.putExtra(EXTRA_DEVICE_INFO, result);
            sendBroadcast(intent);
        }
    }
}
