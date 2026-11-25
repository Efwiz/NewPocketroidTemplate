package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickBroadcastReceiveBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickBroadcastReceiveBinding implements ViewBinding {
    public final BrickLayout brickBroadcastReceiveLayout;
    public final Spinner brickBroadcastSpinner;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickBroadcastReceiveBinding(LinearLayout rootView, BrickLayout brickBroadcastReceiveLayout, Spinner brickBroadcastSpinner, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickBroadcastReceiveLayout = brickBroadcastReceiveLayout;
        this.brickBroadcastSpinner = brickBroadcastSpinner;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickBroadcastReceiveBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickBroadcastReceiveBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_broadcast_receive, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickBroadcastReceiveBinding bind(View rootView) {
        int i = R.id.brick_broadcast_receive_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_broadcast_receive_layout);
        if (brickLayout != null) {
            i = R.id.brick_broadcast_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_broadcast_spinner);
            if (spinner != null) {
                i = R.id.brick_checkbox;
                CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                if (checkBox != null) {
                    return new BrickBroadcastReceiveBinding((LinearLayout) rootView, brickLayout, spinner, checkBox);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
