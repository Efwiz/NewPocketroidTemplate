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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickObjectLookAtCorrectBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickObjectLookAtCorrectBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickObjectLookAtCorrectId;
    public final BrickLayout brickObjectLookAtCorrectLayout;
    public final TextView brickObjectLookAtCorrectX;
    public final TextView brickObjectLookAtCorrectY;
    public final TextView brickObjectLookAtCorrectZ;
    private final LinearLayout rootView;

    private BrickObjectLookAtCorrectBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickObjectLookAtCorrectId, BrickLayout brickObjectLookAtCorrectLayout, TextView brickObjectLookAtCorrectX, TextView brickObjectLookAtCorrectY, TextView brickObjectLookAtCorrectZ) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickObjectLookAtCorrectId = brickObjectLookAtCorrectId;
        this.brickObjectLookAtCorrectLayout = brickObjectLookAtCorrectLayout;
        this.brickObjectLookAtCorrectX = brickObjectLookAtCorrectX;
        this.brickObjectLookAtCorrectY = brickObjectLookAtCorrectY;
        this.brickObjectLookAtCorrectZ = brickObjectLookAtCorrectZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickObjectLookAtCorrectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickObjectLookAtCorrectBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_object_look_at_correct, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickObjectLookAtCorrectBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_object_look_at_correct_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_look_at_correct_id);
            if (textView != null) {
                i = R.id.brick_object_look_at_correct_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_object_look_at_correct_layout);
                if (brickLayout != null) {
                    i = R.id.brick_object_look_at_correct_x;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_look_at_correct_x);
                    if (textView2 != null) {
                        i = R.id.brick_object_look_at_correct_y;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_look_at_correct_y);
                        if (textView3 != null) {
                            i = R.id.brick_object_look_at_correct_z;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_look_at_correct_z);
                            if (textView4 != null) {
                                return new BrickObjectLookAtCorrectBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
