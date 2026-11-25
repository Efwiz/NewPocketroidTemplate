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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickWriteListOnDeviceBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickWriteListOnDeviceBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickWriteListLayout;
    private final LinearLayout rootView;
    public final TextView writeListOnDeviceLabel;
    public final Spinner writeListSpinner;

    private BrickWriteListOnDeviceBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickWriteListLayout, TextView writeListOnDeviceLabel, Spinner writeListSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickWriteListLayout = brickWriteListLayout;
        this.writeListOnDeviceLabel = writeListOnDeviceLabel;
        this.writeListSpinner = writeListSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickWriteListOnDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickWriteListOnDeviceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_write_list_on_device, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickWriteListOnDeviceBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_write_list_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_write_list_layout);
            if (brickLayout != null) {
                i = R.id.write_list_on_device_label;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.write_list_on_device_label);
                if (textView != null) {
                    i = R.id.write_list_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.write_list_spinner);
                    if (spinner != null) {
                        return new BrickWriteListOnDeviceBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
