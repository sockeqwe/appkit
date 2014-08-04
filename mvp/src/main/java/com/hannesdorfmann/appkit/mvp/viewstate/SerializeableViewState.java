package com.hannesdorfmann.appkit.mvp.viewstate;

import android.os.Parcel;
import java.io.Serializable;

/**
 * @author Hannes Dorfmann
 */
public class SerializeableViewState<D extends Serializable> extends ViewState<D> {

  public static final Creator<SerializeableViewState> CREATOR = new Creator<SerializeableViewState>() {
    @Override public SerializeableViewState createFromParcel(Parcel source) {
      return new SerializeableViewState(source);
    }

    @Override public SerializeableViewState[] newArray(int size) {
      return new SerializeableViewState[size];
    }
  };


  public SerializeableViewState(){

  }

  private SerializeableViewState(Parcel in){

  }


  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeSerializable(loadedData);
  }

  protected void readFromParcel(Parcel in) {
    super.readFromParcel(in);
    loadedData = (D) in.readSerializable();
  }

}
