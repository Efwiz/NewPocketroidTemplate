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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickChooseCameraBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickChooseCameraBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickChooseCameraLayout;
    public final TextView brickChooseCameraPrototypeTextView;
    public final Spinner brickChooseCameraSpinner;
    private final LinearLayout rootView;

    private BrickChooseCameraBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickChooseCameraLayout, TextView brickChooseCameraPrototypeTextView, Spinner brickChooseCameraSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickChooseCameraLayout = brickChooseCameraLayout;
        this.brickChooseCameraPrototypeTextView = brickChooseCameraPrototypeTextView;
        this.brickChooseCameraSpinner = brickChooseCameraSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickChooseCameraBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickChooseCameraBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_choose_camera, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickChooseCameraBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_choose_camera_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_choose_camera_layout);
            if (brickLayout != null) {
                i = R.id.brick_choose_camera__prototype_text_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_choose_camera__prototype_text_view);
                if (textView != null) {
                    i = R.id.brick_choose_camera_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_choose_camera_spinner);
                    if (spinner != null) {
                        return new BrickChooseCameraBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
