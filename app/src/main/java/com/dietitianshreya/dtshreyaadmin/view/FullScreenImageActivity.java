package com.dietitianshreya.dtshreyaadmin.view;

/**
 * Created by manyamadan on 07/05/18.
 */

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dietitianshreya.dtshreyaadmin.R;


public class FullScreenImageActivity extends AppCompatActivity {

    private TouchImageView mImageView;
    private ImageView ivUser;
    private TextView tvUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        bindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setValues();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.gc();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void bindViews() {
        progressDialog = new ProgressDialog(this);
        mImageView = (TouchImageView) findViewById(R.id.imageView);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setValues() {
        String nameUser, urlPhotoUser, urlPhotoClick;
        nameUser = getIntent().getStringExtra("nameUser");
       // urlPhotoUser = getIntent().getStringExtra("urlPhotoUser");
        urlPhotoClick = getIntent().getStringExtra("urlPhotoClick");
        Log.i("TAG", "imagem recebida " + urlPhotoClick);
       // tvUser.setText(nameUser); // Name
       // Glide.with(this).load(urlPhotoUser).apply(RequestOptions.centerCropTransform().transform(new CircleTransform(this)).override(40, 40)).into(ivUser);

        Glide.with(this).asBitmap().load(urlPhotoClick).apply(RequestOptions.overrideOf(640, 640).fitCenter()).into(new SimpleTarget<Bitmap>() {

            @Override
            public void onLoadStarted(Drawable placeholder) {
                progressDialog.setMessage("Carregando Imagem...");
                progressDialog.show();
            }

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                progressDialog.dismiss();
                mImageView.setImageBitmap(resource);
            }


        });
    }
}