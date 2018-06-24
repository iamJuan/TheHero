package com.games.ebocc.thehero;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.games.ebocc.thehero.Clouds.CloudGenerator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout relativeLayout = findViewById(R.id.rlayout);
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.sky));

        CloudGenerator cloudGenerator = new CloudGenerator(getApplicationContext(), relativeLayout, layoutParams);
        cloudGenerator.generateClouds();

        ImageView mountains = new ImageView(getApplicationContext());
        mountains.setImageDrawable(getResources().getDrawable(R.drawable.mountain));
        mountains.setScaleType(ImageView.ScaleType.FIT_XY);
        relativeLayout.addView(mountains);
    }
}