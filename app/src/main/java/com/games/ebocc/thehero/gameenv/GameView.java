package com.games.ebocc.thehero.gameenv;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.games.ebocc.thehero.balloons.Balloon;
import com.games.ebocc.thehero.balloons.Enemy;
import com.games.ebocc.thehero.util.BalloonFactory;
import com.games.ebocc.thehero.util.MainThread;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private Hero hero;
    private BalloonFactory balloonFactory;
    private List<Cloud> clouds;
    private List<Enemy> enemies;
    private List<Tube> tubes;

    private boolean isGoingUp = false;

    private int LEVEL = 1;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private boolean isGameStarted = false;

    public GameView(Context context) {
        super(context);

        thread = new MainThread(getHolder(), this);

        hero = new Hero(0, 900, this);
        clouds = new ArrayList<>();
        enemies = new ArrayList<>();
        tubes = new ArrayList<>();

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
                hero.setY(900);
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

            case 4:
                hero.setX(0);
                hero.setY(100);
                clouds.add(new Cloud(0, 300, this));

                tubes.add(new Tube(300, screenHeight - 300, this));
                tubes.add(new Tube(800, screenHeight - 300, this));
                tubes.add(new Tube(1300, screenHeight - 300, this));
                tubes.add(new Tube(1800, screenHeight - 300, this));

                balloonFactory = new BalloonFactory(this);
                break;
        }

        for(Enemy enemy : enemies){
            enemy.setClouds(clouds);
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

            for(Cloud cloud : clouds) {
                cloud.draw(canvas);
            }

            for(Enemy enemy : enemies) {
                enemy.draw(canvas);
            }

            if(balloonFactory != null){
                for(Balloon balloon : balloonFactory.getBalloons()){
                    balloon.draw(canvas);
                }
            }

            for(Tube tube : tubes){
                tube.draw(canvas);
            }

            hero.draw(canvas);
        }
    }

    public void update() {
        if(enemies.size() < 1 && LEVEL < 4){
            clouds.clear();
            setGameStarted(false);
            LEVEL++;
            initStage(LEVEL);
        }

        for(Enemy enemy : enemies) {
            hero.isCollidedWithEnemy(enemy);

            if(enemy.getLives() == 0) {
                enemies.remove(enemy);
            }

            enemy.isEnemyCollidedWithFriends(enemies);
            enemy.isCollidedWithClouds();
        }

        if (isGoingUp) {
            if (!hero.cloudCollisionBottom(clouds))
                hero.goUp();
        }else {
            if(!hero.cloudCollisionTop(clouds)
                    && !hero.tubeCollisionTop(tubes))
                hero.goDown();
        }

        for(Balloon balloon : balloonFactory.getBalloons()){
            balloon.run();
        }
    }


    public void gameStart(){
        for (Enemy enemy : enemies) {
            enemy.run();
        }
        setGameStarted(true);
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

    public BalloonFactory getBalloonFactory(){
        return balloonFactory;
    }
}
