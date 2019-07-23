////float数据是什么精度的
//precision mediump float;
////采样点的坐标
//varying mediump vec2 aCoord;
//uniform int width;
//uniform int height;
////color 输入
//uniform sampler2D vTexture;
////24个坐标
//vec2 blurCoordinates[20];
//void main(){
//
////    vec2 singleStepOffset=vec2(1.0/float(width),1.0/float(height));
//
////    blurCoordinates[0] = aCoord.xy+ singleStepOffset* vec2(0.0,-2.0);
////    blurCoordinates[1] = aCoord.xy+ singleStepOffset* vec2(1.0,-2.0);
////    blurCoordinates[2] = aCoord.xy+ singleStepOffset* vec2(2.0,-2.0);
////    blurCoordinates[3] = aCoord.xy+ singleStepOffset* vec2(2.0,-1.0);
////    blurCoordinates[4] = aCoord.xy+ singleStepOffset* vec2(2.0,0.0);
////    blurCoordinates[5] = aCoord.xy+ singleStepOffset* vec2(2.0,1.0);
////    blurCoordinates[6] = aCoord.xy+ singleStepOffset* vec2(2.0,2.0);
////    blurCoordinates[7] = aCoord.xy+ singleStepOffset* vec2(1.0,2.0);
////    blurCoordinates[8] = aCoord.xy+ singleStepOffset* vec2(0.0,2.0);
////    blurCoordinates[9] = aCoord.xy+ singleStepOffset* vec2(-1.0,2.0);
////    blurCoordinates[10] = aCoord.xy+ singleStepOffset* vec2(-2.0,2.0);
////    blurCoordinates[11] = aCoord.xy+ singleStepOffset* vec2(-2.0,1.0);
////    blurCoordinates[12] = aCoord.xy+ singleStepOffset* vec2(-2.0,0.0);
////    blurCoordinates[13] = aCoord.xy+ singleStepOffset* vec2(-2.0,-1.0);
////    blurCoordinates[14] = aCoord.xy+ singleStepOffset* vec2(-2.0,-2.0);
////    blurCoordinates[15] = aCoord.xy+ singleStepOffset* vec2(-1.0,-2.0);
////    blurCoordinates[16] = aCoord.xy+ singleStepOffset* vec2(0.0,-1.0);
////    blurCoordinates[17] = aCoord.xy+ singleStepOffset* vec2(1.0,-1.0);
////    blurCoordinates[18] = aCoord.xy+ singleStepOffset* vec2(1.0,0.0);
////    blurCoordinates[19] = aCoord.xy+ singleStepOffset* vec2(1.0,1.0);
////    blurCoordinates[20] = aCoord.xy+ singleStepOffset* vec2(0.0,1.0);
////    blurCoordinates[21] = aCoord.xy+ singleStepOffset* vec2(-1.0,1.0);
////    blurCoordinates[22] = aCoord.xy+ singleStepOffset* vec2(-1.0,0.0);
////    blurCoordinates[23] = aCoord.xy+ singleStepOffset* vec2(-1.0,-1.0);
//
//    vec2 singleStepOffset=vec2(1.0/float(width),1.0/float(height));
//    //    一个点
//    blurCoordinates[0]=aCoord.xy+ singleStepOffset*vec2(0.0,-10.0);
//    blurCoordinates[1] = aCoord.xy + singleStepOffset * vec2(0.0, 10.0);
//    blurCoordinates[2] = aCoord.xy + singleStepOffset * vec2(-10.0, 0.0);
//    blurCoordinates[3] = aCoord.xy + singleStepOffset * vec2(10.0, 0.0);
//    blurCoordinates[4] = aCoord.xy + singleStepOffset * vec2(5.0, -8.0);
//    blurCoordinates[5] = aCoord.xy + singleStepOffset * vec2(5.0, 8.0);
//    blurCoordinates[6] = aCoord.xy + singleStepOffset * vec2(-5.0, 8.0);
//    blurCoordinates[7] = aCoord.xy + singleStepOffset * vec2(-5.0, -8.0);
//    blurCoordinates[8] = aCoord.xy + singleStepOffset * vec2(8.0, -5.0);
//    blurCoordinates[9] = aCoord.xy + singleStepOffset * vec2(8.0, 5.0);
//    blurCoordinates[10] = aCoord.xy + singleStepOffset * vec2(-8.0, 5.0);
//    blurCoordinates[11] = aCoord.xy + singleStepOffset * vec2(-8.0, -5.0);
//    blurCoordinates[12] = aCoord.xy + singleStepOffset * vec2(0.0, -6.0);
//    blurCoordinates[13] = aCoord.xy + singleStepOffset * vec2(0.0, 6.0);
//    blurCoordinates[14] = aCoord.xy + singleStepOffset * vec2(6.0, 0.0);
//    blurCoordinates[15] = aCoord.xy + singleStepOffset * vec2(-6.0, 0.0);
//    blurCoordinates[16] = aCoord.xy + singleStepOffset * vec2(-4.0, -4.0);
//    blurCoordinates[17] = aCoord.xy + singleStepOffset * vec2(-4.0, 4.0);
//    blurCoordinates[18] = aCoord.xy + singleStepOffset * vec2(4.0, -4.0);
//    blurCoordinates[19] = aCoord.xy + singleStepOffset * vec2(4.0, 4.0);
//
//
//    vec4 currentColor =  texture2D(vTexture,aCoord);
//    vec3 totalColor = currentColor.rgb;
//    for(int i =0; i < 20; i ++) {
//        totalColor += texture2D(vTexture,blurCoordinates[i].xy).rgb;
//    }
//
//    vec4 blur = vec4(totalColor * 1.0/21.0,currentColor.a);
//
//
//    //实现高反差图
//    vec4 highPassColor = currentColor - blur ;
//    //    原图减去一个模糊图  反向  2.0*highPassColor.r* highPassColor.r   反向
//    highPassColor.r=clamp(2.0*highPassColor.r* highPassColor.r * 24.0, 0.0, 1.0);
//    highPassColor.g = clamp(2.0 * highPassColor.g * highPassColor.g * 24.0, 0.0, 1.0);
//    highPassColor.b = clamp(2.0 * highPassColor.b * highPassColor.b * 24.0, 0.0, 1.0);
//
//    vec3 r = mix(currentColor.rgb,blur.rgb,0.4);
//    //    倒 的   正的
//    //变量 接收像素值
//    // texture2D：采样器 采集 aCoord的像素
//    //赋值给 gl_FragColor 就可以了
//    gl_FragColor=vec4(r,1.0);
//}

precision mediump float;
//当前要采集像素的点
varying mediump vec2 aCoord;
uniform int width;
uniform int height;
//采样器 不是从android的surfaceTexure中的纹理 采数据了，所以不再需要android的扩展纹理采样器了
//使用正常的 sampler2D
uniform sampler2D vTexture;
//20个坐标
vec2 blurCoordinates[20];
void main(){
    vec2 singleStepOffset=vec2(1.0/float(width),1.0/float(height));
    //    一个点
    blurCoordinates[0]=aCoord.xy+ singleStepOffset*vec2(0.0,-10.0);
    blurCoordinates[1] = aCoord.xy + singleStepOffset * vec2(0.0, 10.0);
    blurCoordinates[2] = aCoord.xy + singleStepOffset * vec2(-10.0, 0.0);
    blurCoordinates[3] = aCoord.xy + singleStepOffset * vec2(10.0, 0.0);
    blurCoordinates[4] = aCoord.xy + singleStepOffset * vec2(5.0, -8.0);
    blurCoordinates[5] = aCoord.xy + singleStepOffset * vec2(5.0, 8.0);
    blurCoordinates[6] = aCoord.xy + singleStepOffset * vec2(-5.0, 8.0);
    blurCoordinates[7] = aCoord.xy + singleStepOffset * vec2(-5.0, -8.0);
    blurCoordinates[8] = aCoord.xy + singleStepOffset * vec2(8.0, -5.0);
    blurCoordinates[9] = aCoord.xy + singleStepOffset * vec2(8.0, 5.0);
    blurCoordinates[10] = aCoord.xy + singleStepOffset * vec2(-8.0, 5.0);
    blurCoordinates[11] = aCoord.xy + singleStepOffset * vec2(-8.0, -5.0);
    blurCoordinates[12] = aCoord.xy + singleStepOffset * vec2(0.0, -6.0);
    blurCoordinates[13] = aCoord.xy + singleStepOffset * vec2(0.0, 6.0);
    blurCoordinates[14] = aCoord.xy + singleStepOffset * vec2(6.0, 0.0);
    blurCoordinates[15] = aCoord.xy + singleStepOffset * vec2(-6.0, 0.0);
    blurCoordinates[16] = aCoord.xy + singleStepOffset * vec2(-4.0, -4.0);
    blurCoordinates[17] = aCoord.xy + singleStepOffset * vec2(-4.0, 4.0);
    blurCoordinates[18] = aCoord.xy + singleStepOffset * vec2(4.0, -4.0);
    blurCoordinates[19] = aCoord.xy + singleStepOffset * vec2(4.0, 4.0);

    //    aCoord.xy
    //    者获取中心点的 color值
    vec4 currentColor=texture2D(vTexture,aCoord);
    vec3 totalRGB=currentColor.rgb;

    for(int i=0;i<20;i++)
    {
        totalRGB += texture2D(vTexture,blurCoordinates[i].xy).rgb;
    }
    vec4 blur=vec4( totalRGB*1.0/21.0,currentColor.a);



    // blure
    vec4 highPassColor = currentColor - blur ;

    //    原图减去一个模糊图  反向  2.0*highPassColor.r* highPassColor.r   反向
    highPassColor.r=clamp(2.0*highPassColor.r* highPassColor.r * 24.0, 0.0, 1.0);
    highPassColor.g = clamp(2.0 * highPassColor.g * highPassColor.g * 24.0, 0.0, 1.0);
    highPassColor.b = clamp(2.0 * highPassColor.b * highPassColor.b * 24.0, 0.0, 1.0);
    //
    vec3 r = mix(currentColor.rgb,blur.rgb,0.3);
    gl_FragColor=vec4(r,1.0);
    //    高斯模糊
    //变量 接收像素值
    // texture2D：采样器 采集 aCoord的像素
    //赋值给 gl_FragColor 就可以了
    //    gl_FragColor = texture2D(vTexture,aCoord);
}