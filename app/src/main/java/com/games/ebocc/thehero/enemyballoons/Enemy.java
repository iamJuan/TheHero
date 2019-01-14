package com.games.ebocc.thehero.enemyballoons;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.gameenv.GameObjects;

import java.util.Random;

public class Enemy extends GameObjects implements Runnable{

    private SurfaceView view;

    private int heroX;
    private int heroY;

    private boolean isFacingLeft = false;

    private int targetX;
    private int targetY;
    private Rect rectTarget;

    private int xVelocity = 8;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int lives = 2;
    private boolean hasFallen = false;

    public Enemy(int left, int top, SurfaceView view) {
        super(left, top, view);
        this.view = view;
        this.image = BitmapFactory.decodeResource(view.getResources(),R.drawable.enemyleft);

        targetX = new Random().nextInt(screenWidth);
        targetY = new Random().nextInt(screenHeight - 400);
        rectTarget = new Rect();
        rectTarget.set(targetX, targetY,targetX+100, targetY+100);
    }

    public void goOpposite(){
        Log.d("run", ""+isFacingLeft);
        if(isFacingLeft) {
            targetX = x - 500;
            isFacingLeft = false;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyright);
        }else{
            targetX = x + 500;
            isFacingLeft = true;
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.enemyleft);
        }

        targetY = new Random().nextInt(screenHeight  - 400);
        rectTarget = new Rect();
        rectTarget.set(targetX, targetY, targetX + 100, targetY + 100);
    }

    public void updateHeroPosition(int heroX, int heroY){
        this.heroX = heroX;
        this.heroY = heroY;
    }

    @Override
    public void run() {
        if (Rect.intersects(rect, rectTarget) && !hasFallen) {
            targetX = new Random().nextInt(screenWidth);
            targetY = new Random().nextInt(screenHeight  - 400);
            rectTarget = new Rect();
            rectTarget.set(targetX, targetY, targetX + 100, targetY + 100);
        } else {
            if (x >= targetX) {
                this.image = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.enemyleft);
                x -= xVelocity;
                isFacingLeft = true;
            } else if (x < targetX) {
                this.image = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.enemyright);
                x += xVelocity;
                isFacingLeft = false;
            }

            if (y > targetY) {
                y -= yVelocity;
            } else if (y < targetY) {
                y += yVelocity;
            }
        }
    }

    public void fall() {
        hasFallen = true;
        targetX = x;
        targetY = screenHeight  - 400;
        rectTarget = new Rect();
        rectTarget.set(targetX, targetY, targetX + 100, targetY + 100);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
