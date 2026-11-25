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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickBroadcastWithParamsReceiveBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickBroadcastWithParamsReceiveBinding implements ViewBinding {
    public final TextView brickBroadcastWithParamsReceiveEditSignal;
    public final BrickLayout brickBroadcastWithParamsReceiveLayout;
    public final Spinner brickBroadcastWithParamsSpinnerVariable;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickBroadcastWithParamsReceiveBinding(LinearLayout rootView, TextView brickBroadcastWithParamsReceiveEditSignal, BrickLayout brickBroadcastWithParamsReceiveLayout, Spinner brickBroadcastWithParamsSpinnerVariable, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickBroadcastWithParamsReceiveEditSignal = brickBroadcastWithParamsReceiveEditSignal;
        this.brickBroadcastWithParamsReceiveLayout = brickBroadcastWithParamsReceiveLayout;
        this.brickBroadcastWithParamsSpinnerVariable = brickBroadcastWithParamsSpinnerVariable;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickBroadcastWithParamsReceiveBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickBroadcastWithParamsReceiveBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_broadcast_with_params_receive, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickBroadcastWithParamsReceiveBinding bind(View rootView) {
        int i = R.id.brick_broadcast_with_params_receive_edit_signal;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_broadcast_with_params_receive_edit_signal);
        if (textView != null) {
            i = R.id.brick_broadcast_with_params_receive_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_broadcast_with_params_receive_layout);
            if (brickLayout != null) {
                i = R.id.brick_broadcast_with_params_spinner_variable;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_broadcast_with_params_spinner_variable);
                if (spinner != null) {
                    i = R.id.brick_checkbox;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                    if (checkBox != null) {
                        return new BrickBroadcastWithParamsReceiveBinding((LinearLayout) rootView, textView, brickLayout, spinner, checkBox);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
