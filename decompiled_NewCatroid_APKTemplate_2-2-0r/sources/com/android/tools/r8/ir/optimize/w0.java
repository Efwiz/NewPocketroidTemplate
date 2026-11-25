package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.AbstractC1182Mh;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C1189Mn;
import com.android.tools.r8.internal.C2179hn;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.MR;
import com.android.tools.r8.internal.QW;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class w0 {
    static final /* synthetic */ boolean c = true;
    private final LinkedHashMap a = new LinkedHashMap();
    private int b = 10000;

    w0() {
    }

    final C3591v0 a(int i, C3332x4 c3332x4) {
        AbstractC2412ko abstractC2412koL;
        LinkedHashMap linkedHashMap;
        LinkedHashMap linkedHashMap2;
        LinkedHashSet linkedHashSet;
        LinkedHashMap linkedHashMap3;
        LinkedHashMap linkedHashMap4;
        LinkedHashMap linkedHashMap5;
        if (c3332x4.y().isEmpty()) {
            return new C3591v0(i);
        }
        List<C3332x4> listY = c3332x4.y();
        Iterator<C3332x4> it = listY.iterator();
        C3591v0 c3591v0 = (C3591v0) this.a.get(it.next());
        C3591v0 c3591v1 = new C3591v0(i);
        if (c3591v0 != null) {
            LinkedHashMap linkedHashMap6 = c3591v0.a;
            if (linkedHashMap6 != null && !linkedHashMap6.isEmpty()) {
                LinkedHashMap linkedHashMap7 = new LinkedHashMap();
                c3591v1.a = linkedHashMap7;
                linkedHashMap7.putAll(c3591v0.a);
            }
            LinkedHashMap linkedHashMap8 = c3591v0.b;
            if (linkedHashMap8 != null && !linkedHashMap8.isEmpty()) {
                LinkedHashMap linkedHashMap9 = new LinkedHashMap();
                c3591v1.b = linkedHashMap9;
                linkedHashMap9.putAll(c3591v0.b);
            }
            LinkedHashMap linkedHashMap10 = c3591v0.c;
            if (linkedHashMap10 != null && !linkedHashMap10.isEmpty()) {
                LinkedHashMap linkedHashMap11 = new LinkedHashMap();
                c3591v1.c = linkedHashMap11;
                linkedHashMap11.putAll(c3591v0.c);
            }
            LinkedHashSet linkedHashSet2 = c3591v0.d;
            if (linkedHashSet2 != null && !linkedHashSet2.isEmpty()) {
                LinkedHashSet linkedHashSet3 = new LinkedHashSet();
                c3591v1.d = linkedHashSet3;
                linkedHashSet3.addAll(c3591v0.d);
            }
            LinkedHashMap linkedHashMap12 = c3591v0.e;
            if (linkedHashMap12 != null && !linkedHashMap12.isEmpty()) {
                LinkedHashMap linkedHashMap13 = new LinkedHashMap();
                c3591v1.e = linkedHashMap13;
                linkedHashMap13.putAll(c3591v0.e);
            }
            LinkedHashMap linkedHashMap14 = c3591v0.f;
            if (linkedHashMap14 != null && !linkedHashMap14.isEmpty()) {
                LinkedHashMap linkedHashMap15 = new LinkedHashMap();
                c3591v1.f = linkedHashMap15;
                linkedHashMap15.putAll(c3591v0.f);
            }
            c3591v1.g = c3591v0.g;
            LinkedHashMap linkedHashMap16 = c3591v0.h;
            if (linkedHashMap16 != null && !linkedHashMap16.isEmpty()) {
                LinkedHashMap linkedHashMap17 = new LinkedHashMap();
                c3591v1.h = linkedHashMap17;
                linkedHashMap17.putAll(c3591v0.h);
            }
            LinkedHashMap linkedHashMap18 = c3591v0.i;
            if (linkedHashMap18 != null && !linkedHashMap18.isEmpty()) {
                LinkedHashMap linkedHashMap19 = new LinkedHashMap();
                c3591v1.i = linkedHashMap19;
                linkedHashMap19.putAll(c3591v0.i);
            }
        }
        while (it.hasNext()) {
            C3591v0 c3591v2 = (C3591v0) this.a.get(it.next());
            if (c3591v2 == null) {
                return new C3591v0(i);
            }
            LinkedHashMap linkedHashMap20 = c3591v1.a;
            if (linkedHashMap20 == null || (linkedHashMap5 = c3591v2.a) == null) {
                c3591v1.a = null;
            } else {
                C3591v0.a(linkedHashMap20, linkedHashMap5);
            }
            LinkedHashMap linkedHashMap21 = c3591v1.b;
            if (linkedHashMap21 == null || (linkedHashMap4 = c3591v2.b) == null) {
                c3591v1.b = null;
            } else {
                C3591v0.a(linkedHashMap21, linkedHashMap4);
            }
            LinkedHashMap linkedHashMap22 = c3591v1.c;
            if (linkedHashMap22 == null || (linkedHashMap3 = c3591v2.c) == null) {
                c3591v1.c = null;
            } else {
                C3591v0.a(linkedHashMap22, linkedHashMap3);
            }
            LinkedHashSet linkedHashSet4 = c3591v1.d;
            if (linkedHashSet4 == null || (linkedHashSet = c3591v2.d) == null) {
                c3591v1.d = null;
            } else {
                C3591v0.a(linkedHashSet4, linkedHashSet);
            }
            LinkedHashMap linkedHashMap23 = c3591v1.e;
            if (linkedHashMap23 == null || (linkedHashMap2 = c3591v2.e) == null) {
                c3591v1.e = null;
            } else {
                C3591v0.a(linkedHashMap23, linkedHashMap2);
            }
            LinkedHashMap linkedHashMap24 = c3591v1.f;
            if (linkedHashMap24 == null || (linkedHashMap = c3591v2.f) == null) {
                c3591v1.f = null;
            } else {
                C3591v0.a(linkedHashMap24, linkedHashMap);
            }
            boolean z = C3591v0.k;
            if (!z && c3591v1.g != null) {
                throw new AssertionError();
            }
            if (!z && c3591v1.h != null) {
                throw new AssertionError();
            }
            if (!z && c3591v1.i != null) {
                throw new AssertionError();
            }
        }
        for (C3332x4 c3332x5 : listY) {
            if (c3332x5.c(c3332x4) && (abstractC2412koL = c3332x5.l()) != null) {
                if (abstractC2412koL.K1()) {
                    AbstractC1182Mh abstractC1182MhN = abstractC2412koL.N();
                    Y0 field = abstractC1182MhN.getField();
                    if (abstractC1182MhN instanceof C1189Mn) {
                        y0 y0Var = new y0(field, abstractC1182MhN.T().e().m());
                        LinkedHashMap linkedHashMap25 = c3591v1.b;
                        if (linkedHashMap25 != null) {
                            linkedHashMap25.remove(y0Var);
                        }
                        LinkedHashMap linkedHashMap26 = c3591v1.e;
                        if (linkedHashMap26 != null) {
                            linkedHashMap26.remove(y0Var);
                        }
                        LinkedHashMap linkedHashMap27 = c3591v1.h;
                        if (linkedHashMap27 != null) {
                            linkedHashMap27.remove(y0Var);
                        }
                    } else if (abstractC1182MhN instanceof QW) {
                        LinkedHashMap linkedHashMap28 = c3591v1.c;
                        if (linkedHashMap28 != null) {
                            linkedHashMap28.remove(field);
                        }
                        LinkedHashMap linkedHashMap29 = c3591v1.f;
                        if (linkedHashMap29 != null) {
                            linkedHashMap29.remove(field);
                        }
                        LinkedHashMap linkedHashMap30 = c3591v1.i;
                        if (linkedHashMap30 != null) {
                            linkedHashMap30.remove(field);
                        }
                    }
                } else if (abstractC2412koL instanceof C2179hn) {
                    C2179hn c2179hnR = abstractC2412koL.R();
                    LinkedHashSet linkedHashSet5 = c3591v1.d;
                    if (linkedHashSet5 != null) {
                        linkedHashSet5.remove(c2179hnR.K2());
                    }
                }
            }
        }
        return c3591v1;
    }

    final void a(C3332x4 c3332x4, MR mr) {
        for (C3332x4 c3332x5 : c3332x4.y()) {
            if (c3332x5.K()) {
                C3591v0 c3591v0 = (C3591v0) this.a.remove(c3332x5);
                if (c3591v0 != null) {
                    int iD = c3591v0.d();
                    if (!c && iD <= 0) {
                        throw new AssertionError();
                    }
                    this.b += iD;
                } else {
                    continue;
                }
            } else if (c3332x5.d(c3332x4)) {
                int iA = mr.a(c3332x5) - 1;
                if (iA == 0) {
                    mr.d(c3332x5);
                    C3591v0 c3591v1 = (C3591v0) this.a.remove(c3332x5);
                    if (c3591v1 != null) {
                        int iD2 = c3591v1.d();
                        if (!c && iD2 <= 0) {
                            throw new AssertionError();
                        }
                        this.b += iD2;
                    } else {
                        continue;
                    }
                } else {
                    mr.a(iA, c3332x5);
                }
            } else {
                continue;
            }
        }
    }

    final void a(C3332x4 c3332x4, C3591v0 c3591v0) {
        LinkedHashSet linkedHashSet;
        LinkedHashMap linkedHashMap;
        LinkedHashMap linkedHashMap2;
        LinkedHashSet linkedHashSet2;
        LinkedHashMap linkedHashMap3;
        LinkedHashMap linkedHashMap4;
        int i;
        boolean z = c;
        if (!z && this.a.containsKey(c3332x4)) {
            throw new AssertionError();
        }
        LinkedHashMap linkedHashMap5 = c3591v0.a;
        if ((linkedHashMap5 == null || linkedHashMap5.isEmpty()) && (((linkedHashSet = c3591v0.d) == null || linkedHashSet.isEmpty()) && (((linkedHashMap = c3591v0.b) == null || linkedHashMap.isEmpty()) && (((linkedHashMap2 = c3591v0.c) == null || linkedHashMap2.isEmpty()) && (((linkedHashSet2 = c3591v0.d) == null || linkedHashSet2.isEmpty()) && (((linkedHashMap3 = c3591v0.e) == null || linkedHashMap3.isEmpty()) && ((linkedHashMap4 = c3591v0.f) == null || linkedHashMap4.isEmpty()))))))) {
            return;
        }
        if (!c3332x4.K() || c3332x4.C().y().size() != 1) {
            c3591v0.a();
            c3591v0.g = null;
        }
        int iD = c3591v0.d();
        if (!z && iD > c3591v0.j) {
            throw new AssertionError();
        }
        int i2 = iD - this.b;
        if (i2 > 0) {
            Iterator it = this.a.entrySet().iterator();
            while (true) {
                i = 0;
                if (!it.hasNext() || i2 <= 0) {
                    break;
                }
                C3591v0 c3591v1 = (C3591v0) ((Map.Entry) it.next()).getValue();
                int iD2 = c3591v1.d();
                if (!c && iD2 <= 0) {
                    throw new AssertionError();
                }
                if (iD2 <= i2) {
                    it.remove();
                    this.b += iD2;
                    i2 -= iD2;
                } else {
                    c3591v1.a(i2);
                    this.b += i2;
                    i2 = 0;
                }
            }
            if (i2 > 0) {
                c3591v0.a(i2);
            }
            if (!c) {
                int i3 = this.b;
                Iterator it2 = this.a.values().iterator();
                while (it2.hasNext()) {
                    int iD3 = ((C3591v0) it2.next()).d();
                    if (!c && iD3 <= 0) {
                        throw new AssertionError();
                    }
                    i += iD3;
                }
                if (i3 != 10000 - i) {
                    throw new AssertionError();
                }
            }
        }
        this.a.put(c3332x4, c3591v0);
        int iD4 = this.b - c3591v0.d();
        this.b = iD4;
        if (!c && iD4 < 0) {
            throw new AssertionError();
        }
    }
}
