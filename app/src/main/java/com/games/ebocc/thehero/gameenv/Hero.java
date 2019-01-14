package com.games.ebocc.thehero.gameenv;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class Hero extends GameObjects{

    private int xVelocity = 8;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Hero(Bitmap bitmap, int left, int top) {
        super(bitmap, left, top);
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
    }

    public void goLeft() {
        x -= xVelocity;
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
}
