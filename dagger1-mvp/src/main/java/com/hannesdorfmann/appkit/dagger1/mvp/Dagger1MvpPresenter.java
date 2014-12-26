package com.hannesdorfmann.appkit.dagger1.mvp;

import com.hannesdorfmann.appkit.dagger1.Dagger1Injector;
import com.hannesdorfmann.appkit.mvp.MvpPresenter;
import com.hannesdorfmann.appkit.mvp.MvpView;

/**
 * A {@link MvpPresenter} with dagger 1 support
 *
 * @author Hannes Dorfmann
 * @see MvpPresenter
 */
public class Dagger1MvpPresenter<V extends MvpView<M>, M> extends MvpPresenter<V, M> {

  public Dagger1MvpPresenter(Dagger1Injector injector) {
    if (injector == null) {
      throw new IllegalArgumentException("Injector is null!");
    }

    injector.getObjectGraph().inject(this);
  }
}
