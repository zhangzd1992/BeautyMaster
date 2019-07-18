// 把顶点坐标给这个变量， 确定要画画的形状
attribute vec4 vPosition;
//vPosition 4 顶点
//接收纹理坐标，接收采样器采样图片的坐标
attribute vec4 vCoord;
//摄像头的矩阵
uniform mat4 vMatrix;
//  x   y   z   向量
// x  y
//传给片元着色器 像素点   0 0    1 0
varying vec2 aCoord;
void main(){

    //内置变量 gl_Position ,我们把顶点数据赋值给这个变量 opengl就知道它要画什么形状了
    gl_Position = vPosition;
    aCoord = (vMatrix*vCoord).xy;
}