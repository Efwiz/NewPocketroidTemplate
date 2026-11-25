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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAskBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAskBinding implements ViewBinding {
    public final TextView brickAskLabel;
    public final TextView brickAskQuestionEditText;
    public final Spinner brickAskSpinner;
    public final TextView brickAskStoreLabel;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetVariableLayout;
    private final LinearLayout rootView;

    private BrickAskBinding(LinearLayout rootView, TextView brickAskLabel, TextView brickAskQuestionEditText, Spinner brickAskSpinner, TextView brickAskStoreLabel, CheckBox brickCheckbox, BrickLayout brickSetVariableLayout) {
        this.rootView = rootView;
        this.brickAskLabel = brickAskLabel;
        this.brickAskQuestionEditText = brickAskQuestionEditText;
        this.brickAskSpinner = brickAskSpinner;
        this.brickAskStoreLabel = brickAskStoreLabel;
        this.brickCheckbox = brickCheckbox;
        this.brickSetVariableLayout = brickSetVariableLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAskBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAskBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ask, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAskBinding bind(View rootView) {
        int i = R.id.brick_ask_label;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_label);
        if (textView != null) {
            i = R.id.brick_ask_question_edit_text;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_question_edit_text);
            if (textView2 != null) {
                i = R.id.brick_ask_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_ask_spinner);
                if (spinner != null) {
                    i = R.id.brick_ask_store_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_store_label);
                    if (textView3 != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            i = R.id.brick_set_variable_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_variable_layout);
                            if (brickLayout != null) {
                                return new BrickAskBinding((LinearLayout) rootView, textView, textView2, spinner, textView3, checkBox, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
