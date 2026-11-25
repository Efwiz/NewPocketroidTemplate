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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSplitBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSplitBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSplitEditSimbol;
    public final TextView brickSplitEditText;
    public final TextView brickSplitLabel;
    public final BrickLayout brickSplitLayout;
    public final TextView brickSplitTextview;
    public final TextView brickSplitTextview2;
    private final LinearLayout rootView;
    public final Spinner splitSpinner;

    private BrickSplitBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSplitEditSimbol, TextView brickSplitEditText, TextView brickSplitLabel, BrickLayout brickSplitLayout, TextView brickSplitTextview, TextView brickSplitTextview2, Spinner splitSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSplitEditSimbol = brickSplitEditSimbol;
        this.brickSplitEditText = brickSplitEditText;
        this.brickSplitLabel = brickSplitLabel;
        this.brickSplitLayout = brickSplitLayout;
        this.brickSplitTextview = brickSplitTextview;
        this.brickSplitTextview2 = brickSplitTextview2;
        this.splitSpinner = splitSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSplitBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSplitBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_split, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSplitBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_split_edit_simbol;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_split_edit_simbol);
            if (textView != null) {
                i = R.id.brick_split_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_split_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_split_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_split_label);
                    if (textView3 != null) {
                        i = R.id.brick_split_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_split_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_split_textview;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_split_textview);
                            if (textView4 != null) {
                                i = R.id.brick_split_textview2;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_split_textview2);
                                if (textView5 != null) {
                                    i = R.id.split_spinner;
                                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.split_spinner);
                                    if (spinner != null) {
                                        return new BrickSplitBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5, spinner);
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
