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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreatePointJointBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreatePointJointBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickCreatePointJointLayout;
    public final TextView brickJointNameEdit;
    public final TextView brickJointObjectAEdit;
    public final TextView brickJointObjectBEdit;
    public final TextView brickJointPivotAX;
    public final TextView brickJointPivotAY;
    public final TextView brickJointPivotAZ;
    public final TextView brickJointPivotBX;
    public final TextView brickJointPivotBY;
    public final TextView brickJointPivotBZ;
    private final LinearLayout rootView;

    private BrickCreatePointJointBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickCreatePointJointLayout, TextView brickJointNameEdit, TextView brickJointObjectAEdit, TextView brickJointObjectBEdit, TextView brickJointPivotAX, TextView brickJointPivotAY, TextView brickJointPivotAZ, TextView brickJointPivotBX, TextView brickJointPivotBY, TextView brickJointPivotBZ) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCreatePointJointLayout = brickCreatePointJointLayout;
        this.brickJointNameEdit = brickJointNameEdit;
        this.brickJointObjectAEdit = brickJointObjectAEdit;
        this.brickJointObjectBEdit = brickJointObjectBEdit;
        this.brickJointPivotAX = brickJointPivotAX;
        this.brickJointPivotAY = brickJointPivotAY;
        this.brickJointPivotAZ = brickJointPivotAZ;
        this.brickJointPivotBX = brickJointPivotBX;
        this.brickJointPivotBY = brickJointPivotBY;
        this.brickJointPivotBZ = brickJointPivotBZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreatePointJointBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreatePointJointBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_point_joint, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreatePointJointBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_create_point_joint_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_point_joint_layout);
            if (brickLayout != null) {
                i = R.id.brick_joint_name_edit;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_name_edit);
                if (textView != null) {
                    i = R.id.brick_joint_object_a_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_object_a_edit);
                    if (textView2 != null) {
                        i = R.id.brick_joint_object_b_edit;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_object_b_edit);
                        if (textView3 != null) {
                            i = R.id.brick_joint_pivot_a_x;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_pivot_a_x);
                            if (textView4 != null) {
                                i = R.id.brick_joint_pivot_a_y;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_pivot_a_y);
                                if (textView5 != null) {
                                    i = R.id.brick_joint_pivot_a_z;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_pivot_a_z);
                                    if (textView6 != null) {
                                        i = R.id.brick_joint_pivot_b_x;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_pivot_b_x);
                                        if (textView7 != null) {
                                            i = R.id.brick_joint_pivot_b_y;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_pivot_b_y);
                                            if (textView8 != null) {
                                                i = R.id.brick_joint_pivot_b_z;
                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_pivot_b_z);
                                                if (textView9 != null) {
                                                    return new BrickCreatePointJointBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
