package com.example.thanyani.miwork;

/**
 * Created by THANYANI on 2017/07/06.
 */

public class Work {
    /**
     * Default translation for the word
     */
    private String mDefaultTransaction;

    /**
     * miwork translation for the word
     */
    private String mMyworkTransaction;

    /**
     * Image resource ID for the word
     */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Audio resource ID for the word
     */
    private int mAudioResourceId;


    /**
     * Create a new Word object.
     *  @param defaultTransaction is the word in a language that the user is already familiar with (such as English)
     * @param myworkTransaction  is the word in the miwork language
     */
    public Work(String defaultTransaction, String myworkTransaction, int audioResourceId) {
        mDefaultTransaction = defaultTransaction;
        mMyworkTransaction = myworkTransaction;
        mAudioResourceId = audioResourceId;


    }

    /**
     * Create a new Word object.
     *
     * @param defaultTransaction is the word in a language that the user is already familiar with (such as English)
     * @param myworkTransaction  is the word in the miwork language
     * @param imageResourceId    is the drawable resource ID for the image associated with the word
     */

    public Work(String defaultTransaction, String myworkTransaction, int imageResourceId, int audioResourceId) {
        mDefaultTransaction = defaultTransaction;
        mMyworkTransaction = myworkTransaction;
        mImageResourceId = imageResourceId;
        mAudioResourceId= audioResourceId;

    }

    //getting the default transection of the word
    public String getmDefaultTransaction() {
        return mDefaultTransaction;
    }

    public void setmDefaultTransaction(String mDefaultTransaction) {
        this.mDefaultTransaction = mDefaultTransaction;
    }

    //getting the mywork translation of the word
    public String getmMyworkTransaction() {
        return mMyworkTransaction;
    }

    public void setmMyworkTranslation(String mMyworkTransaction) {
        this.mMyworkTransaction = mMyworkTransaction;
    }

    //Return the image resource ID of the word
    public int getmImageResourceId() {
        return mImageResourceId;
    }

    //Returns whether or not  there is an image for this word
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /**
     * getting the method gor word
     */
    public int getmAudioResourceId() {

        return mAudioResourceId;
    }

}
