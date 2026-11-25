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
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickReadBaseBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickReadBaseBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickReadBaseEditBase;
    public final TextView brickReadBaseEditKey;
    public final BrickLayout brickReadBaseLayout;
    public final Spinner brickReadBaseSpinner;
    private final LinearLayout rootView;

    private BrickReadBaseBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickReadBaseEditBase, TextView brickReadBaseEditKey, BrickLayout brickReadBaseLayout, Spinner brickReadBaseSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickReadBaseEditBase = brickReadBaseEditBase;
        this.brickReadBaseEditKey = brickReadBaseEditKey;
        this.brickReadBaseLayout = brickReadBaseLayout;
        this.brickReadBaseSpinner = brickReadBaseSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickReadBaseBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickReadBaseBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_read_base, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickReadBaseBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_read_base_edit_base;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_read_base_edit_base);
            if (textView != null) {
                i = R.id.brick_read_base_edit_key;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_read_base_edit_key);
                if (textView2 != null) {
                    i = R.id.brick_read_base_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_read_base_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_read_base_spinner;
                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_read_base_spinner);
                        if (spinner != null) {
                            return new BrickReadBaseBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, spinner);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
