package com.hannesdorfmann.appkit.mvp;

import com.hannesdorfmann.appkit.dagger.Injector;
import java.lang.ref.WeakReference;

/**
 * A simple presenter
 *
 * @author Hannes Dorfmann
 */
public class MvpPresenter<V extends MvpView<D>, D> {

  protected WeakReference<V> viewReference;

  public MvpPresenter(Injector injector) {
    injector.getObjectGraph().inject(this);
  }

  public MvpPresenter(Injector injector, V view) {
    this(injector);
    setView(view);
  }

  protected V getView() {
    if (viewReference != null) {
      return viewReference.get();
    }
    return null;
  }

  public boolean isViewAttached() {
    return getView() != null;
  }

  public void setView(V view){
    viewReference = new WeakReference<V>(view);
  }

  public void onDestroy() {
    viewReference = null;
  }


}