package com.example.zhangzd.beautymaster.face;

import android.util.Log;

import java.util.Arrays;

/**
 * @Description:
 * @Author: zhangzd
 * @CreateDate: 2019-07-18 16:22
 */
public class Face {
    // 监测到的人眼坐标数组
    public float[] faceRects;// face  宽度高度     后面5个点的   卓彪
    // 监测到的人脸的宽高
    public int width;
    public int height;
    //原图宽高
    public int imgWidth;
    public int imgHeight;

    protected  Face(int width, int height,int imgWidth,int imgHeight, float[] faceRects) {
        this.width = width;
        this.height = height;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.faceRects = faceRects;
        Log.i("tuch", "Face: "+toString());
    }
    @Override
    public String toString() {
        return "Face{" +
                "eyeRects=" + Arrays.toString(faceRects) +
                ", width=" + width +
                ", height=" + height +
                ", imgWidth=" + imgWidth +
                ", imgHeight=" + imgHeight +
                '}';
    }
}
