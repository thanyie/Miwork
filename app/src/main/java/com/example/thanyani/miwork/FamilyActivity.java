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

public class FamilyActivity extends AppCompatActivity {

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
        words.add(new Work("father","apa", R.drawable.family_father, R.raw.family_father));
        words.add(new Work("mother","ata", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Work("son","angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Work("daughter","tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Work("older brother","taachi", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Work("younger brother","chalitti", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Work("older sister","tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Work("younger sister","kolliti",  R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Work("grandmother","ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Work("grandfather","paapa", R.drawable.family_grandfather, R.raw.family_grandfather));


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


                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAudioResourceId());
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


