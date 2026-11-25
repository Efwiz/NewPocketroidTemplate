package com.android.tools.r8.graph;

import com.android.tools.r8.dex.C0586x;
import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class I2 extends J2 {
    static final /* synthetic */ boolean c = true;
    public static final I2 b = new I2();

    private I2() {
    }

    @Override // com.android.tools.r8.graph.B2
    public final Object M0() {
        return null;
    }

    @Override // com.android.tools.r8.graph.B2
    public final E2 N0() {
        return E2.r;
    }

    @Override // com.android.tools.r8.graph.B2
    public final C0767v2 a(C0722p1 c0722p1) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.B2
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        if (!c && this != b) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.graph.B2
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return obj instanceof I2;
    }

    @Override // com.android.tools.r8.graph.B2
    public final int hashCode() {
        return 42;
    }

    @Override // com.android.tools.r8.graph.J2
    public final long j1() {
        return 0L;
    }

    @Override // com.android.tools.r8.graph.B2
    public final Object s0() {
        return null;
    }

    @Override // com.android.tools.r8.graph.B2
    public final String toString() {
        return "Null";
    }

    @Override // com.android.tools.r8.graph.B2
    public final void a(C0586x c0586x, C0645e5 c0645e5) {
        B2.a(E2.r, 0, c0586x);
    }

    @Override // com.android.tools.r8.graph.B2
    final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
        boolean z = c;
        if (!z && this != b) {
            throw new AssertionError();
        }
        if (z || b2 == b) {
            return 0;
        }
        throw new AssertionError();
    }
}
