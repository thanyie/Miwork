package com.example.thanyani.miwork;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                @Override
                public void onAudioFocusChange(int focusChange) {

                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();

                    }

                }
            };


    private MediaPlayer.OnCompletionListener mCompletionListener =
            new MediaPlayer.OnCompletionListener(){

                @Override
                public void onCompletion(MediaPlayer mp) {
                    releaseMediaPlayer();
                }
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Creating and setup the{@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
// Adding the array of words
       final ArrayList<Work> words = new ArrayList<Work>();

//        words.add("one");
        words.add(new Work("red","wetetti", R.drawable.color_red, R.raw.color_red));
        words.add(new Work("mustard yellow","chiwiita", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow ));
        words.add(new Work("dusty yellow","topiisa", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Work("green","chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Work("brown","takaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Work("gray","topoppi", R.drawable.color_gray, R.raw.color_gray ));
        words.add(new Work("black","kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Work("white","kelelli", R.drawable.color_white, R.raw.color_white));



        WordAdapter adapter =new WordAdapter(this, words, R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.simple_list_item_1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Work word= words.get(position);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result== AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getmAudioResourceId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }

    }}

