package com.hannesdorfmann.appkit.dagger;

import android.app.Activity;
import android.os.Bundle;
import butterknife.ButterKnife;
import dagger.ObjectGraph;
import icepick.Icepick;

/**
 * A base Activity class that support dependency injection with dagger.
 * @author Hannes Dorfmann
 */
public class DaggerActivity extends Activity implements Injector {

  @Override
  public ObjectGraph getObjectGraph() {

    if (!(getApplication() instanceof Injector)) {
      throw new IllegalArgumentException("The application class of your android app must implement "
          + Injector.class.getCanonicalName());
    }

    Injector injector = (Injector) getApplication();

    ObjectGraph og = injector.getObjectGraph();

    if (og == null) {
      throw new NullPointerException("Object Graph is null!");
    }

    return og;
  }

  @Override
  protected void onCreate(Bundle savedInstance) {
    super.onCreate(savedInstance);
    getObjectGraph().inject(this);
    Icepick.restoreInstanceState(this, savedInstance);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Icepick.saveInstanceState(this, outState);
  }

  @Override
  public void onContentChanged() {
    super.onContentChanged();
    ButterKnife.inject(this);
  }
}
