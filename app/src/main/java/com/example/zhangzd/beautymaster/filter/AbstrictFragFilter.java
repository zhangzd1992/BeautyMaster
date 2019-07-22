package com.example.zhangzd.beautymaster.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.example.zhangzd.beautymaster.util.OpenGLUtils;
public abstract class AbstrictFragFilter extends AbstrictFilter{
    //    FBO    int 类型
    int[] mFrameBuffer;
    //    fbo  id
    int[] mFrameBufferTextures;
    public AbstrictFragFilter(Context context, int vertexShaderId, int fragmentShaderId) {
        super(context, vertexShaderId,fragmentShaderId);
    }
    public void onReady(int width,int height){
        super.onReady(width, height);
        if (mFrameBuffer != null) {
            destroyFrameBuffers();
        }
        mFrameBuffer = new int[1];
//        生成fbo   ----缓冲区  -----》 整形   纹理 操作
        GLES20.glGenFramebuffers(1, mFrameBuffer, 0);
//实例化一个纹理  目的   纹理 和 FBO进行绑定   纹理 操作
        mFrameBufferTextures = new int[1];
        OpenGLUtils.glGenTextures(mFrameBufferTextures);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFrameBufferTextures[0]);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffer[0]);
//        设置纹理显示纤细  宽度高度
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, mWidth, mHeight, 0,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        //        将纹理 与FBO联系
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D,

                mFrameBufferTextures[0], 0);
//        解绑
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,0);
    }

    private void destroyFrameBuffers() {
        //删除fbo的纹理
        if (mFrameBufferTextures != null) {
            GLES20.glDeleteTextures(1, mFrameBufferTextures, 0);
            mFrameBufferTextures = null;
        }
        //删除fbo
        if (mFrameBuffer != null) {
            GLES20.glDeleteFramebuffers(1, mFrameBuffer, 0);
            mFrameBuffer = null;
        }
    }
}
