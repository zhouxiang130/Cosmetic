package com.yj.cosmetics.widget;

import android.graphics.Color;
import android.view.View;

/**
 * Created by Suo on 2017/4/13.
 */

public class MaterialRippleView {



    public static View create(View view){
         return MaterialRippleLayout.on(view)
                .rippleOverlay(true)
                /*.rippleAlpha(0.2f)
                .rippleColor(0xFF585858)*/
                 .rippleColor(Color.parseColor("#B7B7B7"))
                .rippleHover(true)
                .create();
    }
}
