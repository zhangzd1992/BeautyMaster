package com.example.zhangzd.beautymaster;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    private String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GLView glView = findViewById(R.id.surfaceView);
        CheckBox beauty = findViewById(R.id.beauty);
        checkPermissions();

        beauty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                glView.enableBeauty(isChecked);
            }
        });
    }

    private void checkPermissions() {
       if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
       ||ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
           ActivityCompat.requestPermissions(this,permissions,0);
       }else {

       }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 ){
            if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }
}
