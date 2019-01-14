package com.games.ebocc.thehero.gameenv;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

public class GameObjects {

    protected Bitmap image;
    protected int x;
    protected int y;
    protected SurfaceView view;

    protected Rect rect;

    public GameObjects(int left, int top, SurfaceView view) {
        this.x = left;
        this.y = top;
        this.view = view;
        rect = new Rect();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public Rect getRect(){
        rect.set(x, y, x + image.getWidth(), y + image.getHeight());
        return rect;
    }
}
