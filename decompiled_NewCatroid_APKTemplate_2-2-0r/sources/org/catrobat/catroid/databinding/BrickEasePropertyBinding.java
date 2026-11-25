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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickEasePropertyBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickEasePropertyBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickEasePropertyDurationEdit;
    public final TextView brickEasePropertyEndEdit;
    public final BrickLayout brickEasePropertyLayout;
    public final TextView brickEasePropertyStartEdit;
    public final Spinner easePropertyTargetSpinner;
    public final Spinner easePropertyTypeSpinner;
    private final LinearLayout rootView;

    private BrickEasePropertyBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickEasePropertyDurationEdit, TextView brickEasePropertyEndEdit, BrickLayout brickEasePropertyLayout, TextView brickEasePropertyStartEdit, Spinner easePropertyTargetSpinner, Spinner easePropertyTypeSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickEasePropertyDurationEdit = brickEasePropertyDurationEdit;
        this.brickEasePropertyEndEdit = brickEasePropertyEndEdit;
        this.brickEasePropertyLayout = brickEasePropertyLayout;
        this.brickEasePropertyStartEdit = brickEasePropertyStartEdit;
        this.easePropertyTargetSpinner = easePropertyTargetSpinner;
        this.easePropertyTypeSpinner = easePropertyTypeSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickEasePropertyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickEasePropertyBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ease_property, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickEasePropertyBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_ease_property_duration_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ease_property_duration_edit);
            if (textView != null) {
                i = R.id.brick_ease_property_end_edit;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ease_property_end_edit);
                if (textView2 != null) {
                    i = R.id.brick_ease_property_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_ease_property_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_ease_property_start_edit;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ease_property_start_edit);
                        if (textView3 != null) {
                            i = R.id.ease_property_target_spinner;
                            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.ease_property_target_spinner);
                            if (spinner != null) {
                                i = R.id.ease_property_type_spinner;
                                Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.ease_property_type_spinner);
                                if (spinner2 != null) {
                                    return new BrickEasePropertyBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, spinner, spinner2);
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
