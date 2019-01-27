package com.games.ebocc.thehero.util;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.balloons.Floater;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FloatingObjectsFactory{

    private final int TUBE_1 = 1;
    private final int TUBE_2 = 2;
    private final int TUBE_3 = 3;
    private final int TUBE_4 = 4;
    private final int PINK_BALLOON = 1;
    private final int BLUE_BALLOON = 2;
    private final int BALLOON_LIMIT = 1;

    private int nBalloon = 0;
    private int nHeart = 0;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private SurfaceView view;

    private List<Floater> floaters;

    private boolean ifBalloon = false;
    private boolean shouldGoToNextLevel = false;

    public FloatingObjectsFactory(SurfaceView view){
        this.view = view;
        floaters = new ArrayList<>();
    }

    public void generate() {
        Floater floater = new Floater(0, screenHeight, view);
        if(ifBalloon){
            if(nBalloon < BALLOON_LIMIT){
                int bound;

                if(nBalloon == 0){
                    bound = 3;
                }else{
                    bound = 7;
                }

                int whichTube = new Random().nextInt(bound) + 1;
                nBalloon++;

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
                floater.setyLimit(100);
                floater.setX(770);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(100);
                floater.setX(970);
                floaters.add(floater);
                nHeart+=2;

            }else if(nHeart == 2){
                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(300);
                floater.setX(770);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(300);
                floater.setX(970);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(300);
                floater.setX(1170);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(300);
                floater.setX(1370);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(300);
                floater.setX(1570);
                floaters.add(floater);
                nHeart+=4;
            }else if(nHeart == 6){
                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(500);
                floater.setX(770);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(500);
                floater.setX(970);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(500);
                floater.setX(1170);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(500);
                floater.setX(1370);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(500);
                floater.setX(1670);
                floaters.add(floater);
                nHeart+=5;
            }else if(nHeart == 11){
                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(700);
                floater.setX(770);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(700);
                floater.setX(970);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(700);
                floater.setX(1170);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(700);
                floater.setX(1370);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(700);
                floater.setX(1570);
                floaters.add(floater);
                nHeart+=5;
            }else if(nHeart == 16){
                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(900);
                floater.setX(770);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(900);
                floater.setX(970);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(900);
                floater.setX(1170);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(900);
                floater.setX(1370);
                floaters.add(floater);

                floater = new Floater(0, screenHeight, view);
                floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubblepink));
                floater.setyLimit(900);
                floater.setX(1570);
                floaters.add(floater);
                nHeart+=5;
            }else if(nHeart >= 21){
                if(!shouldGoToNextLevel) {
                    if (floaters.get(nHeart).getRect().top <= 920) {
                        shouldGoToNextLevel = true;
                    }
                }
            }
        }
    }

    public List<Floater> getFloaters() {
        return floaters;
    }

    public void setIfBalloon(boolean ifBalloon) {
        this.ifBalloon = ifBalloon;
    }

    public boolean shouldGoToNextLevel(){
        return shouldGoToNextLevel;
    }
}
