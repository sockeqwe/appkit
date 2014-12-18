package com.hannesdorfmann.appkit.dagger1.adapter;

import android.content.Context;
import com.hannesdorfmann.appkit.adapter.SimpleExpandableAdapter;
import com.hannesdorfmann.appkit.dagger1.Injector;

/**
 * A {@link SimpleExpandableAdapter} with support for dagger1
 * @author Hannes Dorfmann
 */
public abstract  class DaggerSimpleExpandableAdapter extends SimpleExpandableAdapter {

  public DaggerSimpleExpandableAdapter(Context context, Injector injector) {
    super(context);
    if (injector == null){
      throw new IllegalArgumentException("Injector is null");
    }
    injector.getObjectGraph().inject(this);
  }
}
