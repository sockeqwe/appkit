package com.hannesdorfmann.appkit.dagger;

import android.app.Activity;
import android.os.Bundle;
import dagger.ObjectGraph;

/**
 * A Fragment class that can be used as base class for each fragment that supports Dagger injection
 *
 * @author Hannes Dorfmann
 */
public class DaggerFragment extends android.support.v4.app.Fragment implements Injector {

  @Override public ObjectGraph getObjectGraph() {
    Activity act = getActivity();

    if (act == null) {
      throw new NullPointerException("Activity of Fragment is null");
    }

    if (!(act.getApplication() instanceof Injector)) {
      throw new IllegalArgumentException("The application class of your android app must implement "
          + Injector.class.getCanonicalName());
    }

    Injector injector = (Injector) act.getApplication();
    ObjectGraph og = injector.getObjectGraph();

    if (og == null) {
      throw new NullPointerException("Object Graph is null");
    }

    return og;
  }

  @Override
  public void onCreate(Bundle saved) {
    super.onCreate(saved);
    getObjectGraph().inject(this);
  }
}
