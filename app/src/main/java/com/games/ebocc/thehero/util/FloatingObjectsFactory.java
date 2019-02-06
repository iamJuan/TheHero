package com.games.ebocc.thehero.util;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
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
    private final int BALLOON_LIMIT = 15;

    private int nBalloon = 0;
    private int nHeart = 0;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private SurfaceView view;

    private List<Floater> floaters;

    private boolean ifBalloon = false;
    private boolean shouldGoToNextLevel = false;
    private MediaPlayer bubble;

    public FloatingObjectsFactory(SurfaceView view){
        this.view = view;
        floaters = new ArrayList<>();
        bubble = MediaPlayer.create(view.getContext(), R.raw.bubble);
    }

    public void generate() {
        Floater floater;
        if(ifBalloon){
            if(nBalloon < BALLOON_LIMIT){
                floater = new Floater(0, screenHeight, view);
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
                        bubble.start();
                        floater.setX(570);
                        break;
                    case TUBE_2:
                        bubble.start();
                        floater.setX(1070);
                        break;
                    case TUBE_3:
                        bubble.start();
                        floater.setX(1570);
                        break;
                    case TUBE_4:
                        bubble.start();
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
                for(int lim = 0; lim < 16; lim++){
                    floater = new Floater(0, screenHeight, view);
                    floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.heart));
                    floater.setyLimit(400);
                    floater.setX((65*(lim*2))+70);
                    floaters.add(floater);
                    nHeart++;
                }

            }else if(nHeart == 16){
                for(int lim = 0; lim < 16; lim++){
                    floater = new Floater(0, screenHeight, view);
                    floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.heart));
                    floater.setyLimit(600);
                    floater.setX((65*(lim*2))+70);
                    floaters.add(floater);
                    nHeart++;
                }
            }else if(nHeart == 32){
                for(int lim = 0; lim < 18; lim++){
                    floater = new Floater(0, screenHeight, view);
                    floater.setImage(BitmapFactory.decodeResource(view.getResources(), R.drawable.heart));
                    floater.setyLimit(800);
                    floater.setX((65*(lim*2))+70);
                    floaters.add(floater);
                    nHeart++;
                }
            }else if(nHeart >= 50){
                if(!shouldGoToNextLevel) {
                    if (floaters.get(nHeart-1).getRect().top <= 920) {
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
