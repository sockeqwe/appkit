package com.hannesdorfmann.appkit.dagger1.adapter;

import android.content.Context;
import com.hannesdorfmann.appkit.adapter.SimpleSpinnerAdapter;
import com.hannesdorfmann.appkit.dagger1.Injector;

/**
 * @author Hannes Dorfmann
 */
public abstract class DaggerSimpleSpinnerAdapter<D> extends SimpleSpinnerAdapter<D> {

  protected DaggerSimpleSpinnerAdapter(Context context, Injector injector) {
    super(context);
    if (injector == null){
      throw new IllegalArgumentException("Injector is null");
    }
    injector.getObjectGraph().inject(this);
  }
}
