package com.hannesdorfmann.appkit.dagger1.mvp;

import com.hannesdorfmann.appkit.dagger1.Injector;
import com.hannesdorfmann.appkit.mvp.MvpPresenter;
import com.hannesdorfmann.appkit.mvp.MvpView;

/**
 * A {@link MvpPresenter} with dagger 1 support
 * @author Hannes Dorfmann
 */
public class DaggerMvpPresenter<V extends MvpView<M>, M> extends MvpPresenter<V, M> {

  public DaggerMvpPresenter(Injector injector) {
    if (injector == null) {
      throw new IllegalArgumentException("Injector is null!");
    }

    injector.getObjectGraph().inject(this);
  }
}
