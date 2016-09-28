package com.clarifai.android.starter.api.v2.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SimpleAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

  @LayoutRes
  private final int entryLayoutRes;

  @NonNull
  private List<T> data = new ArrayList<>();

  public SimpleAdapter(@LayoutRes int entryLayoutRes) {
    this.entryLayoutRes = entryLayoutRes;
  }

  @SafeVarargs
  @NonNull
  public final SimpleAdapter<T, VH> setData(@NonNull T... data) {
    return setData(Arrays.asList(data));
  }

  @NonNull
  public final SimpleAdapter<T, VH> setData(@NonNull List<T> data) {
    this.data = data;
    notifyDataSetChanged();
    return this;
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  @Nullable protected final T data(int position) {
    return data.get(position);
  }

  protected final View view(@NonNull ViewGroup parent) {
    return LayoutInflater.from(parent.getContext()).inflate(entryLayoutRes, parent, false);
  }
}
