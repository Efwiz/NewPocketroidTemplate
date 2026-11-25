package org.catrobat.catroid.transfers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.ui.recyclerview.dialog.login.OAuthUsernameDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.login.SignInCompleteListener;
import org.catrobat.catroid.utils.DeviceSettingsProvider;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.web.ServerAuthenticationConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/GoogleLoginHandler.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GoogleLoginHandler implements CheckOAuthTokenTask.OnCheckOAuthTokenCompleteListener, GoogleLogInTask.OnGoogleServerLogInCompleteListener, CheckEmailAvailableTask.OnCheckEmailAvailableCompleteListener, GoogleExchangeCodeTask.OnGoogleExchangeCodeCompleteListener {
    public static final int REQUEST_CODE_GOOGLE_SIGNIN = 100;
    private AppCompatActivity activity;
    private GoogleSignInClient googleSignInClient;

    public GoogleLoginHandler(AppCompatActivity activity) {
        this.activity = activity;
        this.googleSignInClient = GoogleSignIn.getClient((Activity) this.activity, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(ServerAuthenticationConstants.GOOGLE_LOGIN_CATROWEB_SERVER_CLIENT_ID).build());
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return this.googleSignInClient;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            Task<GoogleSignInAccount> signedInAccountFromIntent = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (signedInAccountFromIntent.isSuccessful()) {
                onGoogleLogInComplete(signedInAccountFromIntent.getResult());
            } else {
                AppCompatActivity appCompatActivity = this.activity;
                ToastUtil.showError(appCompatActivity, String.format(appCompatActivity.getString(R.string.error_google_plus_sign_in), signedInAccountFromIntent.getException().getLocalizedMessage().replace(":", "")));
            }
        }
    }

    public void onGoogleLogInComplete(GoogleSignInAccount account) {
        String id2 = account.getId();
        String displayName = account.getDisplayName();
        String email = account.getEmail();
        String userCountryCode = DeviceSettingsProvider.getUserCountryCode();
        String idToken = account.getIdToken();
        PreferenceManager.getDefaultSharedPreferences(this.activity).edit().putString(Constants.GOOGLE_ID, id2).putString(Constants.GOOGLE_USERNAME, displayName).putString(Constants.GOOGLE_EMAIL, email).putString(Constants.GOOGLE_LOCALE, userCountryCode).putString(Constants.GOOGLE_ID_TOKEN, idToken).putString(Constants.GOOGLE_EXCHANGE_CODE, account.getServerAuthCode()).apply();
        CheckOAuthTokenTask checkOAuthTokenTask = new CheckOAuthTokenTask(this.activity, id2, Constants.GOOGLE_PLUS);
        checkOAuthTokenTask.setOnCheckOAuthTokenCompleteListener(this);
        checkOAuthTokenTask.execute(new String[0]);
    }

    @Override // org.catrobat.catroid.transfers.CheckOAuthTokenTask.OnCheckOAuthTokenCompleteListener
    public void onCheckOAuthTokenComplete(Boolean tokenAvailable, String provider) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        if (tokenAvailable.booleanValue()) {
            GoogleLogInTask googleLogInTask = new GoogleLogInTask(this.activity, defaultSharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), defaultSharedPreferences.getString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME), defaultSharedPreferences.getString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID), defaultSharedPreferences.getString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE));
            googleLogInTask.setOnGoogleServerLogInCompleteListener(this);
            googleLogInTask.execute(new Void[0]);
        } else {
            CheckEmailAvailableTask checkEmailAvailableTask = new CheckEmailAvailableTask(defaultSharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), Constants.GOOGLE_PLUS);
            checkEmailAvailableTask.setOnCheckEmailAvailableCompleteListener(this);
            checkEmailAvailableTask.execute(new String[0]);
        }
    }

    @Override // org.catrobat.catroid.transfers.GoogleLogInTask.OnGoogleServerLogInCompleteListener
    public void onGoogleServerLogInComplete() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CURRENT_OAUTH_PROVIDER, Constants.GOOGLE_PLUS);
        ((SignInCompleteListener) this.activity).onLoginSuccessful(bundle);
    }

    @Override // org.catrobat.catroid.transfers.CheckEmailAvailableTask.OnCheckEmailAvailableCompleteListener
    public void onCheckEmailAvailableComplete(Boolean emailAvailable, String provider) {
        if (emailAvailable.booleanValue()) {
            exchangeGoogleAuthorizationCode();
        } else {
            showOauthUserNameDialog(Constants.GOOGLE_PLUS);
        }
    }

    public void exchangeGoogleAuthorizationCode() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        GoogleExchangeCodeTask googleExchangeCodeTask = new GoogleExchangeCodeTask(this.activity, defaultSharedPreferences.getString(Constants.GOOGLE_EXCHANGE_CODE, Constants.NO_GOOGLE_EXCHANGE_CODE), defaultSharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), defaultSharedPreferences.getString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME), defaultSharedPreferences.getString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID), defaultSharedPreferences.getString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE), defaultSharedPreferences.getString(Constants.GOOGLE_ID_TOKEN, Constants.NO_GOOGLE_ID_TOKEN));
        googleExchangeCodeTask.setOnGoogleExchangeCodeCompleteListener(this);
        googleExchangeCodeTask.execute(new Void[0]);
    }

    private void showOauthUserNameDialog(String provider) {
        OAuthUsernameDialogFragment oAuthUsernameDialogFragment = new OAuthUsernameDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CURRENT_OAUTH_PROVIDER, provider);
        oAuthUsernameDialogFragment.setArguments(bundle);
        oAuthUsernameDialogFragment.setSignInCompleteListener((SignInCompleteListener) this.activity);
        oAuthUsernameDialogFragment.show(this.activity.getSupportFragmentManager(), OAuthUsernameDialogFragment.TAG);
    }

    @Override // org.catrobat.catroid.transfers.GoogleExchangeCodeTask.OnGoogleExchangeCodeCompleteListener
    public void onGoogleExchangeCodeComplete() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        GoogleLogInTask googleLogInTask = new GoogleLogInTask(this.activity, defaultSharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), defaultSharedPreferences.getString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME), defaultSharedPreferences.getString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID), defaultSharedPreferences.getString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE));
        googleLogInTask.setOnGoogleServerLogInCompleteListener(this);
        googleLogInTask.execute(new Void[0]);
    }
}
