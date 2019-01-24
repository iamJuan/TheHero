package com.games.ebocc.thehero.gameenv;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceView;

public class GameEntities {

    protected Bitmap image;

    protected int x;
    protected int y;
    protected SurfaceView view;

    protected Rect rect;

    public GameEntities(int left, int top, SurfaceView view) {
        this.x = left;
        this.y = top;
        this.view = view;
        rect = new Rect();
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.rgb(255, 255, 255));
        canvas.drawRect(rect, p);

        canvas.drawBitmap(image, x, y, null);
    }

    public Rect getRect(){
        rect.set(x+20, y+20, x + (image.getWidth()-20), y + (image.getHeight()-20));
        return rect;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
