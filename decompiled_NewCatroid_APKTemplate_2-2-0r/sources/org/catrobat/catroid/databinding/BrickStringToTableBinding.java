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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickStringToTableBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickStringToTableBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickStringToTableEditName;
    public final TextView brickStringToTableEditStr;
    public final TextView brickStringToTableEditX;
    public final TextView brickStringToTableEditY;
    public final BrickLayout brickStringToTableLayout;
    private final LinearLayout rootView;

    private BrickStringToTableBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickStringToTableEditName, TextView brickStringToTableEditStr, TextView brickStringToTableEditX, TextView brickStringToTableEditY, BrickLayout brickStringToTableLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickStringToTableEditName = brickStringToTableEditName;
        this.brickStringToTableEditStr = brickStringToTableEditStr;
        this.brickStringToTableEditX = brickStringToTableEditX;
        this.brickStringToTableEditY = brickStringToTableEditY;
        this.brickStringToTableLayout = brickStringToTableLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickStringToTableBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickStringToTableBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_string_to_table, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickStringToTableBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_string_to_table_edit_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_string_to_table_edit_name);
            if (textView != null) {
                i = R.id.brick_string_to_table_edit_str;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_string_to_table_edit_str);
                if (textView2 != null) {
                    i = R.id.brick_string_to_table_edit_x;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_string_to_table_edit_x);
                    if (textView3 != null) {
                        i = R.id.brick_string_to_table_edit_y;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_string_to_table_edit_y);
                        if (textView4 != null) {
                            i = R.id.brick_string_to_table_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_string_to_table_layout);
                            if (brickLayout != null) {
                                return new BrickStringToTableBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
