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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSceneIdBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSceneIdBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSceneIdEditText;
    public final TextView brickSceneIdLabel;
    public final BrickLayout brickSceneIdLayout;
    private final LinearLayout rootView;

    private BrickSceneIdBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSceneIdEditText, TextView brickSceneIdLabel, BrickLayout brickSceneIdLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSceneIdEditText = brickSceneIdEditText;
        this.brickSceneIdLabel = brickSceneIdLabel;
        this.brickSceneIdLayout = brickSceneIdLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSceneIdBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSceneIdBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_scene_id, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSceneIdBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_scene_id_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_scene_id_edit_text);
            if (textView != null) {
                i = R.id.brick_scene_id_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_scene_id_label);
                if (textView2 != null) {
                    i = R.id.brick_scene_id_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_scene_id_layout);
                    if (brickLayout != null) {
                        return new BrickSceneIdBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
