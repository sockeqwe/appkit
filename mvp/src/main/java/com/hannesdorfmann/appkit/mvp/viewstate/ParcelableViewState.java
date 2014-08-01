package com.hannesdorfmann.appkit.mvp.viewstate;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Hannes Dorfmann
 */
public class ParcelableViewState<D extends Parcelable> extends ViewState<D>{

  public static final Creator<ParcelableViewState> CREATOR = new Creator<ParcelableViewState>() {
    @Override public ParcelableViewState createFromParcel(Parcel source) {
      return new ParcelableViewState(source);
    }

    @Override public ParcelableViewState[] newArray(int size) {
      return new ParcelableViewState[size];
    }
  };

  @Override public void saveInstanceState(Bundle out) {

  }


  private static final String BUNDLE_PARCELABLE_WORKAROUND = "ParcelableViewState.workaround";

  public ParcelableViewState(){

  }

  private ParcelableViewState(Parcel source){
    readFromParcel(source);
  }


  @Override
  public void writeToParcel(Parcel dest, int flags){
    super.writeToParcel(dest, flags);

    // content
    Bundle b = new Bundle();
    b.putParcelable(BUNDLE_PARCELABLE_WORKAROUND, loadedData);
    dest.writeBundle(b);
  }

  @Override
  protected void readFromParcel(Parcel source){
    super.readFromParcel(source);

    Bundle b = source.readBundle();
    if (b != null){
      loadedData = b.getParcelable(BUNDLE_PARCELABLE_WORKAROUND);
    }

    // alternative ((Class) ((ParameterizedType) getClass()
    // .getGenericSuperclass()).getActualTypeArguments()[0]);
  }

}
