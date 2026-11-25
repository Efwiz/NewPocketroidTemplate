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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPerformRaycastBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPerformRaycastBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickEndXEditText;
    public final TextView brickEndYEditText;
    public final TextView brickRayIdEditText;
    public final BrickLayout brickRaycastLayout;
    public final TextView brickStartXEditText;
    public final TextView brickStartYEditText;
    private final LinearLayout rootView;

    private BrickPerformRaycastBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickEndXEditText, TextView brickEndYEditText, TextView brickRayIdEditText, BrickLayout brickRaycastLayout, TextView brickStartXEditText, TextView brickStartYEditText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickEndXEditText = brickEndXEditText;
        this.brickEndYEditText = brickEndYEditText;
        this.brickRayIdEditText = brickRayIdEditText;
        this.brickRaycastLayout = brickRaycastLayout;
        this.brickStartXEditText = brickStartXEditText;
        this.brickStartYEditText = brickStartYEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPerformRaycastBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPerformRaycastBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_perform_raycast, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPerformRaycastBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_end_x_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_end_x_edit_text);
            if (textView != null) {
                i = R.id.brick_end_y_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_end_y_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_ray_id_edit_text;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ray_id_edit_text);
                    if (textView3 != null) {
                        i = R.id.brick_raycast_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_raycast_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_start_x_edit_text;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_start_x_edit_text);
                            if (textView4 != null) {
                                i = R.id.brick_start_y_edit_text;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_start_y_edit_text);
                                if (textView5 != null) {
                                    return new BrickPerformRaycastBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5);
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
