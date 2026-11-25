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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickStartServerBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickStartServerBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickShowToastLabel;
    public final TextView brickStartServerEditText;
    public final BrickLayout brickStartServerLayout;
    private final LinearLayout rootView;

    private BrickStartServerBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickShowToastLabel, TextView brickStartServerEditText, BrickLayout brickStartServerLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickShowToastLabel = brickShowToastLabel;
        this.brickStartServerEditText = brickStartServerEditText;
        this.brickStartServerLayout = brickStartServerLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickStartServerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickStartServerBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_start_server, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickStartServerBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_show_toast_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_label);
            if (textView != null) {
                i = R.id.brick_start_server_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_start_server_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_start_server_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_start_server_layout);
                    if (brickLayout != null) {
                        return new BrickStartServerBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
