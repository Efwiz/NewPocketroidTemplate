package org.catrobat.catroid.soundrecorder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/soundrecorder/RecordButton.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RecordButton extends ImageButton {
    private RecordState state;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/soundrecorder/RecordButton$RecordState.class
      classes14.dex
     */
    public enum RecordState {
        RECORD,
        STOP
    }

    public RecordButton(Context context) {
        super(context);
        this.state = RecordState.STOP;
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.state = RecordState.STOP;
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.state = RecordState.STOP;
    }

    public RecordState getState() {
        return this.state;
    }

    public void setState(RecordState state) {
        this.state = state;
    }
}
