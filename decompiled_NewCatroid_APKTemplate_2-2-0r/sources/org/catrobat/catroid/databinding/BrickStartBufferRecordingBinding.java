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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickStartBufferRecordingBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickStartBufferRecordingBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickStartBufferRecordingEditBitrate;
    public final TextView brickStartBufferRecordingEditBuf;
    public final TextView brickStartBufferRecordingEditFile;
    public final TextView brickStartBufferRecordingEditFps;
    public final BrickLayout brickStartBufferRecordingLayout;
    private final LinearLayout rootView;

    private BrickStartBufferRecordingBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickStartBufferRecordingEditBitrate, TextView brickStartBufferRecordingEditBuf, TextView brickStartBufferRecordingEditFile, TextView brickStartBufferRecordingEditFps, BrickLayout brickStartBufferRecordingLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickStartBufferRecordingEditBitrate = brickStartBufferRecordingEditBitrate;
        this.brickStartBufferRecordingEditBuf = brickStartBufferRecordingEditBuf;
        this.brickStartBufferRecordingEditFile = brickStartBufferRecordingEditFile;
        this.brickStartBufferRecordingEditFps = brickStartBufferRecordingEditFps;
        this.brickStartBufferRecordingLayout = brickStartBufferRecordingLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickStartBufferRecordingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickStartBufferRecordingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_start_buffer_recording, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickStartBufferRecordingBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_start_buffer_recording_edit_bitrate;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_start_buffer_recording_edit_bitrate);
            if (textView != null) {
                i = R.id.brick_start_buffer_recording_edit_buf;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_start_buffer_recording_edit_buf);
                if (textView2 != null) {
                    i = R.id.brick_start_buffer_recording_edit_file;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_start_buffer_recording_edit_file);
                    if (textView3 != null) {
                        i = R.id.brick_start_buffer_recording_edit_fps;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_start_buffer_recording_edit_fps);
                        if (textView4 != null) {
                            i = R.id.brick_start_buffer_recording_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_start_buffer_recording_layout);
                            if (brickLayout != null) {
                                return new BrickStartBufferRecordingBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
