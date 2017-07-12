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

public class NumbersActivity extends AppCompatActivity {

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
        words.add(new Work("one","lutti", R.drawable.number_one , R.raw.number_one));
        words.add(new Work("two","otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Work("three","tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Work("four","oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Work("five","massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Work("six","temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Work("seven","kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Work("eight","kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Work("nine","wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Work("ten","na'aacha", R.drawable.number_ten, R.raw.number_ten));



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


                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceId());
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

