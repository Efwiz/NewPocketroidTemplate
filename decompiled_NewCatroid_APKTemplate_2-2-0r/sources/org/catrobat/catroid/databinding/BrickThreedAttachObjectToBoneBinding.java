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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickThreedAttachObjectToBoneBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickThreedAttachObjectToBoneBinding implements ViewBinding {
    public final TextView brickAttachBoneName;
    public final TextView brickAttachChildId;
    public final TextView brickAttachOx;
    public final TextView brickAttachOy;
    public final TextView brickAttachOz;
    public final TextView brickAttachParentId;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickLayout;
    private final LinearLayout rootView;

    private BrickThreedAttachObjectToBoneBinding(LinearLayout rootView, TextView brickAttachBoneName, TextView brickAttachChildId, TextView brickAttachOx, TextView brickAttachOy, TextView brickAttachOz, TextView brickAttachParentId, CheckBox brickCheckbox, BrickLayout brickLayout) {
        this.rootView = rootView;
        this.brickAttachBoneName = brickAttachBoneName;
        this.brickAttachChildId = brickAttachChildId;
        this.brickAttachOx = brickAttachOx;
        this.brickAttachOy = brickAttachOy;
        this.brickAttachOz = brickAttachOz;
        this.brickAttachParentId = brickAttachParentId;
        this.brickCheckbox = brickCheckbox;
        this.brickLayout = brickLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickThreedAttachObjectToBoneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickThreedAttachObjectToBoneBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_threed_attach_object_to_bone, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickThreedAttachObjectToBoneBinding bind(View rootView) {
        int i = R.id.brick_attach_bone_name;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_attach_bone_name);
        if (textView != null) {
            i = R.id.brick_attach_child_id;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_attach_child_id);
            if (textView2 != null) {
                i = R.id.brick_attach_ox;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_attach_ox);
                if (textView3 != null) {
                    i = R.id.brick_attach_oy;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_attach_oy);
                    if (textView4 != null) {
                        i = R.id.brick_attach_oz;
                        TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_attach_oz);
                        if (textView5 != null) {
                            i = R.id.brick_attach_parent_id;
                            TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_attach_parent_id);
                            if (textView6 != null) {
                                i = R.id.brick_checkbox;
                                CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                                if (checkBox != null) {
                                    i = R.id.brick_layout;
                                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_layout);
                                    if (brickLayout != null) {
                                        return new BrickThreedAttachObjectToBoneBinding((LinearLayout) rootView, textView, textView2, textView3, textView4, textView5, textView6, checkBox, brickLayout);
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
