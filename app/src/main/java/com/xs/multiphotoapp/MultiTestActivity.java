package com.xs.multiphotoapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.cy.src.photos.images.SelectImageFragment;
import com.xs.utilsbag.bm.BmScaleUtil;
import com.xs.utilsbag.bm.BmUtil;

import java.io.File;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-05-25 17:24
 * @email Xs.lin@foxmail.com
 */
public class MultiTestActivity extends AppCompatActivity {
    private static final String TAG = "MultiTestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_layout);
        final SelectImageFragment fragment = (SelectImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_select_img);
        fragment.setAllSelectImagesCount(10);

        final ImageView _image = (ImageView) findViewById(R.id.iv_image);


        final TextView mTvDisplay = (TextView) findViewById(R.id.tv_display_imgs_path);
        mTvDisplay.setText("显示图片路径");
        mTvDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = fragment.getSelectImages();
                StringBuffer stringBuffer = new StringBuffer();

                for (String s :
                        list) {
                    s = "\n" + s;
                    stringBuffer.append(s);
                }
                mTvDisplay.setVisibility(View.VISIBLE);
                mTvDisplay.setText("显示图片路径\n"+stringBuffer);

/*here
*
*
*  */
                mTvDisplay.post(new Runnable() {
                    @Override
                    public void run() {
                        String path = fragment.getSelectImages().get(0);
//                Bitmap bitmap = BitmapFactory.decodeFile(path);
                        bitmap = BmScaleUtil.decodeSampledBitmap(path,4);
                        File b = BmScaleUtil.compressImageToFile(bitmap,MultiTestActivity.this);
                        Log.e(TAG, "onClick: "+(b.length() /(float)(1024*1024)));
                    }
                });

//                _image.setImageBitmap(b);
//                int debug = 1;
//                Log.e(TAG, "onClick: "+ BmUtil.calculateBitmapSizeMb(BitmapFactory.decodeFile(path))+"Mb  "+BmUtil.calculateBitmapSizeMb(bitmap) +"Mb  "+BmUtil.calculateBitmapSizeMb(b)+"Mb" );
            }
        });

    }
    private Bitmap bitmap;
    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Multi photo");
    }

    @Override
    protected void onStop() {
        super.onStop();
        BmUtil.bmRecycle(bitmap);
    }
}
