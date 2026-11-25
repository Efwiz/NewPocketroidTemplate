package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.e10, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1883e10 extends AbstractC1579a10 {
    static final C1883e10 c = new C1883e10();

    private C1883e10() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        return c0767v2;
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C1883e10 o() {
        return this;
    }

    public final String toString() {
        return "uninitialized this";
    }

    @Override // com.android.tools.r8.internal.InterfaceC1348Si
    public final Object a(AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0) {
        return JK.g;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2782pV
    public final InterfaceC2782pV a(C0702m c0702m, InterfaceC2782pV interfaceC2782pV) {
        return this == interfaceC2782pV ? this : FK.c;
    }
}
