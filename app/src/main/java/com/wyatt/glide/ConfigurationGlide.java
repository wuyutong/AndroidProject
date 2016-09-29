package com.wyatt.glide;

import android.content.Context;
import android.os.Environment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by wyt on 2016/9/29.
 */

public class ConfigurationGlide implements GlideModule{
    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        MemorySizeCalculator memorySizeCalculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = memorySizeCalculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = memorySizeCalculator.getBitmapPoolSize();
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setBitmapPool(new LruBitmapPool((int) (defaultBitmapPoolSize * 1.2)));
        builder.setMemoryCache(new LruResourceCache((int) (defaultMemoryCacheSize * 1.2)));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 100 * 1024 * 1024));
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, 100 * 1024 * 1024));

        //指定缓存目录1
        String downLoadPath = Environment.getDownloadCacheDirectory().getPath();
        builder.setDiskCache(new DiskLruCacheFactory(downLoadPath, defaultMemoryCacheSize));
        //指定缓存目录2
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                File cacheLocation = new File(context.getExternalCacheDir(), "cache_dir");
                cacheLocation.mkdirs();
                return DiskLruCacheWrapper.get(cacheLocation, 1024 * 1024 * 100);
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
