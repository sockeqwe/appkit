package com.hannesdorfmann.appkit.mvp.httpkit;

import com.hannesdorfmann.appkit.mvp.MvpPresenter;
import com.hannesdorfmann.appkit.mvp.MvpView;
import com.hannesdorfmann.httpkit.HttpKit;
import com.hannesdorfmann.httpkit.request.HttpRequest;
import com.hannesdorfmann.httpkit.response.HttpResponse;
import com.hannesdorfmann.httpkit.response.HttpResponseReceiver;

/**
 * A MvpPresenter that uses httpkit to make http calls
 * @author Hannes Dorfmann
 */
public class MvpHttpPresenter <V extends MvpView<D>, D> extends MvpPresenter<V, D> {

  protected HttpKit httpKit;

  public void setHttpKit(HttpKit httpKit) {
    this.httpKit = httpKit;
  }

  protected void loadData(HttpRequest request, final boolean pullToRefresh) {
    if (httpKit == null) {
      throw new NullPointerException("The HttpKit is null - did you forget to set it?");
    }
    if(!pullToRefresh && isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }
    request.setOwner(this);
    httpKit.execute(request, new HttpResponseReceiver<D>() {
      @Override
      public void onSuccess(HttpResponse<D> objectHttpResponse) {
        onHttpSuccess(objectHttpResponse, pullToRefresh);
      }

      @Override
      public void onFailure(HttpRequest httpRequest, Exception e) {
        onHttpFailure(httpRequest, e, pullToRefresh);
      }
    });
  }

  protected void onHttpSuccess(HttpResponse<D> response, boolean contentPresent) {
    if (isViewAttached()) {
      D data = response.getValue();
      if (data != null) {
        getView().setData(data);
        getView().showContent();
      } else {
        onHttpFailure(null, new NullPointerException("The data of the HttpResult is null."), contentPresent);
      }
    }
  }

  protected void onHttpFailure(HttpRequest request, Exception e, boolean pullToRefresh) {
    if (isViewAttached()) {
      getView().showError(e, pullToRefresh);
    }
  }

  @Override
  public void onDestroy(boolean retainInstanceState) {
    super.onDestroy(retainInstanceState);
    if(!retainInstanceState || cancelHttpRequestsOnRetainInstanceState()) {
      httpKit.cancelAllOfOwner(this);
    }
  }

  protected boolean cancelHttpRequestsOnRetainInstanceState() {
    return false;
  }


}
