package com.creators.egg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        changeFragment(new HomeScreen(),false);

    }



    public void changeFragment(Fragment fragment,boolean addToBackStack){

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if(addToBackStack)fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.replace(R.id.main_view,fragment);
        fragmentTransaction.commit();

    }



}
