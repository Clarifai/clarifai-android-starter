package com.clarifai.android.starter.api.v2;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import timber.log.Timber;

public class App extends Application {

  private static App INSTANCE;

  @NonNull
  public static App get() {
    final App instance = INSTANCE;
    if (instance == null) {
      throw new IllegalStateException("App has not been created yet!");
    }
    return instance;
  }

  @Nullable
  private ClarifaiClient client;

  @Override
  public void onCreate() {
    INSTANCE = this;
    client = new ClarifaiBuilder(getString(R.string.clarifai_id), getString(R.string.clarifai_secret)).buildSync();
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
  }

  @NonNull
  public ClarifaiClient clarifaiClient() {
    final ClarifaiClient client = this.client;
    if (client == null) {
      throw new IllegalStateException("Cannot use Clarifai client before initialized");
    }
    return client;
  }
}
