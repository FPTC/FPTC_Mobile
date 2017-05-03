package org.pfccap.education.presentation.auth.presenters;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

/**
 * Created by jggomez on 05-Apr-17.
 */

public interface ILoginPresenter {

    void login(String email, String password);

    CallbackManager registerCallbackFacebook(final LoginButton loginButtonFacebook);

    void isLogging();
}
