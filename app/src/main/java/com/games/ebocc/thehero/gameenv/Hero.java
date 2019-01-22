package com.games.ebocc.thehero.gameenv;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.enemyballoons.Enemy;
import com.games.ebocc.thehero.util.CollisionChecker;

import java.util.List;

public class Hero extends GameEntities {

    private CollisionChecker collisionChecker;

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
        collisionChecker = new CollisionChecker();
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
        if(x > screenWidth){
            x = -100;
        }
    }

    public void goLeft() {
        x -= xVelocity;
        this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaleft);
        if(x < -100){
            x = screenWidth;
        }
    }

    public void bounceUp(){
        y -= (yVelocity + 100);
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

    public boolean isCollidedWithEnemy(Enemy enemy) {
        if(collisionChecker.checkCollision(this.getRect(),enemy.getRect())){
            bounceUp();
            enemy.setLives(enemy.getLives()-1);
            if(enemy.getLives() == 1)
                enemy.fall();
            return true;
        }
        return false;
    }

    public boolean cloudCollision(List<Cloud> clouds){
        for (Cloud cloud : clouds) {
            return collisionChecker.checkCollision(this.getRect(), cloud.getRect()) && collisionChecker.checkAlignmentY(this.getRect(), cloud.getRect());
        }

        return false;
    }

    public boolean cloudCollisionBottom(List<Cloud> clouds){
        if((getRect().right < clouds.get(1).getRect().right - (clouds.get(1).getRect().right - clouds.get(1).getRect().left) || getRect().left > clouds.get(1).getRect().right)
                || (getRect().left < clouds.get(1).getRect().right && getRect().right > clouds.get(1).getRect().left && getRect().top > clouds.get(1).getRect().bottom)
                || (getRect().left < clouds.get(1).getRect().right && getRect().right > clouds.get(1).getRect().left && getRect().top < clouds.get(1).getRect().top)){
            return true;
        }
        return false;
    }
}
