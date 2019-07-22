//
// Created by zhangzd on 2019-07-18.
//

#include "FaceTrack.h"
#include "macro.h"

FaceTrack::FaceTrack(const char *path, const char *seeta) {

    //创建跟踪器和检测器
    //创建检测器
    Ptr<CascadeDetectorAdapter> mainDetector = makePtr<CascadeDetectorAdapter>(makePtr<CascadeClassifier>(path));
//创建检测器    classifier RecyclerView    CascadeDetectorAdapter  适配器
    Ptr<CascadeDetectorAdapter> trackingDetector = makePtr<CascadeDetectorAdapter>(makePtr<CascadeClassifier>(path));


    DetectionBasedTracker::Parameters DetectorParams;
//    tracker  含有两个对象 检测器 跟踪器
    tracker = new DetectionBasedTracker(mainDetector, trackingDetector, DetectorParams);
//    tracker->run();

    faceAlignment = makePtr<seeta::FaceAlignment>(seeta);

}

//开始跟踪监测人脸
void FaceTrack::startTracking() {
    tracker->run();
}


//人脸监测
vector<Rect2f> FaceTrack::detector(Mat src) {
    vector<Rect> faces;
    vector<Rect2f> rects;
//    开始检测
    tracker->process(src);
//    获取结果
    tracker->getObjects(faces);
    //关键点定位
    //保存5个关键点的坐标
    // 0:左眼  1:右眼  2:鼻头  3:嘴巴左  4:嘴巴右
    seeta::FacialLandmark points[5];
    if (faces.size()) {
//        遍历多个人脸  对每一个人眼进行定位   放大
        Rect face = faces[0];
//        人脸的区域
        rects.push_back(Rect2f(face.x, face.y, face.width, face.height));

        //图像数据
        seeta::ImageData image_data(src.cols,src.rows);
        image_data.data = src.data;
        //指定人脸部位
        seeta::FaceInfo faceInfo;
        seeta::Rect bbox;
        bbox.x = face.x;
        bbox.y  = face.y;
        bbox.width = face.width;
        bbox.height = face.height;
        faceInfo.bbox = bbox;
        faceAlignment->PointDetectLandmarks(image_data, faceInfo, points);

        for (int i = 0; i < 5; ++i) {
            //把点放入返回的集合
            rects.push_back(Rect2f(points[i].x,points[i].y,0,0));
            if (i == 0) {
                LOGE("左眼坐标  x  %ld   y  %ld", points[0].x,points[0].y);
            }
            if (i == 1) {
                LOGE("右眼坐标  x  %ld  y  %ld", points[1].x,points[1].y);
            }
        }
    }



    return rects;
}
