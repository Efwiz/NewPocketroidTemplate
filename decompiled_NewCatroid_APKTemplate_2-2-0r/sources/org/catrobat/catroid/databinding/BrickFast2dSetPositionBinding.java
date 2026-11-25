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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dSetPositionBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dSetPositionBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dSetPosEditId;
    public final TextView brickFast2dSetPosEditX;
    public final TextView brickFast2dSetPosEditY;
    public final BrickLayout brickFast2dSetPosLayout;
    private final LinearLayout rootView;

    private BrickFast2dSetPositionBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dSetPosEditId, TextView brickFast2dSetPosEditX, TextView brickFast2dSetPosEditY, BrickLayout brickFast2dSetPosLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dSetPosEditId = brickFast2dSetPosEditId;
        this.brickFast2dSetPosEditX = brickFast2dSetPosEditX;
        this.brickFast2dSetPosEditY = brickFast2dSetPosEditY;
        this.brickFast2dSetPosLayout = brickFast2dSetPosLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dSetPositionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dSetPositionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_set_position, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dSetPositionBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_set_pos_edit_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_pos_edit_id);
            if (textView != null) {
                i = R.id.brick_fast2d_set_pos_edit_x;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_pos_edit_x);
                if (textView2 != null) {
                    i = R.id.brick_fast2d_set_pos_edit_y;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_pos_edit_y);
                    if (textView3 != null) {
                        i = R.id.brick_fast2d_set_pos_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_pos_layout);
                        if (brickLayout != null) {
                            return new BrickFast2dSetPositionBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
