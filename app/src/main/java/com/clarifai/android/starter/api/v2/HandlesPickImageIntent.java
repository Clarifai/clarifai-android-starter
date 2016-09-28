package com.clarifai.android.starter.api.v2;

import android.support.annotation.Nullable;

public interface HandlesPickImageIntent {
  void onImagePicked(@Nullable byte[] imageBytes);
}
