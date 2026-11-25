package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.r10, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2892r10 extends B1 {
    private static final C2892r10 c = new C2892r10();

    private C2892r10() {
    }

    public static C2892r10 F() {
        return c;
    }

    @Override // com.android.tools.r8.internal.B1
    public final B1 b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.internal.B1
    public final int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean isUnknown() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        return "UnknownValue";
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean y() {
        return false;
    }
}
