package com.clarifai.android.starter.api.v2;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class ClarifaiUtil {
  private ClarifaiUtil() {
    throw new UnsupportedOperationException("No instances");
  }

  /**
   * @param context
   * @param data
   * @return
   */
  @Nullable
  public static byte[] retrieveSelectedImage(@NonNull Context context, @NonNull Intent data) {
    InputStream inStream = null;
    Bitmap bitmap = null;
    try {
      inStream = context.getContentResolver().openInputStream(data.getData());
      bitmap = BitmapFactory.decodeStream(inStream);
      final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
      return outStream.toByteArray();
    } catch (FileNotFoundException e) {
      return null;
    } finally {
      if (inStream != null) {
        try {
          inStream.close();
        } catch (IOException ignored) {
        }
      }
      if (bitmap != null) {
        bitmap.recycle();
      }
    }
  }

  @NonNull
  public static Activity unwrapActivity(@NonNull Context startFrom) {
    while (startFrom instanceof ContextWrapper) {
      if (startFrom instanceof Activity) {
        return ((Activity) startFrom);
      }
      startFrom = ((ContextWrapper) startFrom).getBaseContext();
    }
    throw new IllegalStateException("This Context can't be unwrapped to an Activity!");
  }

  @Nullable
  public static <T> T firstChildOfType(@NonNull View root, @NonNull Class<T> type) {
    if (type.isInstance(root)) {
      return type.cast(root);
    }
    if (root instanceof ViewGroup) {
      final ViewGroup rootGroup = (ViewGroup) root;
      for (int i = 0; i < rootGroup.getChildCount(); i++) {
        final View child = rootGroup.getChildAt(i);
        final T childResult = firstChildOfType(child, type);
        if (childResult != null) {
          return childResult;
        }
      }
    }
    return null;
  }

  @NonNull
  public static <T> List<T> childrenOfType(@NonNull View root, @NonNull Class<T> type) {
    final List<T> children = new ArrayList<>();
    if (type.isInstance(root)) {
      children.add(type.cast(root));
    }
    if (root instanceof ViewGroup) {
      final ViewGroup rootGroup = (ViewGroup) root;
      for (int i = 0; i < rootGroup.getChildCount(); i++) {
        final View child = rootGroup.getChildAt(i);
        children.addAll(childrenOfType(child, type));
      }
    }
    return children;
  }
}
