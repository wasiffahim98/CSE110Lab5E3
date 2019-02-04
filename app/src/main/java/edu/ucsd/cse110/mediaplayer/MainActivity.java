package edu.ucsd.cse110.mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private static final int MEDIA_RES_ID = R.raw.jazz_in_paris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadMedia(MEDIA_RES_ID);

        findViewById(R.id.buttonPlay).setOnClickListener(v -> mediaPlayer.start());
        findViewById(R.id.buttonPause).setOnClickListener(v -> mediaPlayer.pause());
        findViewById(R.id.buttonReset).setOnClickListener(v -> {
            mediaPlayer.reset();
            loadMedia(MEDIA_RES_ID);
        });
    }

    public void loadMedia(int resourceId) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        mediaPlayer.setOnCompletionListener(l -> mediaPlayer.start());

        AssetFileDescriptor assetFileDescriptor = this.getResources().openRawResourceFd(resourceId);

        try {
            mediaPlayer.setDataSource(assetFileDescriptor);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
