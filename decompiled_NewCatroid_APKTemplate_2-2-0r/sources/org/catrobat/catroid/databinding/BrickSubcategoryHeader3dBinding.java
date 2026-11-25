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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSubcategoryHeader3dBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSubcategoryHeader3dBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickHeaderArrow;
    public final BrickLayout brickHeaderLayout;
    public final TextView brickHeaderTitle;
    private final LinearLayout rootView;

    private BrickSubcategoryHeader3dBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickHeaderArrow, BrickLayout brickHeaderLayout, TextView brickHeaderTitle) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickHeaderArrow = brickHeaderArrow;
        this.brickHeaderLayout = brickHeaderLayout;
        this.brickHeaderTitle = brickHeaderTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSubcategoryHeader3dBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSubcategoryHeader3dBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_subcategory_header_3d, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSubcategoryHeader3dBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_header_arrow;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_header_arrow);
            if (textView != null) {
                i = R.id.brick_header_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_header_layout);
                if (brickLayout != null) {
                    i = R.id.brick_header_title;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_header_title);
                    if (textView2 != null) {
                        return new BrickSubcategoryHeader3dBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
