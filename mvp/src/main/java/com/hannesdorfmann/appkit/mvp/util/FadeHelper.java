package com.hannesdorfmann.appkit.mvp.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

/**
 * @author Hannes Dorfmann
 */
@SuppressLint("NewApi")
public class FadeHelper {

    private static final int DURATION = 200;




    public static void showLoading(View loadingView, View contentView,
                                   View errorView){

            contentView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
    }


    /**
     * Shows the content view if a pull to refresh has been executed
     *
     * @param loadingView
     * @param contentView
     * @param errorView
     */
    public static void showContent( final View loadingView,
                                   final View contentView, final  View errorView){



        if (contentView.getVisibility() == View.VISIBLE){
            // No Changing needed, because contentView is already visible
            errorView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);

        } else {


            if (Build.VERSION.SDK_INT < 11) {
                // Before honeycomb

                loadingView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                contentView.setVisibility(View.VISIBLE);

            } else {

                // HoneyComb and above
                errorView.setVisibility(View.GONE);

                // Not visible yet, so animate the view in
                AnimatorSet set = new AnimatorSet();
                ObjectAnimator contentIn = ObjectAnimator.ofFloat(contentView, "alpha", 1f);
                ObjectAnimator loadingOut = ObjectAnimator.ofFloat(loadingView, "alpha", 0f);

                set.playTogether(contentIn, loadingOut);
                set.setDuration(DURATION);


                set.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        contentView.setVisibility(View.VISIBLE);
                    }


                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        loadingView.setVisibility(View.GONE);
                        loadingView.setAlpha(1f); // For future showLoading calls

                    }
                });

                set.start();


            }
        }
    }


    /**
     * Shows the error view after a little fade animation
     * @param errorMessage
     * @param errorView
     * @param loadingView
     * @param contentView
     */
    public static void showErrorView(String errorMessage, final  View loadingView,
                                     final View contentView, final TextView errorView){


        errorView.setText(errorMessage);

        if (Build.VERSION.SDK_INT < 11) {
            // Before honeycomb
            contentView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);

        } else {

            contentView.setVisibility(View.GONE);


            // Not visible yet, so animate the view in
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator in = ObjectAnimator.ofFloat(errorView, "alpha", 1f);
            ObjectAnimator loadingOut = ObjectAnimator.ofFloat(loadingView, "alpha", 0f);

            set.playTogether(in, loadingOut);
            set.setDuration(DURATION);


            set.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    errorView.setVisibility(View.VISIBLE);
                }


                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    loadingView.setVisibility(View.GONE);
                    loadingView.setAlpha(1f); // For future showLoading calls

                }
            });

            set.start();
        }

    }
}
