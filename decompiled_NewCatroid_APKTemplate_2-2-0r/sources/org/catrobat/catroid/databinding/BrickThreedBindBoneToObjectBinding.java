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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickThreedBindBoneToObjectBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickThreedBindBoneToObjectBinding implements ViewBinding {
    public final TextView brickBindBoneName;
    public final TextView brickBindModelId;
    public final TextView brickBindTargetId;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickLayout;
    private final LinearLayout rootView;

    private BrickThreedBindBoneToObjectBinding(LinearLayout rootView, TextView brickBindBoneName, TextView brickBindModelId, TextView brickBindTargetId, CheckBox brickCheckbox, BrickLayout brickLayout) {
        this.rootView = rootView;
        this.brickBindBoneName = brickBindBoneName;
        this.brickBindModelId = brickBindModelId;
        this.brickBindTargetId = brickBindTargetId;
        this.brickCheckbox = brickCheckbox;
        this.brickLayout = brickLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickThreedBindBoneToObjectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickThreedBindBoneToObjectBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_threed_bind_bone_to_object, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickThreedBindBoneToObjectBinding bind(View rootView) {
        int i = R.id.brick_bind_bone_name;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_bind_bone_name);
        if (textView != null) {
            i = R.id.brick_bind_model_id;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_bind_model_id);
            if (textView2 != null) {
                i = R.id.brick_bind_target_id;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_bind_target_id);
                if (textView3 != null) {
                    i = R.id.brick_checkbox;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                    if (checkBox != null) {
                        i = R.id.brick_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_layout);
                        if (brickLayout != null) {
                            return new BrickThreedBindBoneToObjectBinding((LinearLayout) rootView, textView, textView2, textView3, checkBox, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
