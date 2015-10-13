package com.flyeek.dev.demo.ui.widget.flashlightview;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * A SurfaceView must be set to ensure the flashlight functions available.
 * Created by flyeek on 10/9/15.
 */
public class FlashlightView extends SurfaceView implements SurfaceHolder.Callback {

    private Camera mCamera;
    private Camera.Parameters mParameters;
    private SurfaceHolder mSurfaceHolder;

    private Callback mCallback;
    private boolean mIsCameraOpened;
    private boolean mIsSurfaceAvailable;

    private boolean mIsFlashlightOn;

    public FlashlightView(Context context) {
        this(context, null);
    }

    public FlashlightView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);

        mIsCameraOpened = false;
        mIsSurfaceAvailable = false;
        mIsFlashlightOn = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsSurfaceAvailable = true;
        Log.d("fly", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsSurfaceAvailable = false;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public boolean isAvailable() {
        return mIsSurfaceAvailable && initCamera();
    }

    public boolean isFlashlightOn() {
        return mIsFlashlightOn;
    }

    /**
     * When turnning on flashlight, must first call {@link #isAvailable()} and only when return
     * true, falshlight can be turned on.
     * @param isOn
     */
    public void turnFlashlight(boolean isOn) {
        if (mCamera == null) {
            return;
        }

        Camera.Parameters mParameters = mCamera.getParameters();
        if (isOn && !mIsFlashlightOn) {
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParameters);
            mCamera.startPreview();
            mIsFlashlightOn = true;
        } else if (mIsFlashlightOn) {
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mParameters);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            mIsCameraOpened = false;
            mIsFlashlightOn = false;
        }
    }

    public void releaseFlashlight() {
        turnFlashlight(false);

        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private boolean initCamera() {
        if (mIsCameraOpened) {
            return true;
        }

        boolean result = true;
        try {
            mCamera = Camera.open();
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mIsCameraOpened = true;
        } catch (RuntimeException | IOException runtimeException) {
            // cann't access camera or cann't set preview display.
            result = false;
        }

        return result;
    }

    public interface Callback {
        /**
         * This is called immediately after the surface is ready for trunning on flashlight. Any
         * attempt to trun on flashlight before this is not guaranteed to success.
         */
        public void onReady();
    }
}
