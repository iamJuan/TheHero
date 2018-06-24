package com.games.ebocc.thehero.Clouds;

import android.content.Context;
import android.widget.RelativeLayout;

public class CloudGenerator {

    private final Context context;
    private final RelativeLayout relativeLayout;
    private final RelativeLayout.LayoutParams layoutParams;

    public CloudGenerator(Context context, RelativeLayout relativeLayout, RelativeLayout.LayoutParams layoutParams){
        this.context = context;
        this.relativeLayout = relativeLayout;
        this.layoutParams = layoutParams;
    }

    public void generateClouds() {
        Clouds clouds;

        for(int j = 0; j < 10; j++) {
            clouds = new Clouds(context);

            clouds.setLayoutParams(layoutParams);
            relativeLayout.addView(clouds);
        }
    }
}
