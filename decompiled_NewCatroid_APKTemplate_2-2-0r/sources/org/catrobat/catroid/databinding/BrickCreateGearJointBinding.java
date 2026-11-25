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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateGearJointBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateGearJointBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickGearJointLayout;
    public final TextView brickJointAIdEditText;
    public final TextView brickJointBIdEditText;
    public final TextView brickJointIdEditText;
    public final TextView brickRatioEditText;
    private final LinearLayout rootView;

    private BrickCreateGearJointBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickGearJointLayout, TextView brickJointAIdEditText, TextView brickJointBIdEditText, TextView brickJointIdEditText, TextView brickRatioEditText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickGearJointLayout = brickGearJointLayout;
        this.brickJointAIdEditText = brickJointAIdEditText;
        this.brickJointBIdEditText = brickJointBIdEditText;
        this.brickJointIdEditText = brickJointIdEditText;
        this.brickRatioEditText = brickRatioEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateGearJointBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateGearJointBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_gear_joint, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateGearJointBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_gear_joint_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_gear_joint_layout);
            if (brickLayout != null) {
                i = R.id.brick_joint_a_id_edit_text;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_a_id_edit_text);
                if (textView != null) {
                    i = R.id.brick_joint_b_id_edit_text;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_b_id_edit_text);
                    if (textView2 != null) {
                        i = R.id.brick_joint_id_edit_text;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_id_edit_text);
                        if (textView3 != null) {
                            i = R.id.brick_ratio_edit_text;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ratio_edit_text);
                            if (textView4 != null) {
                                return new BrickCreateGearJointBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
