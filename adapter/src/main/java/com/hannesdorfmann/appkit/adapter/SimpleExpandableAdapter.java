package com.hannesdorfmann.appkit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import com.hannesdorfmann.appkit.dagger.Injector;

/**
 * A Base adapter for {@link android.widget.ExpandableListView}
 *
 * @author Hannes Dorfmann
 */
public abstract class SimpleExpandableAdapter extends BaseExpandableListAdapter {

  /**
   * The inflater for
   */
  protected LayoutInflater inflater;
  protected Context context;

  public SimpleExpandableAdapter(Context context, Injector injector) {
    this.context = context;
    this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    injector.getObjectGraph().inject(this);
  }

  @Override
  public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
      View convertView, ViewGroup parent) {

    int type = getChildType(groupPosition, childPosition);
    if (convertView == null) {
      convertView = newChildView(type, parent);
    }
    bindChildView(groupPosition, childPosition, type, convertView, isLastChild);
    return convertView;
  }

  /**
   * Called to create a new child view item
   *
   * @param childViewType The viewType of the child view
   */
  public abstract View newChildView(int childViewType, ViewGroup parent);

  /**
   * Bind the data of the childview to this
   */
  public abstract void bindChildView(int groupPosition, int childPosition, int childViewType,
      View view, boolean isLastChild);

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
      ViewGroup parent) {

    int type = getGroupType(groupPosition);
    if (convertView == null) {
      convertView = newGroupView(type, parent);
    }
    bindGroupView(groupPosition, type, convertView, isExpanded);
    return convertView;
  }

  /**
   * Called to create a new ViewGroup
   */
  public abstract View newGroupView(int viewType, ViewGroup parent);

  /**
   * Bind the convert view for a view group
   */
  public abstract void bindGroupView(int groupPosition, int type, View convertView,
      boolean isExpanded);

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    // Not needed in the default case, otherwise override it
    return false;
  }
}
