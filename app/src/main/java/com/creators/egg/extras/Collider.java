package com.creators.egg.extras;

import com.creators.egg.objects.BaseObject;

/**
 * Created by PlusTV on 21/6/17.
 */

public class Collider {

    /**
     * @param object Object
     * @param compareWith Object to compare with
     * @return true on collides
     */
    public static boolean checkCollide(BaseObject object,BaseObject compareWith){

        int minX=compareWith.getX()-compareWith.getWidth()/2;
        int maxX=compareWith.getX()+compareWith.getWidth()/2;
        int minY=compareWith.getX()-compareWith.getWidth()/2;
        /*
           no need since only need upper edge
           int maxY=compareWith.getX()+compareWith.getWidth()/2;
        */

        return object.getX() > minX && object.getX() < maxX && object.getY() == minY;

    }




}
