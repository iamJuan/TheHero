
package com.games.ebocc.thehero.Clouds;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;

import com.games.ebocc.thehero.R;

import java.util.Random;

public class Clouds extends AppCompatImageView{
    public Clouds(Context context) {
        super(context);

        setX(new Random().nextInt(2500));//random for now
        setY(new Random().nextInt(500));

        setMaxHeight(300);
        setMaxWidth(350);
        setScaleType(ScaleType.FIT_XY);
        setAdjustViewBounds(true);

        setImageDrawable(getCloudDrawable());
    }

    private Drawable getCloudDrawable() {
        switch (new Random().nextInt(3)+1){
            case 1:
                return getResources().getDrawable(R.drawable.cloud1);
            case 2:
                return getResources().getDrawable(R.drawable.cloud3);
            case 3:
                return getResources().getDrawable(R.drawable.cloud4);
            case 4:
                return getResources().getDrawable(R.drawable.cloud5);
            default:
                return null;
        }
    }
}
