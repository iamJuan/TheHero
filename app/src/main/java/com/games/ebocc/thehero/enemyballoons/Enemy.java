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

    private int heroX;
    private int heroY;

    private boolean isFacingLeft = false;
    private boolean hasFallen = false;

    private boolean hasCollidedWithFriend = false;
    private boolean hasCollidedWithCloud = false;

    private int targetX;
    private int targetY;
    private Rect rectTarget;

    private int xVelocity = 8;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int lives = 2;

    public Enemy(int left, int top, SurfaceView view) {
        super(left, top, view);
        this.view = view;
        this.image = BitmapFactory.decodeResource(view.getResources(),R.drawable.enemyleft);

        int tx = new Random().nextInt(screenWidth);
        int ty = new Random().nextInt(screenHeight - 400);
        createTargetPos(tx, ty);
    }

    @Override
    public void run() {
        if(Rect.intersects(rect, rectTarget)){
            int tx = new Random().nextInt(screenWidth);
            int ty = new Random().nextInt(screenHeight - 400);

            if(!hasFallen && hasCollidedWithFriend) {
                hasCollidedWithFriend = false;
            }else if(!hasFallen && hasCollidedWithCloud) {
                hasCollidedWithCloud = false;
            }
            createTargetPos(tx, ty);
        }

        goToTarget();
    }

    private void createTargetPos(int tx, int ty) {
        targetX = tx;
        targetY = ty;
        rectTarget = new Rect();
        rectTarget.set(targetX, targetY,targetX+100, targetY+100);
    }

    private void goToTarget() {
        if (x >= targetX) {
            x -= xVelocity;
            isFacingLeft = true;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyleft);
        } else if (x < targetX) {
            x += xVelocity;
            isFacingLeft = false;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyright);
        }

        if (y > targetY) {
            y -= yVelocity;
        } else if (y < targetY) {
            y += yVelocity;
        }
    }

    public void goOpposite(){
        int tx;
        if(isFacingLeft) {
            tx = x - 500;
            isFacingLeft = false;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyright);
        }else{
            tx = x + 500;
            isFacingLeft = true;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyleft);
        }

        int ty = new Random().nextInt(screenHeight  - 400);
        createTargetPos(tx, ty);
    }

    public void updateHeroPosition(int heroX, int heroY){
        this.heroX = heroX;
        this.heroY = heroY;
    }

    public void fall() {
        hasFallen = true;
        int tx = x;
        int ty = screenHeight  - 400;
        createTargetPos(tx, ty);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setHasCollidedWithFriend(boolean hasCollidedWithFriend) {
        this.hasCollidedWithFriend = hasCollidedWithFriend;
    }

    public void setHasCollidedWithCloud(boolean hasCollidedWithCloud) {
        this.hasCollidedWithCloud = hasCollidedWithCloud;
    }
}
