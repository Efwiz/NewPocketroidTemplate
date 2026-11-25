package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.c10, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1733c10 extends AbstractC1579a10 {
    static final /* synthetic */ boolean e = true;
    private final M7 c;
    private final C0767v2 d;

    public C1733c10(M7 m7, C0767v2 c0767v2) {
        if (!e && c0767v2 != null && !c0767v2.O0()) {
            throw new AssertionError();
        }
        this.c = m7;
        this.d = c0767v2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C0767v2 D() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final M7 R() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        return this.d;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1733c10.class != obj.getClass()) {
            return false;
        }
        C1733c10 c1733c10 = (C1733c10) obj;
        return this.c == c1733c10.c && this.d == c1733c10.d;
    }

    public final int hashCode() {
        return Objects.hash(this.c, this.d);
    }

    public final String toString() {
        return "uninitialized new";
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public final C1733c10 z() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1348Si
    public final Object a(AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0) {
        return this.c.T();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2782pV
    public final InterfaceC2782pV a(C0702m c0702m, InterfaceC2782pV interfaceC2782pV) {
        return equals(interfaceC2782pV) ? this : FK.c;
    }
}
