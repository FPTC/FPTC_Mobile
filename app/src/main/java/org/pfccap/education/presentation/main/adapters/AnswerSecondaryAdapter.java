package org.pfccap.education.presentation.main.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.presentation.main.presenters.IQuestionPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by USUARIO on 17/05/2017.
 */

public class AnswerSecondaryAdapter extends RecyclerView.Adapter<AnswerSecondaryAdapter.ViewHolder> {

    private List<String> dataset;
    private IQuestionPresenter iQuestionPresenter;

    public AnswerSecondaryAdapter(List<String> dataset, IQuestionPresenter iQuestionPresenter) {
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
        String element = dataset.get(position);
        holder.labelButton.setText(element);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addItemSite(String element){
        dataset.add(0, element);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private IQuestionPresenter iQuestionPresenter;

        @BindView(R.id.questionAnswersSecondaryTxt)
        TextView labelButton;

        public ViewHolder(View itemView, IQuestionPresenter iQuestionPresenter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.iQuestionPresenter = iQuestionPresenter;
        }

        @OnClick(R.id.questionAnswersSecondaryTxt)
        public void clickList(){
            iQuestionPresenter.saveAnswerQuestionDB();
        }
    }
}
