package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogCreateRepoBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogCreateRepoBinding implements ViewBinding {
    public final TextInputEditText repoNameEditText;
    public final TextInputLayout repoNameLayout;
    public final SwitchMaterial repoPrivateSwitch;
    private final LinearLayout rootView;

    private DialogCreateRepoBinding(LinearLayout rootView, TextInputEditText repoNameEditText, TextInputLayout repoNameLayout, SwitchMaterial repoPrivateSwitch) {
        this.rootView = rootView;
        this.repoNameEditText = repoNameEditText;
        this.repoNameLayout = repoNameLayout;
        this.repoPrivateSwitch = repoPrivateSwitch;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogCreateRepoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogCreateRepoBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_create_repo, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogCreateRepoBinding bind(View rootView) {
        int i = R.id.repo_name_edit_text;
        TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.repo_name_edit_text);
        if (textInputEditText != null) {
            i = R.id.repo_name_layout;
            TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.repo_name_layout);
            if (textInputLayout != null) {
                i = R.id.repo_private_switch;
                SwitchMaterial switchMaterial = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.repo_private_switch);
                if (switchMaterial != null) {
                    return new DialogCreateRepoBinding((LinearLayout) rootView, textInputEditText, textInputLayout, switchMaterial);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
