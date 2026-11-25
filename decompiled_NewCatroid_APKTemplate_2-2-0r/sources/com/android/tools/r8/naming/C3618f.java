package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J3;
import com.android.tools.r8.graph.O2;
import com.android.tools.r8.graph.p5;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.internal.NN$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.RD;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.y3;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.naming.f, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C3618f {
    static final /* synthetic */ boolean k = true;
    private final C0702m a;
    private final InterfaceC3612c b;
    private final Iterable c;
    private final HashSet d;
    private final IdentityHashMap e;
    private final HashMap f;
    private final boolean g;
    private final C3616e h;
    private final boolean i;
    private final Predicate j;

    C3618f(C0702m c0702m, InterfaceC3612c interfaceC3612c, Collection collection) {
        HashSet hashSetB = OU.b();
        this.d = hashSetB;
        this.e = RD.b();
        HashMap map = new HashMap();
        this.f = map;
        this.a = c0702m;
        this.b = interfaceC3612c;
        this.c = collection;
        A1 a1Y = c0702m.Y();
        this.g = a1Y.r1();
        C3616e c3616e = new C3616e(this, "");
        this.h = c3616e;
        map.put("", c3616e);
        if (a1Y.D0().y()) {
            this.i = false;
            this.j = new Predicate() { // from class: com.android.tools.r8.naming.f$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.c((String) obj);
                }
            };
        } else {
            this.i = true;
            this.j = new NN$$ExternalSyntheticLambda0(hashSetB);
        }
    }

    private C3616e b(C0767v2 c0767v2) {
        return (C3616e) this.f.computeIfAbsent(C3970u0.o(c0767v2.H0()), new Function() { // from class: com.android.tools.r8.naming.f$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean c(String str) {
        return this.d.contains(str.toLowerCase());
    }

    private void d(C0767v2 c0767v2) {
        if (!((C3733i) this.a.f()).p(c0767v2) || this.e.containsKey(c0767v2)) {
            return;
        }
        if (!k && this.a.c(c0767v2) != null) {
            throw new AssertionError();
        }
        C0759u2 c0759u2B = this.b.b(c0767v2);
        IdentityHashMap identityHashMap = this.e;
        if (c0759u2B == null) {
            C3616e c3616e = this.h;
            C3618f c3618f = c3616e.e;
            C0759u2 c0759u2A = c3618f.b.a(c0767v2, c3616e.b, c3616e, c3618f.j);
            if (!C3616e.f && c3616e.e.d.contains(c0759u2A.toString())) {
                throw new AssertionError();
            }
            C3618f c3618f2 = c3616e.e;
            String string = c0759u2A.toString();
            HashSet hashSet = c3618f2.d;
            if (!c3618f2.i) {
                string = string.toLowerCase();
            }
            hashSet.add(string);
            c0759u2B = c0759u2A;
        }
        identityHashMap.put(c0767v2, c0759u2B);
    }

    final C3614d a(y3 y3Var) {
        y3Var.a("reserve");
        for (p5 p5Var : this.c) {
            C0759u2 c0759u2B = this.b.b(p5Var.getType());
            if (c0759u2B != null) {
                if (!k && this.e.containsKey(p5Var.getType())) {
                    throw new AssertionError();
                }
                a(c0759u2B, p5Var.getType());
            }
        }
        ((C3733i) this.a.f()).q().a(new Consumer() { // from class: com.android.tools.r8.naming.f$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((C0767v2) obj);
            }
        });
        y3Var.c();
        y3Var.a("rename-classes");
        for (com.android.tools.r8.graph.F f : this.c) {
            if (!this.e.containsKey(f.getType())) {
                C0759u2 c0759u2A = a(f.getType());
                this.e.put(f.getType(), c0759u2A);
                if (!k) {
                    a((AbstractC0727q0) f, c0759u2A);
                }
            }
        }
        y3Var.c();
        y3Var.a("rename-dangling-types");
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            a((p5) it.next());
        }
        y3Var.c();
        Map mapUnmodifiableMap = Collections.unmodifiableMap(this.e);
        C1946em c1946emE = AbstractC2178hm.e();
        for (Map.Entry entry : this.f.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = ((C3616e) entry.getValue()).a;
            if (!str2.equals(str)) {
                c1946emE.a(str, str2);
            }
        }
        return new C3614d(mapUnmodifiableMap, c1946emE.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(C0767v2 c0767v2) {
        a(c0767v2.D0(), c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C3616e b(String str) {
        return new C3616e(this, str);
    }

    private void a(AbstractC0727q0 abstractC0727q0, C0759u2 c0759u2) {
        if (!k && this.g && abstractC0727q0.F1() && abstractC0727q0.getType().E0().contains(String.valueOf('$')) && !c0759u2.toString().contains(String.valueOf('$')) && !this.b.a(abstractC0727q0.getType())) {
            throw new AssertionError(abstractC0727q0 + " -> " + c0759u2);
        }
    }

    private void a(p5 p5Var) {
        p5Var.c(new Consumer() { // from class: com.android.tools.r8.naming.f$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0748t0) obj);
            }
        });
        p5Var.a(new Consumer() { // from class: com.android.tools.r8.naming.f$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0735r0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0735r0 abstractC0735r0) {
        d(abstractC0735r0.getReference().h);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0748t0 abstractC0748t0) {
        C0737r2 c0737r2 = abstractC0748t0.getReference().h;
        d(c0737r2.e);
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            d(c0767v2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0062  */
    private void a(C0759u2 c0759u2, C0767v2 c0767v2) {
        C0767v2 c0767v2D;
        J3 j3D1;
        AbstractC0727q0 abstractC0727q0C;
        this.e.put(c0767v2, c0759u2);
        String string = c0759u2.toString();
        HashSet hashSet = this.d;
        if (!this.i) {
            string = string.toLowerCase();
        }
        hashSet.add(string);
        if (this.g) {
            AbstractC0727q0 abstractC0727q0C2 = this.a.c(c0767v2);
            if (abstractC0727q0C2 == null || (j3D1 = abstractC0727q0C2.d1()) == null) {
                c0767v2D = null;
            } else {
                C0702m c0702m = this.a;
                c0767v2D = j3D1.d();
                if (c0767v2D == null && (abstractC0727q0C = c0702m.c(j3D1.b())) != null && abstractC0727q0C.b1() != null) {
                    O2 o2B1 = abstractC0727q0C.b1();
                    if (o2B1.a() != null) {
                        c0767v2D = o2B1.a();
                    } else {
                        C0684j2 c0684j2B = o2B1.b();
                        if (((C3733i) c0702m.f()).k(c0684j2B)) {
                            c0767v2D = c0684j2B.e;
                        } else {
                            c0767v2D = null;
                        }
                    }
                }
            }
            if (c0767v2D == null || this.e.containsKey(c0767v2D) || this.b.b(c0767v2D) != null) {
                return;
            }
            a(c0767v2D.e, c0767v2D);
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004c  */
    private C0759u2 a(C0767v2 c0767v2) {
        C0767v2 c0767v2D;
        J3 j3D1;
        AbstractC0727q0 abstractC0727q0C;
        C3616e c3616eB = null;
        if (this.g) {
            AbstractC0727q0 abstractC0727q0C2 = this.a.c(c0767v2);
            if (abstractC0727q0C2 == null || (j3D1 = abstractC0727q0C2.d1()) == null) {
                c0767v2D = null;
            } else {
                C0702m c0702m = this.a;
                c0767v2D = j3D1.d();
                if (c0767v2D == null && (abstractC0727q0C = c0702m.c(j3D1.b())) != null && abstractC0727q0C.b1() != null) {
                    O2 o2B1 = abstractC0727q0C.b1();
                    if (o2B1.a() != null) {
                        c0767v2D = o2B1.a();
                    } else {
                        C0684j2 c0684j2B = o2B1.b();
                        if (((C3733i) c0702m.f()).k(c0684j2B)) {
                            c0767v2D = c0684j2B.e;
                        } else {
                            c0767v2D = null;
                        }
                    }
                }
            }
            if (c0767v2D != null) {
                AbstractC0727q0 abstractC0727q0C3 = this.a.c(c0767v2);
                boolean z = k;
                if (!z && abstractC0727q0C3 == null) {
                    throw new AssertionError();
                }
                J3 j3D2 = abstractC0727q0C3.d1();
                if (!z && j3D2 == null) {
                    throw new AssertionError();
                }
                String strA = C3970u0.a(j3D2.d(), c0767v2, j3D2.c());
                if (strA == null) {
                    strA = String.valueOf('$');
                }
                String strI = C3970u0.i(c0767v2D.b1());
                C3616e c3616e = (C3616e) this.f.get(strI);
                if (c3616e == null) {
                    C0759u2 c0759u2A = (C0759u2) this.e.get(c0767v2D);
                    if (c0759u2A == null) {
                        c0759u2A = a(c0767v2D);
                        this.e.put(c0767v2D, c0759u2A);
                    }
                    C3616e c3616e2 = new C3616e(this, C3970u0.i(c0759u2A.toString()), strA);
                    this.f.put(strI, c3616e2);
                    c3616eB = c3616e2;
                } else {
                    c3616eB = c3616e;
                }
            }
        }
        if (c3616eB == null) {
            c3616eB = b(c0767v2);
        }
        C3618f c3618f = c3616eB.e;
        C0759u2 c0759u2A2 = c3618f.b.a(c0767v2, c3616eB.b, c3616eB, c3618f.j);
        if (!C3616e.f && c3616eB.e.d.contains(c0759u2A2.toString())) {
            throw new AssertionError();
        }
        C3618f c3618f2 = c3616eB.e;
        String string = c0759u2A2.toString();
        HashSet hashSet = c3618f2.d;
        if (!c3618f2.i) {
            string = string.toLowerCase();
        }
        hashSet.add(string);
        return c0759u2A2;
    }

    static String a(String str) {
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf < 0) {
            return "";
        }
        return str.substring(0, iLastIndexOf);
    }
}
