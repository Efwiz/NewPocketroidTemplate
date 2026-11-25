package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class GW extends B1 {
    static final /* synthetic */ boolean d = true;
    private final AbstractC3121uK c;

    GW(AbstractC3121uK abstractC3121uK) {
        if (!d && abstractC3121uK.e()) {
            throw new AssertionError();
        }
        this.c = abstractC3121uK;
    }

    public static B1 a(AbstractC3121uK abstractC3121uK) {
        return abstractC3121uK.e() ? C2892r10.F() : new GW(abstractC3121uK);
    }

    @Override // com.android.tools.r8.internal.B1
    public final B1 b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return a(this.c.a(c0702m, abstractC1476Wj, abstractC1476Wj2));
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || GW.class != obj.getClass()) {
            return false;
        }
        return this.c.equals(((GW) obj).c);
    }

    @Override // com.android.tools.r8.internal.B1
    public final int hashCode() {
        return this.c.hashCode();
    }

    @Override // com.android.tools.r8.internal.B1
    public final GW r() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final int s() {
        return this.c.c();
    }

    @Override // com.android.tools.r8.internal.B1
    public final AbstractC3121uK t() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        return "StatefulValue";
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean u() {
        return this.c.d();
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean v() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean y() {
        return true;
    }
}
