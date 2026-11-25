package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class KX implements OX {
    static final /* synthetic */ boolean a = true;

    private KX() {
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0097  */
    /* JADX WARN: Code duplicated, block: B:41:0x0099  */
    /* JADX WARN: Code duplicated, block: B:51:0x00c0  */
    /* JADX WARN: Code duplicated, block: B:69:0x0108  */
    /* JADX WARN: Code duplicated, block: B:71:0x011c  */
    /* JADX WARN: Code duplicated, block: B:81:0x013b  */
    /* JADX WARN: Code duplicated, block: B:84:0x014c  */
    /* JADX WARN: Code duplicated, block: B:85:0x0151  */
    @Override // com.android.tools.r8.internal.OX
    public final boolean a(IX ix, IX ix2, NX nx) {
        boolean z;
        boolean z2;
        AbstractC2412ko abstractC2412koA;
        InterfaceC2390kX interfaceC2390kX;
        boolean zContains = nx.c.contains(ix);
        boolean z3 = false;
        while (ix2 != null) {
            if ((ix2 instanceof FX) && !nx.e.contains(ix)) {
                if (!ix2.q().isEmpty() && !ix2.r()) {
                    return false;
                }
                ix2.x();
                if (ix2.w()) {
                    abstractC2412koA = ix2.l().a();
                    interfaceC2390kX = (InterfaceC2390kX) nx.a.get(abstractC2412koA);
                    if (interfaceC2390kX != null) {
                        nx.a.put(abstractC2412koA, C1930eX.d());
                    } else {
                        nx.a.put(abstractC2412koA, C1930eX.d());
                    }
                }
                if (ix2.r()) {
                    ix2 = ix2.o();
                } else {
                    ix2 = null;
                }
                z3 = true;
            } else {
                if (!(ix2 instanceof C3217vX) || zContains) {
                    if ((ix2 instanceof C3441yX) && ix2.r()) {
                        IX ixO = ix2.o();
                        ixO.getClass();
                        if ((ixO instanceof DX) && ix2.q().isEmpty() && !zContains) {
                            if (((QX) nx.b).a((AbstractC1332Rs) ix2.i().a())) {
                            }
                            ix2.x();
                            if (ix2.w()) {
                                abstractC2412koA = ix2.l().a();
                                interfaceC2390kX = (InterfaceC2390kX) nx.a.get(abstractC2412koA);
                                if (interfaceC2390kX != null || interfaceC2390kX.b()) {
                                    nx.a.put(abstractC2412koA, C1930eX.d());
                                } else {
                                    if (!a && !interfaceC2390kX.a()) {
                                        throw new AssertionError();
                                    }
                                    interfaceC2390kX.c().d();
                                }
                            }
                            if (ix2.r()) {
                                ix2 = ix2.o();
                            } else {
                                ix2 = null;
                            }
                            z3 = true;
                        }
                    }
                    return false;
                }
                C3217vX c3217vXG = ix2.g();
                boolean z4 = !nx.d.contains(ix) && ((Set) nx.f.get(ix)).isEmpty();
                if (nx.e.contains(ix)) {
                    com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(ix2);
                    boolean z5 = false;
                    while (true) {
                        if (o3H.c()) {
                            IX ix3 = (IX) o3H.f();
                            ix3.getClass();
                            if (ix3 instanceof DX) {
                                z5 = true;
                            }
                            if (!(ix3 instanceof BX)) {
                                o3H.a((Iterable) ix3.n());
                            } else if (!z5 || !ix3.s()) {
                                z = false;
                            }
                        }
                        if (ix2.q().isEmpty()) {
                            z = true;
                        } else {
                            z = false;
                        }
                    }
                } else if (ix2.q().isEmpty()) {
                    z = true;
                } else {
                    z = false;
                }
                if (c3217vXG.b() || c3217vXG.c()) {
                    z2 = true;
                } else {
                    if (((QX) nx.b).a((AbstractC1332Rs) ix2.g().a())) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                }
                if (!z4 || !z || !z2) {
                    return false;
                }
                ix2.x();
                if (ix2.w()) {
                    abstractC2412koA = ix2.l().a();
                    interfaceC2390kX = (InterfaceC2390kX) nx.a.get(abstractC2412koA);
                    if (interfaceC2390kX != null) {
                        nx.a.put(abstractC2412koA, C1930eX.d());
                    } else {
                        nx.a.put(abstractC2412koA, C1930eX.d());
                    }
                }
                if (ix2.r()) {
                    ix2 = ix2.o();
                } else {
                    ix2 = null;
                }
                z3 = true;
            }
        }
        return z3;
    }
}
