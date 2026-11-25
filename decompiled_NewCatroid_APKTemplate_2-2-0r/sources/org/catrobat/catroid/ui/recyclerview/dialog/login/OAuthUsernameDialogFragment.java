package org.catrobat.catroid.ui.recyclerview.dialog.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.transfers.CheckUserNameAvailableTask;
import org.catrobat.catroid.transfers.GoogleExchangeCodeTask;
import org.catrobat.catroid.transfers.GoogleLogInTask;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DialogInputWatcher;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/login/OAuthUsernameDialogFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class OAuthUsernameDialogFragment extends DialogFragment implements CheckUserNameAvailableTask.OnCheckUserNameAvailableCompleteListener, GoogleExchangeCodeTask.OnGoogleExchangeCodeCompleteListener, GoogleLogInTask.OnGoogleServerLogInCompleteListener {
    public static final String TAG = "OAuthUsernameDialogFragment";
    private TextInputLayout inputLayout;
    private String openAuthProvider;
    private SignInCompleteListener signInCompleteListener;

    public void setSignInCompleteListener(SignInCompleteListener signInCompleteListener) {
        this.signInCompleteListener = signInCompleteListener;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View viewInflate = View.inflate(getActivity(), R.layout.dialog_sign_in_oauth_username, null);
        this.inputLayout = (TextInputLayout) viewInflate.findViewById(R.id.dialog_signin_oauth_username);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.openAuthProvider = arguments.getString(Constants.CURRENT_OAUTH_PROVIDER);
        }
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(getActivity()).setTitle(R.string.sign_in_dialog_title).setView(viewInflate).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).create();
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.OAuthUsernameDialogFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.m5765xe22cd976(alertDialogCreate, dialogInterface);
            }
        });
        return alertDialogCreate;
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$1$org-catrobat-catroid-ui-recyclerview-dialog-login-OAuthUsernameDialogFragment, reason: not valid java name */
    /* synthetic */ void m5765xe22cd976(AlertDialog alertDialog, DialogInterface dialogInterface) {
        Button button = alertDialog.getButton(-1);
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.OAuthUsernameDialogFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5764xdb03f735(view);
            }
        });
        button.setEnabled(!this.inputLayout.getEditText().getText().toString().isEmpty());
        this.inputLayout.getEditText().addTextChangedListener(new DialogInputWatcher(this.inputLayout, button, false));
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$0$org-catrobat-catroid-ui-recyclerview-dialog-login-OAuthUsernameDialogFragment, reason: not valid java name */
    /* synthetic */ void m5764xdb03f735(View view) {
        onPositiveButtonClick();
    }

    private void onPositiveButtonClick() {
        String strTrim = this.inputLayout.getEditText().getText().toString().trim();
        if (strTrim.isEmpty()) {
            this.inputLayout.setError(getString(R.string.signin_choose_username_empty));
            return;
        }
        CheckUserNameAvailableTask checkUserNameAvailableTask = new CheckUserNameAvailableTask(strTrim);
        checkUserNameAvailableTask.setOnCheckUserNameAvailableCompleteListener(this);
        checkUserNameAvailableTask.execute(new Void[0]);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        this.signInCompleteListener.onLoginCancel();
    }

    @Override // org.catrobat.catroid.transfers.CheckUserNameAvailableTask.OnCheckUserNameAvailableCompleteListener
    public void onCheckUserNameAvailableComplete(Boolean userNameAvailable, String username) {
        if (userNameAvailable.booleanValue()) {
            new AlertDialog.Builder(getActivity()).setTitle(R.string.error).setMessage(R.string.oauth_username_taken).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
            return;
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (this.openAuthProvider.equals(Constants.GOOGLE_PLUS)) {
            defaultSharedPreferences.edit().putString(Constants.GOOGLE_USERNAME, username).apply();
            GoogleExchangeCodeTask googleExchangeCodeTask = new GoogleExchangeCodeTask(getActivity(), defaultSharedPreferences.getString(Constants.GOOGLE_EXCHANGE_CODE, Constants.NO_GOOGLE_EXCHANGE_CODE), defaultSharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), defaultSharedPreferences.getString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME), defaultSharedPreferences.getString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID), defaultSharedPreferences.getString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE), defaultSharedPreferences.getString(Constants.GOOGLE_ID_TOKEN, Constants.NO_GOOGLE_ID_TOKEN));
            googleExchangeCodeTask.setOnGoogleExchangeCodeCompleteListener(this);
            googleExchangeCodeTask.execute(new Void[0]);
        }
    }

    @Override // org.catrobat.catroid.transfers.GoogleExchangeCodeTask.OnGoogleExchangeCodeCompleteListener
    public void onGoogleExchangeCodeComplete() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        GoogleLogInTask googleLogInTask = new GoogleLogInTask(getActivity(), defaultSharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), defaultSharedPreferences.getString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME), defaultSharedPreferences.getString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID), defaultSharedPreferences.getString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE));
        googleLogInTask.setOnGoogleServerLogInCompleteListener(this);
        googleLogInTask.execute(new Void[0]);
    }

    @Override // org.catrobat.catroid.transfers.GoogleLogInTask.OnGoogleServerLogInCompleteListener
    public void onGoogleServerLogInComplete() {
        this.signInCompleteListener.onLoginSuccessful(null);
        dismiss();
    }
}
