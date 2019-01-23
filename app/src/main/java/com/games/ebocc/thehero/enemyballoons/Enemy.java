package com.games.ebocc.thehero.enemyballoons;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.gameenv.Cloud;
import com.games.ebocc.thehero.gameenv.GameEntities;
import com.games.ebocc.thehero.util.CollisionChecker;

import java.util.List;
import java.util.Random;

public class Enemy extends GameEntities implements Runnable{

    private SurfaceView view;
    private CollisionChecker collisionChecker;

    private boolean hasFallen = false;

    private Rect targetTravel;

    private int xVelocity = 8;
    private int yVelocity = 5;
    private int targetX;
    private int targetY;
    private int lives = 2;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private final int GO_UP = 2;
    private final int GO_DOWN = 1;

    public Enemy(int left, int top, SurfaceView view) {
        super(left, top, view);
        this.view = view;
        this.image = BitmapFactory.decodeResource(view.getResources(),R.drawable.enemyleft);
        collisionChecker = new CollisionChecker();
        targetTravel = initTravel(GO_UP);
    }

    @Override
    public void run() {

        if (x > targetX - 50) {
            x -= xVelocity;
            if(!hasFallen) {
                image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyleft);
            }
        } else if (x < targetX + 50) {
            x += xVelocity;
            if(!hasFallen) {
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

    private Rect initTravel(int upOrDown) {
        targetX = x;
        if(upOrDown == 2)
            targetY = y - 200;
        else
            targetY = y + 200;
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

    public Rect oppositeTravel(int side){
        if(side == 1) {
            targetX = x + 500;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyright);
        }else if(side == 2){
            targetX = x - 500;
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
            if(!myself.equals(friend) && collisionChecker.checkCollision(myself.getRect(), friend.getRect())) {
                if (collisionChecker.checkIfBounceLeft(myself.getRect(), friend.getRect())) {
                    targetTravel = oppositeTravel(GO_UP);
                } else if (collisionChecker.checkIfBounceRight(myself.getRect(), friend.getRect())) {
                    targetTravel = oppositeTravel(GO_DOWN);
                } else if(collisionChecker.checkIfBounceUp(this.getRect(),friend.getRect())){
                    targetTravel = initTravel(GO_UP);
                }else if(collisionChecker.checkIfBounceDown(this.getRect(),friend.getRect())){
                    targetTravel = initTravel(GO_DOWN);
                }
            }
        }
    }

    public void isCollidedWithClouds(List<Cloud> clouds) {
        for (Cloud cloud : clouds){
            if(this.getRect().intersect(cloud.getRect())){
                if(collisionChecker.checkIfBounceLeft(this.getRect(),cloud.getRect())){
                    targetTravel = oppositeTravel(GO_UP);
                }else if(collisionChecker.checkIfBounceRight(this.getRect(),cloud.getRect())){
                    targetTravel = oppositeTravel(GO_DOWN);
                }else if(collisionChecker.checkIfBounceUp(this.getRect(),cloud.getRect())){
                    targetTravel = initTravel(GO_UP);
                }else if(collisionChecker.checkIfBounceDown(this.getRect(),cloud.getRect())){
                    targetTravel = initTravel(GO_DOWN);
                }
            }
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void reinitiateFirstTravel() {
        this.targetTravel = initTravel(GO_UP);
    }
}
