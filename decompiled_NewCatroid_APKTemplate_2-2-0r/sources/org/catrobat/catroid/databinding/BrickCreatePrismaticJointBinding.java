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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreatePrismaticJointBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreatePrismaticJointBinding implements ViewBinding {
    public final TextView brickAnchorXEditText;
    public final TextView brickAnchorYEditText;
    public final TextView brickAxisXEditText;
    public final TextView brickAxisYEditText;
    public final CheckBox brickCheckbox;
    public final TextView brickJointIdEditText;
    public final BrickLayout brickPrismaticJointLayout;
    public final TextView brickSpriteBEditText;
    private final LinearLayout rootView;

    private BrickCreatePrismaticJointBinding(LinearLayout rootView, TextView brickAnchorXEditText, TextView brickAnchorYEditText, TextView brickAxisXEditText, TextView brickAxisYEditText, CheckBox brickCheckbox, TextView brickJointIdEditText, BrickLayout brickPrismaticJointLayout, TextView brickSpriteBEditText) {
        this.rootView = rootView;
        this.brickAnchorXEditText = brickAnchorXEditText;
        this.brickAnchorYEditText = brickAnchorYEditText;
        this.brickAxisXEditText = brickAxisXEditText;
        this.brickAxisYEditText = brickAxisYEditText;
        this.brickCheckbox = brickCheckbox;
        this.brickJointIdEditText = brickJointIdEditText;
        this.brickPrismaticJointLayout = brickPrismaticJointLayout;
        this.brickSpriteBEditText = brickSpriteBEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreatePrismaticJointBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreatePrismaticJointBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_prismatic_joint, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreatePrismaticJointBinding bind(View rootView) {
        int i = R.id.brick_anchor_x_edit_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_anchor_x_edit_text);
        if (textView != null) {
            i = R.id.brick_anchor_y_edit_text;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_anchor_y_edit_text);
            if (textView2 != null) {
                i = R.id.brick_axis_x_edit_text;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_axis_x_edit_text);
                if (textView3 != null) {
                    i = R.id.brick_axis_y_edit_text;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_axis_y_edit_text);
                    if (textView4 != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            i = R.id.brick_joint_id_edit_text;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_id_edit_text);
                            if (textView5 != null) {
                                i = R.id.brick_prismatic_joint_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_prismatic_joint_layout);
                                if (brickLayout != null) {
                                    i = R.id.brick_sprite_b_edit_text;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_sprite_b_edit_text);
                                    if (textView6 != null) {
                                        return new BrickCreatePrismaticJointBinding((LinearLayout) rootView, textView, textView2, textView3, textView4, checkBox, textView5, brickLayout, textView6);
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
