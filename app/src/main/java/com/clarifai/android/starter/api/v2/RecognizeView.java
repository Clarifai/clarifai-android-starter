package com.clarifai.android.starter.api.v2;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Prediction;
import com.clarifai.android.starter.api.v2.activity.BaseActivity;
import com.clarifai.android.starter.api.v2.adapter.PredictionResultsAdapter;
import timber.log.Timber;

import java.util.List;


/**
 * A view that recognizes using the given {@link clarifai2.dto.model.Model} and displays the image that was recognized
 * upon, along with the list of recognitions for that image
 */
public class RecognizeView<PREDICTION extends Prediction> extends CoordinatorLayout implements HandlesPickImageIntent {

  @BindView(R.id.resultsList)
  RecyclerView resultsList;

  @BindView(R.id.image)
  ImageView imageView;

  @BindView(R.id.switcher)
  ViewSwitcher switcher;

  @BindView(R.id.fab)
  View fab;

  @Nullable
  private Model<PREDICTION> model;

  @NonNull
  private final PredictionResultsAdapter<PREDICTION> tagsAdapter;

  public RecognizeView(Context context, AttributeSet attrs) {
    super(context, attrs);
    inflate(context, R.layout.view_recognize, this);
    ButterKnife.bind(this);

    tagsAdapter = new PredictionResultsAdapter<>();
    resultsList.setLayoutManager(new LinearLayoutManager(context));
    resultsList.setAdapter(tagsAdapter);
  }

  public void setModel(@NonNull Model<PREDICTION> model) {
    this.model = model;
  }

  @Override
  public void onImagePicked(@NonNull final byte[] imageBytes) {
    final Model<PREDICTION> model = this.model;
    if (model == null) {
      throw new IllegalStateException("An image can't be picked before this view has a model set!");
    }
    setBusy(true);
    tagsAdapter.setData();
    new AsyncTask<Void, Void, List<ClarifaiOutput<PREDICTION>>>() {
      @Override
      protected List<ClarifaiOutput<PREDICTION>> doInBackground(Void... params) {
        final ClarifaiResponse<List<ClarifaiOutput<PREDICTION>>> predictions = model.predict()
            .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(imageBytes)))
            .executeSync();
        if (predictions.isSuccessful()) {
          return predictions.get();
        } else {
          Timber.e("API call to get predictions was not successful. Info: %s", predictions.getStatus().toString());
          return null;
        }
      }

      @Override
      protected void onPostExecute(List<ClarifaiOutput<PREDICTION>> predictions) {
        setBusy(false);
        if (predictions == null || predictions.isEmpty()) {
          Snackbar.make(
              findViewById(R.id.content_root),
              predictions == null ? R.string.error_while_contacting_api : R.string.no_results_from_api,
              Snackbar.LENGTH_INDEFINITE
          ).show();
          return;
        }
        tagsAdapter.setData(predictions.get(0).data());
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
      }
    }.execute();

  }

  @OnClick(R.id.fab)
  void selectImageToUpload() {
    ClarifaiUtil.unwrapActivity(getContext())
        .startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), BaseActivity.PICK_IMAGE);
  }

  private void setBusy(final boolean busy) {
    ClarifaiUtil.unwrapActivity(getContext()).runOnUiThread(new Runnable() {
      @Override
      public void run() {
        switcher.setDisplayedChild(busy ? 1 : 0);
        imageView.setVisibility(busy ? GONE : VISIBLE);
        fab.setEnabled(!busy);
      }
    });
  }
}
