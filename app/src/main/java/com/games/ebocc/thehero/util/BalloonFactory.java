package com.games.ebocc.thehero.util;

import android.content.res.Resources;
import android.view.SurfaceView;

import com.games.ebocc.thehero.balloons.Balloon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BalloonFactory implements Runnable{

    private final int TUBE_1 = 1;
    private final int TUBE_2 = 2;
    private final int TUBE_3 = 3;
    private final int TUBE_4 = 4;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private SurfaceView view;

    private List<Balloon> balloons;

    public BalloonFactory(SurfaceView view){
        this.view = view;
        balloons = new ArrayList<>();
    }

    @Override
    public void run() {

        Balloon balloon = new Balloon(0, screenHeight, view);

        int whichTube = new Random().nextInt(4) + 1;

        switch (whichTube){
            case TUBE_1:
                balloon.setX(500);
                break;
            case TUBE_2:
                balloon.setX(1000);
                break;
            case TUBE_3:
                balloon.setX(1500);
                break;
            case TUBE_4:
                balloon.setX(2000);
                break;
        }

        balloons.add(balloon);
    }

    public List<Balloon> getBalloons() {
        return balloons;
    }
}
