package com.hannesdorfmann.appkit.mvp;

import java.lang.ref.WeakReference;

/**
 * A simple presenter
 *
 * @author Hannes Dorfmann
 */
public class MvpPresenter<V extends MvpView<M>, M> {

  protected WeakReference<V> viewReference;

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

  public void onDestroy(boolean retainInstanceState) {
    viewReference = null;
  }


}