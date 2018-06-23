package uk.co.pretzelstudios.lionhearttask;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import github.nisrulz.screenshott.ScreenShott;

import static uk.co.pretzelstudios.lionhearttask.ScrollingActivity.EXTRA_CREATOR;
import static uk.co.pretzelstudios.lionhearttask.ScrollingActivity.EXTRA_LIKES;
import static uk.co.pretzelstudios.lionhearttask.ScrollingActivity.EXTRA_URL;


public class DetailActivity extends AppCompatActivity {

    private final static String[] requestWritePermission =
            { Manifest.permission.WRITE_EXTERNAL_STORAGE };
    private ImageView imageView1;
    private Bitmap bitmap;
    private RelativeLayout view1;

    //defining my views and files.

    ImageButton capture_screenshot;
    ImageView hotdog;
    ImageView notHotdog;

    MediaPlayer hotdogsound;
    MediaPlayer nothotdogsound;
    MediaPlayer camerasound;

    boolean showHotdog = true;

    public void hotDogClick (View view){

        //animated a fade in for my banners

        //plays sound on button pressed
        hotdogsound.start();


        if (showHotdog) {

            //ensures images are switched

            showHotdog = false;

            hotdog.animate().alpha(1).setDuration(500);
            notHotdog.animate().alpha(0).setDuration(500);

        } else {

            showHotdog = true;

            hotdog.animate().alpha(1).setDuration(500);
            notHotdog.animate().alpha(0).setDuration(500);


        }

    }

    public void notHotdogClick (View view){

        //animated a fade in for my banners

        //plays sound on button pressed

        nothotdogsound.start();

        if (showHotdog) {

            showHotdog = false;

            hotdog.animate().alpha(0).setDuration(500);
            notHotdog.animate().alpha(1).setDuration(500);

        } else {

            showHotdog = true;

            hotdog.animate().alpha(0).setDuration(500);
            notHotdog.animate().alpha(1).setDuration(500);


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //defining my buttons and sounds

        hotdog =  findViewById(R.id.hotdog);
        notHotdog = findViewById(R.id.not_hotdog);

        hotdogsound = MediaPlayer.create(this, R.raw.itworks);
        nothotdogsound = MediaPlayer.create(this, R.raw.itdoesntwork);
        camerasound = MediaPlayer.create(this, R.raw.camera);

        //setting my image from my main activity

        final Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR);
        int likeCount = intent.getIntExtra(EXTRA_LIKES, 0);

        imageView1 = findViewById(R.id.image_view_detail);
        TextView textViewCreator = findViewById(R.id.text_view_creator_detail);
        TextView textViewLikes = findViewById(R.id.text_view_like_detail);

        //using picasso to pull in the image

        Picasso.with(this).load(imageUrl).fit().centerCrop().into(imageView1);
        textViewCreator.setText(creatorName);
        textViewLikes.setText("Likes: " + likeCount);

        //boolean defined for permissions statement

        final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);


        view1 = findViewById(R.id.view1);

        capture_screenshot = findViewById(R.id.capture_screenshot);
        capture_screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //taking the screenshot and defining this from the view ID.

                camerasound.start();
                // Take screen shot
                bitmap = ScreenShott.getInstance().takeScreenShotOfView(view1);
                imageView1.setImageBitmap(bitmap);
                Toast.makeText(DetailActivity.this, "Photo taken!", Toast.LENGTH_SHORT).show();
            }
        });

        //created an intent to reload when refresh button pressed.

        ImageButton capture_refresh = findViewById(R.id.capture_refresh);
        capture_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);

            }
        });

        ImageButton capture_save =  findViewById(R.id.capture_save);
        capture_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    if (hasWritePermission) {
                        saveScreenshot();
                    }
                    else {
                        RuntimePermissionUtil.requestPermission(DetailActivity.this, requestWritePermission, 100);
                    }
                }
            }
        });
    }

    private void saveScreenshot() {
        // Save the screenshot

        try {
            File file = ScreenShott.getInstance()
                    .saveScreenshotToPicturesFolder(DetailActivity.this, bitmap, "my_screenshot");

            //intent to pen gallery so that users are able to view the files and edit.
            Intent i = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);

            Toast.makeText(this, "Photo Saved! Edit and Share your image with friends :)", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //checking permissions have been granted and if not a toast will be displayed.

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        switch (requestCode) {
            case 100: {

                RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            saveScreenshot();
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(DetailActivity.this, "Permission Denied! You cannot save image!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
    }
}