
cmake_minimum_required(VERSION 3.4.1)
add_library( # Sets the name of the library.
        native-lib

        SHARED

        src/main/cpp/native-lib.cpp
        src/main/cpp/FaceTrack.cpp)

include_directories(src/main/cpp/include)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}")
find_library( # Sets the name of the path variable.
        log-lib
        log)


target_link_libraries( # Specifies the target library.
        native-lib
        opencv_java4
        android
        ${log-lib})