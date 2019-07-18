package com.example.zhangzd.beautymaster.face;

import java.util.Arrays;

/**
 * @Description:
 * @Author: zhangzd
 * @CreateDate: 2019-07-18 16:22
 */
public class Face {
    // 监测到的人眼坐标数组
    public float[]  eyeRects;
    // 监测到的人脸的宽高
    public int width;
    public int height;
    //原图宽高
    public int imgWidth;
    public int imgHeight;

    @Override
    public String toString() {
        return "Face{" +
                "eyeRects=" + Arrays.toString(eyeRects) +
                ", width=" + width +
                ", height=" + height +
                ", imgWidth=" + imgWidth +
                ", imgHeight=" + imgHeight +
                '}';
    }
}
