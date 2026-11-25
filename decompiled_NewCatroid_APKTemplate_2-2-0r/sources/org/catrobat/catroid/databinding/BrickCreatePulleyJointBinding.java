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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreatePulleyJointBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreatePulleyJointBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickGroundAnchorAXEditText;
    public final TextView brickGroundAnchorAYEditText;
    public final TextView brickGroundAnchorBXEditText;
    public final TextView brickGroundAnchorBYEditText;
    public final TextView brickJointIdEditText;
    public final BrickLayout brickPulleyJointLayout;
    public final TextView brickRatioEditText;
    public final TextView brickSpriteAEditText;
    public final TextView brickSpriteBEditText;
    private final LinearLayout rootView;

    private BrickCreatePulleyJointBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickGroundAnchorAXEditText, TextView brickGroundAnchorAYEditText, TextView brickGroundAnchorBXEditText, TextView brickGroundAnchorBYEditText, TextView brickJointIdEditText, BrickLayout brickPulleyJointLayout, TextView brickRatioEditText, TextView brickSpriteAEditText, TextView brickSpriteBEditText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickGroundAnchorAXEditText = brickGroundAnchorAXEditText;
        this.brickGroundAnchorAYEditText = brickGroundAnchorAYEditText;
        this.brickGroundAnchorBXEditText = brickGroundAnchorBXEditText;
        this.brickGroundAnchorBYEditText = brickGroundAnchorBYEditText;
        this.brickJointIdEditText = brickJointIdEditText;
        this.brickPulleyJointLayout = brickPulleyJointLayout;
        this.brickRatioEditText = brickRatioEditText;
        this.brickSpriteAEditText = brickSpriteAEditText;
        this.brickSpriteBEditText = brickSpriteBEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreatePulleyJointBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreatePulleyJointBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_pulley_joint, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreatePulleyJointBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_ground_anchor_a_x_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ground_anchor_a_x_edit_text);
            if (textView != null) {
                i = R.id.brick_ground_anchor_a_y_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ground_anchor_a_y_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_ground_anchor_b_x_edit_text;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ground_anchor_b_x_edit_text);
                    if (textView3 != null) {
                        i = R.id.brick_ground_anchor_b_y_edit_text;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ground_anchor_b_y_edit_text);
                        if (textView4 != null) {
                            i = R.id.brick_joint_id_edit_text;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_joint_id_edit_text);
                            if (textView5 != null) {
                                i = R.id.brick_pulley_joint_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_pulley_joint_layout);
                                if (brickLayout != null) {
                                    i = R.id.brick_ratio_edit_text;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ratio_edit_text);
                                    if (textView6 != null) {
                                        i = R.id.brick_sprite_a_edit_text;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_sprite_a_edit_text);
                                        if (textView7 != null) {
                                            i = R.id.brick_sprite_b_edit_text;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_sprite_b_edit_text);
                                            if (textView8 != null) {
                                                return new BrickCreatePulleyJointBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, brickLayout, textView6, textView7, textView8);
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
