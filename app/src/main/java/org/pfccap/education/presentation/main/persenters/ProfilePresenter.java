package org.pfccap.education.presentation.main.persenters;

import org.pfccap.education.presentation.main.ui.fragments.IProfileView;

/**
 * Created by USUARIO on 02/05/2017.
 */

public class ProfilePresenter implements IProfilePresenter {

    private IProfileView profileView;

    public ProfilePresenter(IProfileView profileView){
        this.profileView = profileView;
    }


}
