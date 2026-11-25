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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetObjectColorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetObjectColorBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetObjectColorB;
    public final TextView brickSetObjectColorG;
    public final TextView brickSetObjectColorId;
    public final BrickLayout brickSetObjectColorLayout;
    public final TextView brickSetObjectColorR;
    private final LinearLayout rootView;

    private BrickSetObjectColorBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetObjectColorB, TextView brickSetObjectColorG, TextView brickSetObjectColorId, BrickLayout brickSetObjectColorLayout, TextView brickSetObjectColorR) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetObjectColorB = brickSetObjectColorB;
        this.brickSetObjectColorG = brickSetObjectColorG;
        this.brickSetObjectColorId = brickSetObjectColorId;
        this.brickSetObjectColorLayout = brickSetObjectColorLayout;
        this.brickSetObjectColorR = brickSetObjectColorR;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetObjectColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetObjectColorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_object_color, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetObjectColorBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_object_color_b;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_object_color_b);
            if (textView != null) {
                i = R.id.brick_set_object_color_g;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_object_color_g);
                if (textView2 != null) {
                    i = R.id.brick_set_object_color_id;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_object_color_id);
                    if (textView3 != null) {
                        i = R.id.brick_set_object_color_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_object_color_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_set_object_color_r;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_object_color_r);
                            if (textView4 != null) {
                                return new BrickSetObjectColorBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
