package com.hannesdorfmann.appkit.mvp.viewstate;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * @author Hannes Dorfmann
 */
public class ArrayListViewState<D extends ArrayList<Parcelable>> extends ViewState<D> {

  public static final Creator<ArrayListViewState> CREATOR = new Creator<ArrayListViewState>() {
    @Override public ArrayListViewState createFromParcel(Parcel source) {
      return new ArrayListViewState(source);
    }

    @Override public ArrayListViewState[] newArray(int size) {
      return new ArrayListViewState[size];
    }
  };

  private static final String BUNDLE_ARRAY_LIST_WORKAROUND = "ArrayListViewState.workaround";

  public ArrayListViewState() {
  }

  private ArrayListViewState(Parcel source) {
    readFromParcel(source);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);

    // Content
    Bundle b = new Bundle();
    b.putParcelableArrayList(BUNDLE_ARRAY_LIST_WORKAROUND, loadedData);
    dest.writeBundle(b);
  }

  @Override
  protected void readFromParcel(Parcel source) {
    super.readFromParcel(source);

    // content
    Bundle b = source.readBundle();
    if (b != null) {
      loadedData = (D) b.getParcelableArrayList(BUNDLE_ARRAY_LIST_WORKAROUND);
    }

    // alternative ((Class) ((ParameterizedType) getClass()
    // .getGenericSuperclass()).getActualTypeArguments()[0]);
  }
}
