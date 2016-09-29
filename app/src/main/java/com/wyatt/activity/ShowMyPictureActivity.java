package com.wyatt.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wyatt.R;

/**
 * Created by wyt on 2016/9/29.
 */

public class ShowMyPictureActivity extends Activity{

    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_picture);
        img = (ImageView) findViewById(R.id.img);
        Glide.with(this).load("http://img2.3lian.com/2014/f6/173/d/51.jpg").asGif().
                placeholder(R.mipmap.aaaaaaaaaaa).error(R.mipmap.img).
                listener(requestListener).dontAnimate().override(200,200).centerCrop().into(img);

    }
    RequestListener<String,GifDrawable> requestListener = new RequestListener<String, GifDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };
}
