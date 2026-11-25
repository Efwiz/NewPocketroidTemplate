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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickStopScriptBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickStopScriptBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickStopScriptLabel;
    public final BrickLayout brickStopScriptLayout;
    public final Spinner brickStopScriptSpinner;
    private final LinearLayout rootView;

    private BrickStopScriptBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickStopScriptLabel, BrickLayout brickStopScriptLayout, Spinner brickStopScriptSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickStopScriptLabel = brickStopScriptLabel;
        this.brickStopScriptLayout = brickStopScriptLayout;
        this.brickStopScriptSpinner = brickStopScriptSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickStopScriptBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickStopScriptBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_stop_script, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickStopScriptBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_stop_script_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_stop_script_label);
            if (textView != null) {
                i = R.id.brick_stop_script_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_stop_script_layout);
                if (brickLayout != null) {
                    i = R.id.brick_stop_script_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_stop_script_spinner);
                    if (spinner != null) {
                        return new BrickStopScriptBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
