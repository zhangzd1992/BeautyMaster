//
// Created by zhangzd on 2019-07-18.
//

#ifndef BEAUTYMASTER_FACETRACK_H
#define BEAUTYMASTER_FACETRACK_H

#include "include/opencv2/opencv.hpp"
#include <vector>
#include "face_alignment.h"

using namespace cv;
using namespace std;

class CascadeDetectorAdapter : public DetectionBasedTracker::IDetector {
public:
    CascadeDetectorAdapter(cv::Ptr<cv::CascadeClassifier> detector) :
            IDetector(),
            Detector(detector) {
    }

//检测到人脸  调用  Mat == Bitmap
    void detect(const cv::Mat &Image, std::vector<cv::Rect> &objects) {
        Detector->detectMultiScale(Image, objects, scaleFactor, minNeighbours, 0, minObjSize,
                                   maxObjSize);
    }

    virtual ~CascadeDetectorAdapter() {
    }

private:
    CascadeDetectorAdapter();

    cv::Ptr<cv::CascadeClassifier> Detector;
};



class FaceTrack {


public:
    FaceTrack(const char *path, const char *seeta);


    void startTracking();

    vector<Rect2f> detector(Mat mat);

private:
    DetectionBasedTracker *tracker = 0;
    Ptr<seeta::FaceAlignment> faceAlignment;

};


#endif //BEAUTYMASTER_FACETRACK_H
