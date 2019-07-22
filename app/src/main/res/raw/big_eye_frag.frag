//SurfaceTexture比较特殊
//float数据是什么精度的
precision mediump float;
//采样点的坐标
varying vec2 aCoord;
uniform vec2 left_eye;
uniform vec2 right_eye;
//采样器 不是从android的surfaceTexure中的纹理 采数据了，所以不再需要android的扩展纹理采样器了
//使用正常的 sampler2D
uniform sampler2D vTexture;
//实现 公式 ： 得出需要采集的改变后的点距离眼睛中心点的位置
float fs(float r ,float rmax)
{
    //放大系数
    float a = 0.4;
    return (1.0-(r/rmax-1.0)*(r/rmax-1.0)*a);
}
vec2 newCoord(vec2 coord,vec2 eye,float rmax){
    vec2 p=coord;
    float r= distance(coord,eye);
    if(r<rmax)
    {
        //        改变顶点位置
        //想要方法需要采集的点 与 眼睛中心点的距离

        float fsr=fs(r,rmax);
        //        高中数学   (x-eye) =fsr/r*(coord-eye);
        // //(x  ) = fsr/r * (coord-eye)+eye  矩阵   x  y
        p = fsr * (coord - eye) +eye;

    }
    return p;
}
void main(){
    //    函数名    变量
    //     openghl  不通过  newCoord
    float rmax=distance(left_eye,right_eye)/2.0;
    vec2 p= newCoord(aCoord,left_eye,rmax);
    p= newCoord(p,right_eye,rmax);
    //变量 接收像素值
    // texture2D：采样器 采集 aCoord的像素
    //赋值给 gl_FragColor 就可以了
    gl_FragColor = texture2D(vTexture,p);
}
