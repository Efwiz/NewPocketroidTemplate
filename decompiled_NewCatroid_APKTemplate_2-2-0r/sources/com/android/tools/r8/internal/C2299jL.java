package com.android.tools.r8.internal;

import java.util.Comparator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2299jL implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Integer numValueOf = Integer.valueOf(((Number) ((C2772pL) obj).c()).intValue());
        Integer numValueOf2 = Integer.valueOf(((Number) ((C2772pL) obj2).c()).intValue());
        if (numValueOf == numValueOf2) {
            return 0;
        }
        if (numValueOf == null) {
            return -1;
        }
        if (numValueOf2 == null) {
            return 1;
        }
        return numValueOf.compareTo(numValueOf2);
    }
}
