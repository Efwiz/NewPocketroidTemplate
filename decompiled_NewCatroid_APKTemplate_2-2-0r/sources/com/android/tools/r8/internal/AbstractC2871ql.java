package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.utils.AbstractC3962s0;
import java.util.ArrayDeque;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ql, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2871ql {
    static final /* synthetic */ boolean a = true;

    public static C1166Ls a(C0722p1 c0722p1, S10 s10) {
        C1166Ls c1166Ls = null;
        for (AbstractC2412ko abstractC2412ko : s10.h0()) {
            if (abstractC2412ko.W1()) {
                C1166Ls c1166LsZ = abstractC2412ko.Z();
                if (c0722p1.b(c1166LsZ.S2()) && c1166LsZ.U2() == s10) {
                    if (c1166Ls != null) {
                        return null;
                    }
                    c1166Ls = c1166LsZ;
                }
            }
        }
        return c1166Ls;
    }

    public static IdentityHashMap a(C0702m c0702m, C2409kl c2409kl, Set set) {
        Set setC = OU.c();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (RW rw : c2409kl.b((Predicate) new Aj$$ExternalSyntheticLambda6())) {
            com.android.tools.r8.graph.T0 t0S = ((C0680j) c0702m.f()).c(rw.getField()).s();
            if (t0S != null && set.contains(t0S) && !setC.contains(t0S) && identityHashMap.put(t0S, rw) != null) {
                setC.add(t0S);
                identityHashMap.remove(t0S);
            }
        }
        return identityHashMap;
    }

    public static void a(C2409kl c2409kl, AbstractC2412ko abstractC2412ko) {
        if (abstractC2412ko.B1()) {
            if (!a && !abstractC2412ko.E().T2()) {
                throw new AssertionError();
            }
            return;
        }
        S10 s10B = abstractC2412ko.b();
        if (s10B.C() || s10B.z()) {
            return;
        }
        boolean z = abstractC2412ko instanceof AG;
        if (!z && !abstractC2412ko.b2()) {
            if (!a) {
                throw new AssertionError();
            }
            return;
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        if (abstractC2412ko.e0() != null) {
            arrayDeque.add(abstractC2412ko);
        } else if (z) {
            for (AbstractC2412ko abstractC2412ko2 : s10B.h0()) {
                if (!a) {
                    abstractC2412ko2.getClass();
                    if (abstractC2412ko2 instanceof N3) {
                        throw new AssertionError();
                    }
                }
                if (!abstractC2412ko2.r1()) {
                    return;
                } else {
                    arrayDeque.add(abstractC2412ko2);
                }
            }
        } else if (!a) {
            throw new AssertionError();
        }
        a(c2409kl, arrayDeque);
    }

    public static void a(C2409kl c2409kl, RW rw) {
        a(c2409kl, AbstractC3962s0.a(rw));
    }

    private static void a(C2409kl c2409kl, ArrayDeque arrayDeque) {
        Set setC = OU.c();
        while (!arrayDeque.isEmpty()) {
            InterfaceC2729oo interfaceC2729oo = (InterfaceC2729oo) arrayDeque.removeFirst();
            if (!setC.contains(interfaceC2729oo)) {
                if (interfaceC2729oo.i()) {
                    ML mlL = interfaceC2729oo.l();
                    if (!mlL.E() && !mlL.z()) {
                        Iterator it = mlL.g0().iterator();
                        do {
                            if (!it.hasNext()) {
                                mlL.n0();
                                for (S10 s10 : mlL.k0()) {
                                    arrayDeque.add(s10.i() ? s10.l() : s10.b);
                                }
                                setC.add(mlL);
                                break;
                            }
                        } while (((ML) it.next()) == mlL);
                    }
                } else {
                    AbstractC2412ko abstractC2412koK = interfaceC2729oo.k();
                    if (!abstractC2412koK.d1() || !abstractC2412koK.b().y()) {
                        abstractC2412koK.h().a(c2409kl, abstractC2412koK).s();
                        for (S10 s11 : abstractC2412koK.b) {
                            arrayDeque.add(s11.i() ? s11.l() : s11.b);
                        }
                        setC.add(abstractC2412koK);
                    }
                }
            }
        }
    }
}
