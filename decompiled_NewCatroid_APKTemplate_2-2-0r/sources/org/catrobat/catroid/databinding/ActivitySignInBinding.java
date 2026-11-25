package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.gms.common.SignInButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivitySignInBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivitySignInBinding implements ViewBinding {
    public final TextView registerTerms;
    public final TextView registerTermsLink;
    private final LinearLayout rootView;
    public final SignInButton signInGoogleLoginButton;
    public final Button signInLogin;
    public final Button signInRegister;

    private ActivitySignInBinding(LinearLayout rootView, TextView registerTerms, TextView registerTermsLink, SignInButton signInGoogleLoginButton, Button signInLogin, Button signInRegister) {
        this.rootView = rootView;
        this.registerTerms = registerTerms;
        this.registerTermsLink = registerTermsLink;
        this.signInGoogleLoginButton = signInGoogleLoginButton;
        this.signInLogin = signInLogin;
        this.signInRegister = signInRegister;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySignInBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivitySignInBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_sign_in, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivitySignInBinding bind(View rootView) {
        int i = R.id.register_terms;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.register_terms);
        if (textView != null) {
            i = R.id.register_terms_link;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.register_terms_link);
            if (textView2 != null) {
                i = R.id.sign_in_google_login_button;
                SignInButton signInButton = (SignInButton) ViewBindings.findChildViewById(rootView, R.id.sign_in_google_login_button);
                if (signInButton != null) {
                    i = R.id.sign_in_login;
                    Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.sign_in_login);
                    if (button != null) {
                        i = R.id.sign_in_register;
                        Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.sign_in_register);
                        if (button2 != null) {
                            return new ActivitySignInBinding((LinearLayout) rootView, textView, textView2, signInButton, button, button2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
