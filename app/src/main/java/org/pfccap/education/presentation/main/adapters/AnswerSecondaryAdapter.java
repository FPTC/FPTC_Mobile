package org.pfccap.education.presentation.main.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.presentation.main.presenters.IQuestionPresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by USUARIO on 17/05/2017.
 */

public class AnswerSecondaryAdapter extends RecyclerView.Adapter<AnswerSecondaryAdapter.ViewHolder> {

    private List<AnswersQuestion> dataset;
    private IQuestionPresenter iQuestionPresenter;

    public AnswerSecondaryAdapter(List<AnswersQuestion> dataset, IQuestionPresenter iQuestionPresenter) {
        this.dataset = dataset;
        this.iQuestionPresenter = iQuestionPresenter;
    }

    @Override
    public AnswerSecondaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer_secondary_question, parent, false);
        return new ViewHolder(view, iQuestionPresenter);
    }

    @Override
    public void onBindViewHolder(AnswerSecondaryAdapter.ViewHolder holder, int position) {
        AnswersQuestion element = dataset.get(position);
        holder.labelButton.setText(element.getDescription());
        holder.labelButton.setEnabled(element.isEnable());
        holder.pointsAnswer.setText(String.valueOf(element.getPoints()));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addItemSite(AnswersQuestion item) {
        dataset.add(0, item);
        notifyDataSetChanged();
    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }

    public void disableItems() {
        for (AnswersQuestion item : dataset) {
            item.setEnable(false);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private IQuestionPresenter iQuestionPresenter;

        @BindView(R.id.questionAnswersSecondaryTxt)
        Button labelButton;

        @BindView(R.id.questionAnswersPoints)
        TextView pointsAnswer;

        ViewHolder(View itemView, IQuestionPresenter iQuestionPresenter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.iQuestionPresenter = iQuestionPresenter;
        }

        @OnClick(R.id.questionAnswersSecondaryTxt)
        void clickList() {
            Cache.save(Constants.INFO_SNACKBAR, "");
            int points = Integer.valueOf(pointsAnswer.getText().toString());
            iQuestionPresenter.calculatePointsCheck(points);

        }
    }
}
