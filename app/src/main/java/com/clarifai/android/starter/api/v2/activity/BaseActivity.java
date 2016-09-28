package com.clarifai.android.starter.api.v2.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;

import com.clarifai.android.starter.api.v2.ClarifaiUtil;
import com.clarifai.android.starter.api.v2.HandlesPickImageIntent;
import com.clarifai.android.starter.api.v2.R;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

  @BindView(R.id.content_root)
  View root;

  public static final int PICK_IMAGE = 100;

  private static final String INTENT_EXTRA_DRAWER_POSITION = "IntentExtraDrawerPosition";

  private Unbinder unbinder;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    @SuppressLint("InflateParams") final View wrapper = getLayoutInflater().inflate(R.layout.activity_wrapper, null);
    final ViewStub stub = ButterKnife.findById(wrapper, R.id.content_stub);
    stub.setLayoutResource(layoutRes());
    stub.inflate();
    setContentView(wrapper);
    unbinder = ButterKnife.bind(this);

    final Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);

    final Drawer drawer = new DrawerBuilder()
        .withActivity(this)
        .withToolbar(toolbar)
        .withDrawerItems(drawerItems())
        .build();

    // Show the "hamburger"
    setSupportActionBar(toolbar);
    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(false);
    }
    drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

    // Set the selected index to what the intent said we're in
    drawer.setSelectionAtPosition(getIntent().getIntExtra(INTENT_EXTRA_DRAWER_POSITION, 0));
  }

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    final List<HandlesPickImageIntent> imageHandlers = ClarifaiUtil.childrenOfType(root, HandlesPickImageIntent.class);
    for (HandlesPickImageIntent imageHandler : imageHandlers) {
      imageHandler.onImagePicked(retrieveSelectedImage(requestCode, resultCode, data));
    }
  }

  @NonNull
  protected List<IDrawerItem> drawerItems() {
    return Arrays.<IDrawerItem>asList(
        new PrimaryDrawerItem()
            .withName(R.string.drawer_item_recognize_tags)
            .withOnDrawerItemClickListener(goToActivityListener(RecognizeConceptsActivity.class)),
        new SecondaryDrawerItem()
            .withName(R.string.drawer_item_recognize_colors)
            .withOnDrawerItemClickListener(goToActivityListener(RecognizeColorsActivity.class))
    );
  }

  @LayoutRes
  protected abstract int layoutRes();

  @Nullable
  protected final byte[] retrieveSelectedImage(int requestCode, int resultCode, Intent data) {
    if (resultCode != RESULT_OK || requestCode != PICK_IMAGE) {
      return null;
    }
    InputStream inStream = null;
    Bitmap bitmap = null;
    try {
      inStream = getContentResolver().openInputStream(data.getData());
      bitmap = BitmapFactory.decodeStream(inStream);
      final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
      return outStream.toByteArray();
    } catch (FileNotFoundException e) {
      return null;
    } finally {
      if (inStream != null) {
        try {
          inStream.close();
        } catch (IOException ignored) {}
      }
      if (bitmap != null) {
        bitmap.recycle();
      }
    }
  }

  private Drawer.OnDrawerItemClickListener goToActivityListener(@NonNull final Class<? extends Activity> activityClass) {
    return new Drawer.OnDrawerItemClickListener() {
      @Override
      public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        // Don't open a new activity if we're already in the activity the user clicked on
        if (BaseActivity.this.getClass().equals(activityClass)) {
          return true;
        }
        startActivity(new Intent(BaseActivity.this, activityClass)
            .putExtra(INTENT_EXTRA_DRAWER_POSITION, position)
        );
        return true;
      }
    };
  }
}
