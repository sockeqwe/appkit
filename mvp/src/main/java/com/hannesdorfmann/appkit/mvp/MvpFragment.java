package com.hannesdorfmann.appkit.mvp;

import android.view.View;
import com.hannesdorfmann.appkit.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.appkit.mvp.viewstate.ViewState;

/**
 * @author Hannes Dorfmann
 */
public abstract class MvpFragment<AV extends View, M, V extends MvpView<M>, P extends MvpPresenter<V, M>>
    extends MvpViewStateFragment<AV, M, V, P> {

  @Override protected ViewState<M> createViewState() {
    return null;
  }

  public boolean isRetainingViewState() {
    return false;
  }
}
