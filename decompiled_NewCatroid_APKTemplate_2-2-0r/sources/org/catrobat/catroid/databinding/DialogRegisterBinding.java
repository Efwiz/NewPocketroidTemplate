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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogRegisterBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogRegisterBinding implements ViewBinding {
    public final TextInputLayout dialogRegisterEmail;
    public final TextInputLayout dialogRegisterPassword;
    public final TextInputLayout dialogRegisterPasswordConfirm;
    public final TextInputLayout dialogRegisterUsername;
    private final ScrollView rootView;
    public final CheckBox showPassword;

    private DialogRegisterBinding(ScrollView rootView, TextInputLayout dialogRegisterEmail, TextInputLayout dialogRegisterPassword, TextInputLayout dialogRegisterPasswordConfirm, TextInputLayout dialogRegisterUsername, CheckBox showPassword) {
        this.rootView = rootView;
        this.dialogRegisterEmail = dialogRegisterEmail;
        this.dialogRegisterPassword = dialogRegisterPassword;
        this.dialogRegisterPasswordConfirm = dialogRegisterPasswordConfirm;
        this.dialogRegisterUsername = dialogRegisterUsername;
        this.showPassword = showPassword;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogRegisterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogRegisterBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_register, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogRegisterBinding bind(View rootView) {
        int i = R.id.dialog_register_email;
        TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.dialog_register_email);
        if (textInputLayout != null) {
            i = R.id.dialog_register_password;
            TextInputLayout textInputLayout2 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.dialog_register_password);
            if (textInputLayout2 != null) {
                i = R.id.dialog_register_password_confirm;
                TextInputLayout textInputLayout3 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.dialog_register_password_confirm);
                if (textInputLayout3 != null) {
                    i = R.id.dialog_register_username;
                    TextInputLayout textInputLayout4 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.dialog_register_username);
                    if (textInputLayout4 != null) {
                        i = R.id.show_password;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.show_password);
                        if (checkBox != null) {
                            return new DialogRegisterBinding((ScrollView) rootView, textInputLayout, textInputLayout2, textInputLayout3, textInputLayout4, checkBox);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
