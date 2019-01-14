package com.games.ebocc.thehero.gameenv;

import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.games.ebocc.thehero.R;

public class Cloud extends GameObjects{

    public Cloud(int left, int top, SurfaceView view) {
        super(left, top, view);
        this.image = BitmapFactory.decodeResource(view.getResources(), R.drawable.cloud1);
    }
}
