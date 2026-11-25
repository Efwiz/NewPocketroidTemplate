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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetLookByIndexBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetLookByIndexBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetLookByIndexEditText;
    public final TextView brickSetLookByIndexLabel;
    public final BrickLayout brickSetLookByIndexLayout;
    private final LinearLayout rootView;

    private BrickSetLookByIndexBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetLookByIndexEditText, TextView brickSetLookByIndexLabel, BrickLayout brickSetLookByIndexLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetLookByIndexEditText = brickSetLookByIndexEditText;
        this.brickSetLookByIndexLabel = brickSetLookByIndexLabel;
        this.brickSetLookByIndexLayout = brickSetLookByIndexLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetLookByIndexBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetLookByIndexBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_look_by_index, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetLookByIndexBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_look_by_index_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_look_by_index_edit_text);
            if (textView != null) {
                i = R.id.brick_set_look_by_index_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_look_by_index_label);
                if (textView2 != null) {
                    i = R.id.brick_set_look_by_index_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_look_by_index_layout);
                    if (brickLayout != null) {
                        return new BrickSetLookByIndexBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
