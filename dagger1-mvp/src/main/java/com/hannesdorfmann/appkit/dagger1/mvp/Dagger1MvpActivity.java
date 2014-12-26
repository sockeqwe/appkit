package com.hannesdorfmann.appkit.dagger1.mvp;

import android.view.View;
import com.hannesdorfmann.appkit.dagger1.Dagger1Injector;
import com.hannesdorfmann.appkit.mvp.MvpActivity;
import com.hannesdorfmann.appkit.mvp.MvpPresenter;
import com.hannesdorfmann.appkit.mvp.MvpView;
import dagger.ObjectGraph;

/**
 * A {@link MvpActivity} with support for Dagger1
 *
 * @author Hannes Dorfmann
 * @see MvpActivity
 */
public abstract class Dagger1MvpActivity<AV extends View, M, V extends MvpView<M>, P extends MvpPresenter<V, M>>
    extends MvpActivity<AV, M, V, P> implements Dagger1Injector {

  @Override public ObjectGraph getObjectGraph() {

    if (getApplication() == null) {
      throw new NullPointerException("Application is null");
    }

    if (!(getApplication() instanceof Dagger1Injector)) {
      throw new IllegalStateException(
          "Your application have to implement " + Dagger1Injector.class.getCanonicalName());
    }

    return ((Dagger1Injector) getApplication()).getObjectGraph();
  }
}
