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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickTextureTilingBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickTextureTilingBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickTextureTilingLayout;
    private final LinearLayout rootView;
    public final TextView tilingObjId;
    public final TextView tilingU;
    public final TextView tilingV;

    private BrickTextureTilingBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickTextureTilingLayout, TextView tilingObjId, TextView tilingU, TextView tilingV) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickTextureTilingLayout = brickTextureTilingLayout;
        this.tilingObjId = tilingObjId;
        this.tilingU = tilingU;
        this.tilingV = tilingV;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickTextureTilingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickTextureTilingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_texture_tiling, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickTextureTilingBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_texture_tiling_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_texture_tiling_layout);
            if (brickLayout != null) {
                i = R.id.tiling_obj_id;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tiling_obj_id);
                if (textView != null) {
                    i = R.id.tiling_u;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tiling_u);
                    if (textView2 != null) {
                        i = R.id.tiling_v;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tiling_v);
                        if (textView3 != null) {
                            return new BrickTextureTilingBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
