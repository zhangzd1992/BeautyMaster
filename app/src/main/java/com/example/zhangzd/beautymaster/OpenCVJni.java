package com.example.zhangzd.beautymaster;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.example.zhangzd.beautymaster.face.Face;
import com.example.zhangzd.beautymaster.util.CameraHelper;


/**
 * @Description:
 * @Author: zhangzd
 * @CreateDate: 2019-07-17 10:12
 */
public class OpenCVJni {
    private  static final int  CHECK_FACE= 1;
    // Used to load the 'native-lib' library on application startup.
    private long faceTraceIndex;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private CameraHelper mCameraHelper;

    static {
        System.loadLibrary("native-lib");
    }

    public OpenCVJni(String path, CameraHelper cameraHelper) {
        mCameraHelper = cameraHelper;
        faceTraceIndex = native_init(path,"");
        mHandlerThread = new HandlerThread("track");
        mHandlerThread.start();

        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.obj != null) {
                    byte[] data = (byte[]) msg.obj;
                    native_detector(faceTraceIndex,data,mCameraHelper.getCameraId(),CameraHelper.WIDTH,CameraHelper.HEIGHT);
                }

            }
        };
    }



    public void startrack() {
        native_start(faceTraceIndex);
    }

    public void detector(byte[] data) {
        //移除之前的消息
        mHandler.removeMessages(CHECK_FACE);
        Message message = mHandler.obtainMessage(CHECK_FACE);
        message.obj = data;
        mHandler.sendMessage(message);
    }

    //初始化
    public native long native_init(String path,String seetPath);
    public native void native_start(long faceTraceIndex);
    private native Face native_detector(long faceTraceIndex, byte[] data, int cameraId, int width, int height);
//    public native void native_setSurface(Surface surface);
//    public native void native_postData(byte[] data, int width, int height, int cameraId);



}
