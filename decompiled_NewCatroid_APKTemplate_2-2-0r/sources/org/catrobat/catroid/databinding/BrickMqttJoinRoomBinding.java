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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickMqttJoinRoomBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickMqttJoinRoomBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickMqttJoinRoomClientId;
    public final TextView brickMqttJoinRoomId;
    public final BrickLayout brickMqttJoinRoomLayout;
    public final TextView brickMqttJoinRoomSalt;
    private final LinearLayout rootView;

    private BrickMqttJoinRoomBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickMqttJoinRoomClientId, TextView brickMqttJoinRoomId, BrickLayout brickMqttJoinRoomLayout, TextView brickMqttJoinRoomSalt) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickMqttJoinRoomClientId = brickMqttJoinRoomClientId;
        this.brickMqttJoinRoomId = brickMqttJoinRoomId;
        this.brickMqttJoinRoomLayout = brickMqttJoinRoomLayout;
        this.brickMqttJoinRoomSalt = brickMqttJoinRoomSalt;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickMqttJoinRoomBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickMqttJoinRoomBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_mqtt_join_room, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickMqttJoinRoomBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_mqtt_join_room_client_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_mqtt_join_room_client_id);
            if (textView != null) {
                i = R.id.brick_mqtt_join_room_id;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_mqtt_join_room_id);
                if (textView2 != null) {
                    i = R.id.brick_mqtt_join_room_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_mqtt_join_room_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_mqtt_join_room_salt;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_mqtt_join_room_salt);
                        if (textView3 != null) {
                            return new BrickMqttJoinRoomBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
