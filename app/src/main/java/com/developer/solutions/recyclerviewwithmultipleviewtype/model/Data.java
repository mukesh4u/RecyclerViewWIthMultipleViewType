package com.developer.solutions.recyclerviewwithmultipleviewtype.model;

/**
 * Created by Mukesh on 3/8/17.
 * himky02@gmail.com
 */

public class Data {
    public static final int VIEW_PAGER = 0;
    public static final int IMAGE_TYPE = 1;
    public static final int AUDIO_TYPE = 2;
    public int type;
    public int data;
    public String text;

    public Data(int type, String text, int data) {
        this.type = type;
        this.data = data;
        this.text = text;
    }
}
