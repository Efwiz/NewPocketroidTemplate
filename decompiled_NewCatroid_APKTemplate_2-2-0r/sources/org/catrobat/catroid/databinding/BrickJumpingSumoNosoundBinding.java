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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickJumpingSumoNosoundBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickJumpingSumoNosoundBinding implements ViewBinding {
    public final TextView ValueTextView;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickJumpingSumoNosoundLayout;
    private final LinearLayout rootView;

    private BrickJumpingSumoNosoundBinding(LinearLayout rootView, TextView ValueTextView, CheckBox brickCheckbox, BrickLayout brickJumpingSumoNosoundLayout) {
        this.rootView = rootView;
        this.ValueTextView = ValueTextView;
        this.brickCheckbox = brickCheckbox;
        this.brickJumpingSumoNosoundLayout = brickJumpingSumoNosoundLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickJumpingSumoNosoundBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickJumpingSumoNosoundBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_jumping_sumo_nosound, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickJumpingSumoNosoundBinding bind(View rootView) {
        int i = R.id.ValueTextView;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.ValueTextView);
        if (textView != null) {
            i = R.id.brick_checkbox;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
            if (checkBox != null) {
                i = R.id.brick_jumping_sumo_nosound_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_nosound_layout);
                if (brickLayout != null) {
                    return new BrickJumpingSumoNosoundBinding((LinearLayout) rootView, textView, checkBox, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
