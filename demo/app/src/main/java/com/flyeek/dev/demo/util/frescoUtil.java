package com.flyeek.dev.demo.util;

import android.app.Application;
import android.content.Context;

import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.flyeek.dev.demo.constant.Config;

import java.io.File;

/**
 * Created by flyeek on 11/1/15.
 */
public class FrescoUtil {

    /**
     * Must be called within {@link Application#onCreate()}.
     * @param context
     */
    public static void initialize(Context context) {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(getMainDiskCacheConfig(context))
                .build();

        Fresco.initialize(context, config);
    }

    private static DiskCacheConfig getMainDiskCacheConfig(final Context context) {
        String baseCacheLocation = DeviceUtil.getBaseFilePath(context);
        File cacheDir = new File(baseCacheLocation + File.separator + Config.IMAGE_CACHE_DIR_NAME);
        return DiskCacheConfig.newBuilder()
                .setBaseDirectoryPath(cacheDir)
                .setBaseDirectoryName("images")
                .setMaxCacheSize(50 * ByteConstants.MB)
                .setMaxCacheSizeOnLowDiskSpace(15 * ByteConstants.MB)
                .setMaxCacheSizeOnVeryLowDiskSpace(5 * ByteConstants.MB)
                .setCacheErrorLogger(new CacheErrorLogger() {
                    @Override
                    public void logError(CacheErrorCategory cacheErrorCategory, Class<?> clazz, String message, Throwable throwable) {
                    }
                })
                .build();
    }
}
