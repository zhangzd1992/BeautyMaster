package com.example.zhangzd.beautymaster.filter;

import android.content.Context;
import android.opengl.GLES20;


import com.example.zhangzd.beautymaster.util.OpenGLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class AbstrictFilter {
    protected int vertexShaderId;
    protected int fragmentShaderId;
    protected FloatBuffer mVertexBuffer;
    protected FloatBuffer mTextureBuffer;
    protected int mProgram;
    protected int vTexture;
    //    vMatrix  矩阵
    protected int vMatrix;
    protected int vCoord;
    protected int vPosition;
    protected int mWidth;
    protected int mHeight;

    public AbstrictFilter(Context context,int vertexShaderId, int fragmentShaderId) {
        this.vertexShaderId = vertexShaderId;
        this.fragmentShaderId = fragmentShaderId;
        //创建顶点缓冲区 四个顶点 4，每个顶点是二维 2 ，每一维度是一个float 类型 四个字节 4，所以是4 * 2 * 4
        mVertexBuffer = ByteBuffer.allocateDirect(4 * 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.clear();//清空缓冲区

        float[] VERTEX = {
                -1.0f,-1.0f,
                1.0f ,-1.0f,
                -1.0f,1.0f,
                1.0f,1.0f

        };
        mVertexBuffer.put(VERTEX);

        mTextureBuffer = ByteBuffer.allocateDirect(4 * 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTextureBuffer.clear();
        float[] FRAGMENT = {
            0f,0f,
            1f,0f,
            0f,1f,
            1f,1f
//                0.0f, 1.0f,
//                1.0f, 1.0f,
//                0.0f, 0.0f,
//                1.0f, 0.0f
        };
        mTextureBuffer.put(FRAGMENT);
        initialize(context);
        initCoordinate();
    }


    //子类冲洗设置坐标
    protected  abstract  void initCoordinate();

    private void initialize(Context context) {
        String vertexSharder = OpenGLUtils.readRawTextFile(context, vertexShaderId);
        String fragmentShader = OpenGLUtils.readRawTextFile(context, fragmentShaderId);
        //获取GL程序
        mProgram = OpenGLUtils.loadProgram(vertexSharder,fragmentShader);


        vPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");
        vCoord = GLES20.glGetAttribLocation(mProgram,"vCoord");
        vMatrix = GLES20.glGetUniformLocation(mProgram, "vMatrix");
        // 获得Uniform变量的索引值
        vTexture = GLES20.glGetUniformLocation(mProgram, "vTexture");
    }


    //渲染的方法
    public int onDrawFrame(int textureId) {
        //设置显示窗口
        GLES20.glViewport(0, 0, mWidth, mHeight);

        GLES20.glUseProgram(mProgram);
        mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(vPosition,2, GLES20.GL_FLOAT,false,0,mVertexBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);

        mTextureBuffer.position(0);
        GLES20.glVertexAttribPointer(vCoord,2,GLES20.GL_FLOAT,false,0,mTextureBuffer);
        GLES20.glEnableVertexAttribArray(vCoord);



        //        不一样的地方
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // 讲gl代码中的 vTexture 与GL中创建的纹理进行绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);

        GLES20.glUniform1i(vTexture, 0);
//        绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        return textureId;
    }


    public void onReady(int width,int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

}
