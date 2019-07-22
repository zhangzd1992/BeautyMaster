package com.example.zhangzd.beautymaster;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Environment;


import com.example.zhangzd.beautymaster.filter.BigEyeFilter;
import com.example.zhangzd.beautymaster.filter.CameraFilter;
import com.example.zhangzd.beautymaster.filter.ScreenFilter;
import com.example.zhangzd.beautymaster.filter.StickFilter;
import com.example.zhangzd.beautymaster.util.CameraHelper;
import com.example.zhangzd.beautymaster.util.Utils;

import java.io.File;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRender implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener, Camera.PreviewCallback {
    GLView mView;
    private SurfaceTexture mSurfaceTure;
    private int mTextures[] = new int[1];
    private CameraHelper mCameraHelper;
    private float[] mtx = new float[16];
    private CameraFilter mCameraFilter;
    private ScreenFilter mScreenFilter;
    private BigEyeFilter mBigEyeFilter;
    private StickFilter mStickFilter;
    private OpenCVJni openCVJni;
    private int mWidth;
    private int mHeight;

    File lbpcascade_frontalface = new File(Environment.getExternalStorageDirectory(), "lbpcascade_frontalface.xml");
    File seeta_fa = new File(Environment.getExternalStorageDirectory(), "seeta_fa_v1.1.bin");

    GLRender(GLView glView) {
        this.mView = glView;
        init();
    }

    private void init() {
        Utils.copyAssets2SdCard(mView.getContext(), "lbpcascade_frontalface.xml", lbpcascade_frontalface.getAbsolutePath());
        Utils.copyAssets2SdCard(mView.getContext(), "seeta_fa_v1.1.bin", seeta_fa.getAbsolutePath());
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCameraHelper = new CameraHelper(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mTextures = new int[1];
        //创建OpenGL纹理，并获取纹理ID
        GLES20.glGenTextures(mTextures.length, mTextures, 0);

        //通过OpenGL纹理创建SurfaceTexture
        mSurfaceTure = new SurfaceTexture(mTextures[0]);
        mSurfaceTure.setOnFrameAvailableListener(this);
        mSurfaceTure.getTransformMatrix(mtx);
        mCameraFilter = new CameraFilter(mView.getContext());
        mBigEyeFilter = new BigEyeFilter(mView.getContext());
        mStickFilter = new StickFilter(mView.getContext());
        mScreenFilter = new ScreenFilter(mView.getContext());
        mCameraFilter.setMatrix(mtx);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        mWidth = width;
        mHeight = height;
        openCVJni = new OpenCVJni(lbpcascade_frontalface.getAbsolutePath(), seeta_fa.getAbsolutePath(), mCameraHelper);
        mCameraHelper.startPreview(mSurfaceTure);
        mCameraHelper.setPreviewCallback(this);
        mCameraFilter.onReady(width, height);
        mBigEyeFilter.onReady(width, height);
        mStickFilter.onReady(width, height);
        mScreenFilter.onReady(width, height);
        openCVJni.startrack();
    }


    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        //当从流中获取到一帧数据时，就会回调该方法
        //手动请求GLSurfaceView 进行渲染，会调用onDrawFrame()
        mView.requestRender();
    }


    @Override
    public void onDrawFrame(GL10 gl) {

        //
        GLES20.glClearColor(0, 0, 0, 0);
        //开始清理工作
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //调用updateTexImage，引起onFrameAvailable回调
        mSurfaceTure.updateTexImage();
        mSurfaceTure.getTransformMatrix(mtx);
        mCameraFilter.setMatrix(mtx);

        int textureId = mCameraFilter.onDrawFrame(mTextures[0]);
        mBigEyeFilter.setFace(openCVJni.getmFace());
        textureId = mBigEyeFilter.onDrawFrame(textureId);

        mStickFilter.setFace(openCVJni.getmFace());
        textureId = mStickFilter.onDrawFrame(textureId);
        //讲最终的特效显示在surfaceview
        mScreenFilter.onDrawFrame(textureId);
    }


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (openCVJni != null)
            openCVJni.detector(data);
    }
}
