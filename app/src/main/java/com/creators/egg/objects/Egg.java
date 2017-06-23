package com.creators.egg.objects;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.creators.egg.R;

/**
 *
 */

public class Egg extends BaseObject {


    public Egg(Context context, int x, int y) {
        super(context, x, y);
        this.bitmapOfObject = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.egg);
    }

    public void rotate(){
        // Todo

    }

    public void attachParachute(){

    }






}
