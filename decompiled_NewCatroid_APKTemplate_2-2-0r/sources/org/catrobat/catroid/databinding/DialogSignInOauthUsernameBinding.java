package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogSignInOauthUsernameBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogSignInOauthUsernameBinding implements ViewBinding {
    public final TextInputLayout dialogSigninOauthUsername;
    public final TextView dialogSigninOauthUsernameThanks;
    private final ScrollView rootView;

    private DialogSignInOauthUsernameBinding(ScrollView rootView, TextInputLayout dialogSigninOauthUsername, TextView dialogSigninOauthUsernameThanks) {
        this.rootView = rootView;
        this.dialogSigninOauthUsername = dialogSigninOauthUsername;
        this.dialogSigninOauthUsernameThanks = dialogSigninOauthUsernameThanks;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogSignInOauthUsernameBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogSignInOauthUsernameBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_sign_in_oauth_username, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogSignInOauthUsernameBinding bind(View rootView) {
        int i = R.id.dialog_signin_oauth_username;
        TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.dialog_signin_oauth_username);
        if (textInputLayout != null) {
            i = R.id.dialog_signin_oauth_username_thanks;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_signin_oauth_username_thanks);
            if (textView != null) {
                return new DialogSignInOauthUsernameBinding((ScrollView) rootView, textInputLayout, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
