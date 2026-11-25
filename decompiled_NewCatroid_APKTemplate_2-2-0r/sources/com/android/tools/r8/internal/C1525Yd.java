package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0772w0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1525Yd {
    static final /* synthetic */ boolean b = true;
    private final InterfaceC0992Fq a;

    private C1525Yd(C1303Qq c1303Qq) {
        this.a = c1303Qq;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(int i, int i2, int i3) {
        long j = (((long) (i + 1)) * ((long) i2)) - ((long) i3);
        if (j > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(com.android.tools.r8.graph.o5 o5Var) {
        return Integer.MAX_VALUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int b(com.android.tools.r8.graph.o5 o5Var) {
        return -1;
    }

    private static void c(final com.android.tools.r8.graph.o5 o5Var) {
        if (!b && 1 >= com.android.tools.r8.utils.I1.a(o5Var.a().h(new Predicate() { // from class: com.android.tools.r8.internal.Yd$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1525Yd.a(o5Var, (com.android.tools.r8.graph.W0) obj);
            }
        }))) {
            throw new AssertionError();
        }
    }

    public final String toString() {
        return a();
    }

    public static InterfaceC1444Vd a(com.android.tools.r8.utils.A1 a1) {
        if (!b && a1.Y0() && a1.r1 == com.android.tools.r8.utils.A1.j.b) {
            throw new AssertionError();
        }
        return new InterfaceC1444Vd() { // from class: com.android.tools.r8.internal.Yd$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.internal.InterfaceC1444Vd
            public final int a(com.android.tools.r8.graph.o5 o5Var) {
                return C1525Yd.b(o5Var);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:48:0x00c3  */
    public static int a(com.android.tools.r8.utils.A1 a1, Map map, com.android.tools.r8.graph.o5 o5Var) {
        int iA;
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        if (!((w0E.q1() && w0E.a1().B0()) ? AbstractC2773pM.a(w0E, a1) : false)) {
            return -1;
        }
        C1525Yd c1525YdE = ((com.android.tools.r8.dex.r0) map.get(o5Var.a())).e();
        if (c1525YdE.a.isEmpty()) {
            if (!b) {
                c(o5Var);
            }
        } else {
            C0772w0 c0772w0P = o5Var.e().a1().P();
            int size = o5Var.D().size();
            boolean z = b;
            if (!z && c0772w0P.H0() != null && c0772w0P.H0().w0() != size) {
                throw new AssertionError();
            }
            C1369Td c1369Td = (C1369Td) c1525YdE.a.get(size);
            if (c1369Td != null) {
                if (c1369Td.b != null) {
                    com.android.tools.r8.dex.code.B1 b1A = a(c0772w0P.i);
                    if (b1A != null) {
                        InterfaceC2415kr interfaceC2415krB = c1369Td.b.b(b1A.u());
                        if (!interfaceC2415krB.isEmpty()) {
                            iA = interfaceC2415krB.a();
                            C1470Wd c1470Wd = (C1470Wd) c1369Td.b.get(iA);
                            if (c1470Wd.b) {
                                if (!C1369Td.c && iA != c1470Wd.a) {
                                    throw new AssertionError();
                                }
                            }
                        }
                    }
                } else if (!z) {
                    throw new AssertionError();
                }
            } else if (!z && c1369Td == null) {
                throw new AssertionError();
            }
            if (!b && iA != -1) {
                a(o5Var.e().a1().P(), iA);
            }
            return iA;
        }
        iA = -1;
        if (!b) {
            a(o5Var.e().a1().P(), iA);
        }
        return iA;
    }

    public static void a(final C0702m c0702m, com.android.tools.r8.dex.r0 r0Var) {
        int iD;
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        if (a1Y.Y0() && a1Y.r1 == com.android.tools.r8.utils.A1.j.b && !a1Y.U()) {
            C1303Qq c1303Qq = new C1303Qq(16);
            Iterator it = r0Var.b().iterator();
            while (it.hasNext()) {
                for (List list : AbstractC3417yB.a(c0702m, (C0729q2) it.next()).values()) {
                    if (list.size() == 1) {
                        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) list.get(0);
                        com.android.tools.r8.graph.W0 w0E = o5Var.e();
                        if ((w0E.q1() && w0E.a1().B0()) ? AbstractC2773pM.a(w0E, a1Y) : false) {
                            C0772w0 c0772w0P = w0E.a1().P();
                            com.android.tools.r8.graph.J0 j0H0 = c0772w0P.H0();
                            if (j0H0 == null) {
                                C0722p1 c0722p1J0 = a1Y.j0();
                                int i = com.android.tools.r8.graph.J0.d;
                                j0H0 = new com.android.tools.r8.graph.J0.a(0, new C0759u2[w0E.l1().size()], new com.android.tools.r8.graph.B0[]{c0722p1J0.r});
                            }
                            if (!b && j0H0.w0() != o5Var.D().size()) {
                                throw new AssertionError();
                            }
                            com.android.tools.r8.dex.code.B1 b1A = a(c0772w0P.i);
                            if (b1A != null) {
                                int iU = b1A.u();
                                if (j0H0.z0()) {
                                    iD = j0H0.u0().p0();
                                } else {
                                    int iW0 = j0H0.w0();
                                    iD = (com.android.tools.r8.utils.Q1.d(0) * iW0) + com.android.tools.r8.utils.Q1.d(iW0) + com.android.tools.r8.utils.Q1.d(0) + j0H0.t0().g.length + 1;
                                }
                                ((C1397Ud) c1303Qq.computeIfAbsent(Integer.valueOf(j0H0.w0()), new Function() { // from class: com.android.tools.r8.internal.Yd$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return C1525Yd.a(((Integer) obj).intValue());
                                    }
                                })).a(iU, iD);
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            final C1303Qq c1303Qq2 = new C1303Qq(c1303Qq.size());
            c1303Qq.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Yd$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C1525Yd.a(c1303Qq2, c0702m, (Integer) obj, (C1397Ud) obj2);
                }
            });
            r0Var.a(new C1525Yd(c1303Qq2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1397Ud a(int i) {
        return new C1397Ud(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(InterfaceC0992Fq interfaceC0992Fq, C0702m c0702m, Integer num, C1397Ud c1397Ud) {
        c1397Ud.getClass();
        int i = c0702m.Y().n1.T;
        boolean z = c0702m.Y().n1.S;
        if (!C1397Ud.c && c1397Ud.b.isEmpty()) {
            throw new AssertionError();
        }
        int size = c1397Ud.b.size();
        int[] iArr = new int[size];
        AbstractC0935Dr.a(c1397Ud.b.keySet().iterator(), iArr);
        Arrays.sort(iArr);
        C2807pq c2807pq = new C2807pq();
        C1470Wd c1470Wd = C1470Wd.e;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < size; i6++) {
            int i7 = iArr[i6];
            C1499Xd c1499Xd = (C1499Xd) c1397Ud.b.get(i7);
            if (!C1397Ud.c && i7 != c1499Xd.a) {
                throw new AssertionError();
            }
            int i8 = c1499Xd.c + i5;
            int i9 = c1499Xd.b + i4;
            if (i >= 0 && a(i7, i8, i9) > i) {
                int i10 = c1470Wd.a;
                if (i2 < i10) {
                    c2807pq.a(i10, c1470Wd);
                    i2 = c1470Wd.a;
                }
                if (i2 < i3) {
                    if (i2 > 0 && !((C1470Wd) c2807pq.get(i2)).b) {
                        c2807pq.remove(i2);
                    }
                    c2807pq.a(i3, new C1470Wd(i3, false, i5, i4));
                    i2 = i3;
                }
                c1470Wd = C1470Wd.e;
                i4 = 0;
                i5 = 0;
            }
            i5 += c1499Xd.c;
            i4 += c1499Xd.b;
            int i11 = i7 + 1;
            if ((i >= 0 && a(i7, i5, i4) > i) || (!z && i4 <= i11)) {
                i3 = i7;
            } else {
                c1470Wd = new C1470Wd(i7, true, i5, i4);
                i3 = 0;
            }
        }
        int i12 = c1470Wd.a;
        if (i2 < i12) {
            c2807pq.a(i12, c1470Wd);
            i2 = c1470Wd.a;
        }
        if (i2 < i3) {
            if (i2 > 0 && !((C1470Wd) c2807pq.get(i2)).b) {
                c2807pq.remove(i2);
            }
            c2807pq.a(i3, new C1470Wd(i3, false, i5, i4));
        }
        int i13 = c1397Ud.a;
        if (c2807pq.size() <= 1 && (c2807pq.size() != 1 || !((C1470Wd) c2807pq.values().iterator().next()).b)) {
            c2807pq = null;
        }
        c0702m.Y().n1.getClass();
        interfaceC0992Fq.put(num, new C1369Td(i13, c2807pq));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.W0 w0) {
        return w0.K0().equals(o5Var.u());
    }

    public final String a() {
        ArrayList arrayList = new ArrayList(this.a.values());
        arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.Yd$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1525Yd.a((C1369Td) obj);
            }
        }));
        final boolean z = false;
        return com.android.tools.r8.utils.Y2.a("\n", arrayList, new Function() { // from class: com.android.tools.r8.internal.Yd$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1525Yd.a(z, (C1369Td) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer a(C1369Td c1369Td) {
        return Integer.valueOf(c1369Td.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(boolean z, C1369Td c1369Td) {
        return c1369Td.a(z);
    }

    public static void a(C0772w0 c0772w0, int i) {
        int iU = a(c0772w0.i).u();
        if (!b && iU > i) {
            throw new AssertionError();
        }
    }

    public static com.android.tools.r8.dex.code.B1 a(com.android.tools.r8.dex.code.B1[] b1Arr) {
        com.android.tools.r8.dex.code.B1 b1 = null;
        for (com.android.tools.r8.dex.code.B1 b2 : b1Arr) {
            if (!b2.K()) {
                b1 = b2;
            }
        }
        return b1;
    }

    public static InterfaceC1444Vd a(List list, final com.android.tools.r8.utils.A1 a1) {
        if (!a1.Y0() || a1.r1 != com.android.tools.r8.utils.A1.j.b) {
            return a(a1);
        }
        if (a1.U()) {
            return new InterfaceC1444Vd() { // from class: com.android.tools.r8.internal.Yd$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.internal.InterfaceC1444Vd
                public final int a(com.android.tools.r8.graph.o5 o5Var) {
                    return C1525Yd.a(o5Var);
                }
            };
        }
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            final com.android.tools.r8.dex.r0 r0Var = (com.android.tools.r8.dex.r0) it.next();
            a1.n1.getClass();
            r0Var.b().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Yd$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    identityHashMap.put((C0729q2) obj, r0Var);
                }
            });
        }
        return new InterfaceC1444Vd() { // from class: com.android.tools.r8.internal.Yd$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.internal.InterfaceC1444Vd
            public final int a(com.android.tools.r8.graph.o5 o5Var) {
                return C1525Yd.a(a1, identityHashMap, o5Var);
            }
        };
    }
}
