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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickIfBeginIfBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickIfBeginIfBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickIfBeginEditText;
    public final BrickLayout brickIfBeginLayout;
    public final TextView ifElsePrototypePunctuation;
    public final TextView ifElsePrototypePunctuation2;
    public final TextView ifLabel;
    public final TextView ifLabelSecondPart;
    public final TextView ifPrototypeElse;
    private final LinearLayout rootView;

    private BrickIfBeginIfBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickIfBeginEditText, BrickLayout brickIfBeginLayout, TextView ifElsePrototypePunctuation, TextView ifElsePrototypePunctuation2, TextView ifLabel, TextView ifLabelSecondPart, TextView ifPrototypeElse) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickIfBeginEditText = brickIfBeginEditText;
        this.brickIfBeginLayout = brickIfBeginLayout;
        this.ifElsePrototypePunctuation = ifElsePrototypePunctuation;
        this.ifElsePrototypePunctuation2 = ifElsePrototypePunctuation2;
        this.ifLabel = ifLabel;
        this.ifLabelSecondPart = ifLabelSecondPart;
        this.ifPrototypeElse = ifPrototypeElse;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickIfBeginIfBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickIfBeginIfBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_if_begin_if, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickIfBeginIfBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_if_begin_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_if_begin_edit_text);
            if (textView != null) {
                i = R.id.brick_if_begin_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_if_begin_layout);
                if (brickLayout != null) {
                    i = R.id.if_else_prototype_punctuation;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.if_else_prototype_punctuation);
                    if (textView2 != null) {
                        i = R.id.if_else_prototype_punctuation2;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.if_else_prototype_punctuation2);
                        if (textView3 != null) {
                            i = R.id.if_label;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.if_label);
                            if (textView4 != null) {
                                i = R.id.if_label_second_part;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.if_label_second_part);
                                if (textView5 != null) {
                                    i = R.id.if_prototype_else;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.if_prototype_else);
                                    if (textView6 != null) {
                                        return new BrickIfBeginIfBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3, textView4, textView5, textView6);
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
