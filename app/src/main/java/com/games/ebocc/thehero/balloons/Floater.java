package com.games.ebocc.thehero.balloons;

import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.gameenv.GameEntities;

public class Floater extends GameEntities implements Runnable{

    private int yVelocity = 5;
    private boolean exploded = false;

    private int yLimit = 0;

    public Floater(int left, int top, SurfaceView view) {
        super(left, top, view);
        this.image = BitmapFactory.decodeResource(view.getResources(),R.drawable.bubblepink);
    }

    @Override
    public void run() {

        if (y > yLimit) {
            y -= yVelocity;
        }

        if(y <= yLimit){
            exploded = true;
        }
    }

    public boolean ifExploded(){
        return exploded;
    }

    public void setExploded(boolean exploded){
        this.exploded = exploded;
    }

    public void setyLimit(int yLimit) {
        this.yLimit = yLimit;
    }
}
