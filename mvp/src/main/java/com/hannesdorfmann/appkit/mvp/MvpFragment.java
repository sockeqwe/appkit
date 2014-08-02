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
import com.hannesdorfmann.appkit.mvp.viewstate.ViewState;
import icepick.Icepick;

/**
 * <b>Assumption:</b> There must be R.id.contentView, R.id.loadingView and R.id.errorView (type =
 * TextView)
 * specified in the inflated layout. You have to instantiate your presenter in the
 * init()
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
 * @param <M> The data type that will by displayed in this Fragment
 * @param <V> The type of the View (android view like ListView, FrameLayout etc.) that is displayed
 * as content view.
 * @param <P> The type of the presenter
 * @author Hannes Dorfmann
 */
public abstract class MvpFragment<M, V extends View, P extends MvpPresenter<MvpView<M>, M>>
    extends DaggerFragment implements MvpView<M> {

  protected V contentView;

  protected TextView errorView;

  protected View loadingView;

  protected P presenter;

  /**
   * Get the ViewState
   *
   * @return
   */
  protected ViewState<M> viewState;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO insert Fragment Args
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    Icepick.restoreInstanceState(this, savedInstanceState);

    if (presenter == null) {
      presenter = createPresenter(savedInstanceState);
    }

    View v = inflater.inflate(getLayoutRes(), container, false);

    onViewInflated(v);

    init(v, container, savedInstanceState);

    // Restore the view state if there is any.
    // Otherwise create a new one
    if (!restoreViewState(savedInstanceState)) {
      if (isRetainingViewState()) {
        viewState = createViewState();
        if (viewState == null) {
          throw new IllegalStateException("The ViewState can not be null! Return a valid ViewState "
              + "object from createViewState() or disable the ViewState feature by returning false "
              + "in isRetainingViewState()");
        }
      }

      loadData(false);
    }

    return v;
  }

  public void onSaveInstanceState(Bundle out) {
    super.onSaveInstanceState(out);
    Icepick.saveInstanceState(this, out);
    if (isRetainingViewState() && viewState != null) {

      if (!getRetainInstance()) {
        viewState.saveInstanceState(out);

        // Otherwise:
        // Instance will not be saved in bundle because fragments setRetainInstanceState
        // will already save and restore it
      }
    }
  }

  /**
   * This method will be called to restore the previous view state
   *
   * @return true, if the viewState has been restored. Otherwise false
   */
  protected boolean restoreViewState(Bundle saved) {

    if (!isRetainingViewState()) {
      return false;
    }

    if (!getRetainInstance()) {
      // no instance found from fragments setRetainInstanceState(true), so retrieve it from bundle
      viewState = ViewState.restoreInstanceState(saved);
    }

    if (viewState != null) {

      // Content was displayed
      if (viewState.wasShowingContent()) {
        M data = viewState.getLoadedData();
        setData(data);
        showContent();
        return true;
      }

      // Error was displayed
      if (viewState.wasShowingError()) {
        // Restore previous data, if there was any
        if (viewState.getLoadedData() != null) {
          setData(viewState.getLoadedData());
          showContent();
        }
        showError(viewState.getException(), viewState.isPullToRefresh());
        return true;
      }

      // Loading was displayed
      if (viewState.wasShowingLoading()) {

        // Restore previous data, if there was any
        if (viewState.getLoadedData() != null) {
          setData(viewState.getLoadedData());
          showContent();
        }

        showLoading(viewState.isPullToRefresh());
        loadData(viewState.isPullToRefresh());
        return true;
      }
    }

    return false;
  }

  /**
   * Called after the view has been inflated from xml layout specified in {@link #getLayoutRes()}
   * and
   * before
   * {@link #init(android.view.View, android.view.ViewGroup, android.os.Bundle)}
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
  protected abstract Integer getLayoutRes();

  /**
   * Implement this method to setup the view. Butterknife and restoring savedInstanceState has
   * already been handled for you. Also the layout has already been inflated ({@link
   * #getLayoutRes()}).
   * This is the right point to do additional (View) initialisation things, like setting up an
   * adapter
   * for ListViews etc.
   *
   * @param view The inflated view from xml layout. You have to specify the xml layout resource in
   * {@link #getLayoutRes()}
   * @param container The container
   * @param savedInstanceState The saved instance state
   */
  protected abstract void init(View view, ViewGroup container, Bundle savedInstanceState);

  /**
   * Create a ViewState object that matches the needs of your data
   */
  protected abstract ViewState<M> createViewState();

  /**
   * Creates a presenter instance
   */
  protected abstract P createPresenter(Bundle savedInstanceState);

  /**
   * This method will be invoked to load the data (model) that sould be display in this view
   * by calling the corresponding presenter method.
   */
  protected abstract void loadData(boolean pullToRefresh);

  /**
   * Get the presenter that is used. This is one will be used automatically call
   * {@link com.hannesdorfmann.appkit.mvp.MvpPresenter#onDestroy()} for you at correct time and
   * place.
   * So you don't have to care about it
   */
  protected P getPresenter() {
    return presenter;
  }

  /**
   * Return false if you don't want to use the whole ViewState mechanism at all.
   * Override this method and return false, if you want to disable the retaining ViewState
   * mechanism.
   *
   * @return true, if you want ViewState mechanism (i.e. for auto handling screen orientation
   * changes), otherwise false
   */
  protected boolean isRetainingViewState() {
    return true;
  }

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
