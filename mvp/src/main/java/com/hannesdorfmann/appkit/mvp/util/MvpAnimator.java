package com.hannesdorfmann.appkit.mvp.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import com.hannesdorfmann.appkit.screen.DimensUtils;

/**
 * Little helper class for animating content, error and loading view
 * @author Hannes Dorfmann
 */
public class MvpAnimator {

  public static int DURATION = 600;

  /**
   * Shows the content view if a pull to refresh has been executed
   */
  @TargetApi(11)
  public static void showContent(final View loadingView, final View contentView,
      final View errorView) {

    if (contentView.getVisibility() == View.VISIBLE) {
      // No Changing needed, because contentView is already visible
      errorView.setVisibility(View.GONE);
      loadingView.setVisibility(View.GONE);
    } else {

      if (Build.VERSION.SDK_INT < 14) {
        // Before honeycomb

        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
      } else {

        // ICS and above

        errorView.setVisibility(View.GONE);

        int translateDp = 40;
        // Not visible yet, so animate the view in
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator contentFadeIn = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f);
        ObjectAnimator contentTranslateIn = ObjectAnimator.ofFloat(contentView, "translationY",
            DimensUtils.dpToPx(loadingView.getContext(), translateDp), 0);

        ObjectAnimator loadingFadeOut = ObjectAnimator.ofFloat(loadingView, "alpha", 1f, 0f);
        ObjectAnimator loadingTranslateOut = ObjectAnimator.ofFloat(loadingView, "translationY", 0,
            -DimensUtils.dpToPx(loadingView.getContext(), translateDp));

        set.playTogether(contentFadeIn, contentTranslateIn, loadingFadeOut, loadingTranslateOut);
        set.setDuration(DURATION);

        set.addListener(new AnimatorListenerAdapter() {

          @Override
          public void onAnimationStart(Animator animation) {
            contentView.setTranslationY(0);
            loadingView.setTranslationY(0);
            contentView.setVisibility(View.VISIBLE);
          }

          @Override
          public void onAnimationEnd(Animator animation) {
            loadingView.setVisibility(View.GONE);
            loadingView.setAlpha(1f); // For future showLoading calls
            contentView.setTranslationY(0);
            loadingView.setTranslationY(0);
          }
        });

        set.start();
      }
    }
  }
}