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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSpawnThreadBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSpawnThreadBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSpawnThreadIdEdit;
    public final BrickLayout brickSpawnThreadLayout;
    private final LinearLayout rootView;

    private BrickSpawnThreadBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSpawnThreadIdEdit, BrickLayout brickSpawnThreadLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSpawnThreadIdEdit = brickSpawnThreadIdEdit;
        this.brickSpawnThreadLayout = brickSpawnThreadLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSpawnThreadBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSpawnThreadBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_spawn_thread, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSpawnThreadBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_spawn_thread_id_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_spawn_thread_id_edit);
            if (textView != null) {
                i = R.id.brick_spawn_thread_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_spawn_thread_layout);
                if (brickLayout != null) {
                    return new BrickSpawnThreadBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
