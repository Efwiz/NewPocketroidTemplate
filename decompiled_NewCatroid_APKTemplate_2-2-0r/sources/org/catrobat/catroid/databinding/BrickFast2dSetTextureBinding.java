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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dSetTextureBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dSetTextureBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dSetTextureFile;
    public final TextView brickFast2dSetTextureId;
    public final BrickLayout brickFast2dSetTextureLayout;
    private final LinearLayout rootView;

    private BrickFast2dSetTextureBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dSetTextureFile, TextView brickFast2dSetTextureId, BrickLayout brickFast2dSetTextureLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dSetTextureFile = brickFast2dSetTextureFile;
        this.brickFast2dSetTextureId = brickFast2dSetTextureId;
        this.brickFast2dSetTextureLayout = brickFast2dSetTextureLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dSetTextureBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dSetTextureBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_set_texture, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dSetTextureBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_set_texture_file;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_texture_file);
            if (textView != null) {
                i = R.id.brick_fast2d_set_texture_id;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_texture_id);
                if (textView2 != null) {
                    i = R.id.brick_fast2d_set_texture_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_texture_layout);
                    if (brickLayout != null) {
                        return new BrickFast2dSetTextureBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
