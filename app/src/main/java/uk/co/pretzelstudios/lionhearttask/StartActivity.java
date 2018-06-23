package uk.co.pretzelstudios.lionhearttask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

//created seperate config file so this can be accessed easily if needed to change later.

public class StartActivity extends YouTubeBaseActivity {

    private static final String TAG = "StartActivity";

    //referencing my buttons and player

    YouTubePlayerView mYoutubePlayerView;
    Button btnPlay;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.d(TAG, "onCreate: Starting");

        //defining my button and player

        btnPlay = (Button) findViewById(R.id.btnPlay);
        mYoutubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlay);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onClick: Done Initializing");
                youTubePlayer.loadVideo("ACmydtFDTGs");

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onClick: Failed to Initialize");
            }
        };

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Initializing Youtube Player");
                mYoutubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);

            }
        });

    }
}