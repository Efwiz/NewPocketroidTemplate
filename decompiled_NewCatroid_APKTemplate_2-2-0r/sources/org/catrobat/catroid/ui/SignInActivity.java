package org.catrobat.catroid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.transfers.GoogleLoginHandler;
import org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.login.SignInCompleteListener;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/SignInActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SignInActivity extends BaseActivity implements SignInCompleteListener {
    public static final String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";
    private GoogleLoginHandler googleLoginHandler;

    @Override // org.catrobat.catroid.ui.recyclerview.dialog.login.SignInCompleteListener
    public void onLoginCancel() {
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sign_in);
        setUpGoogleSignin();
        TextView textView = (TextView) findViewById(R.id.register_terms_link);
        String string = getString(R.string.about_link_template, new Object[]{Constants.CATROBAT_TERMS_OF_USE_URL, getString(R.string.register_code_terms_of_use_text)});
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml(string));
    }

    public void setUpGoogleSignin() {
        this.googleLoginHandler = new GoogleLoginHandler(this);
        findViewById(R.id.sign_in_google_login_button).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SignInActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onButtonClick(view);
            }
        });
    }

    public void onButtonClick(final View view) {
        if (Utils.checkIsNetworkAvailableAndShowErrorMessage(this)) {
            onButtonClickForRealThisTime(view);
        }
    }

    private void onButtonClickForRealThisTime(View view) {
        switch (view.getId()) {
            case R.id.sign_in_google_login_button /* 2131365220 */:
                startActivityForResult(this.googleLoginHandler.getGoogleSignInClient().getSignInIntent(), 100);
                break;
            case R.id.sign_in_login /* 2131365221 */:
                LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
                loginDialogFragment.setSignInCompleteListener(this);
                loginDialogFragment.show(getSupportFragmentManager(), LoginDialogFragment.TAG);
                break;
            case R.id.sign_in_register /* 2131365222 */:
                RegistrationDialogFragment registrationDialogFragment = new RegistrationDialogFragment();
                registrationDialogFragment.setSignInCompleteListener(this);
                registrationDialogFragment.show(getSupportFragmentManager(), RegistrationDialogFragment.TAG);
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.googleLoginHandler.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.dialog.login.SignInCompleteListener
    public void onLoginSuccessful(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra(LOGIN_SUCCESSFUL, bundle);
        setResult(-1, intent);
        finish();
    }
}
