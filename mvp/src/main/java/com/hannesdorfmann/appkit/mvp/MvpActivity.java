package com.hannesdorfmann.appkit.mvp;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.appkit.dagger.DaggerActivity;
import com.hannesdorfmann.appkit.mvp.util.FadeHelper;

/**
 * A Base activity.
 *
 * <p>
 * <b>Assumption:</b> There must be R.id.contentView, R.id.loadingView and R.id.errorView (type =
 * TextView)
 * specified in the inflated layout
 * </p>
 *
 * <p>
 * It already implements the default behaviours of {@link MvpView}.
 * For custom error messages you have to implement {@link #getErrorMessage(Exception, boolean)} and
 * for displaying error messages in a custom way you can override {@link #showLightError(String)}
 * </p>
 *
 * <p>
 * This class uses IcePick Annotation processing to make working with onSaveInstanceState() much
 * easier.
 * Simply use <code>@Icicle</code> to mark fields which state should be saved in
 * onSaveInstanceState().
 * </p>
 *
 * <p>
 * Uses Butterknife: So you can use Butterknife in any subclass
 * </p>
 *
 * @param <V> The type of the View (android view like ListView, FrameLayout etc.) that is displayed
 * as content view.
 * @param <D> The data type that will by displayed in this Fragment
 * @author Hannes Dorfmann
 */
public abstract class MvpActivity<V extends View, D> extends DaggerActivity
    implements MvpView<D> {

  protected V contentView;

  protected TextView errorView;

  protected View loadingView;

  @SuppressLint("WrongViewCast") @Override
  public void onContentChanged() {
    super.onContentChanged();

    contentView = (V) findViewById(R.id.contentView);
    errorView = (TextView) findViewById(R.id.errorView);
    loadingView = findViewById(R.id.loadingView);

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

    errorView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onErrorViewClicked();
      }
    });
  }

  /**
   * Called if the user clicks on the error view
   */
  protected abstract void onErrorViewClicked();

  /**
   * Get the presenter that is used. This is one will be used automatically call
   * {@link com.hannesdorfmann.appkit.mvp.MvpPresenter#onDestroy()} for you at correct time and
   * place.
   * So you don't have to care about it
   */
  protected abstract MvpPresenter getPresenter();

  @Override
  public void onDestroy() {
    if (getPresenter() != null) {
      getPresenter().onDestroy();
    }
  }

  @Override
  public void showLoading() {

    FadeHelper.showLoading(loadingView, contentView, errorView);
  }

  @Override
  public void showContent() {

    FadeHelper.showContent(loadingView, contentView, errorView);
  }

  /**
   * /**
   * The default behaviour is to display a toast message as light error (i.e. pull-to-refresh
   * error).
   * Override this method if you want to display the light error in another way (like crouton).
   */
  protected void showLightError(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }

  /**
   * Get the error message for a certain Exception that will be shown on {@link
   * #showError(Exception, boolean)}
   */
  protected abstract String getErrorMessage(Exception e, boolean contentPresent);

  @Override
  public void showError(Exception e, boolean contentPresent) {
    String errorMsg = getErrorMessage(e, contentPresent);
    if (contentPresent) {
      showLightError(errorMsg);
    } else {
      FadeHelper.showErrorView(errorMsg, loadingView, contentView, errorView);
    }
  }
}
