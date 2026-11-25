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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickNotificationShowBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickNotificationShowBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickNotifShowDelay;
    public final TextView brickNotifShowId;
    public final BrickLayout brickNotificationShowLayout;
    private final LinearLayout rootView;

    private BrickNotificationShowBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickNotifShowDelay, TextView brickNotifShowId, BrickLayout brickNotificationShowLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickNotifShowDelay = brickNotifShowDelay;
        this.brickNotifShowId = brickNotifShowId;
        this.brickNotificationShowLayout = brickNotificationShowLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickNotificationShowBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickNotificationShowBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_notification_show, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickNotificationShowBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_notif_show_delay;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_show_delay);
            if (textView != null) {
                i = R.id.brick_notif_show_id;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_show_id);
                if (textView2 != null) {
                    i = R.id.brick_notification_show_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_notification_show_layout);
                    if (brickLayout != null) {
                        return new BrickNotificationShowBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
