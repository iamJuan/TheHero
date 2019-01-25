package com.games.ebocc.thehero.balloons;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.gameenv.Cloud;
import com.games.ebocc.thehero.gameenv.GameEntities;
import com.games.ebocc.thehero.util.CollisionChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends GameEntities{

    private SurfaceView view;
    private CollisionChecker collisionChecker;
    private List<Cloud> clouds;

    private boolean hasFallen = false;
    private boolean isGoingUp = false;
    private boolean isGoingRight = false;
    private boolean isImageUp = false;

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
        collisionChecker = new CollisionChecker();
        clouds = new ArrayList<>();
        targetTravel = initTravel(GO_UP);
        this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaleft);
    }

    public void move() {

        boolean intersectedWithCloud = false;

        for(Cloud cloud : clouds){
            Rect cloudRect = cloud.getRect();
            if(Rect.intersects(this.getRect(), cloudRect)&& hasFallen){
                intersectedWithCloud = true;
            }
        }

        if(!intersectedWithCloud) {
            if (x > targetX - 50) {
                x -= xVelocity;
            } else if (x < targetX + 50) {
                x += xVelocity;
            }

            if (y > targetY) {
                y -= yVelocity;
            } else if (y < targetY) {
                y += yVelocity;
            }

            if(y > screenHeight && hasFallen){
                lives--;
            }
        }

        if(Rect.intersects(rect, targetTravel) && !hasFallen){
            targetTravel = justTravel();
        }
    }

    private Rect initTravel(int upOrDown) {
        targetX = x;
        if(upOrDown == 2)
            targetY = y - 300;
        else
            targetY = y + 300;
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
            targetX = x + 200;
        }else if(side == 2){
            targetX = x - 200;
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
        yVelocity += 5;
        targetX = x + xVelocity;
        targetY = screenHeight + 400;
        Rect rectTravel = new Rect();
        rectTravel.set(targetX, targetY,targetX+100, targetY+100);

        return rectTravel;
    }

    public void isEnemyCollidedWithFriends(List<Enemy> friends){
        Enemy myself = this;
        for(Enemy friend : friends){
            if(!myself.equals(friend) && Rect.intersects(myself.getRect(), friend.getRect()) && !hasFallen) {
                if (collisionChecker.checkCollisionOnLeft(myself.getRect(), friend.getRect())) {
                    targetTravel = oppositeTravel(GO_UP);
                } else if (collisionChecker.checkCollisionOnRight(myself.getRect(), friend.getRect())) {
                    targetTravel = oppositeTravel(GO_DOWN);
                } else if(collisionChecker.checkCollisionOnTop(this.getRect(),friend.getRect())){
                    targetTravel = initTravel(GO_UP);
                }else if(collisionChecker.checkCollisionOnBottom(this.getRect(),friend.getRect())){
                    targetTravel = initTravel(GO_DOWN);
                }
            }
        }
    }

    public void isCollidedWithClouds() {
        for (Cloud cloud : clouds){
            if(Rect.intersects(this.getRect(),cloud.getRect()) && !hasFallen){
                if(collisionChecker.checkCollisionOnLeft(this.getRect(),cloud.getRect())){
                    targetTravel = oppositeTravel(GO_UP);
                }else if(collisionChecker.checkCollisionOnRight(this.getRect(),cloud.getRect())){
                    targetTravel = oppositeTravel(GO_DOWN);
                }else if(collisionChecker.checkCollisionOnTop(this.getRect(),cloud.getRect())){
                    targetTravel = initTravel(GO_UP);
                }else if(collisionChecker.checkCollisionOnBottom(this.getRect(),cloud.getRect())){
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

    public void setClouds(List<Cloud> clouds){
        this.clouds = clouds;
    }

    public void setTargetTravel(int side){
        targetTravel = oppositeTravel(side);
    }

    private void flapEffectBooleans() {
        if(targetY < y){
            isGoingUp = true;
        }else{
            isGoingUp = false;
        }

        if(targetX > x){
            isGoingRight = true;
        }else{
            isGoingRight = false;
        }
    }

    public void run() {
        Log.d("ENEMY", "Im flying");
        flapEffectBooleans();

        if(isGoingUp){
            if(isGoingRight){
                if(isImageUp) {
                    this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidarightdown);
                }else {
                    this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaright);
                }
                isImageUp = !isImageUp;
            }else{
                if(isImageUp) {
                    this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaleft);
                }else {
                    this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaleftdown);
                }
                isImageUp = !isImageUp;
            }
        }else{
            if(isGoingRight)
                this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidarightdown);
            else
                this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaleftdown);
        }
    }
}
