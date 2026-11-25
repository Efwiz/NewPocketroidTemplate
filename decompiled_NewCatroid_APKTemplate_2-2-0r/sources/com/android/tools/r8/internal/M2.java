package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class M2 {
    boolean a;
    C0767v2 b;
    InterfaceC0992Fq c;
    InterfaceC1277Pr d;
    static final /* synthetic */ boolean f = true;
    private static final M2 e = new M2(false, null, AbstractC1052Hq.a, AbstractC1331Rr.a);

    M2(boolean z, C0767v2 c0767v2, InterfaceC0992Fq interfaceC0992Fq, InterfaceC1277Pr interfaceC1277Pr) {
        this.a = z;
        this.b = c0767v2;
        this.c = interfaceC0992Fq;
        this.d = interfaceC1277Pr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002d, code lost:
    
        if ((r2 instanceof com.android.tools.r8.graph.proto.j) != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.android.tools.r8.internal.M2 a(final com.android.tools.r8.graph.o5 r5, com.android.tools.r8.graph.proto.k r6) {
        /*
            boolean r0 = r6.g()
            if (r0 == 0) goto L9
            com.android.tools.r8.internal.M2 r5 = com.android.tools.r8.internal.M2.e
            return r5
        L9:
            com.android.tools.r8.graph.proto.c r0 = r6.a()
            boolean r1 = r0.f()
            boolean r2 = com.android.tools.r8.internal.M2.f
            if (r2 != 0) goto L36
            if (r1 == 0) goto L36
            com.android.tools.r8.graph.U0 r2 = r5.e()
            com.android.tools.r8.graph.W0 r2 = (com.android.tools.r8.graph.W0) r2
            boolean r2 = r2.B1()
            if (r2 == 0) goto L30
            r2 = 0
            com.android.tools.r8.graph.proto.b r2 = r0.a(r2)
            r2.getClass()
            boolean r2 = r2 instanceof com.android.tools.r8.graph.proto.j
            if (r2 == 0) goto L30
            goto L36
        L30:
            java.lang.AssertionError r5 = new java.lang.AssertionError
            r5.<init>()
            throw r5
        L36:
            boolean r2 = r6.f()
            if (r2 == 0) goto L45
            com.android.tools.r8.graph.proto.l r6 = r6.c()
            com.android.tools.r8.graph.v2 r6 = r6.g()
            goto L46
        L45:
            r6 = 0
        L46:
            com.android.tools.r8.internal.Qq r2 = new com.android.tools.r8.internal.Qq
            r2.<init>()
            com.android.tools.r8.internal.Lr r3 = new com.android.tools.r8.internal.Lr
            r3.<init>()
            com.android.tools.r8.internal.M2$$ExternalSyntheticLambda0 r4 = new com.android.tools.r8.internal.M2$$ExternalSyntheticLambda0
            r4.<init>()
            r0.a(r4)
            com.android.tools.r8.internal.M2 r5 = new com.android.tools.r8.internal.M2
            r5.<init>(r1, r6, r2, r3)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.M2.a(com.android.tools.r8.graph.o5, com.android.tools.r8.graph.proto.k):com.android.tools.r8.internal.M2");
    }

    public final boolean equals(Object obj) {
        if (obj == null || M2.class != obj.getClass()) {
            return false;
        }
        M2 m2 = (M2) obj;
        return this.a == m2.a && this.b == m2.b && this.c.equals(m2.c) && this.d.equals(m2.d);
    }

    public final int hashCode() {
        return Objects.hash(Boolean.valueOf(this.a), this.b, this.c, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(boolean z, com.android.tools.r8.graph.o5 o5Var, InterfaceC1277Pr interfaceC1277Pr, InterfaceC0992Fq interfaceC0992Fq, int i, com.android.tools.r8.graph.proto.b bVar) {
        bVar.getClass();
        if (bVar instanceof com.android.tools.r8.graph.proto.j) {
            if (!f && !z) {
                throw new AssertionError();
            }
            return;
        }
        int iM = o5Var.e().m(i);
        boolean z2 = f;
        if (!z2 && iM < 0) {
            throw new AssertionError();
        }
        if (bVar.c()) {
            interfaceC1277Pr.a(iM);
        } else {
            if (!z2 && !(bVar instanceof com.android.tools.r8.graph.proto.l)) {
                throw new AssertionError();
            }
            interfaceC0992Fq.a(iM, bVar.b().g());
        }
    }
}
