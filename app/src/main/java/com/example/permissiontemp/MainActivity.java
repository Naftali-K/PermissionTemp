package com.example.permissiontemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * https://youtu.be/y0gX4FD3nxk?si=8xzEKECTR3CdNdjO - Lesson video
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Test_code";
    private static final int PERMISSION_SINGLE_REQ_CODE = 100;
    private static final int PERMISSION_MULTI_REQ_CODE = 101;

    private Button cameraBtn, multiBtn;

    private String[] PERMISSION_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReferences();

        PERMISSION_LIST = new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO
        };

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraPermission();
            }
        });

        multiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                multiPermission();
                multiPermissionNew();
            }
        });
    }

    private void setReferences() {
        cameraBtn = findViewById(R.id.camera_btn);
        multiBtn = findViewById(R.id.multi_btn);
    }

    private void cameraPermission() {
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkPermissions: CAMERA GET permission.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_SINGLE_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermissions: CAMERA Permission already granted.");
            Toast.makeText(getBaseContext(), "Camera has Granted.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean hasPermission(String[] permissions) {
        if (permissions != null) {
            for (String permission: permissions) {
                if (ContextCompat.checkSelfPermission(getBaseContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }

    private void multiPermissionNew() {
        if (!hasPermission(PERMISSION_LIST)) {
            ActivityCompat.requestPermissions(this, PERMISSION_LIST, PERMISSION_MULTI_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_MULTI_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getBaseContext(), "All permissions granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}