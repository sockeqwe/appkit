package com.hannesdorfmann.appkit.mvp;

import android.view.View;
import com.hannesdorfmann.appkit.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.appkit.mvp.viewstate.ViewState;

/**
 * @author Hannes Dorfmann
 */
public abstract class MvpFragment<M, V extends View, P extends MvpPresenter<MvpView<M>, M>>
    extends MvpViewStateFragment<M, V, P> {

  @Override protected ViewState<M> createViewState() {
    return null;
  }

  public boolean isRetainingViewState() {
    return false;
  }
}
