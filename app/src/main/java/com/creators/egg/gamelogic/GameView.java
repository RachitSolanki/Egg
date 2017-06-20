package com.creators.egg.gamelogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


   final int gravityConstant=2;
    int x,y;

    int px,py;

    int  velocityY=0;
    int  velocityX=0;


    boolean onPlatform=true;



    public GameView(Context context) {
        super(context);
        bm = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.egg);
        bm2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.platform);

    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bm = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.egg);
        bm2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.platform);

    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        bm = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.egg);
        bm2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.platform);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        bm = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.egg);
        bm2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.platform);

    }

    Bitmap bm;
    Bitmap bm2;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(bm,x,y,null);
        canvas.drawBitmap(bm2,px,py,null);
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

    private void initializeVariables(){
        y=getHeight()/2;
        x=getWidth()/2;

        py=getHeight()/2;
        px=getWidth()/2;



    }


    private void init(){
        setOnTouchListener(this);
        initializeVariables();
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

            y += (velocityY+gravityConstant);
            x+=velocityX;

             if(y<(py-450)){
                 endJump();
             }

              if(((y+50)<py&&(y+50/2)<(py+50))&&(x>0&&x<px)){
                 onPlatform=true;
                  // activate jump agains
                 //detect if it is on edge or continue to free fall
                  velocityY=-gravityConstant;
             }


            try {
                Thread.sleep(40);
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

        Log.e("accelerai X",   event.values[0]+"   > "+linear_acceleration[0]);
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
