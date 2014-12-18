package com.hannesdorfmann.appkit.dagger1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.hannesdorfmann.appkit.adapter.SimpleAdapter;
import com.hannesdorfmann.appkit.dagger1.Injector;

/**
 * This {@link BaseAdapter} encapsulate the getView() call into
 * {@link #newView(int, ViewGroup)} and {@link #bindView(int, int, View)}
 *
 * @author Hannes Dorfmann
 */
public abstract class DaggerSimpleAdapter<D> extends SimpleAdapter<D> {


  public DaggerSimpleAdapter(Context context, Injector injector) {
    super(context);
    if (injector == null){
      throw new IllegalArgumentException("Injector is null");
    }
    injector.getObjectGraph().inject(this);
  }

}
