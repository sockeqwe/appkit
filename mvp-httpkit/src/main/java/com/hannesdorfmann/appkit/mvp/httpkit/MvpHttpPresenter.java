package com.hannesdorfmann.appkit.mvp.httpkit;

import com.hannesdorfmann.appkit.dagger.Injector;
import com.hannesdorfmann.appkit.mvp.MvpPresenter;
import com.hannesdorfmann.appkit.mvp.MvpView;
import com.hannesdorfmann.httpkit.HttpKit;
import com.hannesdorfmann.httpkit.request.HttpRequest;
import com.hannesdorfmann.httpkit.response.HttpResponse;
import com.hannesdorfmann.httpkit.response.HttpResponseReceiver;
import javax.inject.Inject;

/**
 * A MvpPresenter that uses httpkit to make http calls
 * @author Hannes Dorfmann
 */
public class MvpHttpPresenter <V extends MvpView<D>, D> extends MvpPresenter<V, D> {

  @Inject
  protected HttpKit httpKit;

  public MvpHttpPresenter(Injector injector, V view){
    super(injector);

    if (httpKit == null){
      throw new IllegalArgumentException("No HttpKit could be injected from dagger. Did you forget to specify one in your dagger module?");
    }
    setView(view);
  }

  protected void loadData(HttpRequest request, final boolean pullToRefresh) {
    if (httpKit == null) {
      throw new NullPointerException("The HttpKit is null - did you forget to set it?");
    }
    if(!pullToRefresh && isViewAttached()) {
      getView().showLoading();
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
  public void onDestroy() {
    super.onDestroy();
    if(shouldCancelHttpRequests()) {
      httpKit.cancelAllOfOwner(this);
    }
  }

  protected boolean shouldCancelHttpRequests() {
    return true;
  }


}
