package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityCommunityLoginBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityCommunityLoginBinding implements ViewBinding {
    public final MaterialButton btnAction;
    public final LinearLayout cardContentContainer;
    public final TextInputEditText etEmail;
    public final TextInputEditText etLogin;
    public final TextInputEditText etPassword;
    public final MaterialCardView loginCardView;
    private final ConstraintLayout rootView;
    public final TextInputLayout tilEmail;
    public final TextInputLayout tilLogin;
    public final TextInputLayout tilPassword;
    public final TextView tvSwitchMode;
    public final TextView tvTitle;

    private ActivityCommunityLoginBinding(ConstraintLayout rootView, MaterialButton btnAction, LinearLayout cardContentContainer, TextInputEditText etEmail, TextInputEditText etLogin, TextInputEditText etPassword, MaterialCardView loginCardView, TextInputLayout tilEmail, TextInputLayout tilLogin, TextInputLayout tilPassword, TextView tvSwitchMode, TextView tvTitle) {
        this.rootView = rootView;
        this.btnAction = btnAction;
        this.cardContentContainer = cardContentContainer;
        this.etEmail = etEmail;
        this.etLogin = etLogin;
        this.etPassword = etPassword;
        this.loginCardView = loginCardView;
        this.tilEmail = tilEmail;
        this.tilLogin = tilLogin;
        this.tilPassword = tilPassword;
        this.tvSwitchMode = tvSwitchMode;
        this.tvTitle = tvTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityCommunityLoginBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityCommunityLoginBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_community_login, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityCommunityLoginBinding bind(View rootView) {
        int i = R.id.btnAction;
        MaterialButton materialButton = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnAction);
        if (materialButton != null) {
            i = R.id.cardContentContainer;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.cardContentContainer);
            if (linearLayout != null) {
                i = R.id.etEmail;
                TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etEmail);
                if (textInputEditText != null) {
                    i = R.id.etLogin;
                    TextInputEditText textInputEditText2 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etLogin);
                    if (textInputEditText2 != null) {
                        i = R.id.etPassword;
                        TextInputEditText textInputEditText3 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etPassword);
                        if (textInputEditText3 != null) {
                            i = R.id.loginCardView;
                            MaterialCardView materialCardView = (MaterialCardView) ViewBindings.findChildViewById(rootView, R.id.loginCardView);
                            if (materialCardView != null) {
                                i = R.id.tilEmail;
                                TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.tilEmail);
                                if (textInputLayout != null) {
                                    i = R.id.tilLogin;
                                    TextInputLayout textInputLayout2 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.tilLogin);
                                    if (textInputLayout2 != null) {
                                        i = R.id.tilPassword;
                                        TextInputLayout textInputLayout3 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.tilPassword);
                                        if (textInputLayout3 != null) {
                                            i = R.id.tvSwitchMode;
                                            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvSwitchMode);
                                            if (textView != null) {
                                                i = R.id.tvTitle;
                                                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvTitle);
                                                if (textView2 != null) {
                                                    return new ActivityCommunityLoginBinding((ConstraintLayout) rootView, materialButton, linearLayout, textInputEditText, textInputEditText2, textInputEditText3, materialCardView, textInputLayout, textInputLayout2, textInputLayout3, textView, textView2);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
