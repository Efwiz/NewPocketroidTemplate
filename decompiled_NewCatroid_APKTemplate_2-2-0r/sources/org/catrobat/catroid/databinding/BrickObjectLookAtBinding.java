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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickObjectLookAtBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickObjectLookAtBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickObjectLookAtId;
    public final BrickLayout brickObjectLookAtLayout;
    public final TextView brickObjectLookAtX;
    public final TextView brickObjectLookAtY;
    public final TextView brickObjectLookAtZ;
    private final LinearLayout rootView;

    private BrickObjectLookAtBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickObjectLookAtId, BrickLayout brickObjectLookAtLayout, TextView brickObjectLookAtX, TextView brickObjectLookAtY, TextView brickObjectLookAtZ) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickObjectLookAtId = brickObjectLookAtId;
        this.brickObjectLookAtLayout = brickObjectLookAtLayout;
        this.brickObjectLookAtX = brickObjectLookAtX;
        this.brickObjectLookAtY = brickObjectLookAtY;
        this.brickObjectLookAtZ = brickObjectLookAtZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickObjectLookAtBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickObjectLookAtBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_object_look_at, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickObjectLookAtBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_object_look_at_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_look_at_id);
            if (textView != null) {
                i = R.id.brick_object_look_at_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_object_look_at_layout);
                if (brickLayout != null) {
                    i = R.id.brick_object_look_at_x;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_look_at_x);
                    if (textView2 != null) {
                        i = R.id.brick_object_look_at_y;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_look_at_y);
                        if (textView3 != null) {
                            i = R.id.brick_object_look_at_z;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_look_at_z);
                            if (textView4 != null) {
                                return new BrickObjectLookAtBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
