package com.games.ebocc.thehero.gameenv;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;

public class Hero extends GameEntities {

    private int xVelocity = 8;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private boolean isGoingRight = false;
    private boolean hasDirection = false;

    private final int NO_DIRECTION = 0;
    private final int GO_LEFT = 1;
    private final int GO_RIGHT = 2;

    public Hero(int left, int top, SurfaceView view) {
        super(left, top, view);
        this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaright);
    }

    public void goUp(){

        if (y > 0) {
            y -= yVelocity;
        }
    }

    public void goDown(){

        if (y < screenHeight - image.getHeight()) {
            y += yVelocity + 2;
        }
    }

    public void goRight(){
        x += xVelocity;
        this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaright);
    }

    public void goLeft() {
        x -= xVelocity;
        this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaleft);
    }

    public void bounceUp(){
        y -= (yVelocity + 100);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void direction(){
        if(hasDirection) {
            if (isGoingRight)
                goRight();
            else
                goLeft();
        }
    }

    public void maneuverHero(int direction){
        switch (direction){
            case GO_RIGHT:
                isGoingRight = true;
                hasDirection = true;
                break;
            case GO_LEFT:
                isGoingRight = false;
                hasDirection = true;
                break;
            case NO_DIRECTION:
                hasDirection = false;
                break;

        }
    }
}
