// 把顶点坐标给这个变量， 确定要画画的形状
attribute vec4 vPosition;
//vPosition 4 顶点
attribute vec2 vCoord;


//传给片元着色器 像素点   0 0    1 0
varying vec2 aCoord;
void main(){

    //内置变量 gl_Position ,我们把顶点数据赋值给这个变量 opengl就知道它要画什么形状了
    gl_Position = vPosition;
    aCoord = vCoord;
}