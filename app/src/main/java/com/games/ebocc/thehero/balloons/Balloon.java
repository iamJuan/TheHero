package com.games.ebocc.thehero.balloons;

import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;
import com.games.ebocc.thehero.gameenv.GameEntities;

public class Balloon extends GameEntities implements Runnable{

    private int yVelocity = 7;
    private boolean exploded = false;

    public Balloon(int left, int top, SurfaceView view) {
        super(left, top, view);
        this.image = BitmapFactory.decodeResource(view.getResources(),R.drawable.pinkballoon);
    }

    @Override
    public void run() {

        if (y > -400) {
            y -= yVelocity;
        }

        if(y <= -400){
            exploded = true;
        }
    }

    public boolean ifExploded(){
        return exploded;
    }

    public void setExploded(boolean exploded){
        this.exploded = exploded;
    }
}
