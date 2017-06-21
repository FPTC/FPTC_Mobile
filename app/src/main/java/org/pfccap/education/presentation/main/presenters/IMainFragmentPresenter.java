package org.pfccap.education.presentation.main.presenters;

import java.text.ParseException;

/**
 * Created by jggomez on 14-Jun-17.
 */

public interface IMainFragmentPresenter {

    boolean profileCompleted();

    boolean validateTurn(String turnCancer);

    boolean validateDateLastAnswer(String dateCancer, String lapseCancer) throws ParseException;
}
