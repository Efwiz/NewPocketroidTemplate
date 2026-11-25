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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetMainRenderLoopsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetMainRenderLoopsBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickRenderLoop2d;
    public final TextView brickRenderLoop3d;
    public final TextView brickRenderLoopFast2d;
    public final BrickLayout brickSetMainRenderLoopsLayout;
    private final LinearLayout rootView;

    private BrickSetMainRenderLoopsBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickRenderLoop2d, TextView brickRenderLoop3d, TextView brickRenderLoopFast2d, BrickLayout brickSetMainRenderLoopsLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRenderLoop2d = brickRenderLoop2d;
        this.brickRenderLoop3d = brickRenderLoop3d;
        this.brickRenderLoopFast2d = brickRenderLoopFast2d;
        this.brickSetMainRenderLoopsLayout = brickSetMainRenderLoopsLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetMainRenderLoopsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetMainRenderLoopsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_main_render_loops, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetMainRenderLoopsBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_render_loop_2d;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_render_loop_2d);
            if (textView != null) {
                i = R.id.brick_render_loop_3d;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_render_loop_3d);
                if (textView2 != null) {
                    i = R.id.brick_render_loop_fast2d;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_render_loop_fast2d);
                    if (textView3 != null) {
                        i = R.id.brick_set_main_render_loops_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_main_render_loops_layout);
                        if (brickLayout != null) {
                            return new BrickSetMainRenderLoopsBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
