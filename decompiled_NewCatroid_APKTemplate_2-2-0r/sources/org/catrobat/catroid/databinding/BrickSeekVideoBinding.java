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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSeekVideoBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSeekVideoBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickOpenUrlLayout;
    public final TextView brickSeekVideoName;
    public final TextView brickSeekVideoSec;
    private final LinearLayout rootView;

    private BrickSeekVideoBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickOpenUrlLayout, TextView brickSeekVideoName, TextView brickSeekVideoSec) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickOpenUrlLayout = brickOpenUrlLayout;
        this.brickSeekVideoName = brickSeekVideoName;
        this.brickSeekVideoSec = brickSeekVideoSec;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSeekVideoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSeekVideoBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_seek_video, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSeekVideoBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_open_url_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_open_url_layout);
            if (brickLayout != null) {
                i = R.id.brick_seek_video_name;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seek_video_name);
                if (textView != null) {
                    i = R.id.brick_seek_video_sec;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seek_video_sec);
                    if (textView2 != null) {
                        return new BrickSeekVideoBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
