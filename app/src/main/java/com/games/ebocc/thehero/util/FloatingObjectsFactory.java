package com.games.ebocc.thehero.util;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.balloons.Floater;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FloatingObjectsFactory implements Runnable{

    private final int TUBE_1 = 1;
    private final int TUBE_2 = 2;
    private final int TUBE_3 = 3;
    private final int TUBE_4 = 4;
    private final int PINK_BALLOON = 1;
    private final int BLUE_BALLOON = 2;
    private final int BALLOON_LIMIT = 15;

    private int nBalloon = 0;
    private int nHeart = 0;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private SurfaceView view;

    private List<Floater> floaters;

    private boolean ifBalloon = false;

    public FloatingObjectsFactory(SurfaceView view){
        this.view = view;
        floaters = new ArrayList<>();
    }

    @Override
    public void run() {
        Floater floater = new Floater(0, screenHeight, view);
        if(ifBalloon){
            if(nBalloon < BALLOON_LIMIT){
                nBalloon++;
                int bound = 0;

                if(nBalloon == 0){
                    bound = 3;
                }else{
                    bound = 7;
                }

                int whichTube = new Random().nextInt(bound) + 1;

                switch (whichTube){
                    case TUBE_1:
                        floater.setX(570);
                        break;
                    case TUBE_2:
                        floater.setX(1070);
                        break;
                    case TUBE_3:
                        floater.setX(1570);
                        break;
                    case TUBE_4:
                        floater.setX(2070);
                        break;
                    default:
                        floater.setExploded(true);
                        nBalloon--;
                        break;
                }

                int whichColor = new Random().nextInt(2) + 1;

                switch (whichColor){
                    case PINK_BALLOON:
                        floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                        break;
                    case BLUE_BALLOON:
                        floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubbleblue));
                        break;
                }

                floater.setyLimit(-400);
                floaters.add(floater);
            }
        }else{

            if(nHeart == 0){
                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(200);
                floater.setX(570);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(200);
                floater.setX(870);
                floaters.add(floater);
                nHeart+=2;

            }else if(nHeart == 2){
                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(400);
                floater.setX(570);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(400);
                floater.setX(870);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(400);
                floater.setX(1170);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(400);
                floater.setX(1470);
                floaters.add(floater);
                nHeart+=4;
            }
        }
    }

    public List<Floater> getFloaters() {
        return floaters;
    }

    public void setIfBalloon(boolean ifBalloon) {
        this.ifBalloon = ifBalloon;
    }
}
