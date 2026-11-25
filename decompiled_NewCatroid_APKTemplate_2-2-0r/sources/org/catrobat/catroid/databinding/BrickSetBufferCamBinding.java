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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetBufferCamBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetBufferCamBinding implements ViewBinding {
    public final TextView brickBufferName;
    public final TextView brickBufferRot;
    public final TextView brickBufferX;
    public final TextView brickBufferY;
    public final TextView brickBufferZoom;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetBufferCamLayout;
    private final LinearLayout rootView;

    private BrickSetBufferCamBinding(LinearLayout rootView, TextView brickBufferName, TextView brickBufferRot, TextView brickBufferX, TextView brickBufferY, TextView brickBufferZoom, CheckBox brickCheckbox, BrickLayout brickSetBufferCamLayout) {
        this.rootView = rootView;
        this.brickBufferName = brickBufferName;
        this.brickBufferRot = brickBufferRot;
        this.brickBufferX = brickBufferX;
        this.brickBufferY = brickBufferY;
        this.brickBufferZoom = brickBufferZoom;
        this.brickCheckbox = brickCheckbox;
        this.brickSetBufferCamLayout = brickSetBufferCamLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetBufferCamBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetBufferCamBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_buffer_cam, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetBufferCamBinding bind(View rootView) {
        int i = R.id.brick_buffer_name;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_name);
        if (textView != null) {
            i = R.id.brick_buffer_rot;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_rot);
            if (textView2 != null) {
                i = R.id.brick_buffer_x;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_x);
                if (textView3 != null) {
                    i = R.id.brick_buffer_y;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_y);
                    if (textView4 != null) {
                        i = R.id.brick_buffer_zoom;
                        TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_zoom);
                        if (textView5 != null) {
                            i = R.id.brick_checkbox;
                            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                            if (checkBox != null) {
                                i = R.id.brick_set_buffer_cam_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_buffer_cam_layout);
                                if (brickLayout != null) {
                                    return new BrickSetBufferCamBinding((LinearLayout) rootView, textView, textView2, textView3, textView4, textView5, checkBox, brickLayout);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
