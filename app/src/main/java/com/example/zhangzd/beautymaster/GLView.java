package com.example.zhangzd.beautymaster;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class GLView extends GLSurfaceView {
    public GLView(Context context) {
        super(context);
    }

    public GLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setRenderer(new GLRender(this));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
