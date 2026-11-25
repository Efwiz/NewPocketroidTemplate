package org.catrobat.catroid.io;

import android.content.Context;
import android.media.AudioManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/StageAudioFocus.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StageAudioFocus implements AudioManager.OnAudioFocusChangeListener {
    public static final String TAG = "StageAudioFocus";
    private AudioManager audioManager;
    private boolean isAudioFocusGranted = false;

    public StageAudioFocus(Context context) {
        this.audioManager = null;
        this.audioManager = (AudioManager) context.getSystemService("audio");
    }

    public void requestAudioFocus() {
        if (isAudioFocusGranted()) {
            return;
        }
        this.isAudioFocusGranted = this.audioManager.requestAudioFocus(this, 3, 1) == 1;
    }

    public void releaseAudioFocus() {
        this.audioManager.abandonAudioFocus(this);
        this.isAudioFocusGranted = false;
    }

    public boolean isAudioFocusGranted() {
        return this.isAudioFocusGranted;
    }

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == -1) {
            releaseAudioFocus();
        }
    }
}
