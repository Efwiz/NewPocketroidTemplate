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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRaspiSendDigitalBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRaspiSendDigitalBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickRaspiSendDigitalLayout;
    public final TextView brickRaspiSetDigitalPinEditText;
    public final TextView brickRaspiSetDigitalValueEditText;
    private final LinearLayout rootView;

    private BrickRaspiSendDigitalBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickRaspiSendDigitalLayout, TextView brickRaspiSetDigitalPinEditText, TextView brickRaspiSetDigitalValueEditText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRaspiSendDigitalLayout = brickRaspiSendDigitalLayout;
        this.brickRaspiSetDigitalPinEditText = brickRaspiSetDigitalPinEditText;
        this.brickRaspiSetDigitalValueEditText = brickRaspiSetDigitalValueEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRaspiSendDigitalBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRaspiSendDigitalBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_raspi_send_digital, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRaspiSendDigitalBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_raspi_send_digital_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_raspi_send_digital_layout);
            if (brickLayout != null) {
                i = R.id.brick_raspi_set_digital_pin_edit_text;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_raspi_set_digital_pin_edit_text);
                if (textView != null) {
                    i = R.id.brick_raspi_set_digital_value_edit_text;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_raspi_set_digital_value_edit_text);
                    if (textView2 != null) {
                        return new BrickRaspiSendDigitalBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
