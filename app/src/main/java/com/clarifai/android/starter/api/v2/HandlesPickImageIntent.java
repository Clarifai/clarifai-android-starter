package com.clarifai.android.starter.api.v2;

import android.support.annotation.NonNull;

public interface HandlesPickImageIntent {
  void onImagePicked(@NonNull byte[] imageBytes);
}
