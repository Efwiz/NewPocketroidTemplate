package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickBroadcastWithParamsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickBroadcastWithParamsBinding implements ViewBinding {
    public final TextView brickBroadcastWithParamsEditData;
    public final TextView brickBroadcastWithParamsEditSignal;
    public final BrickLayout brickBroadcastWithParamsLayout;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickBroadcastWithParamsBinding(LinearLayout rootView, TextView brickBroadcastWithParamsEditData, TextView brickBroadcastWithParamsEditSignal, BrickLayout brickBroadcastWithParamsLayout, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickBroadcastWithParamsEditData = brickBroadcastWithParamsEditData;
        this.brickBroadcastWithParamsEditSignal = brickBroadcastWithParamsEditSignal;
        this.brickBroadcastWithParamsLayout = brickBroadcastWithParamsLayout;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickBroadcastWithParamsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickBroadcastWithParamsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_broadcast_with_params, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickBroadcastWithParamsBinding bind(View rootView) {
        int i = R.id.brick_broadcast_with_params_edit_data;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_broadcast_with_params_edit_data);
        if (textView != null) {
            i = R.id.brick_broadcast_with_params_edit_signal;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_broadcast_with_params_edit_signal);
            if (textView2 != null) {
                i = R.id.brick_broadcast_with_params_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_broadcast_with_params_layout);
                if (brickLayout != null) {
                    i = R.id.brick_checkbox;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                    if (checkBox != null) {
                        return new BrickBroadcastWithParamsBinding((LinearLayout) rootView, textView, textView2, brickLayout, checkBox);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
