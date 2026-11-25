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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetObjectTextureBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetObjectTextureBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickObjectTextureFile;
    public final TextView brickObjectTextureId;
    public final BrickLayout brickSetObjectTextureLayout;
    private final LinearLayout rootView;

    private BrickSetObjectTextureBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickObjectTextureFile, TextView brickObjectTextureId, BrickLayout brickSetObjectTextureLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickObjectTextureFile = brickObjectTextureFile;
        this.brickObjectTextureId = brickObjectTextureId;
        this.brickSetObjectTextureLayout = brickSetObjectTextureLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetObjectTextureBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetObjectTextureBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_object_texture, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetObjectTextureBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_object_texture_file;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_texture_file);
            if (textView != null) {
                i = R.id.brick_object_texture_id;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_texture_id);
                if (textView2 != null) {
                    i = R.id.brick_set_object_texture_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_object_texture_layout);
                    if (brickLayout != null) {
                        return new BrickSetObjectTextureBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
