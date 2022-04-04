package com.example.mp34;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button pl_a, st_a;
    View.OnClickListener cl;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pl_a = (Button) findViewById(R.id.play_audio);
        st_a = (Button) findViewById(R.id.stop_audio);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.play_audio:
                        if (mp == null) {
                            try{
                                mp=new MediaPlayer();
                                mp.setDataSource("/data/user/0/com.example.mp34/lobe.mp3");
                                mp.setDataSource("/storage/emulated/0/android/dat/com.example.mp34/file/mysong.mp3");
                                mp.prepare();
                                mp.start();
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.kal);
                            mp.start();
                        }
                            break;
                            case R.id.stop_audio:
                                if (mp != null) {
                                    mp.stop();
                                    mp.release();
                                    mp = null;
                                }
                                    break;
                                }

                        }
                };
                pl_a.setOnClickListener(cl);
                st_a.setOnClickListener(cl);
            };
        }
