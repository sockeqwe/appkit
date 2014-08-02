package com.hannesdorfmann.appkit.image;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Hannes Dorfmann
 */
public class BrightnessImageView extends ImageView {

  private float brightness = 0;

  public BrightnessImageView(Context context) {
    super(context);
  }

  public BrightnessImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public BrightnessImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }


  public void setBrightness(float brightness){

    float[] colorMatrix = {
        0.33f, 0.33f, 0.33f, 0, brightness, //red
        0.33f, 0.33f, 0.33f, 0, brightness, //green
        0.33f, 0.33f, 0.33f, 0, brightness, //blue
        0, 0, 0, 1, 0    //alpha
    };

    ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
    setColorFilter(colorFilter);
  }


  public float getBrightness(){
    return brightness;
  }

}
