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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickEase3dPropertyBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickEase3dPropertyBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickEase3dPropertyDurationEdit;
    public final TextView brickEase3dPropertyEndEdit;
    public final BrickLayout brickEase3dPropertyLayout;
    public final TextView brickEase3dPropertyObjectId;
    public final TextView brickEase3dPropertyStartEdit;
    public final Spinner ease3dPropertyTargetSpinner;
    public final Spinner ease3dPropertyTypeSpinner;
    private final LinearLayout rootView;

    private BrickEase3dPropertyBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickEase3dPropertyDurationEdit, TextView brickEase3dPropertyEndEdit, BrickLayout brickEase3dPropertyLayout, TextView brickEase3dPropertyObjectId, TextView brickEase3dPropertyStartEdit, Spinner ease3dPropertyTargetSpinner, Spinner ease3dPropertyTypeSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickEase3dPropertyDurationEdit = brickEase3dPropertyDurationEdit;
        this.brickEase3dPropertyEndEdit = brickEase3dPropertyEndEdit;
        this.brickEase3dPropertyLayout = brickEase3dPropertyLayout;
        this.brickEase3dPropertyObjectId = brickEase3dPropertyObjectId;
        this.brickEase3dPropertyStartEdit = brickEase3dPropertyStartEdit;
        this.ease3dPropertyTargetSpinner = ease3dPropertyTargetSpinner;
        this.ease3dPropertyTypeSpinner = ease3dPropertyTypeSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickEase3dPropertyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickEase3dPropertyBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ease_3d_property, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickEase3dPropertyBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_ease_3d_property_duration_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ease_3d_property_duration_edit);
            if (textView != null) {
                i = R.id.brick_ease_3d_property_end_edit;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ease_3d_property_end_edit);
                if (textView2 != null) {
                    i = R.id.brick_ease_3d_property_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_ease_3d_property_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_ease_3d_property_object_id;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ease_3d_property_object_id);
                        if (textView3 != null) {
                            i = R.id.brick_ease_3d_property_start_edit;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ease_3d_property_start_edit);
                            if (textView4 != null) {
                                i = R.id.ease_3d_property_target_spinner;
                                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.ease_3d_property_target_spinner);
                                if (spinner != null) {
                                    i = R.id.ease_3d_property_type_spinner;
                                    Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.ease_3d_property_type_spinner);
                                    if (spinner2 != null) {
                                        return new BrickEase3dPropertyBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, textView4, spinner, spinner2);
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
