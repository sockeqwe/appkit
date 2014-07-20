package com.hannesdorfmann.appkit.mvp;

/**
 *
 * @author Hannes Dorfmann
 */
public interface MvpView<D> {


    /**
     * Shows the loading animation (typically progress bar)
     */
    public void showLoading();

    /**
     * Shows the content view instead of the loadingView
     */
    public void showContent();

    /**
     * Shows an error indicator on the screen (depends on contentPresent to dipsplay a light error message (like a Toast)
     * or replaces the loading view / content view with and independent error view)
     * @param e
     * @param contentPresent
     */
    public void showError(Exception e, boolean contentPresent);

    /**
     * Sets the data that should be displayed in this view
     * @param data
     */
    public void setData(D data);


}
