package com.games.ebocc.thehero.gameenv;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.games.ebocc.thehero.enemyballoons.Enemy;
import com.games.ebocc.thehero.util.MainThread;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private List<Cloud> clouds;
    private Hero hero;
    private List<Enemy> enemies;

    private boolean isGoingUp = false;

    private Rect heroRect;

    private int LEVEL = 1;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean isLevelEnded = false;

    private boolean isGameStarted = false;

    public GameView(Context context) {
        super(context);

        thread = new MainThread(getHolder(), this);

        hero = new Hero(0, 800, this);

        clouds = new ArrayList<>();
        enemies = new ArrayList<>();
        heroRect = hero.getRect();

        initStage(LEVEL);

        setFocusable(true);
        getHolder().addCallback(this);
    }

    private void initStage(int level) {
        switch(level){
            case 1:
                clouds.add(new Cloud(0, 1100, this));
                clouds.add(new Cloud(1200, 600, this));
                clouds.add(new Cloud(2200, 1100, this));

                enemies.add(new Enemy(1200, 400, this));
                enemies.add(new Enemy(2200, 900, this));
                break;

            case 2:
                hero.setX(0);
                hero.setY(800);
                clouds.add(new Cloud(0, 1100, this));
                clouds.add(new Cloud(800, screenHeight - 600, this));
                clouds.add(new Cloud(1200, screenHeight - 600, this));
                clouds.add(new Cloud(1600, screenHeight - 600, this));

                enemies.add(new Enemy(800, screenHeight - 800, this));
                enemies.add(new Enemy(1200, screenHeight - 800, this));
                enemies.add(new Enemy(1600, screenHeight - 800, this));
                break;

            case 3:
                hero.setX(0);
                hero.setY(900);
                clouds.add(new Cloud(0, 1100, this));

                clouds.add(new Cloud(600, screenHeight - 1200, this));
                clouds.add(new Cloud(1000, 1100, this));
                clouds.add(new Cloud(1600, screenHeight - 800, this));
                clouds.add(new Cloud(2200, 1100, this));

                enemies.add(new Enemy(600, screenHeight - 1300, this));
                enemies.add(new Enemy(1000, 1000, this));
                enemies.add(new Enemy(1600, screenHeight - 900, this));
                enemies.add(new Enemy(2200, 1000, this));
                break;
        }

        for(Enemy enemy : enemies){
            enemy.reinitiateFirstTravel();
        }
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

            for(int iter = 0; iter < clouds.size(); iter++) {
                clouds.get(iter).draw(canvas);
            }

            for(int iter = 0; iter < enemies.size(); iter++) {
                enemies.get(iter).draw(canvas);
            }

            hero.draw(canvas);
        }

        for(int iter = 0; iter < enemies.size(); iter++) {
            Enemy enemy = enemies.get(iter);
            if (hero.isCollidedWithEnemy(enemy)) {
                if(enemy.getLives() == 0) {
                    enemies.remove(enemy);
                }
            }

            enemy.isEnemyCollidedWithFriends(enemies);
            enemy.isCollidedWithClouds(clouds);
        }
    }

    public void update() {
        if(!isLevelEnded){
            if(enemies.size() < 1){
                clouds.clear();
                LEVEL++;
                setGameStarted(false);
                initStage(LEVEL);

                isLevelEnded = true;
            }

            if (!isGoingUp) {
                if (!hero.cloudCollision(clouds))
                    hero.goDown();
            }else {
                if(!hero.cloudCollisionBottom(clouds))
                    hero.goUp();
            }
        }
    }


    public void gameStart(){
        for (int enemyIter = 0; enemyIter < enemies.size(); enemyIter++) {
            enemies.get(enemyIter).run();
        }
        setGameStarted(true);
    }

    public boolean isLevelEnded() {
        return isLevelEnded;
    }

    public void setIsLevelEnded(boolean isLevelEnded){
        this.isLevelEnded = isLevelEnded;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        if(isGameStarted) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    isGoingUp = false;
                    break;
                case MotionEvent.ACTION_DOWN:
                    isGoingUp = true;
                    break;
            }
        }
        return true;
    }

    public Hero getHero() {
        return hero;
    }
}
