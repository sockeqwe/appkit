package com.hannesdorfmann.appkit.dagger1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.hannesdorfmann.appkit.adapter.SimpleAdapter;
import com.hannesdorfmann.appkit.dagger1.Dagger1Injector;

/**
 * This {@link BaseAdapter} encapsulate the getView() call into
 * {@link #newView(int, ViewGroup)} and {@link #bindView(int, int, View)}
 *
 * @author Hannes Dorfmann
 */
public abstract class Dagger1SimpleAdapter<D> extends SimpleAdapter<D> {


  public Dagger1SimpleAdapter(Context context, Dagger1Injector injector) {
    super(context);
    if (injector == null){
      throw new IllegalArgumentException("Injector is null");
    }
    injector.getObjectGraph().inject(this);
  }

}
