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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRaspiPinChangedBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRaspiPinChangedBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickRaspiWhenLabelSecondPart;
    public final BrickLayout brickRaspiWhenLayout;
    public final Spinner brickRaspiWhenPinspinner;
    public final Spinner brickRaspiWhenValuespinner;
    public final TextView raspiWhenLabel;
    private final LinearLayout rootView;

    private BrickRaspiPinChangedBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickRaspiWhenLabelSecondPart, BrickLayout brickRaspiWhenLayout, Spinner brickRaspiWhenPinspinner, Spinner brickRaspiWhenValuespinner, TextView raspiWhenLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRaspiWhenLabelSecondPart = brickRaspiWhenLabelSecondPart;
        this.brickRaspiWhenLayout = brickRaspiWhenLayout;
        this.brickRaspiWhenPinspinner = brickRaspiWhenPinspinner;
        this.brickRaspiWhenValuespinner = brickRaspiWhenValuespinner;
        this.raspiWhenLabel = raspiWhenLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRaspiPinChangedBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRaspiPinChangedBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_raspi_pin_changed, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRaspiPinChangedBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_raspi_when_label_second_part;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_raspi_when_label_second_part);
            if (textView != null) {
                i = R.id.brick_raspi_when_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_raspi_when_layout);
                if (brickLayout != null) {
                    i = R.id.brick_raspi_when_pinspinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_raspi_when_pinspinner);
                    if (spinner != null) {
                        i = R.id.brick_raspi_when_valuespinner;
                        Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_raspi_when_valuespinner);
                        if (spinner2 != null) {
                            i = R.id.raspi_when_label;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.raspi_when_label);
                            if (textView2 != null) {
                                return new BrickRaspiPinChangedBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner, spinner2, textView2);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
