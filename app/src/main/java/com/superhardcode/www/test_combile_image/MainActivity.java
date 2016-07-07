package com.superhardcode.www.test_combile_image;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "JoinImage";

    private RelativeLayout mainframe;
    private ProgressDialog progress_dialog = null;

    private Bitmap mTopImage, mBackground;
    private BitmapDrawable mBitmapDrawable;

    private Canvas mCanvas;
    private String mSavedImageName = null;
    private FileOutputStream mFileOutputStream = null;

    private String mTempDir;
    public static final int MY_PERMISSIONS_REQUEST = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
        } else {
            createFloder();
        }

        mainframe = (RelativeLayout) findViewById(R.id.mainframe);
        mainframe.setOnClickListener(this);
    }

    private void createFloder() {

        mTempDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_PICTURES;
        File mTempFile = new File(mTempDir, "TestMerge");

        if(!mTempFile.exists()) {
            Log.e("Create Directory", String.valueOf(mTempFile.mkdir()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                createFloder();
                break;
            default:
                break;
        }
    }

    private void mergeImage() {

        File mTempFile = new File(mTempDir, "TestMerge");
        mSavedImageName = mTempFile.getPath() + "/Test.png";

        mTopImage = BitmapFactory.decodeResource(getResources(), R.drawable.mainlayer_ori);
        Bitmap template = BitmapFactory.decodeResource(getResources(), R.drawable.bg_img_ori);

        mBackground = Bitmap.createBitmap(template.getWidth(), template.getHeight(), Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(mBackground);
        mCanvas.drawBitmap(template, 0f, 0f, null);
        mCanvas.drawBitmap(mTopImage, 0f, 0f, null);

        try {
            mBitmapDrawable = new BitmapDrawable(mBackground);
            Bitmap mNewSaving = mBitmapDrawable.getBitmap();
            String FtoSave = mTempDir + mSavedImageName;
            Log.e("ftosave", FtoSave);

            File mFile = new File(FtoSave);
            mFileOutputStream = new FileOutputStream(mFile);
            mNewSaving.compress(Bitmap.CompressFormat.PNG, 95, mFileOutputStream);
            mFileOutputStream.flush();
            mFileOutputStream.close();

        } catch(FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundExceptionError " + e.toString());
        } catch(IOException e) {
            Log.e(TAG, "IOExceptionError " + e.toString());
        }

        if(progress_dialog != null) {
            progress_dialog.dismiss();
            progress_dialog = null;
        }
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.mainframe:
                progress_dialog = ProgressDialog.show(this, "Merging", "Please, wait a minute.");
                mergeImage();
                break;
            default:
                break;
        }
    }
}
