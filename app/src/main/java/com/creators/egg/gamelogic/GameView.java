package com.creators.egg.gamelogic;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.creators.egg.R;
import com.creators.egg.extras.Collider;
import com.creators.egg.objects.BaseObject;
import com.creators.egg.objects.Egg;
import com.creators.egg.objects.Platform;

/**
 * View Rendering will be done here
 *
 */
public class GameView extends View implements Runnable,View.OnTouchListener, SensorEventListener {


    Thread th;
    boolean paused=false;
    boolean stop=false;

    final float alpha = 0.8f;
    float gravity[]=new float[3];
    float linear_acceleration[]=new float[3];
    SensorManager mSensorManager;
    Sensor mSensor;



    Platform[] platforms=new Platform[10];
    BaseObject[] specials=new BaseObject[2];
    Egg egg;

    final int gravityConstant=2;
    public final static int JumpHeight=150;


    int  velocityY=0;
    int  velocityX=0;

    boolean onPlatform=true;


    public GameView(Context context) {
        super(context);
        renderBackground();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        renderBackground();
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        renderBackground();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void renderBackground(){
       setBackgroundResource(R.drawable.gradient);
        initializeGameObjects();
    }


    private  void initializeGameObjects(){
        //Every platform is relative to the first
        for(int i=0;i<platforms.length;i++){

            if(i==0){// special case
                platforms[0]=Platform.createPlatform(getContext(),getWidth()/2,getHeight());
            }else{
                platforms[i]= Platform.createPlatform(getContext(),platforms[i-1].getX(),platforms[i-1].getY());
            }

        }

        egg=new Egg(getContext(),getWidth()/2,getHeight()/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(Platform platform : platforms)
        canvas.drawBitmap(platform.getBitmapOfObject(),platform.getX(),platform.getY(),null);

        canvas.drawBitmap(egg.getBitmapOfObject(),egg.getX(),egg.getY(),null);
    }

    /**
     * Start Game
     */
    public void startGame(){
        init();
    }

    /**
     * Stop Game
     */
    public void stopGame(){
      this.stop=true;
    }

    /**
     * Pause Game
     */
    public void pauseGame(){
         this.paused=true;
    }

    /**
     * Resume Game
     */
    public void resumeGame(){
        this.paused=false;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }




    private void init(){
        setOnTouchListener(this);
        initializeGameObjects();
        attachControls();
        th=new Thread(this);
        th.start();
    }

    private void attachControls(){
        setOnTouchListener(this);
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);

    }


    private void detachControls(){
        if(mSensorManager!=null)
        mSensorManager.unregisterListener(this);
    }

    /**
     * Show Count down 3 2 1..
     */
    private void showCountDown(){
        // // TODO: 6/6/17
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        detachControls();
    }




    @Override
    public void run() {

        while(true){ //game loop
            // TODO: 6/6/17

            if(stop)
                break;

            if(paused)
                pauseLoop();


            //Update Egg coordinates
            egg.setY(egg.getY()+(velocityY+gravityConstant));
            egg.setX(egg.getX()+velocityX);

             if(egg.getY()<(platforms[0].getY()-JumpHeight)){
                 endJump();
             }

              if(Collider.checkCollide(egg,platforms[0])){
                 onPlatform=true;
                  // activate jump agains
                 //detect if it is on edge or continue to free fall
                  velocityY=-gravityConstant;
             }


            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            update();
        }

    }

    private void startJump(){

        if(onPlatform){
            velocityY=-8;
            onPlatform=false;
        }

    }

    private void endJump(){
        velocityY=0;
    }



    private void update(){
        postInvalidateDelayed(0);
    }


    private void pauseLoop(){
        Log.e("game is","paused");
        while(true){
            if(!paused){
                Log.e("game is","resumed");
                break;
            }
        }
    }


    //Touch Action
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        startJump();
        return false;
    }


    //Sensor Action
    @Override
    public void onSensorChanged(SensorEvent event) {

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

       // Log.e("accelerai X",   event.values[0]+"   > "+linear_acceleration[0]);
        //Log.e("accelerai Y","> "+linear_acceleration[1]);
        //Log.e("accelerai Z","> "+linear_acceleration[2]);

        if(event.values[0]>0){ // going left
            //goto left
            velocityX=-3;
        }else{
            //goto right
            velocityX=3;
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }




}
