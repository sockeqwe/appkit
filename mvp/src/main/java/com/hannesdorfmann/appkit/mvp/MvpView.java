package com.hannesdorfmann.appkit.mvp;

/**
 * @author Hannes Dorfmann
 */
public interface MvpView<D> {

  /**
   * Shows the loading animation
   *
   * @param pullToRefresh is showing loading triggered by pullToRefresh
   */
  public void showLoading(boolean pullToRefresh);

  /**
   * Shows the content view instead of the loadingView
   */
  public void showContent();

  /**
   * Shows an error indicator on the screen (depends on pullToRefresh to dipsplay a light error
   * message (like a Toast)
   * or replaces the loading view / content view with and independent error view)
   */
  public void showError(Exception e, boolean pullToRefresh);

  /**
   * Sets the data that should be displayed in this view
   */
  public void setData(D data);

}
