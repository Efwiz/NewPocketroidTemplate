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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPrepareSoundBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPrepareSoundBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPrepareSoundCacheName;
    public final TextView brickPrepareSoundFileName;
    public final BrickLayout brickPrepareSoundLayout;
    private final LinearLayout rootView;

    private BrickPrepareSoundBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPrepareSoundCacheName, TextView brickPrepareSoundFileName, BrickLayout brickPrepareSoundLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPrepareSoundCacheName = brickPrepareSoundCacheName;
        this.brickPrepareSoundFileName = brickPrepareSoundFileName;
        this.brickPrepareSoundLayout = brickPrepareSoundLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPrepareSoundBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPrepareSoundBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_prepare_sound, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPrepareSoundBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_prepare_sound_cache_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_prepare_sound_cache_name);
            if (textView != null) {
                i = R.id.brick_prepare_sound_file_name;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_prepare_sound_file_name);
                if (textView2 != null) {
                    i = R.id.brick_prepare_sound_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_prepare_sound_layout);
                    if (brickLayout != null) {
                        return new BrickPrepareSoundBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
