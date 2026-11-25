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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickListenMicroBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickListenMicroBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickListenMicroEditText;
    public final TextView brickListenMicroLabel;
    public final TextView brickListenMicroLabel2;
    public final BrickLayout brickListenMicroLayout;
    private final LinearLayout rootView;

    private BrickListenMicroBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickListenMicroEditText, TextView brickListenMicroLabel, TextView brickListenMicroLabel2, BrickLayout brickListenMicroLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickListenMicroEditText = brickListenMicroEditText;
        this.brickListenMicroLabel = brickListenMicroLabel;
        this.brickListenMicroLabel2 = brickListenMicroLabel2;
        this.brickListenMicroLayout = brickListenMicroLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickListenMicroBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickListenMicroBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_listen_micro, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickListenMicroBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_listen_micro_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_listen_micro_edit_text);
            if (textView != null) {
                i = R.id.brick_listen_micro_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_listen_micro_label);
                if (textView2 != null) {
                    i = R.id.brick_listen_micro_label2;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_listen_micro_label2);
                    if (textView3 != null) {
                        i = R.id.brick_listen_micro_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_listen_micro_layout);
                        if (brickLayout != null) {
                            return new BrickListenMicroBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
