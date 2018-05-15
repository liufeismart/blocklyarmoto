package com.blockly.android.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.blockly.android.demo.Constants;
import com.blockly.android.demo.R;

import java.util.HashMap;

import static com.blockly.android.demo.Constants.ACTION_DEVICE_INFO;
import static com.blockly.android.demo.Constants.EXTRA_DEVICE_INFO;

/**
 * Created by liufeismart on 2018/3/15.
 */

public class QRResultActivity extends Activity {

    private String TAG = QRResultActivity.class.getSimpleName();

    private long downTime;
    private float downX, downY;
    private boolean isClick = true;
    private String scan_result;

    private RectF[] rects = new RectF[]{
            new RectF(226f, 183f, 386, 259f),
            new RectF(142f, 275f, 300f, 350f),
            new RectF(57f, 427f, 342f, 512f),
            new RectF(85f, 568f, 328f, 653f),
            new RectF(929f, 178f, 1160f, 258f),
            new RectF(970f, 275f, 1194f, 353f),
            new RectF(950f, 508f, 1170f, 591f),
            new RectF(936f, 602f, 1165f, 683f),
        };
    private String[] hints = {
           "复位键,按下会清空之前汆熟的程序",
            "USB口, 可以通过USB线连接电脑，传输程序",
            "通讯信号指示灯,传输程序时,灯会闪烁",
            "DC电源接口，连接电源为ARDUINO板进行供电",
            "数字输入口,用于传输数字信号",
            "电源指示灯,当ARDUINO板通电后,灯会亮起",
            "单片机芯片, 相当于这个ARDUINO板的大脑，执行程序命令",
            "模拟输入口, 用于传输模拟信号"
    };

    private HashMap<String, Integer> imageMap = new HashMap<String, Integer>();
    private HashMap<String, String> hintMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrresult);
        ImageView iv_image = this.findViewById(R.id.iv_scan_image);
        scan_result = getIntent().getStringExtra(Constants.EXTRA_SCAN_RESULT);
        Log.v(TAG, "scan_result = "+ scan_result);
        imageMap.put("Arduino主板",R.mipmap.bg_mainboard);
        hintMap.put("Arduino主板","Arduino Uno是基于ATmega328P的单片机开发板。它有14个数字输入/输出引脚（其中6个可用作PWM输出），6个模拟输入脚，16 MHz晶振，USB连接，电源插孔，ICSP接头和复位按钮。 ");
        imageMap.put("蓝牙",R.mipmap.bg_bluetooth);
        hintMap.put("蓝牙模块","蓝牙是一种无线技术标准，可实现固定设备、移动设备和楼宇个人域网之间的短距离数据交换");
        imageMap.put("扩展板",R.mipmap.bg_extension);
        hintMap.put("扩展板","Arduino扩展板通常具有和Arduino开发板一样的引脚位置，可以堆叠接插到Arduino上，进而实现特定功能的扩展");
        imageMap.put("车身",R.mipmap.bg_carbody);
        hintMap.put("车身","车身采用塑料材质，用于将开发板和电池以及各类模块连接在一起");
        imageMap.put("红外循迹模块",R.mipmap.bg_tracking);
        hintMap.put("红外循迹模块","红外循迹模块检测原理是红外发射管发射光线到路面，红外光遇到白底则被反射，接收管接收到反射光，经施密特触发器整形后输出低电平；当红外光遇到黑线时则被吸收，接收管没有接收到反射光，经施密特触发器整形后输出高电平");
        imageMap.put("红外避障模块",R.mipmap.bg_avoidance);
        hintMap.put("红外避障模块","红外避障模块检测原理是发射管发射红外线后，遇到障碍物后红外线反射回来被接收管接收，接收管导通，经比较器电路处理后，模块输出低电平，如果红外线没有遇到障碍物就不会被反射回，接收管不导通，经比较器电路处理后，模块输出高电平。");
        imageMap.put("电机",R.mipmap.bg_electric_machine);
        hintMap.put("电机","电机通过调节两端电压，控制旋转方向和转速");
        imageMap.put("车轮",R.mipmap.bg_wheel);
        hintMap.put("车轮","车轮连接电机，电机转动，车轮随之转动");
        imageMap.put("万向轮",R.mipmap.bg_caster);
        hintMap.put("万向轮","万向轮允许水平360度旋转，与两个轮子组成三角结构，保证小车可以正常行驶");
        imageMap.put("超声波探头",R.mipmap.bg_ultrasonic);
        hintMap.put("超声波探头","超声波测距模块一触发信号后发射超声波，当超声波投射到物体而反射回来时，模块输出一回响信号以触发信号和回响信号间的时间差，来判定物体的距离");
        imageMap.put("电机驱动板",R.mipmap.bg_power_source);
        hintMap.put("电机驱动板","电机驱动板将电池电源与电机以及Arduino开发板连接器，使电池成为Arduino开发板提供电源，Arduino开发板控制电机");
        imageMap.put("舵机",R.mipmap.bg_steering);
        hintMap.put("舵机","舵机是一种电机，它使用一个反馈系统来控制电机的位置，可以旋转360度，用于控制超声波探头探测不同方向的障碍的距离");
        imageMap.put("电池",R.mipmap.bg_battery);
        hintMap.put("电池","两节锂电池串联，作为小车的电源为Arduino开发板提供电源");
        iv_image.setImageResource(imageMap.get(scan_result));
        introduceInfo(hintMap.get(scan_result));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        introduceInfo("");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if("".equals(scan_result)) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getRawX();
                    downY = event.getRawY();
                    downTime = System.currentTimeMillis();
                    isClick = true;
                    break;
                case MotionEvent.ACTION_UP:
                    long currTime = System.currentTimeMillis();
                    float upX = event.getRawX();
                    float upY = event.getRawY();
                    Log.v(TAG, "x = " +upX+", y =" + upY);
                    if((currTime-downTime) < 1000 && Math.abs(upX-downX) < 10 && Math.abs(upY-downY) < 10) {
                        int index = 0;
                        for(RectF rect : rects) {
//                        Log.v(TAG, "rect = ("+ rect.top+", " +rect.left +", "+rect.bottom+", "+rect.right);
                            if(rect.contains(upX, upY)) {
                                introduceInfo(hints[index]);
                                break;
                            }
                            index++;
                        }
                    }
                    break;
            }
        }

        return super.onTouchEvent(event);
    }

    private void introduceInfo(String info) {
        Intent intent = new Intent();
        intent.setAction(ACTION_DEVICE_INFO);
        intent.putExtra(EXTRA_DEVICE_INFO, info);
        sendBroadcast(intent);
    }

}
