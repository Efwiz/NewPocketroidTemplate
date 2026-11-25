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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPauseVideoBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPauseVideoBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickOpenUrlLayout;
    public final TextView brickPauseVideo;
    private final LinearLayout rootView;

    private BrickPauseVideoBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickOpenUrlLayout, TextView brickPauseVideo) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickOpenUrlLayout = brickOpenUrlLayout;
        this.brickPauseVideo = brickPauseVideo;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPauseVideoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPauseVideoBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_pause_video, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPauseVideoBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_open_url_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_open_url_layout);
            if (brickLayout != null) {
                i = R.id.brick_pause_video;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_pause_video);
                if (textView != null) {
                    return new BrickPauseVideoBinding((LinearLayout) rootView, checkBox, brickLayout, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
