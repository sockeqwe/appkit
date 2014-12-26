package com.hannesdorfmann.appkit.dagger1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import butterknife.ButterKnife;
import dagger.ObjectGraph;
import icepick.Icepick;

/**
 * A base Activity class that support dependency injection with dagger, Butterknife and icepick
 *
 * @author Hannes Dorfmann
 */
public class Dagger1Activity extends ActionBarActivity implements Dagger1Injector {

  @Override
  public ObjectGraph getObjectGraph() {

    if (!(getApplication() instanceof Dagger1Injector)) {
      throw new IllegalArgumentException("The application class of your android app must implement "
          + Dagger1Injector.class.getCanonicalName());
    }

    Dagger1Injector injector = (Dagger1Injector) getApplication();

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

  public void setContentView(int layoutRes) {
    super.setContentView(layoutRes);
    ButterKnife.inject(this);
  }
}
