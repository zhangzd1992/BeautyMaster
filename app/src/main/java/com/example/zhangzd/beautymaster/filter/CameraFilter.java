package com.example.zhangzd.beautymaster.filter;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.example.zhangzd.beautymaster.R;
import com.example.zhangzd.beautymaster.util.OpenGLUtils;


/**
 * @Description:
 * @Author: zhangzd
 * @CreateDate: 2019-07-12 13:59
 */
public class CameraFilter extends AbstrictFilter {
    //    FBO    int 类型
    int[] mFrameBuffer;
    //    纹理
    int[] mFrameBufferTextures;
    float[] mtx;
    public CameraFilter(Context context) {
        super(context, R.raw.camera_vertex, R.raw.camera_frag);
    }

    @Override
    protected void initCoordinate() {
        mTextureBuffer.clear();
//        float[] TEXTURE = {
//                0.0f,0.0f,
//                1.0f,0.0f,
//                0.0f,1.0f,
//                1.0f,1.0f
//        };

        float[] TEXTURE = {
                1.0f,0.0f,
                1.0f,1.0f,
                0.0f,0.0f,
                0.0f,1.0f
        };
        mTextureBuffer.put(TEXTURE);
    }

    public void onReady(int width,int height) {
        super.onReady(width,height);
        mFrameBuffer = new int[1];
        //fbo
        GLES20.glGenFramebuffers(1,mFrameBuffer,0);
        //创建纹理
        mFrameBufferTextures = new int[1];
        OpenGLUtils.glGenTextures(mFrameBufferTextures);

        //讲fbo与纹理进行绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mFrameBufferTextures[0]);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,mFrameBuffer[0]);

        //设置纹理显示信息   显示宽高
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D,0,GLES20.GL_RGBA,
                mWidth,mHeight,0,GLES20.GL_RGBA,GLES20.GL_UNSIGNED_BYTE,null);



        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER,GLES20.GL_COLOR_ATTACHMENT0
                ,GLES20.GL_TEXTURE_2D,mFrameBufferTextures[0],0);


        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,mFrameBuffer[0]);

    }


    @Override
    public int onDrawFrame(int textureId) {
        //设置显示窗口
        GLES20.glViewport(0, 0, mWidth, mHeight);
        //1. 设置不显示在屏幕上
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,mFrameBuffer[0]);
        GLES20.glUseProgram(mProgram);
        mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(vPosition,2, GLES20.GL_FLOAT,false,0,mVertexBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);

        mTextureBuffer.position(0);
        GLES20.glVertexAttribPointer(vCoord,2,GLES20.GL_FLOAT,false,0,mTextureBuffer);
        GLES20.glEnableVertexAttribArray(vCoord);

        //3. 设置屏幕变换矩阵，防止变形
        GLES20.glUniformMatrix4fv(vMatrix,1,false,mtx,0);
        //        不一样的地方
        GLES20.glActiveTexture(GLES20.GL_TEXTURE);
        // 2. 讲gl代码中的 vTexture 与GL中创建的纹理进行绑定
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,textureId);

        GLES20.glUniform1i(vTexture, 0);
//        绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
//        GLES20.glDisableVertexAttribArray(vPosition);
//        GLES20.glDisableVertexAttribArray(vCoord);

        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,0);
        return mFrameBufferTextures[0];
    }

    public void setMatrix(float[] mtx) {
        this.mtx = mtx;
    }
}
