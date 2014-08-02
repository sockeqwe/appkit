package com.hannesdorfmann.appkit.mvp;

import android.view.View;
import com.hannesdorfmann.appkit.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.appkit.mvp.viewstate.ViewState;

/**
 * @author Hannes Dorfmann
 */
public abstract class MvpActivity<D, V extends View, P extends MvpPresenter<MvpView<D>, D>>
    extends MvpViewStateActivity<D, V, P> {
  @Override public ViewState<D> createViewState() {
    return null;
  }

  @Override
  public boolean isRetainingViewState() {
    return false;
  }
}
