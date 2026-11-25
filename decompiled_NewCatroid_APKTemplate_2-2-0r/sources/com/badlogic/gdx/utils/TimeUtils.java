package com.badlogic.gdx.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/TimeUtils.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class TimeUtils {
    private static final long nanosPerMilli = 1000000;

    public static long millisToNanos(long j) {
        return j * 1000000;
    }

    public static long nanoTime() {
        return System.nanoTime();
    }

    public static long millis() {
        return System.currentTimeMillis();
    }

    public static long nanosToMillis(long j) {
        return j / 1000000;
    }

    public static long timeSinceNanos(long j) {
        return nanoTime() - j;
    }

    public static long timeSinceMillis(long j) {
        return millis() - j;
    }
}
