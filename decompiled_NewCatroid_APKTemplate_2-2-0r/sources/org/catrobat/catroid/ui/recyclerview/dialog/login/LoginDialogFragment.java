package org.catrobat.catroid.ui.recyclerview.dialog.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.transfers.LoginTask;
import org.catrobat.catroid.ui.ViewUtils;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.web.ServerCalls;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/login/LoginDialogFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LoginDialogFragment extends DialogFragment implements LoginTask.OnLoginListener {
    public static final String PASSWORD_FORGOTTEN_PATH = "reset-password";
    public static final String TAG = "LoginDialogFragment";
    private AlertDialog alertDialog;
    private EditText passwordEditText;
    private TextInputLayout passwordInputLayout;
    private SignInCompleteListener signInCompleteListener;
    private EditText usernameEditText;
    private TextInputLayout usernameInputLayout;

    public void setSignInCompleteListener(SignInCompleteListener signInCompleteListener) {
        this.signInCompleteListener = signInCompleteListener;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        View viewInflate = View.inflate(getActivity(), R.layout.dialog_login, null);
        this.usernameInputLayout = (TextInputLayout) viewInflate.findViewById(R.id.dialog_login_username);
        this.passwordInputLayout = (TextInputLayout) viewInflate.findViewById(R.id.dialog_login_password);
        this.usernameEditText = this.usernameInputLayout.getEditText();
        this.passwordEditText = this.passwordInputLayout.getEditText();
        ((CheckBox) viewInflate.findViewById(R.id.show_password)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LoginDialogFragment.this.passwordEditText.setInputType(1);
                } else {
                    LoginDialogFragment.this.passwordEditText.setInputType(129);
                }
            }
        });
        this.alertDialog = new AlertDialog.Builder(getActivity()).setTitle(R.string.login).setView(viewInflate).setPositiveButton(R.string.login, (DialogInterface.OnClickListener) null).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).setNeutralButton(R.string.reset_password, (DialogInterface.OnClickListener) null).setCancelable(true).create();
        this.usernameEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    LoginDialogFragment.this.usernameInputLayout.setError(LoginDialogFragment.this.getString(R.string.error_register_empty_username));
                } else if (!s.toString().trim().matches("^[a-zA-Z0-9-_.]*$")) {
                    LoginDialogFragment.this.usernameInputLayout.setError(LoginDialogFragment.this.getString(R.string.error_register_invalid_username));
                } else {
                    LoginDialogFragment.this.usernameInputLayout.setErrorEnabled(false);
                }
                LoginDialogFragment.this.handleLoginBtnStatus();
            }
        });
        this.passwordEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    LoginDialogFragment.this.passwordInputLayout.setError(LoginDialogFragment.this.getString(R.string.error_register_empty_password));
                } else if (s.toString().length() < 6) {
                    LoginDialogFragment.this.passwordInputLayout.setError(LoginDialogFragment.this.getString(R.string.error_register_password_at_least_6_characters));
                } else {
                    LoginDialogFragment.this.passwordInputLayout.setErrorEnabled(false);
                }
                LoginDialogFragment.this.handleLoginBtnStatus();
            }
        });
        this.alertDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.m5763xffc87b73(dialogInterface);
            }
        });
        return this.alertDialog;
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$2$org-catrobat-catroid-ui-recyclerview-dialog-login-LoginDialogFragment, reason: not valid java name */
    /* synthetic */ void m5763xffc87b73(DialogInterface dialogInterface) {
        this.alertDialog.getButton(-1).setEnabled(false);
        this.alertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5761x397114f1(view);
            }
        });
        this.alertDialog.getButton(-3).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5762x1c9cc832(view);
            }
        });
        ViewUtils.showKeyboard(this.usernameEditText);
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$0$org-catrobat-catroid-ui-recyclerview-dialog-login-LoginDialogFragment, reason: not valid java name */
    /* synthetic */ void m5761x397114f1(View view) {
        onLoginButtonClick();
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$1$org-catrobat-catroid-ui-recyclerview-dialog-login-LoginDialogFragment, reason: not valid java name */
    /* synthetic */ void m5762x1c9cc832(View view) {
        onPasswordForgottenButtonClick();
    }

    @Override // org.catrobat.catroid.transfers.LoginTask.OnLoginListener
    public void onLoginComplete() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CURRENT_OAUTH_PROVIDER, Constants.NO_OAUTH_PROVIDER);
        this.signInCompleteListener.onLoginSuccessful(bundle);
        dismiss();
    }

    @Override // org.catrobat.catroid.transfers.LoginTask.OnLoginListener
    public void onLoginFailed(String msg) {
        this.passwordEditText.setError(msg);
        this.alertDialog.getButton(-1).setEnabled(false);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        this.signInCompleteListener.onLoginCancel();
    }

    private void onLoginButtonClick() {
        LoginTask loginTask = new LoginTask(getActivity(), this.usernameEditText.getText().toString().replaceAll("\\s", ""), this.passwordEditText.getText().toString());
        loginTask.setOnLoginListener(this);
        loginTask.execute(new Void[0]);
    }

    private void onPasswordForgottenButtonClick() {
        String strConcat = (ServerCalls.useTestUrl ? ServerCalls.BASE_URL_TEST_HTTPS : "https://t.me/NewCatroidDevs").concat(PASSWORD_FORGOTTEN_PATH);
        Intent intent = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
        intent.putExtra("url", strConcat);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLoginBtnStatus() {
        if (this.alertDialog.getButton(-1) != null) {
            if (!this.usernameInputLayout.isErrorEnabled() && !this.passwordInputLayout.isErrorEnabled()) {
                this.alertDialog.getButton(-1).setEnabled(true);
            } else {
                this.alertDialog.getButton(-1).setEnabled(false);
            }
        }
    }
}
