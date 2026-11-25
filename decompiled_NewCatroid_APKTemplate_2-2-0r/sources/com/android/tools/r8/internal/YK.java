package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class YK extends AbstractC1757cL {
    private final C0767v2 c;

    YK(C0767v2 c0767v2) {
        super(EnumC1686bL.g);
        this.c = c0767v2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int a(AbstractC1757cL abstractC1757cL) {
        return !(abstractC1757cL instanceof YK) ? this.a.compareTo(abstractC1757cL.a) : this.c.compareTo(((YK) abstractC1757cL).c);
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final String b() {
        return this.a.name();
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final boolean c() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int d() {
        return 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final boolean equals(Object obj) {
        return (obj instanceof YK) && this.c == ((YK) obj).c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int hashCode() {
        return this.c.hashCode() + (this.a.ordinal() * 7);
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final String a() {
        return this.c.r0();
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int a(C2102gl c2102gl, ZK zk, int i) {
        c2102gl.a(new DG(this.c, c2102gl.a(zk.a.size(), AbstractC3472z00.a(this.c, WH.b(), (C0702m<?>) c2102gl.o), 2)));
        return i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final boolean a(AbstractC1476Wj abstractC1476Wj) {
        return AbstractC2964s2.a(abstractC1476Wj, this.c) != this.c;
    }
}
