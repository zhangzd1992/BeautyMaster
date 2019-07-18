//float数据是什么精度的
#extension GL_OES_EGL_image_external : require
precision mediump float;
//采样点的坐标
varying vec2 aCoord;
//color 输入
uniform samplerExternalOES vTexture;
void main(){
    //    倒 的   正的
    //变量 接收像素值
    // texture2D：采样器 采集 aCoord的像素
    //赋值给 gl_FragColor 就可以了
    gl_FragColor =texture2D(vTexture,aCoord);
}