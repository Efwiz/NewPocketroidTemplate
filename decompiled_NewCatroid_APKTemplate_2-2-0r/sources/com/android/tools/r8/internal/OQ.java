package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.EnumC0691k2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class OQ {
    static final /* synthetic */ boolean a = true;

    public static boolean a(C0721p0 c0721p0, C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C0722p1 c0722p1A = c0702m.a();
        C0699l2 c0699l2 = c0721p0.f;
        if (!c0699l2.d.g() || c0699l2.e != c0722p1A.C5.a) {
            return false;
        }
        if (c0699l2.f) {
            if (a) {
                return false;
            }
            throw new AssertionError("Invoke-dynamic invoking non interface method ObjectMethods#bootstrap as an interface method.");
        }
        if (c0721p0.g.size() < 2) {
            if (a) {
                return false;
            }
            throw new AssertionError("Invoke-dynamic invoking method ObjectMethods#bootstrap with less than 2 parameters.");
        }
        com.android.tools.r8.graph.B2.k kVarL0 = ((com.android.tools.r8.graph.B2) c0721p0.g.get(0)).L0();
        if (kVarL0 == null) {
            if (a) {
                return false;
            }
            throw new AssertionError("Invoke-dynamic invoking method ObjectMethods#bootstrap with an invalid type.");
        }
        AbstractC0727q0 abstractC0727q0B = c0702m.b(o5Var.a(), (C0767v2) kVarL0.j1());
        if (abstractC0727q0B != null && !abstractC0727q0B.I1()) {
            com.android.tools.r8.graph.B2.j jVarK0 = ((com.android.tools.r8.graph.B2) c0721p0.g.get(1)).K0();
            if (jVarK0 == null) {
                if (a) {
                    return false;
                }
                throw new AssertionError("Invoke-dynamic invoking method ObjectMethods#bootstrap with invalid field names.");
            }
            C0759u2 c0759u2 = (C0759u2) jVarK0.j1();
            boolean z = a;
            if (!z && !c0759u2.toString().isEmpty() && c0759u2.toString().split(";").length != c0721p0.g.size() - 2) {
                throw new AssertionError();
            }
            if (!z && abstractC0727q0B.w1().size() > c0721p0.g.size() - 2) {
                throw new AssertionError();
            }
            int i = 2;
            while (true) {
                if (i < c0721p0.g.size()) {
                    com.android.tools.r8.graph.G2 g2G0 = ((com.android.tools.r8.graph.B2) c0721p0.g.get(i)).G0();
                    if (g2G0 == null) {
                        break;
                    }
                    EnumC0691k2 enumC0691k2 = ((C0699l2) g2G0.b).d;
                    enumC0691k2.getClass();
                    if (enumC0691k2 != EnumC0691k2.e || !((C0699l2) g2G0.b).e.x0()) {
                        break;
                    }
                    i++;
                } else {
                    C0759u2 c0759u3 = c0721p0.d;
                    if (c0759u3 == c0722p1A.h0) {
                        if (a || c0721p0.e == c0722p1A.b(c0722p1A.X1, abstractC0727q0B.getType())) {
                            return true;
                        }
                        throw new AssertionError();
                    }
                    if (c0759u3 == c0722p1A.V) {
                        if (a || c0721p0.e == c0722p1A.b(c0722p1A.A1, abstractC0727q0B.getType())) {
                            return true;
                        }
                        throw new AssertionError();
                    }
                    if (c0759u3 == c0722p1A.U) {
                        if (a || c0721p0.e == c0722p1A.b(c0722p1A.v1, abstractC0727q0B.getType(), c0722p1A.Z1)) {
                            return true;
                        }
                        throw new AssertionError();
                    }
                }
            }
            if (a) {
                return false;
            }
            throw new AssertionError("Invoke-dynamic invoking method ObjectMethods#bootstrap with invalid getters.");
        }
        return false;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.android.tools.r8.internal.NQ.<init>(com.android.tools.r8.graph.u2, com.android.tools.r8.graph.r2, com.android.tools.r8.graph.u2, com.android.tools.r8.graph.Y0[], com.android.tools.r8.graph.q2, com.android.tools.r8.internal.NQ-IA):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:309)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isArgUnused(ProcessVariables.java:146)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.lambda$isVarUnused$0(ProcessVariables.java:131)
        	at jadx.core.utils.ListUtils.allMatch(ListUtils.java:224)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isVarUnused(ProcessVariables.java:131)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.processBlock(ProcessVariables.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:93)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.removeUnusedResults(ProcessVariables.java:73)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:48)
        */
    public static com.android.tools.r8.internal.NQ a(com.android.tools.r8.internal.J7 r8, com.android.tools.r8.graph.C0702m r9, com.android.tools.r8.graph.o5 r10) {
        /*
            boolean r0 = com.android.tools.r8.internal.OQ.a
            if (r0 != 0) goto L15
            com.android.tools.r8.graph.p0 r0 = r8.T()
            boolean r10 = a(r0, r9, r10)
            if (r10 == 0) goto Lf
            goto L15
        Lf:
            java.lang.AssertionError r8 = new java.lang.AssertionError
            r8.<init>()
            throw r8
        L15:
            com.android.tools.r8.graph.p0 r8 = r8.T()
            java.util.List r10 = r8.g
            r0 = 0
            java.lang.Object r10 = r10.get(r0)
            com.android.tools.r8.graph.B2 r10 = (com.android.tools.r8.graph.B2) r10
            com.android.tools.r8.graph.B2$k r10 = r10.L0()
            java.util.List r0 = r8.g
            r1 = 1
            java.lang.Object r0 = r0.get(r1)
            com.android.tools.r8.graph.B2 r0 = (com.android.tools.r8.graph.B2) r0
            com.android.tools.r8.graph.B2$j r0 = r0.K0()
            com.android.tools.r8.graph.I3 r0 = r0.j1()
            r4 = r0
            com.android.tools.r8.graph.u2 r4 = (com.android.tools.r8.graph.C0759u2) r4
            java.util.List r0 = r8.g
            int r0 = r0.size()
            r1 = 2
            int r0 = r0 - r1
            com.android.tools.r8.graph.Y0[] r5 = new com.android.tools.r8.graph.Y0[r0]
        L44:
            java.util.List r0 = r8.g
            int r0 = r0.size()
            if (r1 >= r0) goto L69
            java.util.List r0 = r8.g
            java.lang.Object r0 = r0.get(r1)
            com.android.tools.r8.graph.B2 r0 = (com.android.tools.r8.graph.B2) r0
            com.android.tools.r8.graph.G2 r0 = r0.G0()
            int r2 = r1 + (-2)
            T extends com.android.tools.r8.graph.I3 r0 = r0.b
            com.android.tools.r8.graph.l2 r0 = (com.android.tools.r8.graph.C0699l2) r0
            com.android.tools.r8.graph.e2 r0 = r0.e
            com.android.tools.r8.graph.Y0 r0 = r0.t0()
            r5[r2] = r0
            int r1 = r1 + 1
            goto L44
        L69:
            com.android.tools.r8.graph.I3 r10 = r10.j1()
            com.android.tools.r8.graph.v2 r10 = (com.android.tools.r8.graph.C0767v2) r10
            com.android.tools.r8.graph.q0 r9 = r9.c(r10)
            com.android.tools.r8.graph.q2 r6 = r9.a0()
            com.android.tools.r8.internal.NQ r9 = new com.android.tools.r8.internal.NQ
            com.android.tools.r8.graph.u2 r2 = r8.d
            com.android.tools.r8.graph.r2 r3 = r8.e
            r7 = 0
            r1 = r9
            r1.<init>(r2, r3, r4, r5, r6)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.OQ.a(com.android.tools.r8.internal.J7, com.android.tools.r8.graph.m, com.android.tools.r8.graph.o5):com.android.tools.r8.internal.NQ");
    }
}
