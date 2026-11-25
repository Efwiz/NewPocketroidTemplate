package org.catrobat.catroid.ui.recyclerview.dialog.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.beust.jcommander.Parameters;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.transfers.CheckEmailAvailableTask;
import org.catrobat.catroid.transfers.CheckUserNameAvailableTask;
import org.catrobat.catroid.transfers.RegistrationTask;
import org.catrobat.catroid.ui.ViewUtils;
import org.catrobat.catroid.utils.DeviceSettingsProvider;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/login/RegistrationDialogFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RegistrationDialogFragment extends DialogFragment implements RegistrationTask.OnRegistrationListener {
    public static final String TAG = "RegistrationDialogFragment";
    private AlertDialog alertDialog;
    private EditText confirmPasswordEditText;
    private TextInputLayout confirmPasswordInputLayout;
    private EditText emailAddressEditText;
    private TextInputLayout emailInputLayout;
    private EditText passwordEditText;
    private TextInputLayout passwordInputLayout;
    private SignInCompleteListener signInCompleteListener;
    private EditText userNameEditText;
    private TextInputLayout usernameInputLayout;

    public void setSignInCompleteListener(SignInCompleteListener signInCompleteListener) {
        this.signInCompleteListener = signInCompleteListener;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        View viewInflate = View.inflate(getActivity(), R.layout.dialog_register, null);
        this.usernameInputLayout = (TextInputLayout) viewInflate.findViewById(R.id.dialog_register_username);
        this.emailInputLayout = (TextInputLayout) viewInflate.findViewById(R.id.dialog_register_email);
        this.passwordInputLayout = (TextInputLayout) viewInflate.findViewById(R.id.dialog_register_password);
        this.confirmPasswordInputLayout = (TextInputLayout) viewInflate.findViewById(R.id.dialog_register_password_confirm);
        this.alertDialog = new AlertDialog.Builder(getActivity()).setTitle(R.string.register).setView(viewInflate).setPositiveButton(R.string.register, (DialogInterface.OnClickListener) null).create();
        this.userNameEditText = this.usernameInputLayout.getEditText();
        this.emailAddressEditText = this.emailInputLayout.getEditText();
        this.passwordEditText = this.passwordInputLayout.getEditText();
        this.confirmPasswordEditText = this.confirmPasswordInputLayout.getEditText();
        this.userNameEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(final Editable s) {
                if (s.toString().trim().isEmpty()) {
                    RegistrationDialogFragment.this.usernameInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_empty_username));
                } else if (s.toString().trim().contains(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT)) {
                    RegistrationDialogFragment.this.usernameInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_username_as_email));
                } else if (!s.toString().trim().matches("^[a-zA-Z0-9-_.]*$")) {
                    RegistrationDialogFragment.this.usernameInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_invalid_username));
                } else if (s.toString().trim().startsWith(Parameters.DEFAULT_OPTION_PREFIXES) || s.toString().startsWith("_") || s.toString().startsWith(".")) {
                    RegistrationDialogFragment.this.usernameInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_username_start_with));
                } else {
                    RegistrationDialogFragment.this.usernameInputLayout.setErrorEnabled(false);
                }
                if (!RegistrationDialogFragment.this.usernameInputLayout.isErrorEnabled()) {
                    CheckUserNameAvailableTask checkUserNameAvailableTask = new CheckUserNameAvailableTask(s.toString());
                    checkUserNameAvailableTask.setOnCheckUserNameAvailableCompleteListener(new CheckUserNameAvailableTask.OnCheckUserNameAvailableCompleteListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment.1.1
                        @Override // org.catrobat.catroid.transfers.CheckUserNameAvailableTask.OnCheckUserNameAvailableCompleteListener
                        public void onCheckUserNameAvailableComplete(Boolean userNameAvailable, String username) {
                            if (userNameAvailable == null) {
                                ToastUtil.showError(RegistrationDialogFragment.this.getActivity(), R.string.error_internet_connection);
                            } else if (userNameAvailable.booleanValue()) {
                                RegistrationDialogFragment.this.usernameInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_username_already_exists));
                            }
                        }
                    });
                    checkUserNameAvailableTask.execute(new Void[0]);
                }
                RegistrationDialogFragment.this.handleRegisterBtnStatus();
            }
        });
        this.emailAddressEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(final Editable s) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches() || s.toString().isEmpty()) {
                    RegistrationDialogFragment.this.emailInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_invalid_email_format));
                } else {
                    RegistrationDialogFragment.this.emailInputLayout.setErrorEnabled(false);
                }
                if (!RegistrationDialogFragment.this.emailInputLayout.isErrorEnabled()) {
                    CheckEmailAvailableTask checkEmailAvailableTask = new CheckEmailAvailableTask(s.toString(), Constants.NO_OAUTH_PROVIDER);
                    checkEmailAvailableTask.setOnCheckEmailAvailableCompleteListener(new CheckEmailAvailableTask.OnCheckEmailAvailableCompleteListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment.2.1
                        @Override // org.catrobat.catroid.transfers.CheckEmailAvailableTask.OnCheckEmailAvailableCompleteListener
                        public void onCheckEmailAvailableComplete(Boolean emailAvailable, String provider) {
                            if (emailAvailable == null) {
                                ToastUtil.showError(RegistrationDialogFragment.this.getActivity(), R.string.error_internet_connection);
                            } else if (emailAvailable.booleanValue()) {
                                RegistrationDialogFragment.this.emailInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_email_exists));
                            }
                        }
                    });
                    checkEmailAvailableTask.execute(new String[0]);
                }
                RegistrationDialogFragment.this.handleRegisterBtnStatus();
            }
        });
        this.passwordEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    RegistrationDialogFragment.this.passwordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_empty_password));
                } else if (s.toString().length() < 6) {
                    RegistrationDialogFragment.this.passwordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_password_at_least_6_characters));
                } else if (!s.toString().equals(RegistrationDialogFragment.this.confirmPasswordEditText.getText().toString())) {
                    RegistrationDialogFragment.this.confirmPasswordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_passwords_mismatch));
                    RegistrationDialogFragment.this.passwordInputLayout.setErrorEnabled(false);
                } else {
                    RegistrationDialogFragment.this.passwordInputLayout.setErrorEnabled(false);
                    RegistrationDialogFragment.this.confirmPasswordInputLayout.setErrorEnabled(false);
                }
                RegistrationDialogFragment.this.handleRegisterBtnStatus();
            }
        });
        this.confirmPasswordEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment.4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    RegistrationDialogFragment.this.confirmPasswordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_empty_confirm_password));
                } else if (s.toString().length() < 6) {
                    RegistrationDialogFragment.this.confirmPasswordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_password_at_least_6_characters));
                } else if (!s.toString().equals(RegistrationDialogFragment.this.passwordEditText.getText().toString())) {
                    RegistrationDialogFragment.this.confirmPasswordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_passwords_mismatch));
                } else {
                    RegistrationDialogFragment.this.confirmPasswordInputLayout.setErrorEnabled(false);
                    RegistrationDialogFragment.this.passwordInputLayout.setErrorEnabled(false);
                }
                RegistrationDialogFragment.this.handleRegisterBtnStatus();
            }
        });
        ((CheckBox) viewInflate.findViewById(R.id.show_password)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RegistrationDialogFragment.this.passwordEditText.setInputType(1);
                    RegistrationDialogFragment.this.confirmPasswordEditText.setInputType(1);
                } else {
                    RegistrationDialogFragment.this.passwordEditText.setInputType(129);
                    RegistrationDialogFragment.this.confirmPasswordEditText.setInputType(129);
                }
            }
        });
        String userEmail = DeviceSettingsProvider.getUserEmail(getActivity());
        if (userEmail != null) {
            this.emailAddressEditText.setText(userEmail);
            this.emailInputLayout.setErrorEnabled(false);
        }
        this.alertDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.m5774x732c4300(dialogInterface);
            }
        });
        return this.alertDialog;
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$1$org-catrobat-catroid-ui-recyclerview-dialog-login-RegistrationDialogFragment, reason: not valid java name */
    /* synthetic */ void m5774x732c4300(DialogInterface dialogInterface) {
        this.alertDialog.getButton(-1).setEnabled(false);
        this.alertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5773x5a2af161(view);
            }
        });
        ViewUtils.showKeyboard(this.userNameEditText);
    }

    /* JADX INFO: renamed from: lambda$onCreateDialog$0$org-catrobat-catroid-ui-recyclerview-dialog-login-RegistrationDialogFragment, reason: not valid java name */
    /* synthetic */ void m5773x5a2af161(View view) {
        onRegisterButtonClick();
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        this.signInCompleteListener.onLoginCancel();
    }

    @Override // org.catrobat.catroid.transfers.RegistrationTask.OnRegistrationListener
    public void onRegistrationComplete() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CURRENT_OAUTH_PROVIDER, Constants.NO_OAUTH_PROVIDER);
        this.signInCompleteListener.onLoginSuccessful(bundle);
        dismiss();
    }

    @Override // org.catrobat.catroid.transfers.RegistrationTask.OnRegistrationListener
    public void onRegistrationFailed(String msg) {
        this.confirmPasswordEditText.setError(msg);
        this.alertDialog.getButton(-1).setEnabled(false);
    }

    private void onRegisterButtonClick() {
        RegistrationTask registrationTask = new RegistrationTask(getActivity(), this.userNameEditText.getText().toString().trim(), this.passwordEditText.getText().toString(), this.emailAddressEditText.getText().toString());
        registrationTask.setOnRegistrationListener(this);
        registrationTask.execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRegisterBtnStatus() {
        if (this.alertDialog.getButton(-1) != null) {
            if (!this.usernameInputLayout.isErrorEnabled() && !this.emailInputLayout.isErrorEnabled() && !this.passwordInputLayout.isErrorEnabled() && !this.confirmPasswordInputLayout.isErrorEnabled() && Utils.isNetworkAvailable(getActivity())) {
                this.alertDialog.getButton(-1).setEnabled(true);
            } else {
                this.alertDialog.getButton(-1).setEnabled(false);
            }
        }
    }
}
