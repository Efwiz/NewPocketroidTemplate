package com.android.tools.r8.dex;

import com.android.tools.r8.dex.code.B1;
import com.android.tools.r8.dex.code.C0421c1;
import com.android.tools.r8.dex.code.C0426d1;
import com.android.tools.r8.dex.code.C0430e0;
import com.android.tools.r8.dex.code.C0431e1;
import com.android.tools.r8.dex.code.C0435f0;
import com.android.tools.r8.dex.code.C0436f1;
import com.android.tools.r8.dex.code.C0441g1;
import com.android.tools.r8.dex.code.C0446h1;
import com.android.tools.r8.dex.code.C0451i1;
import com.android.tools.r8.dex.code.C0456j1;
import com.android.tools.r8.dex.code.C0461k1;
import com.android.tools.r8.dex.code.C0466l1;
import com.android.tools.r8.dex.code.C0471m1;
import com.android.tools.r8.dex.code.C0476n1;
import com.android.tools.r8.dex.code.C0481o1;
import com.android.tools.r8.dex.code.C0486p1;
import com.android.tools.r8.dex.code.C0491q1;
import com.android.tools.r8.dex.code.N0;
import com.android.tools.r8.dex.code.R0;
import com.android.tools.r8.dex.code.R2;
import com.android.tools.r8.dex.code.X0;
import com.android.tools.r8.dex.code.d4;
import com.android.tools.r8.graph.B0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0772w0;
import com.android.tools.r8.graph.H0;
import com.android.tools.r8.graph.J0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.AbstractC2763pC;
import com.android.tools.r8.internal.C1303Qq;
import com.android.tools.r8.internal.InterfaceC0934Dq;
import com.android.tools.r8.internal.InterfaceC1912eK;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.UnaryOperator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class T {
    static final /* synthetic */ boolean k = true;
    private final W0 a;
    private final C0759u2 b;
    private final C0722p1 c;
    private final IdentityHashMap d = new IdentityHashMap();
    private J0.a e = null;
    private final C1303Qq f = new C1303Qq();
    private final IdentityHashMap g = new IdentityHashMap();
    private final IdentityHashMap h = new IdentityHashMap();
    private final C1303Qq i = new C1303Qq();
    private final IdentityHashMap j = new IdentityHashMap();

    public T(W0 w0, C0759u2 c0759u2, C0722p1 c0722p1) {
        this.a = w0;
        this.b = c0759u2;
        this.c = c0722p1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ B1 a(B1 b1, B1 b2, B1 b3) {
        return b3 == b1 ? b2 : b3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ B1 b(B1 b1, B1 b2, B1 b3) {
        return b3 == b1 ? b2 : b3;
    }

    private int a(int i, ListIterator listIterator, com.android.tools.r8.dex.code.K k2, com.android.tools.r8.dex.code.K k3) {
        int iZ = k2.z() + k2.u();
        C0426d1 c0426d1 = new C0426d1(0);
        c0426d1.g(iZ);
        k3.g(k2.u());
        listIterator.set(k3);
        a(k2, k3);
        listIterator.add(c0426d1);
        int i2 = i + 3;
        IdentityHashMap identityHashMap = this.d;
        identityHashMap.put(c0426d1, (List) identityHashMap.remove(k2));
        this.d.put(k3, AbstractC2763pC.a((B1) listIterator.next()));
        listIterator.previous();
        return i2;
    }

    private void a(final B1 b1, final B1 b2) {
        Iterator it = this.d.values().iterator();
        while (it.hasNext()) {
            ((List) it.next()).replaceAll(new UnaryOperator() { // from class: com.android.tools.r8.dex.T$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return T.a(b1, b2, (B1) obj);
                }
            });
        }
        InterfaceC1912eK it2 = this.f.c().iterator();
        while (it2.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it2.next();
            if (interfaceC0934Dq.getValue() == b1) {
                interfaceC0934Dq.setValue(b2);
            }
        }
        Iterator it3 = this.h.entrySet().iterator();
        while (it3.hasNext()) {
            S s = (S) ((Map.Entry) it3.next()).getValue();
            if (s.a == b1) {
                s.a = b2;
            }
            if (s.b == b1) {
                s.b = b2;
            }
        }
        Iterator it4 = this.j.values().iterator();
        while (it4.hasNext()) {
            ((List) it4.next()).replaceAll(new UnaryOperator() { // from class: com.android.tools.r8.dex.T$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return T.b(b1, b2, (B1) obj);
                }
            });
        }
    }

    public C0772w0 a() {
        int iA;
        J0 j0H0;
        B1 c0426d1;
        B1 b1;
        S s;
        C1303Qq c1303Qq = new C1303Qq(16);
        B1[] b1Arr = this.a.a1().P().i;
        int length = b1Arr.length;
        int i = 0;
        boolean z = false;
        while (i < length) {
            B1 b2 = b1Arr[i];
            c1303Qq.a(b2.u(), b2);
            if (b2.D()) {
                z = true;
            }
            i++;
            z = z;
        }
        if (z) {
            for (B1 b3 : b1Arr) {
                if (b3.D()) {
                    B1 b4 = (B1) c1303Qq.get(b3.x() + b3.u());
                    if (!k && b4 == null) {
                        throw new AssertionError();
                    }
                    this.g.put(b4, b3);
                }
            }
        }
        for (B1 b5 : this.a.a1().P().i) {
            if (b5 instanceof R0) {
                R0 r0 = (R0) b5;
                B1 b6 = (B1) c1303Qq.get(r0.u() + r0.g);
                if (!k && b6 == null) {
                    throw new AssertionError();
                }
                this.d.put(b5, AbstractC2763pC.a(b6));
            } else if (b5 instanceof N0) {
                N0 n0 = (N0) b5;
                B1 b7 = (B1) c1303Qq.get(n0.u() + n0.f);
                if (!k && b7 == null) {
                    throw new AssertionError();
                }
                this.d.put(b5, AbstractC2763pC.a(b7));
            } else if (b5 instanceof C0431e1) {
                C0431e1 c0431e1 = (C0431e1) b5;
                B1 b8 = (B1) c1303Qq.get(c0431e1.u() + c0431e1.e);
                if (!k && b8 == null) {
                    throw new AssertionError();
                }
                this.d.put(b5, AbstractC2763pC.a(b8));
            } else if (b5 instanceof C0421c1) {
                C0421c1 c0421c1 = (C0421c1) b5;
                B1 b9 = (B1) c1303Qq.get(c0421c1.u() + c0421c1.e);
                if (!k && b9 == null) {
                    throw new AssertionError();
                }
                this.d.put(b5, AbstractC2763pC.a(b9));
            } else if (b5 instanceof C0426d1) {
                C0426d1 c0426d2 = (C0426d1) b5;
                B1 b10 = (B1) c1303Qq.get(c0426d2.u() + c0426d2.e);
                if (!k && b10 == null) {
                    throw new AssertionError();
                }
                this.d.put(b5, AbstractC2763pC.a(b10));
            } else if (b5.D()) {
                X0 x0 = (X0) b5;
                B1 b11 = (B1) c1303Qq.get(x0.x() + x0.u());
                if (!k && b11 == null) {
                    throw new AssertionError();
                }
                this.d.put(b5, AbstractC2763pC.a(b11));
            } else if (b5 instanceof d4) {
                int[] iArrP = ((d4) b5).P();
                int iU = ((B1) this.g.get(b5)).u();
                ArrayList arrayList = new ArrayList();
                for (int i2 : iArrP) {
                    B1 b12 = (B1) c1303Qq.get(i2 + iU);
                    if (!k && b12 == null) {
                        throw new AssertionError();
                    }
                    arrayList.add(b12);
                }
                this.d.put(b5, arrayList);
            } else {
                continue;
            }
        }
        J0.a aVarA = J0.a(this.a.a1().P(), this.c);
        if (aVarA != null) {
            this.e = aVarA;
            int iY0 = 0;
            for (B0 b0 : aVarA.g) {
                if (b0 instanceof B0.a) {
                    iY0 += ((B0.a) b0).c;
                    B1 b13 = (B1) c1303Qq.get(iY0);
                    if (!k && b13 == null) {
                        throw new AssertionError();
                    }
                    this.f.a(iY0, b13);
                } else if (b0 instanceof B0.b) {
                    iY0 += ((B0.b) b0).y0();
                    B1 b14 = (B1) c1303Qq.get(iY0);
                    if (!k && b14 == null) {
                        throw new AssertionError();
                    }
                    this.f.a(iY0, b14);
                } else {
                    continue;
                }
            }
        }
        B1 b15 = b1Arr[b1Arr.length - 1];
        C0772w0 c0772w0P = this.a.a1().P();
        for (C0772w0.a aVar : c0772w0P.g) {
            B1 b16 = (B1) c1303Qq.get(aVar.b);
            int i3 = aVar.b + aVar.c;
            if (i3 > b15.u()) {
                s = new S(b16, b15, true);
                b1 = b15;
            } else {
                B1 b17 = (B1) c1303Qq.get(i3);
                b1 = b17;
                s = new S(b16, b17, false);
            }
            boolean z2 = k;
            if (!z2 && aVar.b != s.a.u()) {
                throw new AssertionError();
            }
            if (!z2) {
                if (aVar.c != (s.c ? (s.b.z() + s.b.u()) - s.a.u() : s.b.u() - s.a.u())) {
                    throw new AssertionError();
                }
            }
            this.h.put(aVar, s);
            this.i.a(b16.u(), b16);
            this.i.a(b1.u(), b1);
        }
        for (C0772w0.b bVar : c0772w0P.h) {
            ArrayList arrayList2 = new ArrayList();
            int i4 = bVar.b;
            if (i4 != -1) {
                B1 b18 = (B1) c1303Qq.get(i4);
                if (!k && b18 == null) {
                    throw new AssertionError();
                }
                arrayList2.add(b18);
            }
            for (C0772w0.b.a aVar2 : bVar.a) {
                B1 b19 = (B1) c1303Qq.get(aVar2.b);
                if (!k && b19 == null) {
                    throw new AssertionError();
                }
                arrayList2.add(b19);
            }
            this.j.put(bVar, arrayList2);
        }
        LinkedList<B1> linkedList = new LinkedList();
        Collections.addAll(linkedList, this.a.a1().P().i);
        do {
            ListIterator listIterator = linkedList.listIterator();
            iA = 0;
            while (listIterator.hasNext()) {
                B1 b20 = (B1) listIterator.next();
                int iU2 = b20.u();
                b20.g(iU2 + iA);
                if (b20 instanceof C0430e0) {
                    C0430e0 c0430e0 = (C0430e0) b20;
                    if (c0430e0.N().a(this.b) >= 0) {
                        C0435f0 c0435f0 = new C0435f0(c0430e0.e, c0430e0.N());
                        c0435f0.g(c0430e0.u());
                        iA++;
                        listIterator.set(c0435f0);
                        a(b20, c0435f0);
                    }
                } else {
                    com.android.tools.r8.dex.code.K c0436f1 = null;
                    com.android.tools.r8.dex.code.K c0441g1 = null;
                    if (b20 instanceof R0) {
                        R0 r1 = (R0) b20;
                        int iU3 = ((B1) ((List) this.d.get(r1)).get(0)).u() - b20.u();
                        if (-32768 > iU3 || iU3 > 32767) {
                            switch (Q.a[r1.O().b().ordinal()]) {
                                case 1:
                                    c0436f1 = new C0436f1(r1.e, r1.f, 0);
                                    break;
                                case 2:
                                    c0436f1 = new C0446h1(r1.e, r1.f, 0);
                                    break;
                                case 3:
                                    c0436f1 = new C0456j1(r1.e, r1.f, 0);
                                    break;
                                case 4:
                                    c0436f1 = new C0466l1(r1.e, r1.f, 0);
                                    break;
                                case 5:
                                    c0436f1 = new C0476n1(r1.e, r1.f, 0);
                                    break;
                                case 6:
                                    c0436f1 = new C0486p1(r1.e, r1.f, 0);
                                    break;
                            }
                            iA = a(iA, listIterator, r1, c0436f1);
                        }
                    } else if (b20 instanceof N0) {
                        N0 n1 = (N0) b20;
                        int iU4 = ((B1) ((List) this.d.get(n1)).get(0)).u() - b20.u();
                        if (-32768 > iU4 || iU4 > 32767) {
                            switch (Q.a[n1.O().b().ordinal()]) {
                                case 1:
                                    c0441g1 = new C0441g1(n1.e, 0);
                                    break;
                                case 2:
                                    c0441g1 = new C0451i1(n1.e, 0);
                                    break;
                                case 3:
                                    c0441g1 = new C0461k1(n1.e, 0);
                                    break;
                                case 4:
                                    c0441g1 = new C0471m1(n1.e, 0);
                                    break;
                                case 5:
                                    c0441g1 = new C0481o1(n1.e, 0);
                                    break;
                                case 6:
                                    c0441g1 = new C0491q1(n1.e, 0);
                                    break;
                            }
                            iA = a(iA, listIterator, n1, c0441g1);
                        }
                    } else if (b20 instanceof C0431e1) {
                        B1 b21 = (C0431e1) b20;
                        int iU5 = ((B1) ((List) this.d.get(b21)).get(0)).u() - b20.u();
                        if (-128 > iU5 || iU5 > 127) {
                            if (-32768 <= iU5 && iU5 <= 32767) {
                                c0426d1 = new C0421c1(iU5);
                            } else {
                                c0426d1 = new C0426d1(iU5);
                            }
                            c0426d1.g(b21.u());
                            listIterator.set(c0426d1);
                            iA += c0426d1.z() - b21.z();
                            a(b21, c0426d1);
                            this.d.put(c0426d1, (List) this.d.remove(b21));
                        }
                    } else if (b20 instanceof C0421c1) {
                        B1 b22 = (C0421c1) b20;
                        int iU6 = ((B1) ((List) this.d.get(b22)).get(0)).u() - b20.u();
                        if (-32768 > iU6 || iU6 > 32767) {
                            C0426d1 c0426d3 = new C0426d1(iU6);
                            c0426d3.g(b22.u());
                            listIterator.set(c0426d3);
                            iA++;
                            a(b22, c0426d3);
                            this.d.put(c0426d3, (List) this.d.remove(b22));
                        }
                    } else if (!(b20 instanceof C0426d1) && !b20.D() && b20.K() && b20.u() % 2 != 0) {
                        listIterator.previous();
                        B1 b23 = listIterator.hasPrevious() ? (B1) listIterator.previous() : null;
                        if (b23 != null && b23.L() && this.f.get(iU2) == null && this.i.get(iU2) == null) {
                            listIterator.remove();
                            iA--;
                        } else {
                            if (b23 != null) {
                                listIterator.next();
                            }
                            R2 r2 = new R2();
                            r2.g(b20.u());
                            listIterator.add(r2);
                            iA++;
                        }
                        b20.g(iU2 + iA);
                        listIterator.next();
                    }
                }
            }
        } while (iA > 0);
        for (B1 b24 : linkedList) {
            if (b24 instanceof R0) {
                R0 r3 = (R0) b24;
                int iU7 = ((B1) ((List) this.d.get(r3)).get(0)).u() - b24.u();
                if (!k && (-32768 > iU7 || iU7 > 32767)) {
                    throw new AssertionError();
                }
                r3.g = (short) iU7;
            } else if (b24 instanceof N0) {
                N0 n2 = (N0) b24;
                int iU8 = ((B1) ((List) this.d.get(n2)).get(0)).u() - b24.u();
                if (!k && (-32768 > iU8 || iU8 > 32767)) {
                    throw new AssertionError();
                }
                n2.f = (short) iU8;
            } else if (b24 instanceof C0431e1) {
                C0431e1 c0431e2 = (C0431e1) b24;
                int iU9 = ((B1) ((List) this.d.get(c0431e2)).get(0)).u() - b24.u();
                if (!k && (-128 > iU9 || iU9 > 127)) {
                    throw new AssertionError();
                }
                c0431e2.e = (byte) iU9;
            } else if (b24 instanceof C0421c1) {
                C0421c1 c0421c2 = (C0421c1) b24;
                int iU10 = ((B1) ((List) this.d.get(c0421c2)).get(0)).u() - b24.u();
                if (!k && (-32768 > iU10 || iU10 > 32767)) {
                    throw new AssertionError();
                }
                c0421c2.e = (short) iU10;
            } else if (b24 instanceof C0426d1) {
                C0426d1 c0426d4 = (C0426d1) b24;
                c0426d4.e = ((B1) ((List) this.d.get(c0426d4)).get(0)).u() - b24.u();
            } else if (b24.D()) {
                X0 x1 = (X0) b24;
                x1.j(((B1) ((List) this.d.get(x1)).get(0)).u() - b24.u());
            } else if (b24 instanceof d4) {
                d4 d4Var = (d4) b24;
                B1 b25 = (B1) this.g.get(d4Var);
                List list = (List) this.d.get(d4Var);
                int[] iArrP2 = d4Var.P();
                for (int i5 = 0; i5 < list.size(); i5++) {
                    iArrP2[i5] = ((B1) list.get(i5)).u() - b25.u();
                }
            }
        }
        C0772w0 c0772w0P2 = this.a.a1().P();
        C0772w0.a[] aVarArr = new C0772w0.a[c0772w0P2.g.length];
        int i6 = 0;
        while (true) {
            C0772w0.a[] aVarArr2 = c0772w0P2.g;
            if (i6 < aVarArr2.length) {
                C0772w0.a aVar3 = aVarArr2[i6];
                S s2 = (S) this.h.get(aVar3);
                C0772w0.a aVar4 = new C0772w0.a(s2.a.u(), s2.c ? (s2.b.z() + s2.b.u()) - s2.a.u() : s2.b.u() - s2.a.u(), -1);
                aVarArr[i6] = aVar4;
                aVar4.d = aVar3.d;
                i6++;
            } else {
                C0772w0 c0772w0P3 = this.a.a1().P();
                C0772w0.b[] bVarArr = new C0772w0.b[c0772w0P3.h.length];
                int i7 = 0;
                while (true) {
                    C0772w0.b[] bVarArr2 = c0772w0P3.h;
                    if (i7 < bVarArr2.length) {
                        C0772w0.b bVar2 = bVarArr2[i7];
                        Iterator it = ((List) this.j.get(bVar2)).iterator();
                        int iU11 = bVar2.b != -1 ? ((B1) it.next()).u() : -1;
                        C0772w0.b.a[] aVarArr3 = new C0772w0.b.a[bVar2.a.length];
                        int i8 = 0;
                        while (true) {
                            C0772w0.b.a[] aVarArr4 = bVar2.a;
                            if (i8 < aVarArr4.length) {
                                aVarArr3[i8] = new C0772w0.b.a(((B1) it.next()).u(), aVarArr4[i8].getType());
                                i8++;
                            }
                        }
                        bVarArr[i7] = new C0772w0.b(aVarArr3, iU11);
                        i7++;
                    } else {
                        C0772w0 c0772w0P4 = this.a.a1().P();
                        if (!this.f.isEmpty()) {
                            if (!k && this.e == null) {
                                throw new AssertionError();
                            }
                            ArrayList arrayList3 = new ArrayList();
                            int i9 = 0;
                            int iU12 = 0;
                            for (B0 b26 : this.e.g) {
                                if (b26 instanceof B0.a) {
                                    i9 += ((B0.a) b26).c;
                                    B1 b27 = (B1) this.f.get(i9);
                                    arrayList3.add(this.c.b(b27.u() - iU12));
                                    iU12 = b27.u();
                                } else if (b26 instanceof B0.b) {
                                    B0.b bVar3 = (B0.b) b26;
                                    int iY1 = bVar3.y0() + i9;
                                    B1 b28 = (B1) this.f.get(iY1);
                                    H0.a(bVar3.x0(), b28.u() - iU12, arrayList3, this.c);
                                    iU12 = b28.u();
                                    i9 = iY1;
                                } else {
                                    arrayList3.add(b26);
                                }
                            }
                            J0.a aVar5 = this.e;
                            j0H0 = new J0.a(aVar5.e, aVar5.f, (B0[]) arrayList3.toArray(B0.a));
                        } else {
                            j0H0 = c0772w0P4.H0();
                        }
                        C0772w0 c0772w0P5 = this.a.a1().P();
                        C0772w0 c0772w0 = new C0772w0(c0772w0P5.d, c0772w0P5.e, c0772w0P5.f, (B1[]) linkedList.toArray(B1.b), aVarArr, bVarArr, j0H0);
                        c0772w0.j = this.b;
                        return c0772w0;
                    }
                }
            }
        }
    }
}
