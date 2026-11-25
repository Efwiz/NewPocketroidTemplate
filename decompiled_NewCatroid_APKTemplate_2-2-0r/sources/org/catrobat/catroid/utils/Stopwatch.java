package org.catrobat.catroid.utils;

import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/Stopwatch.class
  classes14.dex
 */
/* JADX INFO: compiled from: Stopwatch.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004J\u0006\u0010\u0003\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/utils/Stopwatch;", "", "()V", "start", "", "getElapsedMilliseconds", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Stopwatch {
    private long start;

    public final void start() {
        this.start = System.currentTimeMillis();
    }

    public final long getElapsedMilliseconds() {
        return System.currentTimeMillis() - this.start;
    }
}
