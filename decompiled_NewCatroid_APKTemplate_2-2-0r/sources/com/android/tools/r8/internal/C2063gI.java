package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2063gI extends AbstractC2980sH {
    static final /* synthetic */ boolean d = true;
    private final InterfaceC1277Pr c;

    private C2063gI(InterfaceC1277Pr interfaceC1277Pr) {
        this.c = interfaceC1277Pr;
    }

    @Override // com.android.tools.r8.internal.AbstractC2980sH
    public final long F() {
        return this.c.size();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1204Nc
    public final boolean a(int i) {
        return this.c.f(i);
    }

    @Override // com.android.tools.r8.internal.B1
    public final B1 b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean equals(Object obj) {
        if (obj == null || obj.getClass() != C2063gI.class) {
            return false;
        }
        return this.c.equals(((C2063gI) obj).c);
    }

    @Override // com.android.tools.r8.internal.B1
    public final int hashCode() {
        return this.c.hashCode();
    }

    @Override // com.android.tools.r8.internal.B1, com.android.tools.r8.internal.InterfaceC1204Nc
    public final C2063gI i() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        StringBuilder sb = new StringBuilder("NumberFromSetValue(");
        InterfaceC0841Ar it = this.c.iterator();
        sb.append(it.t());
        while (it.hasNext()) {
            sb.append(", ").append(it.t());
        }
        return sb.append(Constant.AFTER_QUTO).toString();
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean y() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1204Nc
    public final AbstractC3938n2 a(int[] iArr) {
        if (!d) {
            com.android.tools.r8.utils.B.a(iArr);
        }
        InterfaceC0841Ar it = this.c.iterator();
        while (it.hasNext()) {
            if (Arrays.binarySearch(iArr, ((Integer) it.next()).intValue()) < 0) {
                return AbstractC3938n2.b;
            }
        }
        return AbstractC3938n2.a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.InterfaceC1204Nc
    public final boolean a(InterfaceC1204Nc interfaceC1204Nc) {
        B1 b1 = (B1) interfaceC1204Nc;
        b1.getClass();
        if (b1 instanceof C2853qV) {
            return this.c.f(interfaceC1204Nc.e().G());
        }
        if (!d && !interfaceC1204Nc.c()) {
            throw new AssertionError();
        }
        InterfaceC0841Ar it = this.c.iterator();
        while (it.hasNext()) {
            if (interfaceC1204Nc.a(((Integer) it.next()).intValue())) {
                return true;
            }
        }
        return false;
    }
}
