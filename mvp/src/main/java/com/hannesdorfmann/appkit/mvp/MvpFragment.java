package com.hannesdorfmann.appkit.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.hannesdorfmann.appkit.dagger.DaggerFragment;
import com.hannesdorfmann.appkit.mvp.util.FadeHelper;
import icepick.Icepick;

/**
 * <b>Assumption:</b> There must be R.id.contentView, R.id.loadingView and R.id.errorView (type =
 * TextView)
 * specified in the inflated layout. You have to instantiate your presenter in the onCreateView()
 * method (onDestroyView() will call the presenters onDestory() method).
 * If you instantiate your presenter in fragments onCreate() than you also have to call {@link
 * com.hannesdorfmann.appkit.mvp.MvpPresenter#onDestroy()}
 * in fragments onDestroy().
 *
 * <p>
 * It already implements the default behaviours of {@link MvpView}.
 * For custom error messages you have to implement {@link #getErrorMessage(Exception, boolean)} and
 * for displaying error messages in a custom way you can override {@link #showLightError(String)}
 * </p>
 *
 * <p>
 * Uses Butterknife and IcePick: So you can use Butterknife and IcePick in any subclass
 * </p>
 *
 * @param <V> The type of the View (android view like ListView, FrameLayout etc.) that is displayed
 * as content view.
 * @param <D> The data type that will by displayed in this Fragment
 * @author Hannes Dorfmann
 */
public abstract class MvpFragment<V extends View, D> extends DaggerFragment implements MvpView<D> {

  protected V contentView;

  protected TextView errorView;

  protected View loadingView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    Icepick.restoreInstanceState(this, savedInstanceState);

    View v = inflater.inflate(getLayout(), container, false);

    onViewInflated(v);

    onCreateView(v, container, savedInstanceState);
    return v;
  }

  public void onSaveInstanceState(Bundle out) {
    super.onSaveInstanceState(out);
    Icepick.saveInstanceState(this, out);
  }

  /**
   * Called after the view has been inflated from xml layout specified in {@link #getLayout()} and
   * before
   * {@link #onCreateView(android.view.View, android.view.ViewGroup, android.os.Bundle)}
   */
  protected void onViewInflated(View view) {

    ButterKnife.inject(this, view);

    contentView = (V) view.findViewById(R.id.contentView);
    loadingView = view.findViewById(R.id.loadingView);
    errorView = (TextView) view.findViewById(R.id.errorView);

    if (contentView == null) {
      throw new IllegalStateException("The content view is not specified. "
          + "You have to provide a View with R.id.contentView in your inflated xml layout");
    }

    if (loadingView == null) {
      throw new IllegalStateException("The loading view is not specified. "
          + "You have to provide a View with R.id.loadingView in your inflated xml layout");
    }

    if (contentView == null) {
      throw new IllegalStateException("The error view is not specified. "
          + "You have to provide a View with R.id.errorView in your inflated xml layout");
    }
  }

  /**
   * Get the layout resource id for the layout that should be inflated.
   * This method will be called in {@link #onCreateView(android.view.LayoutInflater,
   * android.view.ViewGroup, android.os.Bundle)}
   */
  protected abstract int getLayout();

  /**
   * Implement this method to setup the view. Butterknife and restoring savedInstanceState has
   * alredy been handled for you.
   *
   * @param view The inflated view from xml layout. You have to specify the xml layout resource in
   * {@link #getLayout()}
   */
  protected abstract void onCreateView(View view, ViewGroup container, Bundle savedInstanceState);

  /**
   * Get the presenter that is used. This is one will be used automatically call
   * {@link com.hannesdorfmann.appkit.mvp.MvpPresenter#onDestroy()} for you at correct time and
   * place.
   * So you don't have to care about it
   */
  protected abstract MvpPresenter getPresenter();

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    if (getPresenter() != null) {
      getPresenter().onDestroy();
    }
  }

  /**
   * Called if the user clicks on the error view
   */
  protected abstract void onErrorViewClicked();

  @Override
  public void showLoading(boolean pullToRefresh) {

    if (!pullToRefresh) {
      FadeHelper.showLoading(loadingView, contentView, errorView);
    }
    // Otherwise it was a pull to refresh, and the content view is already displayed
    // (otherwise pull to refresh could not be started)
  }

  @Override
  public void showContent() {
    FadeHelper.showContent(loadingView, contentView, errorView);
  }

  /**
   * Get the error message for a certain Exception that will be shown on {@link
   * #showError(Exception, boolean)}
   */
  protected abstract String getErrorMessage(Exception e, boolean contentPresent);

  /**
   * The default behaviour is to display a toast message as light error (i.e. pull-to-refresh
   * error).
   * Override this method if you want to display the light error in another way (like crouton).
   */
  protected void showLightError(String msg) {
    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showError(Exception e, boolean pullToRefresh) {

    String errorMsg = getErrorMessage(e, pullToRefresh);

    if (pullToRefresh) {
      showLightError(errorMsg);
    } else {
      FadeHelper.showErrorView(errorMsg, loadingView, contentView, errorView);
    }
  }
}
