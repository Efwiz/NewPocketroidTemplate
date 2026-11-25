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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPrepareSound2Binding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPrepareSound2Binding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPrepareSoundFileEdit;
    public final BrickLayout brickPrepareSoundLayout;
    public final TextView brickPrepareSoundNameEdit;
    private final LinearLayout rootView;

    private BrickPrepareSound2Binding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPrepareSoundFileEdit, BrickLayout brickPrepareSoundLayout, TextView brickPrepareSoundNameEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPrepareSoundFileEdit = brickPrepareSoundFileEdit;
        this.brickPrepareSoundLayout = brickPrepareSoundLayout;
        this.brickPrepareSoundNameEdit = brickPrepareSoundNameEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPrepareSound2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPrepareSound2Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_prepare_sound2, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPrepareSound2Binding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_prepare_sound_file_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_prepare_sound_file_edit);
            if (textView != null) {
                i = R.id.brick_prepare_sound_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_prepare_sound_layout);
                if (brickLayout != null) {
                    i = R.id.brick_prepare_sound_name_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_prepare_sound_name_edit);
                    if (textView2 != null) {
                        return new BrickPrepareSound2Binding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
