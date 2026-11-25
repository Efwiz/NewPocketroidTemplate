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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickMqttPublishBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickMqttPublishBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickMqttPublishClientId;
    public final BrickLayout brickMqttPublishLayout;
    public final TextView brickMqttPublishMessage;
    public final TextView brickMqttPublishRoomId;
    public final TextView brickMqttPublishSalt;
    private final LinearLayout rootView;

    private BrickMqttPublishBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickMqttPublishClientId, BrickLayout brickMqttPublishLayout, TextView brickMqttPublishMessage, TextView brickMqttPublishRoomId, TextView brickMqttPublishSalt) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickMqttPublishClientId = brickMqttPublishClientId;
        this.brickMqttPublishLayout = brickMqttPublishLayout;
        this.brickMqttPublishMessage = brickMqttPublishMessage;
        this.brickMqttPublishRoomId = brickMqttPublishRoomId;
        this.brickMqttPublishSalt = brickMqttPublishSalt;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickMqttPublishBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickMqttPublishBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_mqtt_publish, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickMqttPublishBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_mqtt_publish_client_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_mqtt_publish_client_id);
            if (textView != null) {
                i = R.id.brick_mqtt_publish_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_mqtt_publish_layout);
                if (brickLayout != null) {
                    i = R.id.brick_mqtt_publish_message;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_mqtt_publish_message);
                    if (textView2 != null) {
                        i = R.id.brick_mqtt_publish_room_id;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_mqtt_publish_room_id);
                        if (textView3 != null) {
                            i = R.id.brick_mqtt_publish_salt;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_mqtt_publish_salt);
                            if (textView4 != null) {
                                return new BrickMqttPublishBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
