package com.creators.egg;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.creators.egg.gamelogic.GameView;

public class GameFragment extends Fragment implements View.OnClickListener {

    Button resumeButton;
    Button startButton;
    Button pauseButton;
    Button stopButton;

    GameView gameView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.game_view,container,false);

        resumeButton  = (Button) view.findViewById(R.id.resumeButton);
        startButton   = (Button) view.findViewById(R.id.startButton );
        pauseButton   = (Button) view.findViewById(R.id.pauseButton );
        stopButton    = (Button) view.findViewById(R.id.stopButton  );

        gameView = (GameView) view.findViewById(R.id.gameView);

        resumeButton.setOnClickListener(this);
        startButton .setOnClickListener(this);
        pauseButton .setOnClickListener(this);
        stopButton  .setOnClickListener(this);







        return view;
    }
    final float alpha = 0.8f;
    float gravity[]=new float[3];
    float linear_acceleration[]=new float[3];
    private static SensorManager mSensorManager;
    private static Sensor mSensor;




    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {

        if(v.equals(startButton))
            gameView.startGame();

        if(v.equals(pauseButton))
            gameView.pauseGame();

        if(v.equals(resumeButton))
            gameView.resumeGame();

        if(v.equals(stopButton))
            gameView.stopGame();

    }
}
