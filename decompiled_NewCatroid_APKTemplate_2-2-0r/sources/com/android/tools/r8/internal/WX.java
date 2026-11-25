package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0595a2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class WX implements C7 {
    private final C0722p1 a;
    private final com.android.tools.r8.graph.Z1 b;
    private final IdentityHashMap c;

    public WX(C0702m c0702m) {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        this.c = identityHashMap;
        C0722p1 c0722p1A = c0702m.a();
        this.a = c0722p1A;
        com.android.tools.r8.graph.Z1 z1 = c0722p1A.W3;
        this.b = z1;
        identityHashMap.put(c0722p1A.v1, z1.a);
        identityHashMap.put(c0722p1A.x1, z1.b);
        identityHashMap.put(c0722p1A.w1, z1.g);
        identityHashMap.put(c0722p1A.C1, z1.g);
        identityHashMap.put(c0722p1A.A1, z1.g);
        identityHashMap.put(c0722p1A.B1, z1.j);
        identityHashMap.put(c0722p1A.z1, z1.i);
        identityHashMap.put(c0722p1A.y1, z1.h);
        identityHashMap.put(c0722p1A.X1, z1.l);
    }

    private C3224ve b(final J7 j7) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.WX$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.b(j7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        b7.getClass();
        if (b7 instanceof J7) {
            J7 j7N = b7.n();
            C0721p0 c0721p0T = j7N.T();
            if (c0721p0T.f.d.g()) {
                C0684j2 c0684j2U0 = c0721p0T.f.u0();
                C0595a2 c0595a2 = this.a.E5;
                if (c0684j2U0 == c0595a2.a) {
                    return a(j7N);
                }
                if (c0684j2U0 == c0595a2.b) {
                    return b(j7N);
                }
            }
        }
        return C3224ve.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection b(J7 j7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return a(j7, interfaceC1374Ti, ac, o5Var);
    }

    private C3224ve a(final J7 j7) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.WX$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(j7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(J7 j7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        C0767v2[] c0767v2Arr = j7.T().e.f.a;
        UX ux = new UX(this);
        for (C0767v2 c0767v2 : c0767v2Arr) {
            ux.a(new SX(interfaceC1374Ti.a(EnumC1812d20.a(c0767v2).c()), (C0684j2) this.c.getOrDefault(c0767v2, this.b.k)));
        }
        return ux.a(ac);
    }

    private Collection a(J7 j7, InterfaceC1374Ti interfaceC1374Ti, AC ac, com.android.tools.r8.graph.o5 o5Var) {
        C0721p0 c0721p0T = j7.T();
        C0800x2 c0800x2U0 = c0721p0T.e.u0();
        List list = c0721p0T.g;
        if (!list.isEmpty()) {
            int i = 0;
            com.android.tools.r8.graph.B2.j jVarK0 = ((com.android.tools.r8.graph.B2) list.get(0)).K0();
            if (jVarK0 != null) {
                String string = ((C0759u2) jVarK0.j1()).toString();
                ArrayList arrayList = new ArrayList();
                char c = 1;
                for (int i2 = 1; i2 < list.size(); i2++) {
                    arrayList.add((com.android.tools.r8.graph.B2) list.get(i2));
                }
                UX ux = new UX(this);
                StringBuilder sb = new StringBuilder();
                int length = string.length();
                Iterator it = arrayList.iterator();
                Iterator<C0767v2> it2 = c0800x2U0.iterator();
                int i3 = 0;
                while (i3 < length) {
                    char cCharAt = string.charAt(i3);
                    if (cCharAt == c) {
                        if (sb.length() > 0) {
                            ux.a(new VX((C0684j2) this.c.get(this.a.X1), this.a.c(sb.toString())));
                            sb.setLength(i);
                        }
                        if (it2.hasNext()) {
                            C0767v2 next = it2.next();
                            ux.a(new SX(interfaceC1374Ti.a(EnumC1812d20.a(next).c()), (C0684j2) this.c.getOrDefault(next, this.b.k)));
                        } else {
                            throw a(o5Var, "too many argument references in `recipe`");
                        }
                    } else if (cCharAt == 2) {
                        if (it.hasNext()) {
                            sb.append(a((com.android.tools.r8.graph.B2) it.next(), o5Var));
                        } else {
                            throw a(o5Var, "too many constant references in `recipe`");
                        }
                    } else {
                        sb.append(cCharAt);
                    }
                    i3++;
                    i = 0;
                    c = 1;
                }
                if (!it2.hasNext()) {
                    if (!it.hasNext()) {
                        if (sb.length() > 0) {
                            ux.a(new VX((C0684j2) this.c.get(this.a.X1), this.a.c(sb.toString())));
                        }
                        return ux.a(ac);
                    }
                    throw a(o5Var, "too few constant references in `recipe`, expected " + arrayList.size() + ", referenced: " + (arrayList.size() - com.android.tools.r8.utils.L1.a(it)));
                }
                throw a(o5Var, "too few argument references in `recipe`, expected " + c0800x2U0.size() + ", referenced: " + (c0800x2U0.size() - com.android.tools.r8.utils.L1.a(it2)));
            }
            throw a(o5Var, "bootstrap method argument `recipe` must be a string");
        }
        throw a(o5Var, "bootstrap method misses `recipe` argument");
    }

    private static String a(com.android.tools.r8.graph.B2 b2, com.android.tools.r8.graph.o5 o5Var) {
        if (b2.e1()) {
            return ((C0759u2) b2.K0().j1()).toString();
        }
        throw a(o5Var, "const arg referenced from `recipe` is not supported: " + b2.getClass().getName());
    }

    private static C1367Tb a(com.android.tools.r8.graph.o5 o5Var, String str) {
        return new C1367Tb("String concatenation desugaring error (method: " + o5Var.w() + "): " + str);
    }
}
