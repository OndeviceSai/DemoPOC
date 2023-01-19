package com.example.demopoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaExtractor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MediaStubActivity";
    private SurfaceHolder mHolder;
    private SurfaceHolder mHolder2;
    Button select;
    String pathToReEncodedFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurfaceView surfaceV = (SurfaceView)findViewById(R.id.surface);
        mHolder = surfaceV.getHolder();
        SurfaceView surfaceV2 = (SurfaceView)findViewById(R.id.surface2);
        mHolder2 = surfaceV2.getHolder();


        select = findViewById(R.id.Selected);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gallery permission.
                if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    selectVideo();

                }
                //when permission not granted.
                else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                }
            }
        });


    }

    private void selectVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("video/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
//        startActivityForResult(Intent.createChooser(intent,"SelectVideo"),100);

//        startActivityForResult(Intent.createChooser(intent, "select video"), 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            selectVideo();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            String filepath=Environment.getExternalStorageDirectory()+ "/storage/emulated/0/0_out.mp4";

            try {
            MediaExtractor mediaExtractor=new MediaExtractor();
            mediaExtractor.setDataSource((filepath));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String pathToReEncodedFile =filepath;
            try {
                pathToReEncodedFile = new String(new VideoResolutionChanger().changeResolution(file));
            } catch (Throwable t) {
                t.getLocalizedMessage();
            }
            Log.d("TAG_S", pathToReEncodedFile + "");


        }

    }
//    private Object setupExtractorForClip(File clip) {
//
//        MediaExtractor extractor = new MediaExtractor();
//        try {
//            extractor.setDataSource(clip.getAbsolutePath());
//        } catch ( IOException e ) {
//            e.printStackTrace();
//            return clip;
//        }
//
//        return extractor;
//    }





    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }
    public SurfaceHolder getSurfaceHolder() {
        return mHolder;
    }
    public SurfaceHolder getSurfaceHolder2() {
        return mHolder2;
    }


}