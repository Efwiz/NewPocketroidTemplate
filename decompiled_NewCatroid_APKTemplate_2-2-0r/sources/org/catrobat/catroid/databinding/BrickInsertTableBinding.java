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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickInsertTableBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickInsertTableBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickInsertTableEditName;
    public final TextView brickInsertTableEditValue;
    public final TextView brickInsertTableEditX;
    public final TextView brickInsertTableEditY;
    public final BrickLayout brickInsertTableLayout;
    public final TextView brickShowToastLabel;
    public final TextView brickShowToastTextview;
    public final TextView brickShowToastTextview2;
    private final LinearLayout rootView;

    private BrickInsertTableBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickInsertTableEditName, TextView brickInsertTableEditValue, TextView brickInsertTableEditX, TextView brickInsertTableEditY, BrickLayout brickInsertTableLayout, TextView brickShowToastLabel, TextView brickShowToastTextview, TextView brickShowToastTextview2) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickInsertTableEditName = brickInsertTableEditName;
        this.brickInsertTableEditValue = brickInsertTableEditValue;
        this.brickInsertTableEditX = brickInsertTableEditX;
        this.brickInsertTableEditY = brickInsertTableEditY;
        this.brickInsertTableLayout = brickInsertTableLayout;
        this.brickShowToastLabel = brickShowToastLabel;
        this.brickShowToastTextview = brickShowToastTextview;
        this.brickShowToastTextview2 = brickShowToastTextview2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickInsertTableBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickInsertTableBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_insert_table, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickInsertTableBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_insert_table_edit_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_insert_table_edit_name);
            if (textView != null) {
                i = R.id.brick_insert_table_edit_value;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_insert_table_edit_value);
                if (textView2 != null) {
                    i = R.id.brick_insert_table_edit_x;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_insert_table_edit_x);
                    if (textView3 != null) {
                        i = R.id.brick_insert_table_edit_y;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_insert_table_edit_y);
                        if (textView4 != null) {
                            i = R.id.brick_insert_table_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_insert_table_layout);
                            if (brickLayout != null) {
                                i = R.id.brick_show_toast_label;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_label);
                                if (textView5 != null) {
                                    i = R.id.brick_show_toast_textview;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_textview);
                                    if (textView6 != null) {
                                        i = R.id.brick_show_toast_textview2;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_textview2);
                                        if (textView7 != null) {
                                            return new BrickInsertTableBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout, textView5, textView6, textView7);
                                        }
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
