package com.example.zhangzd.beautymaster.filter;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.example.zhangzd.beautymaster.R;
import com.example.zhangzd.beautymaster.face.Face;
import com.example.zhangzd.beautymaster.util.OpenGLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


/**
 * @Description:
 * @Author: zhangzd
 * @CreateDate: 2019-07-12 13:59
 */
public class BigEyeFilter extends AbstrictFragFilter {
    private Face mFace;
    private int left_eye;
    private int right_eye;
    private FloatBuffer left;
    private FloatBuffer right;

    public BigEyeFilter(Context context) {
        super(context, R.raw.base_vertex, R.raw.big_eye_frag);
        left_eye = GLES20.glGetUniformLocation(mProgram,"left_eye");
        right_eye = GLES20.glGetUniformLocation(mProgram,"right_eye");

        left = ByteBuffer.allocateDirect(2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        right = ByteBuffer.allocateDirect(2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    @Override
    protected void initCoordinate() {
        mTextureBuffer.clear();
        float[] TEXTURE = {
                0.0f,0.0f,
                1.0f,0.0f,
                0.0f,1.0f,
                1.0f,1.0f
        };
        mTextureBuffer.put(TEXTURE);
    }


    @Override
    public int onDrawFrame(int textureId) {

        if (mFace == null) {
            return textureId;
        }


        GLES20.glViewport(0, 0, mWidth, mHeight);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffer[0]);
        GLES20.glUseProgram(mProgram);

        mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);

        mTextureBuffer.position(0);
        GLES20.glVertexAttribPointer(vCoord, 2, GLES20.GL_FLOAT, false, 0, mTextureBuffer);
        GLES20.glEnableVertexAttribArray(vCoord);

//
        float[] landmarks = mFace.faceRects;
//        左眼  5        10  0 1    2  3   4  5  0   1
        float x = landmarks[2] / mFace.imgWidth;
        float y = landmarks[3] / mFace.imgHeight;
        left.clear();
        left.put(x);
        left.put(y);
        left.position(0);
        GLES20.glUniform2fv(left_eye, 1, left);

        //右眼的x、y
        x = landmarks[4] / mFace.imgWidth;
        y = landmarks[5] / mFace.imgHeight;
        right.clear();
        right.put(x);
        right.put(y);
        right.position(0);
        GLES20.glUniform2fv(right_eye,1,right);


        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        //因为这一层是摄像头后的第一层，所以需要使用扩展的  GL_TEXTURE_EXTERNAL_OES
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glUniform1i(vTexture, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        //返回fbo的纹理id textureId  1  fbo 2
        return mFrameBufferTextures[0];
    }

    public void setFace(Face face) {
        this.mFace = face;
    }
}
