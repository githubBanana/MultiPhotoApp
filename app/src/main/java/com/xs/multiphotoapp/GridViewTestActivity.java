package com.xs.multiphotoapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.cy.src.photos.images.ImagesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-05-26 11:08
 * @email Xs.lin@foxmail.com
 */
public class GridViewTestActivity extends AppCompatActivity {
    private static final String TAG = "GridViewTestActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_layout);

        final DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        final GridView mGridView = (GridView) findViewById(R.id.gv_images);

        String images[] = {"http://weidongzn.com//Files/upload/201605/25/i20160525142556561.jpg",
        /*"http://weidongzn.com//Files/upload/201605/25/i20160525142556659.jpg",
        "http://weidongzn.com//Files/upload/201605/25/i20160525142558560.jpg",
        "http://weidongzn.com//Files/upload/201605/25/i20160525142600386.jpg",*/
        "http://weidongzn.com//Files/upload/201605/16/i20160516112217007.jpg"};
        if (images.length > 0) {
            ViewGroup.LayoutParams params = mGridView.getLayoutParams();

            if (images.length == 1) {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.width = mDisplayMetrics.widthPixels * 2 / 3;
                mGridView.setNumColumns(1);
            } else if (images.length == 2) {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                mGridView.setNumColumns(2);
            }
            else {
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                mGridView.setNumColumns(3);
            }

            mGridView.setLayoutParams(params);

            mGridView.setVisibility(View.VISIBLE);
            ImagesAdapter imagesAdapter = new ImagesAdapter(this);
            mGridView.setAdapter(imagesAdapter);
            for (String image : images) {
                imagesAdapter.addListData(Uri.parse(image), false);
            }
            imagesAdapter.notifyDataSetChanged();
        } else {

            mGridView.setVisibility(View.GONE);
            mGridView.setAdapter(null);
        }
    }
}

