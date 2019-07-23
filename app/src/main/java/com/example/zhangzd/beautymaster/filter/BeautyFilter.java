package com.example.zhangzd.beautymaster.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.example.zhangzd.beautymaster.R;

/**
 * @Description:
 * @Author: zhangzd
 * @CreateDate: 2019-07-23 09:30
 */
public class BeautyFilter extends AbstrictFragFilter {
    private int width;
    private int height;
    public BeautyFilter(Context context) {
        super(context, R.raw.base_vertex, R.raw.beauty_fragment2);
        width = GLES20.glGetUniformLocation(mProgram,"width");
        height = GLES20.glGetUniformLocation(mProgram,"height");
    }

    @Override
    protected void initCoordinate() {
        float[] TEXTURE = {
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f
        };
//
        mTextureBuffer.clear();
        mTextureBuffer.put(TEXTURE);
        mVertexBuffer.position(0);
    }


    @Override
    public int onDrawFrame(int textureId) {
        GLES20.glViewport(0,0,mWidth,mHeight);
        //使用着色器
        GLES20.glUseProgram(mProgram);


        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,mFrameBuffer[0]);

        GLES20.glVertexAttribPointer(vPosition,2,GLES20.GL_FLOAT,false,0,mVertexBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);


        GLES20.glUniform1i(width, mWidth);
        GLES20.glUniform1i(height, mHeight);


        mTextureBuffer.position(0);
        GLES20.glVertexAttribPointer(vCoord, 2, GLES20.GL_FLOAT, false, 0, mTextureBuffer);
        GLES20.glEnableVertexAttribArray(vCoord);


        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);

        GLES20.glUniform1i(vTexture, 0);
//        绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

//  解绑
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,0);





        return mFrameBufferTextures[0];
    }
}
