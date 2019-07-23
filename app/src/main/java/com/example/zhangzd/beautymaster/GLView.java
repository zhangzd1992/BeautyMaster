package com.example.zhangzd.beautymaster;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class GLView extends GLSurfaceView {
    private GLRender mRender;
    public GLView(Context context) {
        super(context);
    }

    public GLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        mRender = new GLRender(this);
        setRenderer(mRender);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public void enableBeauty(boolean isChecked) {
        mRender.enableBeauty(isChecked);
    }
}
