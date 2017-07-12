package com.example.thanyani.miwork;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by THANYANI on 2017/07/06.
 */

public class WordAdapter extends ArrayAdapter<Work> {

    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Work> words, int colorResourceId){
        super(context, 0, words);
        mColorResourceId = colorResourceId;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
        }

        Work currentWork = getItem(position);

        TextView myworkTextView = (TextView) listItemView.findViewById(R.id.mywork_text_view);
        myworkTextView.setText(currentWork.getmMyworkTransaction());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWork.getmDefaultTransaction());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.the_image);

        if (currentWork.hasImage()) {
            imageView.setImageResource(currentWork.getmImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }

        else {
            imageView.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.text_container) ;
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
