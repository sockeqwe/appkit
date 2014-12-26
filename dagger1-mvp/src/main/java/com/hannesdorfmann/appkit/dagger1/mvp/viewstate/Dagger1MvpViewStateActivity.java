package com.hannesdorfmann.appkit.dagger1.mvp.viewstate;

import android.view.View;
import com.hannesdorfmann.appkit.dagger1.Dagger1Injector;
import com.hannesdorfmann.appkit.mvp.MvpPresenter;
import com.hannesdorfmann.appkit.mvp.MvpView;
import com.hannesdorfmann.appkit.mvp.viewstate.MvpViewStateActivity;
import dagger.ObjectGraph;

/**
 * A {@link MvpViewStateActivity} with dagger 1 support
 *
 * @author Hannes Dorfmann
 * @see MvpViewStateActivity
 */
public abstract class Dagger1MvpViewStateActivity<AV extends View, M, V extends MvpView<M>, P extends MvpPresenter<V, M>>
    extends MvpViewStateActivity<AV, M, V, P> implements Dagger1Injector {

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
