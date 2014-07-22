package com.hannesdorfmann.appkit.image;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * A ImageView that has states for beeing pressed etc. like any other view in combination with
 * selector.
 *
 * <p>
 * You can specify this selector for each TintableImageView in your xml layout by setting the tint
 * selector attribute:
 * <pre>{@code
 *  <com.hannesdorfmann.image.TintableImageView
 *    xmlns:custom="http://schemas.android.com/apk/res-auto"
 *    android:layout_width="match_parent"
 *    android:layout_height="match_parent"
 *    custom:tintSelector="@color/my_selector"
 *  />
 * }
 * </pre>
 *
 * You can also apply a style for all TintableImageViews in your application by specifying the
 * selector in your theme xml file:
 * <pre>
 *   {@code
 *    <item name="tintSelector"></item>
 *   }
 * </pre>
 *
 *
 * The selector itself is a simple color selector. So add a file in
 * res/values/color/my_selector.xml
 * <pre>
 *   {@code
 *   <?xml version="1.0" encoding="utf-8"?>
 *   <selector xmlns:android="http://schemas.android.com/apk/res/android">
 *     <item android:state_pressed="true" android:color="@color/my_highlighted_color"/>
 *     <item android:color="#00000000"/>
 *   </selector>
 *   }
 * </pre>
 * </p>
 *
 * @author Hannes Dorfmann
 */
public class TintableImageView extends ImageView {

  private ColorStateList tint;

  public TintableImageView(Context context) {
    super(context);
  }

  public TintableImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public TintableImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, attrs, defStyle);
  }

  private void init(Context context, AttributeSet attrs, int defStyle) {
    TypedArray a =
        context.obtainStyledAttributes(attrs, R.styleable.TintableImageView, defStyle, 0);
    tint = a.getColorStateList(R.styleable.TintableImageView_tintSelector);
    a.recycle();
  }

  @Override
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    if (tint != null && tint.isStateful()) updateTintColor();
  }

  public void setColorFilter(ColorStateList tint) {
    this.tint = tint;
    super.setColorFilter(tint.getColorForState(getDrawableState(), 0));
  }

  private void updateTintColor() {
    int color = tint.getColorForState(getDrawableState(), 0);
    setColorFilter(color);
  }
}
