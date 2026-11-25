package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ListItemEditableParamBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ListItemEditableParamBinding implements ViewBinding {
    public final ImageButton deleteParamButton;
    public final TextInputEditText paramNameEdit;
    public final TextInputLayout paramNameLayout;
    public final TextInputEditText paramValueEdit;
    public final TextInputLayout paramValueLayout;
    private final LinearLayout rootView;

    private ListItemEditableParamBinding(LinearLayout rootView, ImageButton deleteParamButton, TextInputEditText paramNameEdit, TextInputLayout paramNameLayout, TextInputEditText paramValueEdit, TextInputLayout paramValueLayout) {
        this.rootView = rootView;
        this.deleteParamButton = deleteParamButton;
        this.paramNameEdit = paramNameEdit;
        this.paramNameLayout = paramNameLayout;
        this.paramValueEdit = paramValueEdit;
        this.paramValueLayout = paramValueLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ListItemEditableParamBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ListItemEditableParamBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.list_item_editable_param, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ListItemEditableParamBinding bind(View rootView) {
        int i = R.id.delete_param_button;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.delete_param_button);
        if (imageButton != null) {
            i = R.id.param_name_edit;
            TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.param_name_edit);
            if (textInputEditText != null) {
                i = R.id.param_name_layout;
                TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.param_name_layout);
                if (textInputLayout != null) {
                    i = R.id.param_value_edit;
                    TextInputEditText textInputEditText2 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.param_value_edit);
                    if (textInputEditText2 != null) {
                        i = R.id.param_value_layout;
                        TextInputLayout textInputLayout2 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.param_value_layout);
                        if (textInputLayout2 != null) {
                            return new ListItemEditableParamBinding((LinearLayout) rootView, imageButton, textInputEditText, textInputLayout, textInputEditText2, textInputLayout2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
