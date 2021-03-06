package com.games.ebocc.thehero.util;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.games.ebocc.thehero.gameenv.GameView;

public class MainThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;
    private int targetFPS = 60;
    private long averageFPS;
    private int prestart = 0;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / targetFPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            if (prestart > 240) {
                this.gameView.gameStart();
            }else{
                if(prestart % 80 == 0){
                    this.gameView.setGameTimer(this.gameView.getGameTimer()-1);
                }
            }

            if(this.gameView.getFloatingObjectsFactory() != null && this.gameView.getLEVEL() >= 4 ){

                if(prestart % 70 == 0){
                    this.gameView.getFloatingObjectsFactory().generate();
                }
            }

            if(this.gameView.getLEVEL() == 6){
                if(prestart % 60 == 0){
                    this.gameView.getFloatingObjectsFactory().generate();
                }
            }

            if(prestart % 10 == 0){
                this.gameView.getHero().run();
                this.gameView.enemyfly();
            }

            if(this.gameView.getLEVEL() == 6)
                this.gameView.setMsgDelay();

            prestart++;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.getHero().direction();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {       }
            finally {
                if (canvas != null)            {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == targetFPS)        {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
