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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSceneTransitionBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSceneTransitionBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSceneTransitionLabel;
    public final BrickLayout brickSceneTransitionLayout;
    public final Spinner brickSceneTransitionSpinner;
    private final LinearLayout rootView;

    private BrickSceneTransitionBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSceneTransitionLabel, BrickLayout brickSceneTransitionLayout, Spinner brickSceneTransitionSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSceneTransitionLabel = brickSceneTransitionLabel;
        this.brickSceneTransitionLayout = brickSceneTransitionLayout;
        this.brickSceneTransitionSpinner = brickSceneTransitionSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSceneTransitionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSceneTransitionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_scene_transition, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSceneTransitionBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_scene_transition_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_scene_transition_label);
            if (textView != null) {
                i = R.id.brick_scene_transition_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_scene_transition_layout);
                if (brickLayout != null) {
                    i = R.id.brick_scene_transition_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_scene_transition_spinner);
                    if (spinner != null) {
                        return new BrickSceneTransitionBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
