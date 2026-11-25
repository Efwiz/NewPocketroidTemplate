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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetNeuralBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetNeuralBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetNeutralEditName;
    public final TextView brickSetNeutralEditText;
    public final BrickLayout brickSetNeutralLayout;
    private final LinearLayout rootView;

    private BrickSetNeuralBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetNeutralEditName, TextView brickSetNeutralEditText, BrickLayout brickSetNeutralLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetNeutralEditName = brickSetNeutralEditName;
        this.brickSetNeutralEditText = brickSetNeutralEditText;
        this.brickSetNeutralLayout = brickSetNeutralLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetNeuralBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetNeuralBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_neural, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetNeuralBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_neutral_edit_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_neutral_edit_name);
            if (textView != null) {
                i = R.id.brick_set_neutral_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_neutral_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_set_neutral_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_neutral_layout);
                    if (brickLayout != null) {
                        return new BrickSetNeuralBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
