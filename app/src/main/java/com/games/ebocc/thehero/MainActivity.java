package com.games.ebocc.thehero;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.games.ebocc.thehero.gameenv.GameView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout buttons;
    private GameView gameView;
    private FrameLayout game;

    private final int NO_DIRECTION = 0;
    private final int GO_LEFT = 1;
    private final int GO_RIGHT = 2;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        game = new FrameLayout(this);

        buttons = new RelativeLayout(this);

        Button rightButton = new Button(this);
        rightButton.setText(">");
        rightButton.setId(21690);

        Button leftButton = new Button(this);
        leftButton.setText("<");
        leftButton.setId(92188);

        gameView.maneuverHero(NO_DIRECTION);

        leftButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        gameView.changeWhereHeroIsFacing(GO_LEFT);
                        gameView.maneuverHero(GO_LEFT);
                        break;
                    case MotionEvent.ACTION_UP:
                        gameView.maneuverHero(NO_DIRECTION);
                        break;
                }

                return false;
            }
        });

        rightButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        gameView.changeWhereHeroIsFacing(GO_RIGHT);
                        gameView.maneuverHero(GO_RIGHT);
                        break;
                    case MotionEvent.ACTION_UP:
                        gameView.maneuverHero(NO_DIRECTION);
                        break;
                }

                return false;
            }
        });

        RelativeLayout.LayoutParams b1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams b2 = new RelativeLayout.LayoutParams(
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

        game.addView(gameView);
        game.addView(buttons);

        setContentView(game);
    }
}