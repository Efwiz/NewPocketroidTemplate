package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.soundrecorder.RecordButton;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivitySoundrecorderBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivitySoundrecorderBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final View soundrecorderChronometerBar;
    public final Chronometer soundrecorderChronometerTimeRecorded;
    public final RecordButton soundrecorderRecordButton;
    public final TextView soundrecorderRecordTextView;

    private ActivitySoundrecorderBinding(LinearLayout rootView, View soundrecorderChronometerBar, Chronometer soundrecorderChronometerTimeRecorded, RecordButton soundrecorderRecordButton, TextView soundrecorderRecordTextView) {
        this.rootView = rootView;
        this.soundrecorderChronometerBar = soundrecorderChronometerBar;
        this.soundrecorderChronometerTimeRecorded = soundrecorderChronometerTimeRecorded;
        this.soundrecorderRecordButton = soundrecorderRecordButton;
        this.soundrecorderRecordTextView = soundrecorderRecordTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySoundrecorderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivitySoundrecorderBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_soundrecorder, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivitySoundrecorderBinding bind(View rootView) {
        int i = R.id.soundrecorder_chronometer_bar;
        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.soundrecorder_chronometer_bar);
        if (viewFindChildViewById != null) {
            i = R.id.soundrecorder_chronometer_time_recorded;
            Chronometer chronometer = (Chronometer) ViewBindings.findChildViewById(rootView, R.id.soundrecorder_chronometer_time_recorded);
            if (chronometer != null) {
                i = R.id.soundrecorder_record_button;
                RecordButton recordButton = (RecordButton) ViewBindings.findChildViewById(rootView, R.id.soundrecorder_record_button);
                if (recordButton != null) {
                    i = R.id.soundrecorder_record_text_view;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.soundrecorder_record_text_view);
                    if (textView != null) {
                        return new ActivitySoundrecorderBinding((LinearLayout) rootView, viewFindChildViewById, chronometer, recordButton, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
