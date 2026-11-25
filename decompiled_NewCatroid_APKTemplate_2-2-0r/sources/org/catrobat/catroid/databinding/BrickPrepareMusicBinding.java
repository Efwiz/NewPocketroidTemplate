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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPrepareMusicBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPrepareMusicBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPrepareMusicFileEdit;
    public final BrickLayout brickPrepareMusicLayout;
    public final TextView brickPrepareMusicNameEdit;
    private final LinearLayout rootView;

    private BrickPrepareMusicBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPrepareMusicFileEdit, BrickLayout brickPrepareMusicLayout, TextView brickPrepareMusicNameEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPrepareMusicFileEdit = brickPrepareMusicFileEdit;
        this.brickPrepareMusicLayout = brickPrepareMusicLayout;
        this.brickPrepareMusicNameEdit = brickPrepareMusicNameEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPrepareMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPrepareMusicBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_prepare_music, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPrepareMusicBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_prepare_music_file_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_prepare_music_file_edit);
            if (textView != null) {
                i = R.id.brick_prepare_music_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_prepare_music_layout);
                if (brickLayout != null) {
                    i = R.id.brick_prepare_music_name_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_prepare_music_name_edit);
                    if (textView2 != null) {
                        return new BrickPrepareMusicBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
