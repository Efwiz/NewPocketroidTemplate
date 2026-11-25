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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateTableBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateTableBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickCreateTableEditName;
    public final TextView brickCreateTableEditX;
    public final TextView brickCreateTableEditY;
    public final BrickLayout brickCreateTableLayout;
    public final TextView brickShowToastLabel;
    public final TextView brickShowToastTextview;
    private final LinearLayout rootView;

    private BrickCreateTableBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickCreateTableEditName, TextView brickCreateTableEditX, TextView brickCreateTableEditY, BrickLayout brickCreateTableLayout, TextView brickShowToastLabel, TextView brickShowToastTextview) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCreateTableEditName = brickCreateTableEditName;
        this.brickCreateTableEditX = brickCreateTableEditX;
        this.brickCreateTableEditY = brickCreateTableEditY;
        this.brickCreateTableLayout = brickCreateTableLayout;
        this.brickShowToastLabel = brickShowToastLabel;
        this.brickShowToastTextview = brickShowToastTextview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateTableBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateTableBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_table, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateTableBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_create_table_edit_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_create_table_edit_name);
            if (textView != null) {
                i = R.id.brick_create_table_edit_x;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_create_table_edit_x);
                if (textView2 != null) {
                    i = R.id.brick_create_table_edit_y;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_create_table_edit_y);
                    if (textView3 != null) {
                        i = R.id.brick_create_table_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_table_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_show_toast_label;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_label);
                            if (textView4 != null) {
                                i = R.id.brick_show_toast_textview;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_textview);
                                if (textView5 != null) {
                                    return new BrickCreateTableBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5);
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
