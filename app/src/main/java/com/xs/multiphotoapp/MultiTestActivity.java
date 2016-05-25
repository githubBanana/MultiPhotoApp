package com.xs.multiphotoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.cy.src.photos.images.SelectImageFragment;
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
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Multi photo");
    }
}
