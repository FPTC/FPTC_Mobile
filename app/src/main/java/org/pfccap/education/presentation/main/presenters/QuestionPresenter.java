package org.pfccap.education.presentation.main.presenters;

import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.main.ui.activities.IQuestionView;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionPresenter implements IQuestionPresenter {

    private IQuestionView questionView;
    private IUserBP userBP;

    public QuestionPresenter(IQuestionView questionView) {
        this.questionView = questionView;
        userBP = new UserBP();
    }

    @Override
    public void getQuestions() {

    }

    @Override
    public void saveAnswerQuestionDB(UserAuth user) {

    }
}
