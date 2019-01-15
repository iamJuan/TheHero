package com.games.ebocc.thehero.enemyballoons;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.gameenv.GameEntities;

import java.util.Random;

public class Enemy extends GameEntities implements Runnable{

    private SurfaceView view;
    private int targetX;
    private int targetY;
    private int heroX;
    private int heroY;

    private boolean isFacingLeft = false;
    private boolean hasFallen = false;

    private Rect targetTravel;

    private int xVelocity = 8;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int lives = 2;

    public Enemy(int left, int top, SurfaceView view) {
        super(left, top, view);
        this.view = view;
        this.image = BitmapFactory.decodeResource(view.getResources(),R.drawable.enemyleft);

        targetTravel = justTravel();
    }

    @Override
    public void run() {

        if (x > targetX - 50) {
            x -= xVelocity;
            if(!hasFallen) {
                isFacingLeft = true;
                image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyleft);
            }
        } else if (x < targetX + 50) {
            x += xVelocity;
            if(!hasFallen) {
                isFacingLeft = false;
                image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyright);
            }
        }

        if (y > targetY) {
            y -= yVelocity;
        } else if (y < targetY) {
            y += yVelocity;
        }

        if(Rect.intersects(rect, targetTravel) && !hasFallen){
            targetTravel = justTravel();
        }
    }

    private Rect justTravel() {
        targetX = new Random().nextInt(screenWidth);
        targetY = new Random().nextInt(screenHeight - 400);
        Rect rectTravel = new Rect();
        rectTravel.set(targetX, targetY,targetX+50, targetY+50);

        return rectTravel;
    }

    public Rect oppositeTravel(int bounceFrom){
        if(isFacingLeft) {
            targetX = bounceFrom - 500;
            isFacingLeft = false;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyright);
        }else{
            targetX = bounceFrom + 500;
            isFacingLeft = true;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyleft);
        }

        targetY = y;
        Rect rectTravel = new Rect();
        rectTravel.set(targetX, targetY,targetX+50, targetY+50);

        return rectTravel;
    }

    public Rect fallTravel() {
        hasFallen = true;
        targetX = x;
        targetY = screenHeight  - 400;
        Rect rectTravel = new Rect();
        rectTravel.set(targetX, targetY,targetX+100, targetY+100);

        return rectTravel;
    }

    public void collidedWithFriend(int src){
        if(!hasFallen){
           targetTravel = oppositeTravel(src);
        }
    }

//    public void collidedWithCloud(boolean isAlignedWithY){
//        if(isAlignedWithY)
//            targetTravel = oppositeTravel();
//        else
//            targetTravel = justTravel();
//    }

    public void updateHeroPosition(int heroX, int heroY){
        this.heroX = heroX;
        this.heroY = heroY;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
