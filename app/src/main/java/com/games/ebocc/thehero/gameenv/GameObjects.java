package com.games.ebocc.thehero.gameenv;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameObjects {

    protected Bitmap image;
    protected int x;
    protected int y;

    protected Rect rect;

    public GameObjects(Bitmap bitmap, int left, int top) {
        this.x = left;
        this.y = top;
        this.image = bitmap;
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
