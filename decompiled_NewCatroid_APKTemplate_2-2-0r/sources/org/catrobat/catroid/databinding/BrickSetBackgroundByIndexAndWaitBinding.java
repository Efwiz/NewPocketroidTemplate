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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetBackgroundByIndexAndWaitBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetBackgroundByIndexAndWaitBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetBackgroundByIndexAndWait;
    public final TextView brickSetBackgroundByIndexLabel;
    public final BrickLayout brickSetBackgroundByIndexLayout;
    public final TextView brickSetBackgroundByIndexWaitEditText;
    private final LinearLayout rootView;

    private BrickSetBackgroundByIndexAndWaitBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetBackgroundByIndexAndWait, TextView brickSetBackgroundByIndexLabel, BrickLayout brickSetBackgroundByIndexLayout, TextView brickSetBackgroundByIndexWaitEditText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetBackgroundByIndexAndWait = brickSetBackgroundByIndexAndWait;
        this.brickSetBackgroundByIndexLabel = brickSetBackgroundByIndexLabel;
        this.brickSetBackgroundByIndexLayout = brickSetBackgroundByIndexLayout;
        this.brickSetBackgroundByIndexWaitEditText = brickSetBackgroundByIndexWaitEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetBackgroundByIndexAndWaitBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetBackgroundByIndexAndWaitBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_background_by_index_and_wait, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetBackgroundByIndexAndWaitBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_background_by_index_and_wait;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_background_by_index_and_wait);
            if (textView != null) {
                i = R.id.brick_set_background_by_index_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_background_by_index_label);
                if (textView2 != null) {
                    i = R.id.brick_set_background_by_index_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_background_by_index_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_set_background_by_index_wait_edit_text;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_background_by_index_wait_edit_text);
                        if (textView3 != null) {
                            return new BrickSetBackgroundByIndexAndWaitBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
