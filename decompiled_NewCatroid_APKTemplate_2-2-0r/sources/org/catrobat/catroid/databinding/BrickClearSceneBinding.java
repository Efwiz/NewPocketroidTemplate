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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickClearSceneBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickClearSceneBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickClearSceneLayout;
    public final Spinner brickClearSceneSpinner;
    public final TextView brickSceneStartLabel;
    private final LinearLayout rootView;

    private BrickClearSceneBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickClearSceneLayout, Spinner brickClearSceneSpinner, TextView brickSceneStartLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickClearSceneLayout = brickClearSceneLayout;
        this.brickClearSceneSpinner = brickClearSceneSpinner;
        this.brickSceneStartLabel = brickSceneStartLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickClearSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickClearSceneBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_clear_scene, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickClearSceneBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_clear_scene_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_clear_scene_layout);
            if (brickLayout != null) {
                i = R.id.brick_clear_scene_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_clear_scene_spinner);
                if (spinner != null) {
                    i = R.id.brick_scene_start_label;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_scene_start_label);
                    if (textView != null) {
                        return new BrickClearSceneBinding((LinearLayout) rootView, checkBox, brickLayout, spinner, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
