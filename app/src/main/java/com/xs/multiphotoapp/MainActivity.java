package com.xs.multiphotoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvHead;
    private TextView mTvDisplayPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvHead = (ImageView) findViewById(R.id.iv_head);
        mTvDisplayPath = (TextView) findViewById(R.id.tv_display_path);
        final Button mBtnSelectSingle = (Button) findViewById(R.id.btn_select_single);
        mBtnSelectSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        mTvDisplayPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = mOutPutUri.getPath();
                File uploadFile = new File(path);
                mTvDisplayPath.setText(path);
            }
        });

        final Button mBtnMulti = (Button) findViewById(R.id.btn_select_multi);
        mBtnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MultiTestActivity.class);
                startActivity(intent);
            }
        });

        final Button mBtnGvShow = (Button) findViewById(R.id.gv_show);
        mBtnGvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GridViewTestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Single Photo");
    }

    private Uri mOutPutUri;
    public static final int REQUEST_SELECT_PHOTO_CODE = 0x1;
    public static final int REQUEST_CORP_PHOTO_CODE = 0x2;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult", "resultCode =" + resultCode);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_SELECT_PHOTO_CODE:

                ArrayList<String> arrayList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String path = arrayList.get(0);
                File file = new File(path);
                Uri fileUri = Uri.fromFile(file);
                File tempFile = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
                if (tempFile.exists()) {
                    tempFile.delete();
                }
                mOutPutUri = Uri.fromFile(tempFile);
                cropImage(fileUri, mOutPutUri);
                break;
            case REQUEST_CORP_PHOTO_CODE:
                Log.e("REQUEST_CORP_PHOTO_CODE", "data=" + data + "  " + mOutPutUri);
//                mHead.setImageURI(null);
//                mHead.setImageURI(mOutPutUri);
                showHead(mOutPutUri);
                mTvDisplayPath.setVisibility(View.VISIBLE);
                mTvDisplayPath.setText("显示路径");
//                Uri uri = data.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
                break;
        }

    }

    private void showHead(Uri uri) {

        Glide.with(getApplicationContext()).load(uri).asBitmap().thumbnail(0.6f).centerCrop().placeholder(R.mipmap.ic_default_head).into(new BitmapImageViewTarget(mIvHead) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                roundedBitmapDrawable.setCircular(true);
                getView().setImageDrawable(roundedBitmapDrawable);
            }
        });

    }


    /**
     * 裁剪图片
     *
     * @param fileUri
     * @param outPutUri
     */
    private void cropImage(Uri fileUri, Uri outPutUri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fileUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", true);
        intent.putExtra("aspectX", 0.8f);
        intent.putExtra("aspectY", 0.8f);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        startActivityForResult(intent, REQUEST_CORP_PHOTO_CODE);
    }

    /**
     * 选择图片
     */
    private void selectImage() {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        startActivityForResult(intent, REQUEST_SELECT_PHOTO_CODE);
    }

}
