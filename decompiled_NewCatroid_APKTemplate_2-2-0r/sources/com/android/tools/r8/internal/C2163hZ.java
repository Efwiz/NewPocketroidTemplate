package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2163hZ extends AbstractC3472z00 {
    private static final C2163hZ c = new C2163hZ();

    static C2163hZ P() {
        return c;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final WH N() {
        return WH.i();
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final int hashCode() {
        return System.identityHashCode(c);
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final String toString() {
        return "TOP (everything)";
    }
}
