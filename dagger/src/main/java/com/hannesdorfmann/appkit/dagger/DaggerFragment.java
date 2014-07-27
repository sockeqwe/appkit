package com.hannesdorfmann.appkit.dagger;

import android.app.Activity;
import android.app.Fragment;
import dagger.ObjectGraph;

/**
 * @author Hannes Dorfmann
 */
public class DaggerFragment extends Fragment implements Injector {
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
}
