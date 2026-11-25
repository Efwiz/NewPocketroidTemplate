package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1910eI extends AbstractC2980sH {
    static final /* synthetic */ boolean e = true;
    private final long c;
    private final long d;

    public C1910eI(long j, long j2) {
        if (!e && j2 <= j) {
            throw new AssertionError();
        }
        this.c = j;
        this.d = j2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2980sH
    public final long F() {
        return (this.d - this.c) + 1;
    }

    public final long G() {
        return this.d;
    }

    public final long H() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1204Nc
    public final boolean a(int i) {
        long j = i;
        return this.c <= j && j <= this.d;
    }

    @Override // com.android.tools.r8.internal.B1
    public final B1 b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1, com.android.tools.r8.internal.InterfaceC1204Nc
    public final C1910eI d() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean equals(Object obj) {
        if (obj == null || obj.getClass() != C1910eI.class) {
            return false;
        }
        C1910eI c1910eI = (C1910eI) obj;
        return this.c == c1910eI.c && this.d == c1910eI.d;
    }

    @Override // com.android.tools.r8.internal.B1
    public final int hashCode() {
        int iHashCode = (Long.hashCode(this.d) + ((Long.hashCode(this.c) + 31) * 31)) * 31;
        if (e || iHashCode == Objects.hash(Long.valueOf(this.c), Long.valueOf(this.d))) {
            return iHashCode;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        return "NumberFromIntervalValue([" + this.c + "; " + this.d + "])";
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean y() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.InterfaceC1204Nc
    public final boolean a(InterfaceC1204Nc interfaceC1204Nc) {
        B1 b1 = (B1) interfaceC1204Nc;
        b1.getClass();
        if (b1 instanceof C2853qV) {
            return a(interfaceC1204Nc.e().G());
        }
        if (b1 instanceof C1910eI) {
            C1910eI c1910eID = interfaceC1204Nc.d();
            return this.c <= c1910eID.d && this.d >= c1910eID.c;
        }
        if (e || (b1 instanceof C2063gI)) {
            return interfaceC1204Nc.i().a(this);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1204Nc
    public final AbstractC3938n2 a(int[] iArr) {
        return AbstractC3938n2.c;
    }
}
