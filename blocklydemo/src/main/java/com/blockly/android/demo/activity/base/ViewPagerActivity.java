package com.blockly.android.demo.activity.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.blockly.android.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufeismart on 2018/3/15.
 */

public abstract class ViewPagerActivity extends BackActivity {

    private ImageButton ib_pre, ib_next;
    private ViewPager vp_image;

    private List<ImageView> images = new ArrayList<>();
    @Override
    protected void onCreateSelf(Bundle savedInstanceState) {
        setContentView(R.layout.activity_structure);
        ib_pre = this.findViewById(R.id.ib_pre);
        ib_next = this.findViewById(R.id.ib_next);
        vp_image = this.findViewById(R.id.vp_image);
        //
        int[] image_ids = getImage();
        for(int i=0; i<image_ids.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setImageResource(image_ids[i]);
            images.add(iv);
        }
        MyPagerAdapter mAdapter = new MyPagerAdapter(images);
        vp_image.setAdapter(mAdapter);
        vp_image.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                setButtonState();
            }
        });
        //
        ib_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currIndex = vp_image.getCurrentItem();
                if(currIndex>=1) {
                    vp_image.setCurrentItem(currIndex-1);
                }
                setButtonState();
            }
        });
        ib_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currIndex = vp_image.getCurrentItem();
                if(currIndex<(images.size()-1)) {
                    vp_image.setCurrentItem(currIndex+1);
                }
                setButtonState();
            }
        });
        ib_pre.setVisibility(View.GONE);
    }

    private void setButtonState() {
        if(vp_image.getCurrentItem() == 0) {
            ib_pre.setVisibility(View.GONE);
            ib_next.setVisibility(View.VISIBLE);
        }
        else if(vp_image.getCurrentItem() == images.size()-1) {
            ib_pre.setVisibility(View.VISIBLE);
            ib_next.setVisibility(View.GONE);
        } else {
            ib_pre.setVisibility(View.VISIBLE);
            ib_next.setVisibility(View.VISIBLE);
        }
    }

    protected abstract int[] getImage();

    class MyPagerAdapter extends PagerAdapter {
        List<ImageView> images;

        public MyPagerAdapter(List<ImageView> images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView iv = images.get(position);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(images.get(position));
        }
    }
}
