package com.blockly.android.demo.activity;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.blockly.android.demo.R;

/**
 * Created by liufeismart on 2018/3/14.
 */

public class VideoAssembleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        VideoView vv_video = (VideoView) this.findViewById(R.id.vv_video);
        MediaController controller = new MediaController(this);
        vv_video.setMediaController(controller);
        vv_video.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.movie));
        vv_video.start();
    }
}
