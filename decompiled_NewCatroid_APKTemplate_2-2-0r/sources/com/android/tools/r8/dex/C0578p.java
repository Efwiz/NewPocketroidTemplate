package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.C1566Zr;
import com.android.tools.r8.internal.C3378xe;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3892f0;
import com.android.tools.r8.utils.C3970u0;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.dex.p, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0578p extends r {
    private final C0702m a;
    private final ConcurrentHashMap b = new ConcurrentHashMap();

    public C0578p(C0702m c0702m) {
        this.a = c0702m;
    }

    private void b(C0767v2 c0767v2) {
        C0722p1 c0722p1A = this.a.a();
        int iG0 = c0767v2.G0();
        if (iG0 != 0) {
            C0759u2 c0759u2 = c0767v2.e;
            int i = c0759u2.d - iG0;
            byte[] bArr = c0759u2.e;
            c0767v2 = c0722p1A.e(c0722p1A.c(i, Arrays.copyOfRange(bArr, iG0, bArr.length)));
        }
        if (C3378xe.a(c0767v2)) {
            return;
        }
        this.b.putIfAbsent(c0767v2, new C0577o());
    }

    private boolean c(C0767v2 c0767v2) {
        A1 a1Y = this.a.Y();
        if (this.a.z().e(c0767v2) != null || a1Y.C1.a(c0767v2) || a1Y.C1.b(c0767v2)) {
            return true;
        }
        if (c0767v2.b1().startsWith("L" + a1Y.C1.f())) {
            return true;
        }
        if (C1566Zr.d(c0767v2)) {
            c0767v2 = C1566Zr.b(this.a.a(), c0767v2);
        }
        return a1Y.C1.r().contains(c0767v2);
    }

    @Override // com.android.tools.r8.dex.r
    final boolean a() {
        return false;
    }

    @Override // com.android.tools.r8.dex.r
    public final void a(C0684j2 c0684j2) {
        C0767v2 c0767v2B = c0684j2.e.b(this.a.a());
        if (c(c0767v2B)) {
            b(c0767v2B);
            if (!c0684j2.e.M0() && !c0767v2B.e.toString().startsWith("L$-vivified-$/")) {
                ((C0577o) this.b.get(c0684j2.e)).b.add(c0684j2);
            }
        }
        if (c(c0684j2.h.e)) {
            b(c0684j2.h.e);
        }
        for (C0767v2 c0767v2 : c0684j2.h.f.a) {
            if (c(c0767v2)) {
                b(c0767v2);
            }
        }
    }

    @Override // com.android.tools.r8.dex.r
    public final void a(Y0 y0) {
        C0767v2 c0767v2B = y0.e.b(this.a.a());
        if (c(c0767v2B)) {
            b(c0767v2B);
            if (!y0.e.M0() && !c0767v2B.e.toString().startsWith("L$-vivified-$/")) {
                ((C0577o) this.b.get(y0.e)).a.add(y0);
            }
        }
        if (c(y0.h)) {
            b(y0.h);
        }
    }

    private static Comparator b() {
        return new C0579p$$ExternalSyntheticLambda0();
    }

    @Override // com.android.tools.r8.dex.r
    public final void a(C0767v2 c0767v2) {
        if (c(c0767v2)) {
            b(c0767v2);
        }
    }

    @Override // com.android.tools.r8.dex.r
    final void a(C0729q2 c0729q2) {
        C0767v2 c0767v2 = c0729q2.f;
        if (c(c0767v2)) {
            b(c0767v2);
            ((C0577o) this.b.get(c0767v2)).c = true;
        }
        for (C0767v2 c0767v3 : c0729q2.g.a) {
            if (c(c0767v3)) {
                b(c0767v3);
                ((C0577o) this.b.get(c0767v3)).c = true;
            }
        }
    }

    @Override // com.android.tools.r8.dex.r
    final void a(A1 a1) {
        StringBuilder sb = new StringBuilder();
        String strLineSeparator = System.lineSeparator();
        for (C0767v2 c0767v2 : C3892f0.a(this.b.keySet(), b())) {
            C0577o c0577o = (C0577o) this.b.get(c0767v2);
            StringBuilder sbAppend = sb.append("-keep class ");
            C0759u2 c0759u2E = this.a.z().e(c0767v2);
            if (c0759u2E == null) {
                c0759u2E = c0767v2.e;
            }
            sbAppend.append(C3970u0.b(c0759u2E.toString()));
            if (c0577o.c) {
                sb.append(" { *; }").append(strLineSeparator);
            } else if (c0577o.a.isEmpty() && c0577o.b.isEmpty()) {
                sb.append(strLineSeparator);
            } else {
                sb.append(" {").append(strLineSeparator);
                for (Y0 y0 : C3892f0.a(c0577o.a, b())) {
                    StringBuilder sbAppend2 = sb.append("    ");
                    C0767v2 c0767v3 = y0.h;
                    C0759u2 c0759u2E2 = this.a.z().e(c0767v3);
                    if (c0759u2E2 == null) {
                        c0759u2E2 = c0767v3.e;
                    }
                    sbAppend2.append(C3970u0.b(c0759u2E2.toString())).append(" ").append(y0.f).append(";").append(strLineSeparator);
                }
                for (C0684j2 c0684j2 : C3892f0.a(c0577o.b, b())) {
                    StringBuilder sbAppend3 = sb.append("    ");
                    C0767v2 c0767v4 = c0684j2.h.e;
                    C0759u2 c0759u2E3 = this.a.z().e(c0767v4);
                    if (c0759u2E3 == null) {
                        c0759u2E3 = c0767v4.e;
                    }
                    sbAppend3.append(C3970u0.b(c0759u2E3.toString())).append(" ").append(c0684j2.f).append("(");
                    for (int i = 0; i < c0684j2.F0(); i++) {
                        if (i != 0) {
                            sb.append(", ");
                        }
                        C0767v2 c0767v5 = c0684j2.h.f.a[i];
                        C0759u2 c0759u2E4 = this.a.z().e(c0767v5);
                        if (c0759u2E4 == null) {
                            c0759u2E4 = c0767v5.e;
                        }
                        sb.append(C3970u0.b(c0759u2E4.toString()));
                    }
                    sb.append(");").append(strLineSeparator);
                }
                sb.append("}").append(strLineSeparator);
            }
        }
        a1.E1.accept(sb.toString(), a1.e);
        a1.E1.finished(a1.e);
    }
}
