package com.example.permisos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

public class login extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSION=111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        solicitarPermisos();
    }

    private void solicitarPermisos() {
        int permisoStorage = ActivityCompat.checkSelfPermission(login.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permisoCamara = ActivityCompat.checkSelfPermission(login.this, Manifest.permission.CAMERA);

        if(permisoStorage!=PackageManager.PERMISSION_GRANTED || permisoCamara!=PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},REQUEST_CODE_ASK_PERMISSION);
            }
        }
    }
}