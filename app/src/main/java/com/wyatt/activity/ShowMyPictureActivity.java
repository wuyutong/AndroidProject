package com.wyatt.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.wyatt.R;
import com.wyatt.glide.GlideRotateTransform;
import com.wyatt.glide.GlideRoundTransform;

/**
 * Created by wyt on 2016/9/29.
 */

public class ShowMyPictureActivity extends Activity{

    private ImageView img;
    private TextView tvGlide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_picture);
        img = (ImageView) findViewById(R.id.img);
        tvGlide = (TextView) findViewById(R.id.tvGlide);
        //java文件设置动画
        ViewPropertyAnimation.Animator animator=new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha(0f);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
                fadeAnim.setDuration( 2500 );
                fadeAnim.start();
            }
        };
        Glide.with(this).load("http://img2.3lian.com/2014/f6/173/d/51.jpg").
                animate(animator).
                placeholder(R.mipmap.aaaaaaaaaaa).transform(new GlideRoundTransform(this,50),new GlideRotateTransform(this,50)).error(R.mipmap.img).into(img);
        Glide.with(this).load("http://img1.3lian.com/2015/w4/17/d/64.gif").error(R.mipmap.aaaaaaaaaaa).into(simpleTarget);
        notificationTarget();

    }

    SimpleTarget simpleTarget = new SimpleTarget<Drawable>() {
        @Override
        public void onResourceReady(Drawable resource, GlideAnimation<? super Drawable> glideAnimation) {
            tvGlide.setBackground(resource);
        }
    };


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

    /**
     * 设置通知栏网络图标
     */
    private void notificationTarget() {
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.notifition);
        remoteViews.setImageViewResource(R.id.notification_icon, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notification_text, "HeadLine");

        //build notifition
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Content Title")
                .setContentText("Content Text")
                .setContent(remoteViews)
                .setPriority(NotificationCompat.PRIORITY_MIN);
        final Notification notification=builder.build();
        if (Build.VERSION.SDK_INT>=16){
            notification.bigContentView=remoteViews;
        }
        NotificationManager notificationManager=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);

        NotificationTarget notificationTarget=new NotificationTarget(this,remoteViews,R.id.notification_icon,notification,1);
        Glide.with(this).load("http://img2.3lian.com/2014/f6/173/d/51.jpg").asBitmap().
                placeholder(R.mipmap.ic_launcher).
                error(R.mipmap.aaaaaaaaaaa).into(notificationTarget);

    }
}
