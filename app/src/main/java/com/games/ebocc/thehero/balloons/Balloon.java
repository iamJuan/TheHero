package com.games.ebocc.thehero.balloons;

import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.gameenv.GameEntities;

public class Balloon extends GameEntities implements Runnable{

    private int yVelocity = 5;

    public Balloon(int left, int top, SurfaceView view) {
        super(left, top, view);
        this.image = BitmapFactory.decodeResource(view.getResources(),R.drawable.enemyright);
    }

    @Override
    public void run() {

        if (y > -200) {
            y -= yVelocity;
        }
    }
}
