package org.catrobat.catroid.utils;

import android.os.SystemClock;
import android.os.Vibrator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/VibrationManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: VibrationManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0004H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0006\u0010\u0019\u001a\u00020\u0014J\u0006\u0010\u001a\u001a\u00020\u0014J\u0006\u0010\u001b\u001a\u00020\u0014J\u000e\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/utils/VibrationManager;", "", "()V", "startTime", "", "getStartTime", "()J", "setStartTime", "(J)V", "timeToVibrate", "getTimeToVibrate", "setTimeToVibrate", "vibration", "Landroid/os/Vibrator;", "getVibration", "()Landroid/os/Vibrator;", "setVibration", "(Landroid/os/Vibrator;)V", "calcRemainingTime", "cancelVibrationIfHappening", "", "hasActiveVibration", "", "newVibrationLongerThanCurrent", "duration", "pause", "reset", "resume", "vibrateFor", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VibrationManager {
    private long startTime;
    private long timeToVibrate;
    public Vibrator vibration;

    public final Vibrator getVibration() {
        Vibrator vibrator = this.vibration;
        if (vibrator != null) {
            return vibrator;
        }
        Intrinsics.throwUninitializedPropertyAccessException("vibration");
        return null;
    }

    public final void setVibration(Vibrator vibrator) {
        Intrinsics.checkNotNullParameter(vibrator, "<set-?>");
        this.vibration = vibrator;
    }

    public final long getStartTime() {
        return this.startTime;
    }

    public final void setStartTime(long j) {
        this.startTime = j;
    }

    public final long getTimeToVibrate() {
        return this.timeToVibrate;
    }

    public final void setTimeToVibrate(long j) {
        this.timeToVibrate = j;
    }

    public final void vibrateFor(long duration) {
        if (newVibrationLongerThanCurrent(duration)) {
            cancelVibrationIfHappening();
            this.startTime = SystemClock.uptimeMillis();
            this.timeToVibrate = duration;
            getVibration().vibrate(this.timeToVibrate);
        }
    }

    private final void cancelVibrationIfHappening() {
        if (SystemClock.uptimeMillis() < this.startTime + this.timeToVibrate) {
            getVibration().cancel();
        }
    }

    private final boolean newVibrationLongerThanCurrent(long duration) {
        return SystemClock.uptimeMillis() + duration > this.startTime + this.timeToVibrate;
    }

    private final long calcRemainingTime() {
        return (this.startTime + this.timeToVibrate) - SystemClock.uptimeMillis();
    }

    public final boolean hasActiveVibration() {
        return ((double) calcRemainingTime()) > 0.0d;
    }

    public final void pause() {
        if (hasActiveVibration()) {
            this.timeToVibrate = calcRemainingTime();
            getVibration().cancel();
            this.startTime = -1L;
            return;
        }
        reset();
    }

    public final void resume() {
        if (this.timeToVibrate > 0) {
            this.startTime = SystemClock.uptimeMillis();
            getVibration().vibrate(this.timeToVibrate);
        }
    }

    public final void reset() {
        this.startTime = 0L;
        this.timeToVibrate = 0L;
    }
}
