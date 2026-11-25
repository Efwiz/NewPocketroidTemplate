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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickArduinoSendAnalogBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickArduinoSendAnalogBinding implements ViewBinding {
    public final BrickLayout brickArduinoSendAnalogLayout;
    public final TextView brickArduinoSetAnalogPinEditText;
    public final TextView brickArduinoSetAnalogPinTextView;
    public final TextView brickArduinoSetAnalogValueEditText;
    public final TextView brickArduinoSetAnalogValueTextView;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickArduinoSendAnalogBinding(LinearLayout rootView, BrickLayout brickArduinoSendAnalogLayout, TextView brickArduinoSetAnalogPinEditText, TextView brickArduinoSetAnalogPinTextView, TextView brickArduinoSetAnalogValueEditText, TextView brickArduinoSetAnalogValueTextView, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickArduinoSendAnalogLayout = brickArduinoSendAnalogLayout;
        this.brickArduinoSetAnalogPinEditText = brickArduinoSetAnalogPinEditText;
        this.brickArduinoSetAnalogPinTextView = brickArduinoSetAnalogPinTextView;
        this.brickArduinoSetAnalogValueEditText = brickArduinoSetAnalogValueEditText;
        this.brickArduinoSetAnalogValueTextView = brickArduinoSetAnalogValueTextView;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickArduinoSendAnalogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickArduinoSendAnalogBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_arduino_send_analog, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickArduinoSendAnalogBinding bind(View rootView) {
        int i = R.id.brick_arduino_send_analog_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_arduino_send_analog_layout);
        if (brickLayout != null) {
            i = R.id.brick_arduino_set_analog_pin_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_arduino_set_analog_pin_edit_text);
            if (textView != null) {
                i = R.id.brick_arduino_set_analog_pin_text_view;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_arduino_set_analog_pin_text_view);
                if (textView2 != null) {
                    i = R.id.brick_arduino_set_analog_value_edit_text;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_arduino_set_analog_value_edit_text);
                    if (textView3 != null) {
                        i = R.id.brick_arduino_set_analog_value_text_view;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_arduino_set_analog_value_text_view);
                        if (textView4 != null) {
                            i = R.id.brick_checkbox;
                            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                            if (checkBox != null) {
                                return new BrickArduinoSendAnalogBinding((LinearLayout) rootView, brickLayout, textView, textView2, textView3, textView4, checkBox);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
