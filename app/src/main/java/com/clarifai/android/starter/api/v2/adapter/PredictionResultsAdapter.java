package com.clarifai.android.starter.api.v2.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clarifai.android.starter.api.v2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import clarifai2.dto.prediction.Color;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;

public class PredictionResultsAdapter<PREDICTION extends Prediction>
    extends SimpleAdapter<PREDICTION, PredictionResultsAdapter.Holder> {

  public PredictionResultsAdapter() {
    super(R.layout.item_concept);
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new Holder(view(parent));
  }

  @SuppressLint("SetTextI18n")
  @Override
  public void onBindViewHolder(Holder holder, int position) {
    holder.label.setBackgroundColor(0);

    final PREDICTION prediction = data(position);
    if (prediction instanceof Concept) {
      final Concept concept = (Concept) prediction;
      final String name = concept.name();
      holder.label.setText(name != null ? name : concept.id());
      holder.probability.setText(String.valueOf(concept.value()));
      return;
    }
    if (prediction instanceof Color) {
      final Color color = (Color) prediction;
      holder.label.setText(color.hex());
      holder.label.setBackgroundColor(android.graphics.Color.parseColor(color.hex()));
      holder.probability.setText(String.valueOf(color.value()));
      return;
    }
    holder.label.setText("Unknown data type: " + (prediction == null ? "" : prediction.getClass().getSimpleName()));
    holder.probability.setText("--");
//    holder.label.setText(data == null ? "--" : data.name() == null ? data.id() : data.name());
//    holder.probability.setText(data == null ? "--" : String.valueOf(data.value()));
  }

  static class Holder extends RecyclerView.ViewHolder {
    @BindView(android.R.id.text1)
    TextView label;

    @BindView(android.R.id.text2)
    TextView probability;

    Holder(View root) {
      super(root);
      ButterKnife.bind(this, root);
    }
  }
}
