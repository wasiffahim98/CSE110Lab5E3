package edu.ucsd.cse110.mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    public static final int MEDIA_RES_ID = R.raw.jazz_in_paris;
    public static final int MEDIA_RES_ID_2 = R.raw.memories;


    private int currentResource;
    private LocalTime intervalStart, intervalEnd;
    private boolean init = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intervalStart = LocalTime.parse("11:00:00");
        intervalEnd = LocalTime.parse("16:00:00");

        //loadMedia(MEDIA_RES_ID);

        findViewById(R.id.buttonPlay).setOnClickListener(v -> {
            if(init){
                currentResource = getCurrentResource();
                loadMedia(currentResource);
                init = false;
            }
            mediaPlayer.start();
        });
        findViewById(R.id.buttonPause).setOnClickListener(v -> mediaPlayer.pause());
        findViewById(R.id.buttonReset).setOnClickListener(v -> {
            mediaPlayer.reset();
            currentResource = getCurrentResource();
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
            mediaPlayer.reset();
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

    public int getCurrentResource(){
        int cr;
        LocalTime currentTime = TimeMachine.now().toLocalTime();
        if(currentTime.isAfter(intervalStart) && currentTime.isBefore(intervalEnd)) {
            cr = MEDIA_RES_ID;
        }
        else {
            cr = MEDIA_RES_ID_2;
        }
        return cr;
    }
}
