package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AR;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2331jn;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C3505zR;
import com.android.tools.r8.internal.YJ;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.graph.e5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0645e5 {
    static final /* synthetic */ boolean p = true;
    private final int a;
    private final C0702m b;
    private final AbstractC1476Wj c;
    private final AbstractC2331jn d;
    private final C2363kB e;
    private final C0729q2[] f;
    private final C3505zR g;
    private final C3505zR h;
    private final C3505zR i;
    private final C3505zR j;
    private C3505zR k;
    private final C3505zR l;
    private final C3505zR m;
    private C0759u2 n;
    private final com.android.tools.r8.utils.structural.a o;

    public C0645e5(C0702m<?> c0702m, C0645e5 c0645e5, C2363kB c2363kB, Collection<C0729q2> collection, Collection<C0737r2> collection2, Collection<C0767v2> collection3, Collection<C0684j2> collection4, Collection<Y0> collection5, Collection<C0759u2> collection6, Collection<C0721p0> collection7, Collection<C0699l2> collection8, int i, com.android.tools.r8.utils.y3 y3Var) {
        boolean z = p;
        if (!z && c0702m == null) {
            throw new AssertionError();
        }
        if (!z && collection == null) {
            throw new AssertionError();
        }
        if (!z && collection2 == null) {
            throw new AssertionError();
        }
        if (!z && collection3 == null) {
            throw new AssertionError();
        }
        if (!z && collection4 == null) {
            throw new AssertionError();
        }
        if (!z && collection5 == null) {
            throw new AssertionError();
        }
        if (!z && collection6 == null) {
            throw new AssertionError();
        }
        if (!z && collection7 == null) {
            throw new AssertionError();
        }
        if (!z && collection8 == null) {
            throw new AssertionError();
        }
        this.a = i;
        this.b = c0702m;
        this.c = c0702m.E();
        AbstractC3649o0 abstractC3649o0Z = c0702m.z();
        this.d = c0702m.N();
        this.e = c2363kB;
        y3Var.a("Sort strings");
        if (c0645e5 == null) {
            this.k = a(collection6, new e5$$ExternalSyntheticLambda4(), new Consumer() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((C0759u2) obj);
                }
            }, i);
        } else {
            this.k = c0645e5.k;
            this.n = c0645e5.n;
        }
        final C3505zR c3505zR = this.k;
        Objects.requireNonNull(c3505zR);
        com.android.tools.r8.utils.structural.e eVar = new com.android.tools.r8.utils.structural.e(abstractC3649o0Z, new ToIntFunction() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda7
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return c3505zR.a((C0759u2) obj);
            }
        });
        y3Var.c();
        y3Var.a("Sort types");
        final C3505zR c3505zRA = a(collection3, a(eVar), new Consumer() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0594a1) obj);
            }
        });
        this.h = c3505zRA;
        final C3505zR c3505zR2 = this.k;
        Objects.requireNonNull(c3505zR2);
        com.android.tools.r8.utils.structural.g gVar = new com.android.tools.r8.utils.structural.g(abstractC3649o0Z, new ToIntFunction() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda7
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return c3505zR2.a((C0759u2) obj);
            }
        }, new ToIntFunction() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda8
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return c3505zRA.a((C0767v2) obj);
            }
        });
        y3Var.c();
        y3Var.a("Sort classes");
        this.f = a(collection, gVar);
        y3Var.c();
        y3Var.a("Sort protos");
        this.g = a(collection2, a(gVar), new Consumer() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0594a1) obj);
            }
        });
        y3Var.c();
        y3Var.a("Sort methods");
        this.i = a(collection4, a(gVar), new Consumer() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0594a1) obj);
            }
        });
        y3Var.c();
        y3Var.a("Sort fields");
        this.j = a(collection5, a(gVar), new Consumer() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0594a1) obj);
            }
        });
        y3Var.c();
        y3Var.a("Sort call-sites");
        this.l = a(collection7, a(gVar), new Consumer() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0594a1) obj);
            }
        });
        y3Var.c();
        y3Var.a("Sort method handles");
        this.m = a(collection8, a(gVar), new Consumer() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0594a1) obj);
            }
        });
        y3Var.c();
        final C3505zR c3505zR3 = this.k;
        Objects.requireNonNull(c3505zR3);
        this.o = new C0631c5(abstractC3649o0Z, new ToIntFunction() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda7
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return c3505zR3.a((C0759u2) obj);
            }
        }, new ToIntFunction() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda8
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return c3505zRA.a((C0767v2) obj);
            }
        }, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0759u2 c0759u2) {
        if (!p && this.n != null) {
            throw new AssertionError();
        }
        this.n = c0759u2;
    }

    public final void a(List list) {
        if (!p && this.a != list.size()) {
            throw new AssertionError();
        }
        if (list.isEmpty()) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C0759u2 c0759u2 = (C0759u2) it.next();
            if (c0759u2 != null) {
                this.k.a(-1, c0759u2);
            }
        }
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        this.k = a(this.k.keySet(), new e5$$ExternalSyntheticLambda4(), new Consumer() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                n.a((C0759u2) obj);
            }
        }, 0);
        if (!p && o() && !((C0759u2) n.a()).a(this.n)) {
            throw new AssertionError();
        }
    }

    public final C0729q2[] c() {
        return this.f;
    }

    public final com.android.tools.r8.utils.structural.a d() {
        return this.o;
    }

    public final Collection e() {
        return a(this.j);
    }

    public final C0759u2 f() {
        return this.n;
    }

    public final C0759u2 g() {
        YJ it = this.k.f().iterator();
        while (it.hasNext()) {
            AR ar2 = (AR) it.next();
            if (ar2.getIntValue() == 0) {
                return (C0759u2) ar2.getKey();
            }
        }
        return null;
    }

    public final AbstractC1476Wj h() {
        return this.c;
    }

    public final C2363kB i() {
        return this.e;
    }

    public final Collection j() {
        return a(this.m);
    }

    public final Collection k() {
        return a(this.i);
    }

    public final Collection l() {
        return a(this.g);
    }

    public final Collection m() {
        return a(this.k);
    }

    public final Collection n() {
        return a(this.h);
    }

    public final boolean o() {
        return this.n != null;
    }

    public final Collection b() {
        return a(this.l);
    }

    public final int b(C0767v2 c0767v2) {
        return a(c0767v2, this.h);
    }

    private static Comparator a(final com.android.tools.r8.utils.structural.e eVar) {
        return new Comparator() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return C0645e5.a(eVar, (com.android.tools.r8.utils.structural.x) obj, (com.android.tools.r8.utils.structural.x) obj2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.x xVar2) {
        return xVar.a(xVar2, aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0594a1 abstractC0594a1) {
        throw new C1367Tb("Index overflow for " + abstractC0594a1.getClass());
    }

    private static C3505zR a(Collection collection, Comparator comparator, Consumer consumer) {
        return a(collection, comparator, consumer, 0);
    }

    private static C3505zR a(Collection collection, Comparator comparator, Consumer consumer, int i) {
        if (collection.isEmpty()) {
            return new C3505zR(16);
        }
        ArrayList arrayList = new ArrayList(collection);
        arrayList.sort(comparator);
        C3505zR c3505zR = new C3505zR(collection.size());
        c3505zR.e(-1);
        int i2 = 0;
        for (Object obj : arrayList) {
            if (i2 + i == 65536) {
                consumer.accept(obj);
            }
            c3505zR.a(i2, obj);
            i2++;
        }
        return c3505zR;
    }

    private C0729q2[] a(Collection collection, final com.android.tools.r8.utils.structural.g gVar) {
        final C0638d5 c0638d5 = new C0638d5(this.b.f());
        C0729q2[] c0729q2Arr = (C0729q2[]) collection.toArray(C0729q2.A);
        Arrays.sort(c0729q2Arr, new Comparator() { // from class: com.android.tools.r8.graph.e5$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return C0645e5.a(c0638d5, gVar, (C0729q2) obj, (C0729q2) obj2);
            }
        });
        return c0729q2Arr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C0638d5 c0638d5, com.android.tools.r8.utils.structural.a aVar, C0729q2 c0729q2, C0729q2 c0729q3) {
        int iA = c0638d5.a(c0729q2);
        int iA2 = c0638d5.a(c0729q3);
        return iA != iA2 ? iA - iA2 : aVar.a(c0729q2.d, c0729q3.d);
    }

    private static Collection a(C3505zR c3505zR) {
        return c3505zR == null ? Collections.emptyList() : c3505zR.keySet();
    }

    public final C0722p1 a() {
        return this.b.a();
    }

    private static int a(I3 i3, C3505zR c3505zR) {
        int iA = c3505zR.a(i3);
        if (p || iA != -1) {
            return iA;
        }
        throw new AssertionError("Missing dependency: " + i3);
    }

    public final int a(C0737r2 c0737r2) {
        return a(c0737r2, this.g);
    }

    public final int a(Y0 y0) {
        return a(y0, this.j);
    }

    public final int a(C0684j2 c0684j2) {
        return a(c0684j2, this.i);
    }

    public final int a(C0759u2 c0759u2) {
        return a(c0759u2, this.k);
    }

    public final int a(C0721p0 c0721p0) {
        return a(c0721p0, this.l);
    }

    public final int a(C0699l2 c0699l2) {
        return a(c0699l2, this.m);
    }

    public final Y0 a(C0767v2 c0767v2) {
        return this.d.a(c0767v2);
    }
}
