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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPhiroIfSensorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPhiroIfSensorBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final Spinner brickPhiroSensorActionSpinner;
    public final TextView brickPhiroSensorLabel;
    public final BrickLayout brickPhiroSensorLayout;
    public final TextView phiroSensorLabelSecondPart;
    private final LinearLayout rootView;

    private BrickPhiroIfSensorBinding(LinearLayout rootView, CheckBox brickCheckbox, Spinner brickPhiroSensorActionSpinner, TextView brickPhiroSensorLabel, BrickLayout brickPhiroSensorLayout, TextView phiroSensorLabelSecondPart) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPhiroSensorActionSpinner = brickPhiroSensorActionSpinner;
        this.brickPhiroSensorLabel = brickPhiroSensorLabel;
        this.brickPhiroSensorLayout = brickPhiroSensorLayout;
        this.phiroSensorLabelSecondPart = phiroSensorLabelSecondPart;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPhiroIfSensorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPhiroIfSensorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_phiro_if_sensor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPhiroIfSensorBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_phiro_sensor_action_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_sensor_action_spinner);
            if (spinner != null) {
                i = R.id.brick_phiro_sensor_label;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_sensor_label);
                if (textView != null) {
                    i = R.id.brick_phiro_sensor_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_sensor_layout);
                    if (brickLayout != null) {
                        i = R.id.phiro_sensor_label_second_part;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.phiro_sensor_label_second_part);
                        if (textView2 != null) {
                            return new BrickPhiroIfSensorBinding((LinearLayout) rootView, checkBox, spinner, textView, brickLayout, textView2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
