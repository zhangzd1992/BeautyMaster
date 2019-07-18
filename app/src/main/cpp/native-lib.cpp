#include <jni.h>
#include <string>
#include "android/native_window_jni.h"
#include "opencv2/opencv.hpp"
#include "FaceTrack.h"


using namespace cv;

ANativeWindow *mWindow;









extern "C" JNIEXPORT void JNICALL
Java_com_example_zhangzd_facedetection_OpenCVJni_native_1setSurface(JNIEnv *env, jobject instance,
                                                                    jobject surface) {
    if (mWindow) {
        ANativeWindow_release(mWindow);
        mWindow = 0;
    }

    //创建显示surface的window
    mWindow = ANativeWindow_fromSurface(env, surface);
}





extern "C" JNIEXPORT jlong JNICALL
Java_com_example_zhangzd_beautymaster_OpenCVJni_native_1init(JNIEnv *env, jobject instance,
                                                             jstring path_, jstring seetPath_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    const char *seetPath = env->GetStringUTFChars(seetPath_, 0);

    //初始化检测器和跟踪器
    FaceTrack *faceTrack = new FaceTrack(path);

    env->ReleaseStringUTFChars(path_, path);
    env->ReleaseStringUTFChars(seetPath_, seetPath);

    return reinterpret_cast<jlong>(faceTrack);
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_zhangzd_beautymaster_OpenCVJni_native_1start(JNIEnv *env, jobject instance,
                                                              jlong faceTraceIndex) {
    if(faceTraceIndex == 0) {
        return;
    }

   FaceTrack * faceTrack = reinterpret_cast<FaceTrack *>(faceTraceIndex);

   faceTrack->startTracking();


}

extern "C" JNIEXPORT jobject JNICALL
Java_com_example_zhangzd_beautymaster_OpenCVJni_native_1detector(JNIEnv *env, jobject instance,
                                                                 jlong faceTraceIndex,
                                                                 jbyteArray data_, jint cameraId,
                                                                 jint width, jint height) {
    jbyte *data = env->GetByteArrayElements(data_, NULL);

    if (faceTraceIndex == 0)
        return NULL;


    FaceTrack * faceTrack = reinterpret_cast<FaceTrack *>(faceTraceIndex);

    // 将一帧数据转换成mat
    Mat src(height + height / 2, width, CV_8UC1, data);
    cvtColor(src, src, COLOR_YUV2RGBA_NV21);
    //旋转图像的角度
    if (cameraId == 1) {
        //前置摄像头，逆时针旋转90度
        rotate(src, src, ROTATE_90_COUNTERCLOCKWISE);
        //水平翻转
        flip(src, src, 1);
    } else {
        //顺时针旋转90度
        rotate(src, src, ROTATE_90_CLOCKWISE);
    }

    // 将图像灰度化
    Mat gray;
    cvtColor(src, gray, COLOR_RGBA2GRAY);
    //增强对比度
    equalizeHist(gray, gray);


    faceTrack->detector(gray);




    src.release();
    gray.release();



    env->ReleaseByteArrayElements(data_, data, 0);
}