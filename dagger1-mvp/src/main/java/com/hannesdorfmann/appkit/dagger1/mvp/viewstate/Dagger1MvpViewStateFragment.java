package com.hannesdorfmann.appkit.dagger1.mvp.viewstate;

import android.app.Activity;
import android.view.View;
import com.hannesdorfmann.appkit.dagger1.Dagger1Injector;
import com.hannesdorfmann.appkit.mvp.MvpPresenter;
import com.hannesdorfmann.appkit.mvp.MvpView;
import com.hannesdorfmann.appkit.mvp.viewstate.MvpViewStateFragment;
import dagger.ObjectGraph;

/**
 * A {@link MvpViewStateFragment} with Dagger1 injection support
 *
 * @author Hannes Dorfmann
 * @see MvpViewStateFragment
 */
public abstract class Dagger1MvpViewStateFragment<AV extends View, M, V extends MvpView<M>, P extends MvpPresenter<V, M>>
    extends MvpViewStateFragment<AV, M, V, P> implements Dagger1Injector {

  @Override public ObjectGraph getObjectGraph() {

    Activity activity = getActivity();
    if (activity == null) {
      throw new NullPointerException("Activity is null");
    }

    if (activity.getApplication() == null) {
      throw new NullPointerException("Application is null");
    }

    if (!(activity.getApplication() instanceof Dagger1Injector)) {
      throw new IllegalStateException(
          "Your application have to implement " + Dagger1Injector.class.getCanonicalName());
    }

    return ((Dagger1Injector) activity.getApplication()).getObjectGraph();
  }
}
