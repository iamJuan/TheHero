package com.games.ebocc.thehero.gameenv;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
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

        enemies.add(new Enemy(2200, 300, this));
        heroPos.addObserver(enemies.get(1));

        heroRect = hero.getRect();
        cloudRect1 = clouds[0].getRect();
        cloudRect2 = clouds[1].getRect();
        cloudRect3 = clouds[2].getRect();

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
            Enemy enemy = enemies.get(iter);
            if (hero.isCollidedWithEnemy(enemy)) {
                if(enemy.getLives() == 0)
                    enemies.remove(enemy); //enemy death
            }

            enemy.isEnemyCollidedWithFriends(enemies);
        }
    }

    public void update() {

        if (!isGoingUp) {
            if (hero.cloudCollisionTop(clouds))
                hero.goDown();
        }else {
            if(hero.cloudCollisionBottom(clouds))
                hero.goUp();
        }

        for(int enemyIter = 0; enemyIter < enemies.size(); enemyIter++){
            heroPos.notifyEnemies(heroRect.left, heroRect.top);
        }
    }


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
