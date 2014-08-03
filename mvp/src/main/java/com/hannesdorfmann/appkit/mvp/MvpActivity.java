package com.hannesdorfmann.appkit.mvp;

import android.view.View;
import com.hannesdorfmann.appkit.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.appkit.mvp.viewstate.ViewState;

/**
 * @author Hannes Dorfmann
 */
public abstract class MvpActivity<M, V extends View, P extends MvpPresenter<MvpView<M>, M>>
    extends MvpViewStateActivity<M, V, P> {
  @Override public ViewState<M> createViewState() {
    return null;
  }

  @Override
  public boolean isRetainingViewState() {
    return false;
  }
}
