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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickWhenGamepadButtonBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickWhenGamepadButtonBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickWhenGamepadButtonLayout;
    public final Spinner brickWhenGamepadButtonSpinner;
    public final TextView brickWhenGamepadButtonText;
    public final TextView brickWhenGamepadButtonTextPressed;
    private final LinearLayout rootView;

    private BrickWhenGamepadButtonBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickWhenGamepadButtonLayout, Spinner brickWhenGamepadButtonSpinner, TextView brickWhenGamepadButtonText, TextView brickWhenGamepadButtonTextPressed) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickWhenGamepadButtonLayout = brickWhenGamepadButtonLayout;
        this.brickWhenGamepadButtonSpinner = brickWhenGamepadButtonSpinner;
        this.brickWhenGamepadButtonText = brickWhenGamepadButtonText;
        this.brickWhenGamepadButtonTextPressed = brickWhenGamepadButtonTextPressed;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickWhenGamepadButtonBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickWhenGamepadButtonBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_when_gamepad_button, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickWhenGamepadButtonBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_when_gamepad_button_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_when_gamepad_button_layout);
            if (brickLayout != null) {
                i = R.id.brick_when_gamepad_button_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_when_gamepad_button_spinner);
                if (spinner != null) {
                    i = R.id.brick_when_gamepad_button_text;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_when_gamepad_button_text);
                    if (textView != null) {
                        i = R.id.brick_when_gamepad_button_text_pressed;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_when_gamepad_button_text_pressed);
                        if (textView2 != null) {
                            return new BrickWhenGamepadButtonBinding((LinearLayout) rootView, checkBox, brickLayout, spinner, textView, textView2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
