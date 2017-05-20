package yasmin.harmony.playsound;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class PlaySound extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private MediaPlayer mp;
    static SoundPool sp;
    static private int soundID;
    static boolean plays = false, loaded = false;
    static float actVolume, maxVolume, volume;
    AudioManager audioManager;
    static int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_sound);

        mp = new MediaPlayer();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(this);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;

        counter = 0;

        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool sp, int sampleId, int status) {
                loaded = true;
            }
        });
        soundID = sp.load(this, R.raw.som, 1);

    }

    @Override
   /* public void onClick(View v){
        // Is the sound loaded does it already play?
        if (loaded && !plays) {
            sp.play(soundID, volume, volume, 1, 0, 1f);
            counter = counter++;
            Toast.makeText(this, "Played sound", Toast.LENGTH_SHORT).show();
            plays = true;
        }
    }
}*/

    public void onClick(View v) {
        try {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.reset();
            AssetFileDescriptor afd = null;
            switch (v.getId()) {
                case R.id.btn:
                    afd = getResources().openRawResourceFd(R.raw.som);
                break;
            }
            if (afd != null) {
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepareAsync();
            }
        } catch (IOException e) {
            Log.e("", e.getMessage());
        }
    }
        @Override
        protected void onStop() {
            super.onStop();
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
        }
    }

