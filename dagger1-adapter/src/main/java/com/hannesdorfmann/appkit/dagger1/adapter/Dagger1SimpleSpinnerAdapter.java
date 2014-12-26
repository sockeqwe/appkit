package com.hannesdorfmann.appkit.dagger1.adapter;

import android.content.Context;
import com.hannesdorfmann.appkit.adapter.SimpleSpinnerAdapter;
import com.hannesdorfmann.appkit.dagger1.Dagger1Injector;

/**
 * @author Hannes Dorfmann
 */
public abstract class Dagger1SimpleSpinnerAdapter<D> extends SimpleSpinnerAdapter<D> {

  protected Dagger1SimpleSpinnerAdapter(Context context, Dagger1Injector injector) {
    super(context);
    if (injector == null){
      throw new IllegalArgumentException("Injector is null");
    }
    injector.getObjectGraph().inject(this);
  }
}
