package com.clarifai.android.starter.api.v2.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.clarifai.android.starter.api.v2.R;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.tbruyelle.rxpermissions.RxPermissions;
import rx.functions.Action1;

import java.util.Arrays;
import java.util.List;

/**
 * A common class to set up boilerplate logic for
 */
public abstract class BaseActivity extends AppCompatActivity {

  private static final String INTENT_EXTRA_DRAWER_POSITION = "IntentExtraDrawerPosition";

  @BindView(R.id.content_root) protected View root;

  private Unbinder unbinder;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      RxPermissions.getInstance(this)
          .request(Manifest.permission.READ_EXTERNAL_STORAGE)
          .subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean granted) {
              if (!granted) {
                new AlertDialog.Builder(BaseActivity.this)
                    .setCancelable(false)
                    .setMessage(R.string.error_external_storage_permission_not_granted)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                      @Override public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        finish();
                      }
                    })
                    .show();
              }
            }
          });
    }

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

  @NonNull
  protected List<IDrawerItem> drawerItems() {
    return Arrays.<IDrawerItem>asList(
        new PrimaryDrawerItem()
            .withName(R.string.drawer_item_recognize_tags)
            .withOnDrawerItemClickListener(goToActivityListener(RecognizeConceptsActivity.class))
    );
  }

  /**
   * @return the layout file to use. This is used in place of {@code R.id.content_stub} in the activity_wrapper.xml
   * file, by using a {@link ViewStub}.
   */
  @LayoutRes
  protected abstract int layoutRes();

  private Drawer.OnDrawerItemClickListener goToActivityListener(
      @NonNull final Class<? extends Activity> activityClass) {
    return new Drawer.OnDrawerItemClickListener() {
      @Override
      public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        // Don't open a new activity if we're already in the activity the user clicked on
        if (!drawerItem.isSelected()) {
          startActivity(new Intent(BaseActivity.this, activityClass).putExtra(INTENT_EXTRA_DRAWER_POSITION, position));
        }
        return true;
      }
    };
  }
}
