package com.hannesdorfmann.appkit.recyclerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.hannesdorfmann.appkit.mvp.MvpPresenter;
import com.hannesdorfmann.appkit.mvp.MvpView;
import com.hannesdorfmann.appkit.mvp.viewstate.MvpViewStateFragment;

/**
 * A Fragment that supports view state (mvp), swiperefreshlayout (as content view), RecyclerView
 * and an empty view
 * (displayed if recyclerviews adapter is empty, no items). The inflated layout must contain a
 * RecyclerView with the id =
 * R.id.recyclerView and optionally an empty view with the id = R.id.recylcerEmptyView. Note that
 * recyclers empty is optional. If you don't want to use
 * the empty view override the method {@link #isEmptyViewEnabled()}
 *
 * @author Hannes Dorfmann
 */
public abstract class RecyclerFragment<A extends RecyclerView.Adapter, M, V extends MvpView<M>, P extends MvpPresenter<V, M>>
    extends MvpViewStateFragment<SwipeRefreshLayout, M, V, P> {

  protected A adapter;
  protected RecyclerView recyclerView;
  protected View emptyView;

  @Override protected void onViewInflated(View view) {
    super.onViewInflated(view);

    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

    if (recyclerView == null) {
      throw new IllegalArgumentException(
          "No recyclerview with the id = R.id.recyclerView has been found in your layout.");
    }

    adapter = createAdapter();
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(createLayoutManager());

    // empty view is optional
    emptyView = view.findViewById(R.id.recyclerEmptyView);
  }

  @Override public void showLoading(boolean pullToRefresh) {
    super.showLoading(pullToRefresh);
    if (pullToRefresh) {
      contentView.setRefreshing(true);
    }
  }

  @Override public void showError(Exception e, boolean pullToRefresh) {
    super.showError(e, pullToRefresh);
    contentView.setRefreshing(false);
  }

  @Override public void showContent() {

    if (isEmptyViewEnabled()) {
      if (adapter.getItemCount() == 0) {
        emptyView.setVisibility(View.VISIBLE);
      } else {
        emptyView.setVisibility(View.GONE);
      }
    }

    contentView.setRefreshing(false);
    super.showContent();
  }

  /**
   * Override this method and return false if you don't want to use the empty view anyway
   */
  protected boolean isEmptyViewEnabled() {
    return emptyView != null;
  }

  /**
   * Create the adapter for the RecyclerView
   */
  protected abstract A createAdapter();

  /**
   * Create the layout manager
   */
  protected abstract RecyclerView.LayoutManager createLayoutManager();
}
