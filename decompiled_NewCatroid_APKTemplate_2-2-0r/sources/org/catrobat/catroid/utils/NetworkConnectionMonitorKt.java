package org.catrobat.catroid.utils;

import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NetworkConnectionMonitorKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: NetworkConnectionMonitor.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0007\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0005"}, d2 = {"roundTo", "", "places", "", "", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NetworkConnectionMonitorKt {
    public static final double roundTo(double d, int i) {
        double dPow = Math.pow(10.0d, i);
        return Math.rint(d * dPow) / dPow;
    }

    public static final float roundTo(float f, int i) {
        return (float) roundTo(f, i);
    }
}
