package com.baidu.v_lining05.myapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private List<String> listUrl;
    int i = 0;
    public MediaPlayer mediaPlayer;
    private AppCompatSeekBar horizontalSeekBar;
    private boolean isSeekBarChanging;
    private Timer timer;
    private TextView play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        horizontalSeekBar = (AppCompatSeekBar) findViewById(R.id.horizontal_seek_bar);
        play = (TextView) findViewById(R.id.play);
        mediaPlayer = new MediaPlayer();
        listUrl = new ArrayList<String>();
//        listUrl.add("https://od.open.qingting.fm/vod/00/00/0000000000000000000025037444_24.m4a?u=399&channelId=90620&programId=2373589");
//        listUrl.add("http://od.qingting.fm/m4a/577c72827cb8910243182732_5415913_64.m4a");
//        listUrl.add("http://od.qingting.fm/m4a/577c72827cb891024747ff00_5415914_64.m4a");
//        listUrl.add("http://od.qingting.fm/m4a/5ac181fb7cb8914e117befa7_8912498_64.m4a");
//        listUrl.add("http://audio.xmcdn.com/group29/M0B/FD/F8/wKgJWVkmVw2RfnDGAVWf22jxoag220.m4a");
//        listUrl.add("http://od.qingting.fm/m4a/5ac185947cb8914e1312bf89_8912558_64.m4a");
//        listUrl.add("http://od.qingting.fm/m4a/577a01347cb8910243181bb1_5407220_64.m4a");
//        listUrl.add("http://od.qingting.fm/m4a/577a01327cb89102442462df_5407215_64.m4a");
//        listUrl.add("http://vd3.bdstatic.com/mda-hkrf6kr6nimp3q04/mda-hkrf6kr6nimp3q04.mp4?playlist=%5B%22hd%22%2C%22sc%22%5D");
//        listUrl.add("http://od.qingting.fm//vod//00//00//0000000000000000000002713237_24.m4a");
        listUrl.add("http://vd3.bdstatic.com/mda-hkwuwy2idtmikbwf/mda-hkwuwy2idtmikbwf.mp4");

        playMusic(null, i);

        GetRequest robokitRequest = new GetRequest();
        robokitRequest.execute();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.d("debugli", "onCompletion");
                Toast.makeText(getApplicationContext(), "onCompletion", Toast.LENGTH_SHORT).show();
                if (i >= listUrl.size()-1) {
                    i = 0;
                } else {
                    i++;
                }
                playMusic(null, i);
            }
        });

        horizontalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                isSeekBarChanging = false;
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBarChanging = true;
            }
        });

    }

    public void play(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            play.setText("开始播放");
        } else {
            mediaPlayer.start();
            play.setText("播放中...");
        }
    }

    public void pre(View view) {
        if (i <= listUrl.size()-1 && i > 0) {
            i--;
        } else {
            i = listUrl.size()-1;
        }
        playMusic(null, i);
    }

    public void next(View view) {
        if (i >= listUrl.size()-1) {
            i = 0;
        } else {
            i++;
        }
        playMusic(null, i);
    }

    public void playMusic(View view, int i) {
        final long l = System.currentTimeMillis();
//        String url = "http://other.web.rc01.sycdn.kuwo.cn/0caaea0aadbf7c8e56d78c3e3262e8a9/5a66d688/resource/n2/70/86/1609956462.mp3";
        String url = "http://vd3.bdstatic.com/mda-hkrf6kr6nimp3q04/mda-hkrf6kr6nimp3q04.mp4?playlist=%5B%22hd%22%2C%22sc%22%5D";

        Uri uri = Uri.parse(listUrl.get(i));

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(listUrl.get(i));
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.e("mainActivity", e.toString());
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                long currentTimeMillis = System.currentTimeMillis();
                Log.d("debugli", "播放时长:" + (currentTimeMillis - l));
                mediaPlayer.start();
                horizontalSeekBar.setMax(mediaPlayer.getDuration());
            }
        });

        //监听播放时回调函数
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isSeekBarChanging){
                    horizontalSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
            }
        },0,50);
    }

    public void start(View view) {
        Intent launchIntentForPackage = this.getPackageManager().getLaunchIntentForPackage("com.baidu.car.radio");
        startActivity(launchIntentForPackage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
