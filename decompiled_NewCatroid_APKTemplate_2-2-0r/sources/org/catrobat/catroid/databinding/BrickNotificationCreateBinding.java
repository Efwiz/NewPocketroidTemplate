package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickNotificationCreateBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickNotificationCreateBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickNotifCreateChannel;
    public final TextView brickNotifCreateIcon;
    public final TextView brickNotifCreateId;
    public final Spinner brickNotifCreateImportance;
    public final CheckBox brickNotifCreateOngoing;
    public final TextView brickNotifCreateText;
    public final TextView brickNotifCreateTitle;
    public final BrickLayout brickNotificationCreateLayout;
    private final LinearLayout rootView;

    private BrickNotificationCreateBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickNotifCreateChannel, TextView brickNotifCreateIcon, TextView brickNotifCreateId, Spinner brickNotifCreateImportance, CheckBox brickNotifCreateOngoing, TextView brickNotifCreateText, TextView brickNotifCreateTitle, BrickLayout brickNotificationCreateLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickNotifCreateChannel = brickNotifCreateChannel;
        this.brickNotifCreateIcon = brickNotifCreateIcon;
        this.brickNotifCreateId = brickNotifCreateId;
        this.brickNotifCreateImportance = brickNotifCreateImportance;
        this.brickNotifCreateOngoing = brickNotifCreateOngoing;
        this.brickNotifCreateText = brickNotifCreateText;
        this.brickNotifCreateTitle = brickNotifCreateTitle;
        this.brickNotificationCreateLayout = brickNotificationCreateLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickNotificationCreateBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickNotificationCreateBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_notification_create, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickNotificationCreateBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_notif_create_channel;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_create_channel);
            if (textView != null) {
                i = R.id.brick_notif_create_icon;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_create_icon);
                if (textView2 != null) {
                    i = R.id.brick_notif_create_id;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_create_id);
                    if (textView3 != null) {
                        i = R.id.brick_notif_create_importance;
                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_notif_create_importance);
                        if (spinner != null) {
                            i = R.id.brick_notif_create_ongoing;
                            CheckBox checkBox2 = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_notif_create_ongoing);
                            if (checkBox2 != null) {
                                i = R.id.brick_notif_create_text;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_create_text);
                                if (textView4 != null) {
                                    i = R.id.brick_notif_create_title;
                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_create_title);
                                    if (textView5 != null) {
                                        i = R.id.brick_notification_create_layout;
                                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_notification_create_layout);
                                        if (brickLayout != null) {
                                            return new BrickNotificationCreateBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, spinner, checkBox2, textView4, textView5, brickLayout);
                                        }
                                    }
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
