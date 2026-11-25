package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogLoginBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogLoginBinding implements ViewBinding {
    public final TextInputLayout dialogLoginPassword;
    public final TextInputLayout dialogLoginUsername;
    private final ScrollView rootView;
    public final CheckBox showPassword;

    private DialogLoginBinding(ScrollView rootView, TextInputLayout dialogLoginPassword, TextInputLayout dialogLoginUsername, CheckBox showPassword) {
        this.rootView = rootView;
        this.dialogLoginPassword = dialogLoginPassword;
        this.dialogLoginUsername = dialogLoginUsername;
        this.showPassword = showPassword;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogLoginBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogLoginBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_login, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogLoginBinding bind(View rootView) {
        int i = R.id.dialog_login_password;
        TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.dialog_login_password);
        if (textInputLayout != null) {
            i = R.id.dialog_login_username;
            TextInputLayout textInputLayout2 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.dialog_login_username);
            if (textInputLayout2 != null) {
                i = R.id.show_password;
                CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.show_password);
                if (checkBox != null) {
                    return new DialogLoginBinding((ScrollView) rootView, textInputLayout, textInputLayout2, checkBox);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
