package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCloneAndNameBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCloneAndNameBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickCloneNameEditText;
    public final Spinner brickCloneSpinner;
    private final LinearLayout rootView;

    private BrickCloneAndNameBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickCloneNameEditText, Spinner brickCloneSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCloneNameEditText = brickCloneNameEditText;
        this.brickCloneSpinner = brickCloneSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCloneAndNameBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCloneAndNameBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_clone_and_name, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCloneAndNameBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_clone_name_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_clone_name_edit_text);
            if (textView != null) {
                i = R.id.brick_clone_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_clone_spinner);
                if (spinner != null) {
                    return new BrickCloneAndNameBinding((LinearLayout) rootView, checkBox, textView, spinner);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
