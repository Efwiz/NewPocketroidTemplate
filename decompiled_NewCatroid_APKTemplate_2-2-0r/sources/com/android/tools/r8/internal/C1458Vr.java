package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0413a3;
import com.android.tools.r8.dex.code.C0418b3;
import com.android.tools.r8.utils.InterfaceC3937n1;
import java.util.ArrayList;
import java.util.List;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1458Vr extends IY {
    static final /* synthetic */ boolean m = true;
    private final int[] l;

    public C1458Vr(int i, S10 s10, int[] iArr, int[] iArr2) {
        super(s10, iArr2, i);
        this.l = iArr;
        if (m) {
            return;
        }
        N2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 31;
    }

    @Override // com.android.tools.r8.internal.IY
    public final void N2() {
        boolean z = m;
        if (!z) {
            super.N2();
        }
        int i = 1;
        if (!z && this.l.length < 1) {
            throw new AssertionError();
        }
        if (!z && this.l.length > 65535) {
            throw new AssertionError();
        }
        if (!z && this.l.length != L2()) {
            throw new AssertionError();
        }
        while (true) {
            int[] iArr = this.l;
            if (i >= iArr.length) {
                return;
            }
            if (!m && iArr[i - 1] >= iArr[i]) {
                throw new AssertionError();
            }
            i++;
        }
    }

    public final int O2() {
        return this.l[0];
    }

    public final C2807pq P2() {
        C2807pq c2807pq = new C2807pq();
        int i = 0;
        while (true) {
            int[] iArr = this.l;
            if (i >= iArr.length) {
                return c2807pq;
            }
            c2807pq.a(iArr[i], e(i));
            i++;
        }
    }

    public int[] Q2() {
        return this.l;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean T1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1458Vr W() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return false;
    }

    public final int f(int i) {
        return this.l[i];
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        StringBuilder sbAppend = new StringBuilder(super.toString()).append(System.lineSeparator());
        for (int i = 0; i < L2(); i++) {
            sbAppend.append("          ").append(this.l[i]).append(" -> ").append(e(i).w()).append(System.lineSeparator());
        }
        return sbAppend.append("          F -> ").append(J2().v()).toString();
    }

    public static long b(com.android.tools.r8.utils.H1 h1, long j) {
        if (!h1.a()) {
            return (j * 2) + 4;
        }
        if (m || j <= 4294967295L) {
            return j * 4;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.dex.code.d4 a(int[] iArr, int i) {
        com.android.tools.r8.utils.H1 h1 = com.android.tools.r8.utils.H1.a;
        boolean z = m;
        if (!z && this.l.length != iArr.length) {
            throw new AssertionError();
        }
        if (!z && !h1.b()) {
            throw new AssertionError();
        }
        if (a(h1, a(this.l))) {
            int[] iArr2 = this.l;
            if (!z && !a(h1, a(iArr2))) {
                throw new AssertionError();
            }
            long jB = b(h1, a(iArr2));
            long length = this.l.length;
            if (jB <= (h1.a() ? length * 8 : (length * 4) + 2)) {
                int iA = (int) a(this.l);
                if (iArr.length == iA) {
                    return new C0418b3(iArr, O2());
                }
                int[] iArr3 = new int[iA];
                int i2 = 0;
                for (int i3 = 0; i3 < iA; i3++) {
                    if (this.l[i2] == O2() + i3) {
                        iArr3[i3] = iArr[i2];
                        i2++;
                    } else {
                        iArr3[i3] = i;
                    }
                }
                if (m || i2 == this.l.length) {
                    return new C0418b3(iArr3, O2());
                }
                throw new AssertionError();
            }
        }
        if (z || L2() == this.l.length) {
            return new com.android.tools.r8.dex.code.N3(this.l, iArr);
        }
        throw new AssertionError();
    }

    public final void a(InterfaceC3937n1 interfaceC3937n1) {
        int i = 0;
        while (true) {
            int[] iArr = this.l;
            if (i >= iArr.length) {
                return;
            }
            interfaceC3937n1.a(iArr[i], e(i));
            i++;
        }
    }

    private static long a(int[] iArr) {
        return (((long) iArr[iArr.length - 1]) - ((long) iArr[0])) + 1;
    }

    public static boolean a(com.android.tools.r8.utils.H1 h1, long j) {
        return j <= (h1.a() ? 4294967295L : WebSocketProtocol.PAYLOAD_SHORT_MAX);
    }

    public static long a(com.android.tools.r8.utils.H1 h1, int[] iArr) {
        long j;
        long length = iArr.length;
        long j2 = (h1.a() ? length * 8 : (length * 4) + 2) + ((long) (h1.a() ? 12 : 3));
        if (!a(h1, a(iArr))) {
            j = Long.MAX_VALUE;
        } else {
            if (!m && !a(h1, a(iArr))) {
                throw new AssertionError();
            }
            long jB = b(h1, a(iArr));
            j = ((long) (h1.a() ? 16 : 3)) + jB;
            if (j < jB) {
                j = 2147483647L;
            }
        }
        return Math.min(j2, j);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        int iA = c2322jf.a(W0(), value());
        com.android.tools.r8.utils.H1 h1 = com.android.tools.r8.utils.H1.a;
        if (a(h1, a(this.l))) {
            int[] iArr = this.l;
            if (!m && !a(h1, a(iArr))) {
                throw new AssertionError();
            }
            long jB = b(h1, a(iArr));
            long length = this.l.length;
            if (jB <= (h1.a() ? length * 8 : (length * 4) + 2)) {
                c2322jf.a(this, (com.android.tools.r8.dex.code.X0) new C0413a3(iA));
                return;
            }
        }
        c2322jf.a(this, (com.android.tools.r8.dex.code.X0) new com.android.tools.r8.dex.code.M3(iA));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        M7 m7A = l6.a(J2());
        ArrayList arrayList = new ArrayList(L2());
        List<C3332x4> listZ = h().z();
        com.android.tools.r8.utils.H1 h1 = com.android.tools.r8.utils.H1.c;
        int i = 0;
        if (a(h1, a(this.l))) {
            int[] iArr = this.l;
            if (!m && !a(h1, a(iArr))) {
                throw new AssertionError();
            }
            long jB = b(h1, a(iArr));
            long length = this.l.length;
            if (jB <= (h1.a() ? length * 8 : (length * 4) + 2)) {
                int[] iArr2 = this.l;
                int i2 = iArr2[0];
                int i3 = iArr2[iArr2.length - 1];
                for (long j = i2; j <= i3; j++) {
                    if (j == this.l[i]) {
                        arrayList.add(l6.a(listZ.get(c(i))));
                        i++;
                    } else {
                        arrayList.add(m7A);
                    }
                }
                if (!m && i != L2()) {
                    throw new AssertionError();
                }
                l6.a(new U8(U8.a.TABLE, m7A, new int[]{i2}, arrayList), this);
                return;
            }
        }
        int[] iArrM2 = M2();
        int length2 = iArrM2.length;
        while (i < length2) {
            arrayList.add(l6.a(listZ.get(iArrM2[i])));
            i++;
        }
        l6.a(new U8(U8.a.LOOKUP, m7A, this.l, arrayList), this);
    }
}
