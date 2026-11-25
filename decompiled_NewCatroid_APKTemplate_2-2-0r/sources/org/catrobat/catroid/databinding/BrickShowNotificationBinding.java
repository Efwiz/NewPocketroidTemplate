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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickShowNotificationBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickShowNotificationBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final CheckBox brickNotificationCheckboxOngoing;
    public final TextView brickNotificationEditChannel;
    public final TextView brickNotificationEditId;
    public final TextView brickNotificationEditLargeIcon;
    public final TextView brickNotificationEditText;
    public final TextView brickNotificationEditTitle;
    public final Spinner brickNotificationSpinnerImportance;
    public final BrickLayout brickShowNotificationLayout;
    private final LinearLayout rootView;

    private BrickShowNotificationBinding(LinearLayout rootView, CheckBox brickCheckbox, CheckBox brickNotificationCheckboxOngoing, TextView brickNotificationEditChannel, TextView brickNotificationEditId, TextView brickNotificationEditLargeIcon, TextView brickNotificationEditText, TextView brickNotificationEditTitle, Spinner brickNotificationSpinnerImportance, BrickLayout brickShowNotificationLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickNotificationCheckboxOngoing = brickNotificationCheckboxOngoing;
        this.brickNotificationEditChannel = brickNotificationEditChannel;
        this.brickNotificationEditId = brickNotificationEditId;
        this.brickNotificationEditLargeIcon = brickNotificationEditLargeIcon;
        this.brickNotificationEditText = brickNotificationEditText;
        this.brickNotificationEditTitle = brickNotificationEditTitle;
        this.brickNotificationSpinnerImportance = brickNotificationSpinnerImportance;
        this.brickShowNotificationLayout = brickShowNotificationLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickShowNotificationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickShowNotificationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_show_notification, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickShowNotificationBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_notification_checkbox_ongoing;
            CheckBox checkBox2 = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_notification_checkbox_ongoing);
            if (checkBox2 != null) {
                i = R.id.brick_notification_edit_channel;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notification_edit_channel);
                if (textView != null) {
                    i = R.id.brick_notification_edit_id;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notification_edit_id);
                    if (textView2 != null) {
                        i = R.id.brick_notification_edit_large_icon;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notification_edit_large_icon);
                        if (textView3 != null) {
                            i = R.id.brick_notification_edit_text;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notification_edit_text);
                            if (textView4 != null) {
                                i = R.id.brick_notification_edit_title;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notification_edit_title);
                                if (textView5 != null) {
                                    i = R.id.brick_notification_spinner_importance;
                                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_notification_spinner_importance);
                                    if (spinner != null) {
                                        i = R.id.brick_show_notification_layout;
                                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_notification_layout);
                                        if (brickLayout != null) {
                                            return new BrickShowNotificationBinding((LinearLayout) rootView, checkBox, checkBox2, textView, textView2, textView3, textView4, textView5, spinner, brickLayout);
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
