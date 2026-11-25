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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickNotificationAddButtonBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickNotificationAddButtonBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickLayout;
    public final TextView brickNotifBtnAction;
    public final CheckBox brickNotifBtnCheckboxClose;
    public final CheckBox brickNotifBtnCheckboxInput;
    public final TextView brickNotifBtnHint;
    public final LinearLayout brickNotifBtnHintLayout;
    public final TextView brickNotifBtnIcon;
    public final TextView brickNotifBtnId;
    public final Spinner brickNotifBtnSpinnerBehavior;
    public final TextView brickNotifBtnText;
    private final LinearLayout rootView;

    private BrickNotificationAddButtonBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickLayout, TextView brickNotifBtnAction, CheckBox brickNotifBtnCheckboxClose, CheckBox brickNotifBtnCheckboxInput, TextView brickNotifBtnHint, LinearLayout brickNotifBtnHintLayout, TextView brickNotifBtnIcon, TextView brickNotifBtnId, Spinner brickNotifBtnSpinnerBehavior, TextView brickNotifBtnText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickLayout = brickLayout;
        this.brickNotifBtnAction = brickNotifBtnAction;
        this.brickNotifBtnCheckboxClose = brickNotifBtnCheckboxClose;
        this.brickNotifBtnCheckboxInput = brickNotifBtnCheckboxInput;
        this.brickNotifBtnHint = brickNotifBtnHint;
        this.brickNotifBtnHintLayout = brickNotifBtnHintLayout;
        this.brickNotifBtnIcon = brickNotifBtnIcon;
        this.brickNotifBtnId = brickNotifBtnId;
        this.brickNotifBtnSpinnerBehavior = brickNotifBtnSpinnerBehavior;
        this.brickNotifBtnText = brickNotifBtnText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickNotificationAddButtonBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickNotificationAddButtonBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_notification_add_button, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickNotificationAddButtonBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_layout);
            if (brickLayout != null) {
                i = R.id.brick_notif_btn_action;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_btn_action);
                if (textView != null) {
                    i = R.id.brick_notif_btn_checkbox_close;
                    CheckBox checkBox2 = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_notif_btn_checkbox_close);
                    if (checkBox2 != null) {
                        i = R.id.brick_notif_btn_checkbox_input;
                        CheckBox checkBox3 = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_notif_btn_checkbox_input);
                        if (checkBox3 != null) {
                            i = R.id.brick_notif_btn_hint;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_btn_hint);
                            if (textView2 != null) {
                                i = R.id.brick_notif_btn_hint_layout;
                                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.brick_notif_btn_hint_layout);
                                if (linearLayout != null) {
                                    i = R.id.brick_notif_btn_icon;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_btn_icon);
                                    if (textView3 != null) {
                                        i = R.id.brick_notif_btn_id;
                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_btn_id);
                                        if (textView4 != null) {
                                            i = R.id.brick_notif_btn_spinner_behavior;
                                            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_notif_btn_spinner_behavior);
                                            if (spinner != null) {
                                                i = R.id.brick_notif_btn_text;
                                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_notif_btn_text);
                                                if (textView5 != null) {
                                                    return new BrickNotificationAddButtonBinding((LinearLayout) rootView, checkBox, brickLayout, textView, checkBox2, checkBox3, textView2, linearLayout, textView3, textView4, spinner, textView5);
                                                }
                                            }
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
