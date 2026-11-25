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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickStopServerBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickStopServerBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickStopServerLabel;
    public final BrickLayout brickStopServerLayout;
    private final LinearLayout rootView;

    private BrickStopServerBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickStopServerLabel, BrickLayout brickStopServerLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickStopServerLabel = brickStopServerLabel;
        this.brickStopServerLayout = brickStopServerLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickStopServerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickStopServerBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_stop_server, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickStopServerBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_stop_server_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_stop_server_label);
            if (textView != null) {
                i = R.id.brick_stop_server_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_stop_server_layout);
                if (brickLayout != null) {
                    return new BrickStopServerBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
