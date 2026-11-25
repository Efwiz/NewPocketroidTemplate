package com.reandroid.commons.utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public class ElapsedTime {
    private boolean mIsPaused;
    private long previous;
    private long startTime = System.currentTimeMillis();

    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.previous = 0L;
    }

    public String now() {
        return now(true);
    }

    public String now(boolean z) {
        return msToDisplayTime(elapsed(), true, z);
    }

    public boolean isExpired(long j) {
        return elapsed() >= j;
    }

    public String remaining(long j) {
        long jElapsed = j - elapsed();
        if (jElapsed < 0) {
            return "--:--";
        }
        return msToDisplayTime(jElapsed, true, true);
    }

    public void pause() {
        if (this.mIsPaused) {
            return;
        }
        this.previous = elapsed();
        this.mIsPaused = true;
    }

    public void resume() {
        if (this.mIsPaused) {
            this.mIsPaused = false;
            this.startTime = System.currentTimeMillis();
        }
    }

    public boolean isPaused() {
        return this.mIsPaused;
    }

    public long elapsed() {
        return getDiff() + this.previous;
    }

    private long getDiff() {
        if (this.mIsPaused) {
            return 0L;
        }
        return System.currentTimeMillis() - this.startTime;
    }

    private String msToDisplayTime(long j, boolean z, boolean z2) {
        long hours = TimeUnit.MILLISECONDS.toHours(j);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(j - TimeUnit.HOURS.toMillis(hours));
        long seconds = TimeUnit.MILLISECONDS.toSeconds((j - TimeUnit.HOURS.toMillis(hours)) - TimeUnit.MINUTES.toMillis(minutes));
        if (!z) {
            return String.format(Locale.ENGLISH, "%02d:%02d:%02d", Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds));
        }
        long millis = TimeUnit.MILLISECONDS.toMillis(((j - TimeUnit.HOURS.toMillis(hours)) - TimeUnit.MINUTES.toMillis(minutes)) - TimeUnit.SECONDS.toMillis(seconds));
        boolean z3 = minutes < 10;
        if (z2) {
            if (hours == 0 && minutes == 0) {
                return String.format(Locale.ENGLISH, "%02d.%03d", Long.valueOf(seconds), Long.valueOf(millis));
            }
            if (hours == 0) {
                if (z3) {
                    return String.format(Locale.ENGLISH, "%02d:%02d.%03d", Long.valueOf(minutes), Long.valueOf(seconds), Long.valueOf(millis));
                }
                return String.format(Locale.ENGLISH, "%02d:%02d", Long.valueOf(minutes), Long.valueOf(seconds));
            }
        }
        if (!z3) {
            return String.format(Locale.ENGLISH, "%02d:%02d:%02d", Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds));
        }
        return String.format(Locale.ENGLISH, "%02d:%02d:%02d.%03d", Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds), Long.valueOf(millis));
    }
}
