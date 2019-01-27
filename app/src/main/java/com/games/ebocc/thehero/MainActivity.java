package com.games.ebocc.thehero;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.games.ebocc.thehero.gameenv.GameView;
import com.games.ebocc.thehero.gameenv.Hero;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout buttons;
    private GameView gameView;
    private ImageView bgView;
    private FrameLayout game;
    private Button rightButton;
    private Button leftButton;

    private final int NO_DIRECTION = 0;
    private final int GO_LEFT = 1;
    private final int GO_RIGHT = 2;

    private Hero hero;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = new FrameLayout(this);

        buttons = new RelativeLayout(this);

        rightButton = new Button(this);
        rightButton.setText(">");
        rightButton.setId(21690);

        leftButton = new Button(this);
        leftButton.setText("<");
        leftButton.setId(92188);

        gameView = new GameView(this);

        hero = gameView.getHero();

        hero.maneuverHero(NO_DIRECTION);

        leftButton.setOnTouchListener(new ButtonListener());
        rightButton.setOnTouchListener(new ButtonListener());

        RelativeLayout.LayoutParams b1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams b2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams scoreLayout = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);

        buttons.setLayoutParams(params);
        buttons.addView(leftButton);
        buttons.addView(rightButton);
        b2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        b2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        b1.addRule(RelativeLayout.LEFT_OF, 21690);
        b1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        leftButton.setLayoutParams(b1);
        rightButton.setLayoutParams(b2);

        gameView.setZOrderOnTop(true);
        gameView.getHolder().setFormat(PixelFormat.TRANSPARENT);

        bgView = new ImageView(this);
        bgView.setBackgroundResource(R.drawable.sky);

        RelativeLayout.LayoutParams fillParentLayout = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        RelativeLayout rootPanel = new RelativeLayout(this);
        rootPanel.setLayoutParams(fillParentLayout);
        rootPanel.addView(gameView, fillParentLayout);
        rootPanel.addView(bgView, fillParentLayout);
        rootPanel.addView(buttons, fillParentLayout);

        game.addView(rootPanel);

        setContentView(game);
    }

    private class ButtonListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(view.getId() == leftButton.getId()){
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        hero.maneuverHero(GO_LEFT);
                        break;
                    case MotionEvent.ACTION_UP:
                        hero.maneuverHero(NO_DIRECTION);
                        break;
                }
            }else if(view.getId() == rightButton.getId()){
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        hero.maneuverHero(GO_RIGHT);
                        break;
                    case MotionEvent.ACTION_UP:
                        hero.maneuverHero(NO_DIRECTION);
                        break;
                }

            }
            return false;
        }
    }
}