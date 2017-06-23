package com.creators.egg.objects;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.creators.egg.R;

/**
 * Created by PlusTV on 21/6/17.
 */

public class EggShell extends BaseObject {


    private EggShell(Context context, int x, int y) {
        super(context, x, y);
        this.bitmapOfObject= BitmapFactory.decodeResource(getContext().getResources(), R.drawable.egg);
    }





    public static EggShell createEggShell(Context context,Platform platform){
        // Todo calculate egg shell on platform
        return new EggShell(context,platform.getX(),platform.getY());
    }



    public void hoverShell(){
        // Todo
    }




}


