package com.games.ebocc.thehero.enemyballoons;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.gameenv.Cloud;
import com.games.ebocc.thehero.gameenv.GameEntities;

import java.util.List;
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

    public Enemy(){}

    public Enemy(int left, int top, SurfaceView view) {
        super(left, top, view);
        this.view = view;
        this.image = BitmapFactory.decodeResource(view.getResources(),R.drawable.enemyleft);

        targetTravel = initTravel();
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

    private Rect initTravel() {
        targetX = x;
        targetY = y - 400;
        Rect rectTravel = new Rect();
        rectTravel.set(targetX, targetY,targetX+100, targetY+100);

        return rectTravel;
    }

    private Rect justTravel() {
        targetX = new Random().nextInt(screenWidth);
        targetY = new Random().nextInt(screenHeight - 500);
        Rect rectTravel = new Rect();
        rectTravel.set(targetX, targetY,targetX+100, targetY+100);

        return rectTravel;
    }

    public Rect oppositeTravel(){
        if(isFacingLeft) {
            targetX = x + 500;
            isFacingLeft = false;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyright);
        }else{
            targetX = x - 500;
            isFacingLeft = true;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyleft);
        }

        targetY = y;
        Rect rectTravel = new Rect();
        rectTravel.set(targetX, targetY,targetX+100, targetY+100);

        return rectTravel;
    }

    public void fall(){
        hasFallen = true;
        targetTravel = fallTravel();
    }

    public Rect fallTravel() {
        hasFallen = true;
        targetX = x;
        targetY = screenHeight  - 400;
        Rect rectTravel = new Rect();
        rectTravel.set(targetX, targetY,targetX+100, targetY+100);

        return rectTravel;
    }

    public void isEnemyCollidedWithFriends(List<Enemy> friends){

        for(int iter = 0; iter < friends.size(); iter++){
            Enemy myself = this;
            Enemy friend = friends.get(iter);
            if(Rect.intersects(myself.getRect(), friend.getRect()) && !myself.equals(friend)) {
                targetTravel = oppositeTravel();
            }
        }
    }

    public void isCollidedWithClouds(List<Cloud> clouds) {
        if(getRect().intersect(clouds.get(1).getRect())){
            targetTravel = oppositeTravel();
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
