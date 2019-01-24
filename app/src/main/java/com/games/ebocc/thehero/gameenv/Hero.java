package com.games.ebocc.thehero.gameenv;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.balloons.Balloon;
import com.games.ebocc.thehero.balloons.Enemy;
import com.games.ebocc.thehero.util.CollisionChecker;

import java.util.List;

public class Hero extends GameEntities implements Runnable{

    private CollisionChecker collisionChecker;

    private int xVelocity = 8;
    private int yVelocity = 5;
    private int targetX;
    private int targetY;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private boolean isGoingRight = false;
    private boolean hasDirection = false;
    private boolean isOnTravel = false;

    private final int NO_DIRECTION = 0;
    private final int GO_LEFT = 1;
    private final int GO_RIGHT = 2;

    private Rect targetTravel;

    public Hero(int left, int top, SurfaceView view) {
        super(left, top, view);
        collisionChecker = new CollisionChecker();
        this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaright);
        targetTravel = null;
    }

    public void goUp(){

        if (y > 0 && !isOnTravel) {
            y -= yVelocity;
        }
    }

    public void goDown(){

        if (y < screenHeight - image.getHeight()  && !isOnTravel) {
            y += yVelocity + 2;
        }
    }

    public void goRight(){
        if(!isOnTravel) {
            x += xVelocity;
            this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaright);
            if (x > screenWidth) {
                x = -100;
            }
        }
    }

    public void goLeft() {
        if(!isOnTravel) {
            x -= xVelocity;
            this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.bidaleft);
            if(x < -100){
                x = screenWidth;
            }
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
        if(Rect.intersects(this.getRect(),enemy.getRect())){
            if(collisionChecker.checkCollisionOnTop(this.getRect(), enemy.getRect())) {
                bounceUp();
                enemy.setLives(enemy.getLives() - 1);
                if (enemy.getLives() == 1) {
                    enemy.fall();
                }
                return true;
            }else if(collisionChecker.checkCollisionOnLeft(this.getRect(), enemy.getRect())){
                enemy.setTargetTravel(1);
                targetTravel = oppositeTravel(2);
                isOnTravel = true;
            }else if(collisionChecker.checkCollisionOnRight(this.getRect(), enemy.getRect())){
                enemy.setTargetTravel(2);
                targetTravel = oppositeTravel(1);
                isOnTravel = true;
            }
        }
        return false;
    }

    public boolean getIsOnTravel(){
        return isOnTravel;
    }

    public boolean isCollidedWithBalloon(Balloon balloon){
        if(Rect.intersects(this.getRect(),balloon.getRect())){
            bounceUp();
            balloon.setExploded(true);
            return true;
        }
        return false;
    }

    public boolean tubeCollisionTop(List<Tube> tubes) {
        for(Tube tube : tubes){
            Rect tubeRect = tube.getRect();
            if(Rect.intersects(this.getRect(), tubeRect) && collisionChecker.checkCollisionOnTopWithNonMovingObjects(this.getRect(), tubeRect))
                return true;
        }
        return false;
    }

    public boolean cloudCollisionTop(List<Cloud> clouds) {
        for(Cloud cloud : clouds){
            Rect cloudRect = cloud.getRect();
            if(Rect.intersects(this.getRect(), cloudRect) && collisionChecker.checkCollisionOnTopWithNonMovingObjects(this.getRect(), cloudRect))
                return true;
        }
        return false;
    }

    public boolean cloudCollisionBottom(List<Cloud> clouds) {
        for(Cloud cloud : clouds){
            Rect cloudRect = cloud.getRect();
            if(Rect.intersects(this.getRect(), cloudRect) && collisionChecker.checkCollisionOnBottomWithNonMovingObjects(this.getRect(), cloudRect))
                return true;
        }
        return false;
    }

    @Override
    public void run() {
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

        if(Rect.intersects(this.getRect(), targetTravel)){
            isOnTravel = false;
        }
    }

    public Rect oppositeTravel(int side){
        if(side == 1) {
            targetX = x + 300;
        }else if(side == 2){
            targetX = x - 300;
        }

        targetY = y;
        Rect rectTravel = new Rect();
        rectTravel.set(targetX, targetY,targetX+100, targetY+100);

        return rectTravel;
    }
}
