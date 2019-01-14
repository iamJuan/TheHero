package com.games.ebocc.thehero.gameenv;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.games.ebocc.thehero.enemyballoons.Enemy;
import com.games.ebocc.thehero.util.HeroPos;
import com.games.ebocc.thehero.util.MainThread;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Cloud clouds[];
    private Hero hero;
    private List<Enemy> enemies;
    private HeroPos heroPos;

    private boolean isGoingUp = false;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Rect heroRect;
    private Rect cloudRect1;
    private Rect cloudRect2;
    private Rect cloudRect3;

    public GameView(Context context) {
        super(context);

        thread = new MainThread(getHolder(), this);

        hero = new Hero(0, 950, this);

        heroPos = new HeroPos();

        clouds = new Cloud[3];
        enemies = new ArrayList<>();

        clouds[0] = new Cloud(0, 1100, this);
        clouds[1] = new Cloud(1200, 600, this);
        clouds[2] = new Cloud(2200, 1100, this);

        enemies.add(new Enemy(1200, 300, this));
        heroPos.addObserver(enemies.get(0));

        enemies.add(new Enemy(2200, 950, this));
        heroPos.addObserver(enemies.get(1));

        setFocusable(true);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            clouds[0].draw(canvas);
            clouds[1].draw(canvas);
            clouds[2].draw(canvas);
            hero.draw(canvas);
            for(int iter = 0; iter < enemies.size(); iter++) {
                enemies.get(iter).draw(canvas);
            }
        }

        for(int iter = 0; iter < enemies.size(); iter++) {
            if (isCollidedWithEnemy(enemies.get(iter))) {
                if(heroRect.bottom < enemies.get(iter).getRect().bottom) {
                    hero.bounceUp();
                    enemies.get(iter).setLives(enemies.get(iter).getLives()-1);
                    if(enemies.get(iter).getLives() == 1)
                        enemies.get(iter).fall();
                    else
                        enemies.remove(iter); //enemy death
                }else{

                }
            }
            isEnemyCollidedWithEnemy(enemies.get(iter));
            isCollidedWithCloud(enemies.get(iter));
        }
    }

    public void update() {

        heroRect = hero.getRect();
        cloudRect1 = clouds[0].getRect();
        cloudRect2 = clouds[1].getRect();
        cloudRect3 = clouds[2].getRect();

        if (!isGoingUp) {
            if ((!Rect.intersects(heroRect, cloudRect1) || heroRect.bottom < cloudRect1.top + 100)
                    && (!Rect.intersects(heroRect, cloudRect3) || heroRect.bottom < cloudRect3.top + 100)
                    && ((heroRect.right < cloudRect2.right - (cloudRect2.right - cloudRect2.left) || heroRect.left > cloudRect2.right)
                    || (heroRect.left < cloudRect2.right && heroRect.right > cloudRect2.left && heroRect.bottom < cloudRect2.top + 100)
                    || heroRect.top > cloudRect2.bottom - 100))
                hero.goDown();
        }else {
            if((heroRect.right < cloudRect2.right - (cloudRect2.right - cloudRect2.left) || heroRect.left > cloudRect2.right)
                    || (heroRect.left < cloudRect2.right && heroRect.right > cloudRect2.left && heroRect.top > cloudRect2.bottom)
                    || (heroRect.left < cloudRect2.right && heroRect.right > cloudRect2.left && heroRect.top < cloudRect2.top))
            hero.goUp();
        }

        for(int enemyIter = 0; enemyIter < enemies.size(); enemyIter++){
            heroPos.notifyEnemies(heroRect.left, heroRect.top);
        }
    }
    //----------------------collision------------------
    private boolean isCollidedWithEnemy(Enemy enemy) {
        boolean hasCollided = false;

        if(Rect.intersects(hero.getRect(), enemy.getRect())){
            hasCollided = true;
        }

        return hasCollided;
    }

    private void isEnemyCollidedWithEnemy(Enemy enemy){

        for(int enemyIter = 0; enemyIter < enemies.size(); enemyIter++){
            if(enemy.getRect().intersect(enemies.get(enemyIter).getRect()) && !enemy.equals(enemies.get(enemyIter))){
                enemy.setHasCollidedWithFriend(true);
                enemy.goOpposite();
                enemies.get(enemyIter).setHasCollidedWithFriend(true);
                enemies.get(enemyIter).goOpposite();
            }
        }
    }

    private void isCollidedWithCloud(Enemy enemy) {
        Rect enemyRect = enemy.getRect();
        if(Rect.intersects(enemyRect, cloudRect2)) {
            enemy.goOpposite();
            enemy.setHasCollidedWithCloud(true);
            Log.d("collided with cloud", "true");
        }
    }
    //----------------------collision------------------

    public void gameStart(){
        for(int enemyIter = 0; enemyIter < enemies.size(); enemyIter++){
            enemies.get(enemyIter).run();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_UP:
                isGoingUp = false;
                break;
            case MotionEvent.ACTION_DOWN:
                isGoingUp = true;
                break;
        }
        return true;
    }

    public Hero getHero() {
        return hero;
    }
}
