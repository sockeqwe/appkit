package com.hannesdorfmann.appkit.mvp;

import android.view.View;
import com.hannesdorfmann.appkit.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.appkit.mvp.viewstate.ViewState;

/**
 * @author Hannes Dorfmann
 */
public abstract class MvpActivity<AV extends View, M, V extends MvpView<M>, P extends MvpPresenter<V, M>>
    extends MvpViewStateActivity<AV, M, V, P> {
  @Override public ViewState<M> createViewState() {
    return null;
  }

  @Override
  public boolean isRetainingViewState() {
    return false;
  }
}
