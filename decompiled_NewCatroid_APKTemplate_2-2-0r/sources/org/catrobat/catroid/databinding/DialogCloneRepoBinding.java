package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogCloneRepoBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogCloneRepoBinding implements ViewBinding {
    public final TextInputEditText repoUrlEditText;
    public final TextInputLayout repoUrlLayout;
    private final LinearLayout rootView;

    private DialogCloneRepoBinding(LinearLayout rootView, TextInputEditText repoUrlEditText, TextInputLayout repoUrlLayout) {
        this.rootView = rootView;
        this.repoUrlEditText = repoUrlEditText;
        this.repoUrlLayout = repoUrlLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogCloneRepoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogCloneRepoBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_clone_repo, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogCloneRepoBinding bind(View rootView) {
        int i = R.id.repo_url_edit_text;
        TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.repo_url_edit_text);
        if (textInputEditText != null) {
            i = R.id.repo_url_layout;
            TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.repo_url_layout);
            if (textInputLayout != null) {
                return new DialogCloneRepoBinding((LinearLayout) rootView, textInputEditText, textInputLayout);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
