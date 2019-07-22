package com.example.zhangzd.beautymaster.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.zhangzd.beautymaster.R;
import com.example.zhangzd.beautymaster.face.Face;
import com.example.zhangzd.beautymaster.util.OpenGLUtils;

/**
 * @Description:
 * @Author: zhangzd
 * @CreateDate: 2019-07-22 15:29
 */
public class StickFilter extends AbstrictFragFilter {
    private Bitmap mBitmap;
    private int[] mTextureId;
    private Face mFace;


    public StickFilter(Context context) {
        super(context, R.raw.base_vertex, R.raw.base_frag);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.erduo_000);
    }

    @Override
    protected void initCoordinate() {

    }

    @Override
    public void onReady(int width, int height) {
        super.onReady(width, height);
        mTextureId = new int[1];
        OpenGLUtils.glGenTextures(mTextureId);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTextureId[0]);
        //将bitmap 与GPU中的纹理进行绑定
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,mBitmap,0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
    }




    @Override
    public int onDrawFrame(int textureId) {
        if (mFace == null) {
            return textureId;
        }

        GLES20.glViewport(0,0,mWidth,mHeight);
        //绑定fbo
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,mFrameBuffer[0]);
        GLES20.glUseProgram(mProgram);

        mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(vPosition,2,GLES20.GL_FLOAT,false,0,mVertexBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);
        //激活图层
        GLES20.glActiveTexture(GLES20.GL_TEXTURE);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);
        GLES20.glUniform1i(vTexture,0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,0);

        //                上一个滤镜    绘制屏幕
        //================================================
        onDrawStick();


        return mFrameBufferTextures[0];
    }

    private void onDrawStick() {
        //开启混合模式
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE,GLES20.GL_ONE_MINUS_SRC_ALPHA);
        float[] faceRects = mFace.faceRects;
        float x =  (faceRects[0] / mFace.imgWidth * mWidth);
        float y = (faceRects[1] / mFace.imgHeight * mHeight);

        GLES20.glViewport((int) x,(int)y-mBitmap.getHeight()/2, (int) ((float)mFace.width / mFace.imgWidth * mWidth),mBitmap.getHeight());


        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,mFrameBuffer[0]);
        GLES20.glUseProgram(mProgram);
        mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(vPosition,2,GLES20.GL_FLOAT,false,0,mVertexBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTextureId[0]);

        GLES20.glUniform1i(vTexture, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);

        GLES20.glDisable(GLES20.GL_BLEND);

    }

    public void setFace(Face mFace) {
        this.mFace = mFace;
    }
}
