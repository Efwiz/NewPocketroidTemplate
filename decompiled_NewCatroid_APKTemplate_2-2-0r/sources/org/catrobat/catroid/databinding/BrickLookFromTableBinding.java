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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickLookFromTableBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickLookFromTableBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickLookFromTableEditA;
    public final TextView brickLookFromTableEditB;
    public final TextView brickLookFromTableEditG;
    public final TextView brickLookFromTableEditR;
    public final BrickLayout brickLookFromTableLayout;
    private final LinearLayout rootView;

    private BrickLookFromTableBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickLookFromTableEditA, TextView brickLookFromTableEditB, TextView brickLookFromTableEditG, TextView brickLookFromTableEditR, BrickLayout brickLookFromTableLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickLookFromTableEditA = brickLookFromTableEditA;
        this.brickLookFromTableEditB = brickLookFromTableEditB;
        this.brickLookFromTableEditG = brickLookFromTableEditG;
        this.brickLookFromTableEditR = brickLookFromTableEditR;
        this.brickLookFromTableLayout = brickLookFromTableLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickLookFromTableBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickLookFromTableBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_look_from_table, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickLookFromTableBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_look_from_table_edit_a;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_look_from_table_edit_a);
            if (textView != null) {
                i = R.id.brick_look_from_table_edit_b;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_look_from_table_edit_b);
                if (textView2 != null) {
                    i = R.id.brick_look_from_table_edit_g;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_look_from_table_edit_g);
                    if (textView3 != null) {
                        i = R.id.brick_look_from_table_edit_r;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_look_from_table_edit_r);
                        if (textView4 != null) {
                            i = R.id.brick_look_from_table_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_look_from_table_layout);
                            if (brickLayout != null) {
                                return new BrickLookFromTableBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
