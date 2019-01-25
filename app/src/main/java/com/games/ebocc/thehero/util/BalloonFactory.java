package com.games.ebocc.thehero.util;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.balloons.Balloon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BalloonFactory implements Runnable{

    private final int TUBE_1 = 1;
    private final int TUBE_2 = 2;
    private final int TUBE_3 = 3;
    private final int TUBE_4 = 4;
    private final int PINK_BALLOON = 1;
    private final int BLUE_BALLOON = 2;
    private final int BALLOON_LIMIT = 20;

    private int nBalloon = 0;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private SurfaceView view;

    private List<Balloon> balloons;

    public BalloonFactory(SurfaceView view){
        this.view = view;
        balloons = new ArrayList<>();
    }

    @Override
    public void run() {
        if(nBalloon < BALLOON_LIMIT){
            Balloon balloon = new Balloon(0, screenHeight, view);
            nBalloon++;
            int whichTube = new Random().nextInt(7) + 1;

            switch (whichTube){
                case TUBE_1:
                    balloon.setX(570);
                    break;
                case TUBE_2:
                    balloon.setX(1070);
                    break;
                case TUBE_3:
                    balloon.setX(1570);
                    break;
                case TUBE_4:
                    balloon.setX(2070);
                    break;
                default:
                    balloon.setExploded(true);
                    nBalloon--;
                    break;
            }

            int whichColor = new Random().nextInt(2) + 1;

            switch (whichColor){
                case PINK_BALLOON:
                    balloon.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                    break;
                case BLUE_BALLOON:
                    balloon.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubbleblue));
                    break;
            }

            balloons.add(balloon);
        }
    }

    public List<Balloon> getBalloons() {
        return balloons;
    }
}
