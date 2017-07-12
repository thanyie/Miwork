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

public class PhrasesActivity extends AppCompatActivity {

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
        words.add(new Work("Where are you going?","minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Work("What is your name?","tinne oyaase'en", R.raw.phrase_what_is_your_name));
        words.add(new Work("My name is...","oyaaset", R.raw.phrase_my_name_is));
        words.add(new Work("How are you feeling?","michakses", R.raw.phrase_how_are_you_feeling));
        words.add(new Work("Am feeling good","kichi achit", R.raw.phrase_im_feeling_good));
        words.add(new Work("Are you coming?","eenes'aa", R.raw.phrase_are_you_coming));
        words.add(new Work("Yes i'm coming","hee'eenem", R.raw.phrase_yes_im_coming));
        words.add(new Work("I'm comming","eenem", R.raw.phrase_im_coming));
        words.add(new Work("Let's go","yoowutis", R.raw.phrase_lets_go));
        words.add(new Work("Come here","enni'nem", R.raw.phrase_come_here));


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


                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getmAudioResourceId());
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

