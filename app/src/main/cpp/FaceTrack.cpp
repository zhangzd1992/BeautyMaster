//
// Created by zhangzd on 2019-07-18.
//

#include "FaceTrack.h"

FaceTrack::FaceTrack(const char *path) {

    //创建跟踪器和检测器

    //创建
    Ptr<CascadeClassifier> classifier = makePtr<CascadeClassifier>(path);

    //创建检测器
    Ptr<CascadeDetectorAdapter> mainDetector = makePtr<CascadeDetectorAdapter>(classifier);


    //    跟踪器
    //智能指针
    Ptr<CascadeClassifier> classifier1 = makePtr<CascadeClassifier>(path);

//创建检测器    classifier RecyclerView    CascadeDetectorAdapter  适配器
    Ptr<CascadeDetectorAdapter> trackingDetector = makePtr<CascadeDetectorAdapter>(classifier1);


    DetectionBasedTracker::Parameters DetectorParams;
//    tracker  含有两个对象 检测器 跟踪器
    tracker = new DetectionBasedTracker(mainDetector, trackingDetector, DetectorParams);
//    tracker->run();

}

//开始跟踪监测人脸
void FaceTrack::startTracking() {
    tracker->run();
}

//人脸监测
void FaceTrack::detector(Mat src) {
    tracker->process(src);
    vector<Rect> faces;
    tracker->getObjects(faces);

}
