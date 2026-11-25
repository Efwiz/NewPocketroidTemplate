package com.android.tools.r8.internal;

import androidx.exifinterface.media.ExifInterface;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class VU extends C2994sV {
    private static final VU e = new VU();

    static VU S() {
        return e;
    }

    @Override // com.android.tools.r8.internal.C2994sV, com.android.tools.r8.internal.XM
    public final String P() {
        return ExifInterface.LATITUDE_SOUTH;
    }

    @Override // com.android.tools.r8.internal.C2994sV, com.android.tools.r8.internal.XM
    public final String Q() {
        return "short";
    }

    @Override // com.android.tools.r8.internal.C2994sV, com.android.tools.r8.internal.AbstractC3472z00
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.internal.C2994sV, com.android.tools.r8.internal.AbstractC3472z00
    public final int hashCode() {
        return System.identityHashCode(e);
    }

    @Override // com.android.tools.r8.internal.C2994sV, com.android.tools.r8.internal.AbstractC3472z00
    public final String toString() {
        return "SHORT";
    }
}
