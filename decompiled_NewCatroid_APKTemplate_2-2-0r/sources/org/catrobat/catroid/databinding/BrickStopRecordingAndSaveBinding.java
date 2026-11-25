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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickStopRecordingAndSaveBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickStopRecordingAndSaveBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickRecordingFilename;
    private final LinearLayout rootView;

    private BrickStopRecordingAndSaveBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickRecordingFilename) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRecordingFilename = brickRecordingFilename;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickStopRecordingAndSaveBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickStopRecordingAndSaveBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_stop_recording_and_save, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickStopRecordingAndSaveBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_recording_filename;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_recording_filename);
            if (textView != null) {
                return new BrickStopRecordingAndSaveBinding((LinearLayout) rootView, checkBox, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
