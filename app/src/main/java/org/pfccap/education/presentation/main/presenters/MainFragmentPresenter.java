package org.pfccap.education.presentation.main.presenters;

import android.content.Context;
import android.content.Intent;

import org.pfccap.education.R;
import org.pfccap.education.presentation.main.ui.activities.ProfileActivity;
import org.pfccap.education.presentation.main.ui.fragments.IMainFragmentView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jggomez on 14-Jun-17.
 */

public class MainFragmentPresenter implements IMainFragmentPresenter {

    private IMainFragmentView view;
    private Context context;

    public MainFragmentPresenter(IMainFragmentView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public boolean profileCompleted() {

        if (Cache.getByKey(Constants.PROFILE_COMPLETED).equals("0")) {
            Utilities.dialogoInfoAndIntent(context.getResources().getString(R.string.title_info_dialog)
                    , context.getResources().getString(R.string.profile_completed)
                    , context
                    , new Intent(context, ProfileActivity.class)
            );

            return false;
        }

        return true;
    }

    @Override
    public boolean validateTurn(String turnCancer) {
        //TODO validar cuando las contasntes vienes "" ya que genera error con el Integer.valueOf
        if (Integer.valueOf(Cache.getByKey(turnCancer)) ==
                Integer.valueOf(Cache.getByKey(Constants.NUM_OPPORTUNITIES))) {

            Utilities.dialogoInfo(context.getResources().getString(R.string.title_info_dialog)
                    , String.format(context.getResources().getString(R.string.end_turn),
                            Integer.valueOf(Cache.getByKey(Constants.NUM_OPPORTUNITIES)))
                    , context
            );
            return true;
        }
        return false;
    }

    @Override
    public boolean validateDateLastAnswer(String dateCancer, String lapseCancer) throws ParseException {
        if (dateCancer.equals("null") || dateCancer.equals(""))
            return false;

        if (lapseCancer.equals("0"))
            return false;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        cal.setTime(sdf.parse(dateCancer));
        cal.add(Calendar.DATE, Integer.valueOf(lapseCancer));

        if (cal.after(Calendar.getInstance())) {
            Utilities.dialogoInfo(context.getResources().getString(R.string.title_info_dialog)
                    , String.format(context.getResources().getString(R.string.lapse_answer),
                            Integer.valueOf(lapseCancer))
                    , context
            );

            return true;
        }

        return false;
    }
}
