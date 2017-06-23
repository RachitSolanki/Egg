package com.creators.egg.objects;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.creators.egg.R;
import com.creators.egg.gamelogic.GameView;

import java.util.Random;

/**
 * Created by PlusTV on 21/6/17.
 */

public class Platform  extends BaseObject{

    // Todo with spike or without spikesSz

    //in radians                 degree - radians
    final static double angles[]={/*30*/0.523599,
                                  /*45*/0.785398,
                                  /*60*/1.0472,
                                  /*90*/1.5708,
                                  /*120*/2.0944,
                                  /*135*/2.35619,
                                  /*150*/2.61799};

    boolean withSpikes;

    private static final  Random rand=new Random();

    private Platform(Context context, int x, int y) {
        super(context, x, y);
       this.bitmapOfObject= BitmapFactory.decodeResource(getContext().getResources(), R.drawable.platform);
    }


    /**
     * Factory Method it will always draw bottom to top
     * @param respectiveToX
     * @param respectiveToY
     * @param context
     * @return
     */
    public static Platform createPlatform(Context context,int respectiveToX, int respectiveToY){
        double angle = angles[randInt()];
        int calculatedX= respectiveToX + (int) (GameView.JumpHeight*Math.cos(angle));
        int calculatedY=respectiveToY  - (int)  (GameView.JumpHeight*Math.sin(angle));
        return new Platform(context,calculatedX,calculatedY);
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
     static int randInt() {
        // nextInt is normally exclusive of the top value,
        int randomNum = rand.nextInt(7);
        return randomNum;
    }


    private void refreshCoordinates(){
        double angle = angles[randInt()];
        setX(getX() + (int) (GameView.JumpHeight*Math.cos(angle)));
        setY(getY()  - (int) (GameView.JumpHeight*Math.sin(angle)));
    }



}

