package com.example.zhangzd.beautymaster.util;

import android.content.Context;
import android.opengl.GLES20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Description:
 * @Author: zhangzd
 * @CreateDate: 2019-07-12 11:40
 */
public class OpenGLUtils {
    public static String readRawTextFile(Context context, int rawId) {
        InputStream is = context.getResources().openRawResource(rawId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //创建OpenGl 程序，并绑定顶点着色器代码和片元着色器代码
    public static int loadProgram(String vertexSharder, String fragmentShader) {

        //创建顶点着色器并绑定着色器代码
        int vShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vShader,vertexSharder);
        //编译顶点着色器代码
        GLES20.glCompileShader(vShader);
        int[] status = new int[1];
        GLES20.glGetShaderiv(vShader,GLES20.GL_COMPILE_STATUS,status,0);
        if (status[0] != GLES20.GL_TRUE){
            throw new IllegalStateException("load vertex shader:"+GLES20.glGetShaderInfoLog(vShader));
        }

        //创建片元着色器，并绑定片元着色器代码
        int fShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fShader,fragmentShader);
        //编译片元着色器代码
        GLES20.glCompileShader(fShader);
        int[] compile = new int[1];
        GLES20.glGetShaderiv(fShader,GLES20.GL_COMPILE_STATUS,compile,0);
        if (compile[0] != GLES20.GL_TRUE){
            throw new IllegalStateException("load fragment shader:"+GLES20.glGetShaderInfoLog(fShader));
        }


        //创建OpenGL程序，关联顶点着色器和片元着色器
        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program,vShader);
        GLES20.glAttachShader(program,fShader);
        //链接着色器程序
        GLES20.glLinkProgram(program);

        //获取链接状态
        GLES20.glGetProgramiv(program,GLES20.GL_LINK_STATUS,status,0);
        if (status[0] != GLES20.GL_TRUE) {
            throw new IllegalStateException("link program :"+GLES20.glGetProgramInfoLog(program));
        }

        //绑定成功后删除着色器
        GLES20.glDeleteShader(vShader);
        GLES20.glDeleteShader(fShader);
        return program;
    }

    public static void glGenTextures(int[] textures) {
        GLES20.glGenTextures(textures.length,textures,0);

        for (int i = 0; i < textures.length; i++) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textures[i]);
            //配置纹理
            //
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
            //设置环绕模式
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
        }
    }
}
