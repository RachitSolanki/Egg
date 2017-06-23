package com.creators.egg.objects;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.creators.egg.R;

/**
 * Created by PlusTV on 21/6/17.
 */

public class Parachute extends BaseObject{


    private Parachute(Context context, int x, int y) {
        super(context, x, y);
        this.bitmapOfObject= BitmapFactory.decodeResource(getContext().getResources(), R.drawable.egg);
    }


    public static Parachute createParachute(Context context,Platform platform){
        return new Parachute(context,platform.getX(),platform.getY());
    }





}
