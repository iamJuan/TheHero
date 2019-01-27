package com.games.ebocc.thehero.balloons;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

import com.games.ebocc.thehero.gameenv.GameEntities;

public class Message extends GameEntities{

    private String message = "HiEmEm!WillumarryJohn?";
    private int index;
    private boolean willShow = false;
    private Typeface tf;

    public Message(int left, int top, SurfaceView view, int index) {
        super(left, top, view);
        this.index = index;
        tf = Typeface.createFromAsset(view.getContext().getAssets(),"font/myfont.ttf");
    }

    public void appear(boolean willShow){
        this.willShow = willShow;
    }

    @Override
    public void draw(Canvas canvas) {
        if(willShow) {
            Paint timer = new Paint();
            timer.setTypeface(tf);
            timer.setTextSize(150);
            timer.setColor(Color.rgb(214, 0, 96));
            canvas.drawText(""+(message.charAt(index)), x + 50, y + 140, timer);
        }
    }
}
