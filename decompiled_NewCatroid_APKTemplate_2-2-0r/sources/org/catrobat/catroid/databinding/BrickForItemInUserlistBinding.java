package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickForItemInUserlistBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickForItemInUserlistBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickForValuesFromLabel;
    public final TextView brickLoopInLabel;
    public final BrickLayout brickRepeatLayout;
    public final Spinner forItemInUserlistListSpinner;
    public final Spinner forItemInUserlistVariableSpinner;
    private final LinearLayout rootView;

    private BrickForItemInUserlistBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickForValuesFromLabel, TextView brickLoopInLabel, BrickLayout brickRepeatLayout, Spinner forItemInUserlistListSpinner, Spinner forItemInUserlistVariableSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickForValuesFromLabel = brickForValuesFromLabel;
        this.brickLoopInLabel = brickLoopInLabel;
        this.brickRepeatLayout = brickRepeatLayout;
        this.forItemInUserlistListSpinner = forItemInUserlistListSpinner;
        this.forItemInUserlistVariableSpinner = forItemInUserlistVariableSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickForItemInUserlistBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickForItemInUserlistBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_for_item_in_userlist, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickForItemInUserlistBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_for_values_from_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_for_values_from_label);
            if (textView != null) {
                i = R.id.brick_loop_in_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_loop_in_label);
                if (textView2 != null) {
                    i = R.id.brick_repeat_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_repeat_layout);
                    if (brickLayout != null) {
                        i = R.id.for_item_in_userlist_list_spinner;
                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.for_item_in_userlist_list_spinner);
                        if (spinner != null) {
                            i = R.id.for_item_in_userlist_variable_spinner;
                            Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.for_item_in_userlist_variable_spinner);
                            if (spinner2 != null) {
                                return new BrickForItemInUserlistBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, spinner, spinner2);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
