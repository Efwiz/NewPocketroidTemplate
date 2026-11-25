package com.android.tools.r8.graph;

import com.android.tools.r8.dex.code.C0410a0;
import com.android.tools.r8.dex.code.C0435f0;
import com.android.tools.r8.dex.code.C0447h2;
import com.android.tools.r8.dex.code.C0467l2;
import com.android.tools.r8.dex.code.C0488p3;
import com.android.tools.r8.dex.code.InterfaceC0419c;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2619nY;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.C1890e6;
import com.android.tools.r8.internal.C2049g6;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2145hI;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3148uf;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.internal.DE;
import com.android.tools.r8.origin.Origin;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.graph.w0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0772w0 extends W implements M2, com.android.tools.r8.utils.structural.x<C0772w0> {
    static final /* synthetic */ boolean n = true;
    public final int d;
    public final int e;
    public final int f;
    public final a[] g;
    public final b[] h;
    public final com.android.tools.r8.dex.code.B1[] i;
    public C0759u2 j;
    private J0 k;
    private M0 l;
    private final C2049g6 m;

    /* JADX INFO: renamed from: com.android.tools.r8.graph.w0$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends AbstractC0594a1 implements com.android.tools.r8.utils.structural.x<a> {
        public final int a;
        public int b;
        public int c;
        public int d = -1;
        static final /* synthetic */ boolean f = true;
        public static final a[] e = new a[0];

        public a(int i, int i2, int i3) {
            this.b = i;
            this.c = i2;
            this.a = i3;
        }

        @Override // com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        public final boolean equals(Object obj) {
            return com.android.tools.r8.utils.structural.k.a(this, obj);
        }

        public final int hashCode() {
            return (this.d * 5) + (this.c * 3) + (this.b * 2);
        }

        @Override // com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.y p() {
            return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.w0$a$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.structural.y
                public final void a(com.android.tools.r8.utils.structural.z zVar) {
                    C0772w0.a.a(zVar);
                }
            };
        }

        public final String toString() {
            return "[" + com.android.tools.r8.utils.Y2.a(this.b, 2) + " .. " + com.android.tools.r8.utils.Y2.a((this.b + this.c) - 1, 2) + "] -> " + this.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void a(com.android.tools.r8.utils.structural.z zVar) {
            zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.graph.w0$a$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((C0772w0.a) obj).b;
                }
            }).a(new ToIntFunction() { // from class: com.android.tools.r8.graph.w0$a$$ExternalSyntheticLambda2
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((C0772w0.a) obj).c;
                }
            }).a(new ToIntFunction() { // from class: com.android.tools.r8.graph.w0$a$$ExternalSyntheticLambda3
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((C0772w0.a) obj).d;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.tools.r8.graph.AbstractC0594a1
        public final void a(com.android.tools.r8.dex.V v) {
            if (!f) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.w0$b */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b extends AbstractC0594a1 implements com.android.tools.r8.utils.structural.x<b> {
        public final a[] a;
        public final int b;
        static final /* synthetic */ boolean d = true;
        public static final b[] c = new b[0];

        /* JADX INFO: renamed from: com.android.tools.r8.graph.w0$b$a */
        /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
        public static class a extends AbstractC0594a1 implements com.android.tools.r8.utils.structural.x<a> {
            static final /* synthetic */ boolean c = true;
            private final C0767v2 a;
            public final int b;

            public a(int i, C0767v2 c0767v2) {
                this.a = c0767v2;
                this.b = i;
            }

            @Override // com.android.tools.r8.utils.structural.x
            public final com.android.tools.r8.utils.structural.x U() {
                return this;
            }

            public final boolean equals(Object obj) {
                return com.android.tools.r8.utils.structural.k.a(this, obj);
            }

            public C0767v2 getType() {
                return this.a;
            }

            public final int hashCode() {
                return (this.a.hashCode() * 7) + this.b;
            }

            @Override // com.android.tools.r8.utils.structural.x
            public final com.android.tools.r8.utils.structural.y p() {
                return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.w0$b$a$$ExternalSyntheticLambda2
                    @Override // com.android.tools.r8.utils.structural.y
                    public final void a(com.android.tools.r8.utils.structural.z zVar) {
                        C0772w0.b.a.a(zVar);
                    }
                };
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static void a(com.android.tools.r8.utils.structural.z zVar) {
                zVar.c(new Function() { // from class: com.android.tools.r8.graph.w0$b$a$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((C0772w0.b.a) obj).a;
                    }
                }).a(new ToIntFunction() { // from class: com.android.tools.r8.graph.w0$b$a$$ExternalSyntheticLambda1
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        return ((C0772w0.b.a) obj).b;
                    }
                });
            }

            public final C0767v2 a(AbstractC1476Wj abstractC1476Wj) {
                return AbstractC2964s2.a(abstractC1476Wj, this.a);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.android.tools.r8.graph.AbstractC0594a1
            public final void a(com.android.tools.r8.dex.V v) {
                if (!c) {
                    throw new AssertionError();
                }
            }
        }

        public b(a[] aVarArr, int i) {
            this.a = aVarArr;
            this.b = i;
        }

        @Override // com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        public final boolean equals(Object obj) {
            return com.android.tools.r8.utils.structural.k.a(this, obj);
        }

        public final int hashCode() {
            int i = com.android.tools.r8.utils.structural.l.d;
            return com.android.tools.r8.utils.structural.l.a(this, p());
        }

        @Override // com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.y p() {
            return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.w0$b$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.structural.y
                public final void a(com.android.tools.r8.utils.structural.z zVar) {
                    C0772w0.b.a(zVar);
                }
            };
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[\n");
            for (a aVar : this.a) {
                sb.append("       ");
                sb.append(aVar.a);
                sb.append(" -> ");
                sb.append(com.android.tools.r8.utils.Y2.a(aVar.b, 2));
                sb.append("\n");
            }
            if (this.b != -1) {
                sb.append("       default -> ");
                sb.append(com.android.tools.r8.utils.Y2.a(this.b, 2));
                sb.append("\n");
            }
            sb.append("     ]");
            return sb.toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void a(com.android.tools.r8.utils.structural.z zVar) {
            zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.graph.w0$b$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((C0772w0.b) obj).b;
                }
            }).d(new Function() { // from class: com.android.tools.r8.graph.w0$b$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((C0772w0.b) obj).a;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.tools.r8.graph.AbstractC0594a1
        public final void a(com.android.tools.r8.dex.V v) {
            if (!d) {
                throw new AssertionError();
            }
        }
    }

    private C0772w0(C0772w0 c0772w0) {
        this(c0772w0.d, c0772w0.e, c0772w0.f, c0772w0.i, c0772w0.g, c0772w0.h, c0772w0.k, c0772w0.m);
        this.l = c0772w0.l;
        this.j = c0772w0.j;
    }

    @Override // com.android.tools.r8.graph.M2
    public final C0759u2 B() {
        return this.j;
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean B0() {
        return true;
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean C0() {
        return true;
    }

    @Override // com.android.tools.r8.graph.M2
    public final int D() {
        return this.f;
    }

    @Override // com.android.tools.r8.graph.M2
    public final M0 F() {
        if (this.l == null) {
            this.l = J0.a(this.k);
        }
        return this.l;
    }

    public J0 H0() {
        return this.k;
    }

    public boolean I0() {
        com.android.tools.r8.dex.code.B1[] b1Arr = this.i;
        return b1Arr.length == 1 && (b1Arr[0] instanceof C0488p3);
    }

    @Override // com.android.tools.r8.graph.W, com.android.tools.r8.graph.M2
    public final C0772w0 P() {
        return this;
    }

    public com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.M2
    public final int V() {
        com.android.tools.r8.dex.code.B1[] b1Arr = this.i;
        com.android.tools.r8.dex.code.B1 b1 = b1Arr[b1Arr.length - 1];
        boolean z = n;
        if (!z && !b1.C()) {
            throw new AssertionError();
        }
        int iZ = b1.z() + b1.u();
        if (!z) {
            int iZ2 = 0;
            for (com.android.tools.r8.dex.code.B1 b2 : this.i) {
                iZ2 += b2.z();
            }
            if (iZ != iZ2) {
                throw new AssertionError();
            }
        }
        return iZ;
    }

    @Override // com.android.tools.r8.graph.M2
    public final L2 o() {
        return L2.DEFAULT;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.w0$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0772w0.a(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.graph.M2
    public final a[] s() {
        return this.g;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final int s0() {
        int iHashCode = (Arrays.hashCode(this.i) * 7) + (this.f * 5) + (this.d * 3) + (this.e * 2);
        J0 j0 = this.k;
        return (Arrays.hashCode(this.h) * 17) + (Arrays.hashCode(this.g) * 13) + ((j0 == null ? 0 : j0.hashCode()) * 11) + iHashCode;
    }

    @Override // com.android.tools.r8.graph.M2
    public final W t() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        return a((W0) null, com.android.tools.r8.utils.P2.a());
    }

    @Override // com.android.tools.r8.graph.W
    public final M2 u0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        return V();
    }

    @Override // com.android.tools.r8.graph.W
    public final int x0() {
        return V();
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean y0() {
        for (com.android.tools.r8.dex.code.B1 b1 : this.i) {
            if (b1 instanceof C0467l2) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.graph.M2
    public final b[] z() {
        return this.h;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.graph.w0$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C0772w0) obj).d;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.graph.w0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C0772w0) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.graph.w0$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C0772w0) obj).f;
            }
        }).d(new Function() { // from class: com.android.tools.r8.graph.w0$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0772w0) obj).g;
            }
        }).d(new Function() { // from class: com.android.tools.r8.graph.w0$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0772w0) obj).h;
            }
        }).h(new Function() { // from class: com.android.tools.r8.graph.w0$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0772w0) obj).k;
            }
        }).d(new Function() { // from class: com.android.tools.r8.graph.w0$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0772w0) obj).i;
            }
        });
    }

    @Override // com.android.tools.r8.graph.M2
    public final int b(o5 o5Var) {
        return this.e;
    }

    @Override // com.android.tools.r8.graph.M2
    public final void c(o5 o5Var) {
        for (com.android.tools.r8.dex.code.B1 b1 : this.i) {
            C0721p0 c0721p0Q = b1.q();
            if (c0721p0Q != null) {
                c0721p0Q.a(b1.u(), o5Var.getReference());
            }
        }
    }

    @Override // com.android.tools.r8.graph.W
    public final void d(Consumer consumer) {
        if (H0() == null || H0().z0()) {
            return;
        }
        for (B0 b0 : H0().t0().g) {
            b0.getClass();
            if (b0 instanceof F0) {
                consumer.accept(b0.u0().x0());
            }
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final boolean c(Object obj) {
        return com.android.tools.r8.utils.structural.k.a(this, obj);
    }

    public final C0772w0 a(AbstractC1476Wj abstractC1476Wj) {
        return new C0764v0(this, abstractC1476Wj);
    }

    @Override // com.android.tools.r8.graph.W
    public final C1890e6 a(InterfaceC0419c interfaceC0419c) {
        return this.m.a(interfaceC0419c.o());
    }

    public C0772w0(int i, int i2, int i3, com.android.tools.r8.dex.code.B1[] b1Arr, a[] aVarArr, b[] bVarArr, J0 j0) {
        this(i, i2, i3, b1Arr, aVarArr, bVarArr, j0, C2049g6.a());
    }

    @Override // com.android.tools.r8.graph.M2
    public final M2 a(o5 o5Var, C0645e5 c0645e5, C0722p1 c0722p1, boolean z) {
        C0759u2 c0759u2F;
        if (z) {
            c0759u2F = c0645e5.g();
        } else {
            boolean z2 = n;
            if (!z2 && this.j == null && !Arrays.stream(this.i).noneMatch(new Predicate() { // from class: com.android.tools.r8.graph.w0$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((com.android.tools.r8.dex.code.B1) obj).F();
                }
            })) {
                throw new AssertionError();
            }
            if (!z2 && !Arrays.stream(this.i).noneMatch(new Predicate() { // from class: com.android.tools.r8.graph.w0$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((com.android.tools.r8.dex.code.B1) obj).G();
                }
            })) {
                throw new AssertionError();
            }
            C0759u2 c0759u2 = this.j;
            c0759u2F = (c0759u2 == null || !c0759u2.a(c0645e5.f())) ? null : c0645e5.f();
        }
        return c0759u2F != null ? new com.android.tools.r8.dex.T(o5Var.e(), c0759u2F, c0722p1).a() : this;
    }

    public C0772w0(int i, int i2, int i3, com.android.tools.r8.dex.code.B1[] b1Arr, a[] aVarArr, b[] bVarArr, J0 j0, C2049g6 c2049g6) {
        this.e = i2;
        this.d = i;
        this.f = i3;
        this.i = b1Arr;
        this.g = aVarArr;
        this.h = bVarArr;
        this.k = j0;
        this.m = c2049g6;
        boolean z = n;
        if (!z && aVarArr == null) {
            throw new AssertionError();
        }
        if (!z && bVarArr == null) {
            throw new AssertionError();
        }
        if (!z && b1Arr == null) {
            throw new AssertionError();
        }
        if (!z && !com.android.tools.r8.utils.E0.a(j0)) {
            throw new AssertionError();
        }
        hashCode();
    }

    public void a(J0 j0) {
        this.k = j0;
        if (this.l != null) {
            this.l = null;
        }
    }

    public final J0.a a(C0722p1 c0722p1) {
        J0.a aVarA = J0.a(this, c0722p1);
        if (aVarA == null) {
            return aVarA;
        }
        int iMax = 0;
        for (C0759u2 c0759u2 : aVarA.f) {
            iMax = Integer.max(iMax, a(c0722p1, c0759u2));
        }
        for (B0 b0 : aVarA.g) {
            if (b0 instanceof B0.d) {
                iMax = Integer.max(iMax, a(c0722p1, ((B0.d) b0).d));
            }
        }
        String str = AbstractC2619nY.a(iMax + 1, "_") + "this";
        C0759u2[] c0759u2Arr = aVarA.f;
        C0759u2[] c0759u2Arr2 = new C0759u2[c0759u2Arr.length + 1];
        c0759u2Arr2[0] = c0722p1.c(str);
        System.arraycopy(c0759u2Arr, 0, c0759u2Arr2, 1, c0759u2Arr.length);
        return new J0.a(aVarA.e, c0759u2Arr2, aVarA.g);
    }

    @Override // com.android.tools.r8.graph.W
    public final W a(C0722p1 c0722p1, C0684j2 c0684j2, C0684j2 c0684j3, boolean z) {
        J0.a aVar;
        int i = this.d;
        int i2 = this.e;
        int i3 = this.f;
        com.android.tools.r8.dex.code.B1[] b1Arr = this.i;
        a[] aVarArr = this.g;
        b[] bVarArr = this.h;
        AbstractC1915eM.c cVarD = ((AbstractC1915eM.c.a) AbstractC1915eM.c.x().a(0).a(c0684j2).a(true)).a();
        J0.a aVarA = J0.a(this, c0722p1);
        if (aVarA == null) {
            if (!z) {
                cVarD = ((AbstractC1915eM.c.a) AbstractC1915eM.c.x().a(c0684j3).a(cVarD)).a(0).a();
            }
            bVarArr = bVarArr;
            aVar = new J0.a(0, new C0759u2[c0684j3.F0()], new B0[]{c0722p1.a(cVarD), c0722p1.r});
        } else {
            com.android.tools.r8.utils.D0 d0A = com.android.tools.r8.utils.E0.a(c0684j3, aVarA);
            B0[] b0Arr = aVarA.g;
            int i4 = (d0A.d() || this.k.x0() <= 0) ? 0 : 1;
            ArrayList arrayList = new ArrayList(b0Arr.length + (!d0A.c() ? 1 : 0) + (!d0A.d() ? 1 : 0) + i4);
            if (d0A.c()) {
                i4 = i4;
            } else {
                arrayList.add(c0722p1.a(W.a(cVarD, (z ? AbstractC1915eM.c.x() : AbstractC1915eM.b.x()).a(d0A.d() ? d0A.b() : 0).a(c0684j3).a(z).a(), z)));
            }
            if (!d0A.d()) {
                arrayList.add(c0722p1.r);
            }
            int length = b0Arr.length;
            int i5 = i4;
            int i6 = 0;
            while (i6 < length) {
                int i7 = length;
                B0 b0 = b0Arr[i6];
                b0.getClass();
                B0[] b0Arr2 = b0Arr;
                if ((b0 instanceof A0) && i5 != 0) {
                    arrayList.add(c0722p1.a(this.k.x0() + b0.s0().c));
                } else {
                    if ((b0 instanceof B0.b) && i5 != 0) {
                        B0.b bVarT0 = b0.t0();
                        H0.a(this.k.x0() + bVarT0.x0(), bVarT0.y0(), arrayList, c0722p1);
                    } else if (b0 instanceof F0) {
                        F0 f0U0 = b0.u0();
                        if (!n && f0U0.x0() == null) {
                            throw new AssertionError();
                        }
                        arrayList.add(new F0(W.a(cVarD, f0U0.x0(), z)));
                    } else {
                        arrayList.add(b0);
                    }
                    i6++;
                    length = i7;
                    b0Arr = b0Arr2;
                }
                i5 = 0;
                i6++;
                length = i7;
                b0Arr = b0Arr2;
            }
            if (i5 != 0) {
                arrayList.add(c0722p1.a(this.k.x0()));
            }
            aVar = new J0.a(d0A.d() ? aVarA.e : 0, aVarA.f, (B0[]) arrayList.toArray(B0.a));
        }
        return new C0772w0(i, i2, i3, b1Arr, aVarArr, bVarArr, aVar);
    }

    public static int a(C0722p1 c0722p1, C0759u2 c0759u2) {
        if (c0759u2 != null && c0759u2.b(c0722p1.c1)) {
            String string = c0759u2.toString();
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) != '_') {
                    return i;
                }
            }
        }
        return 0;
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, p());
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        return C2102gl.a(o5Var, c0702m, new C3148uf(this, o5Var, c0702m.E().a((AbstractC1476Wj) null, o5Var.getReference()), null, c0702m.a()), origin).a(o5Var, aVar);
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, o5 o5Var2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, C2145hI c2145hI, AbstractC1915eM abstractC1915eM, Origin origin, com.android.tools.r8.graph.proto.k kVar) {
        return C2102gl.a(c0702m, o5Var2, abstractC1476Wj, kVar, c2145hI, new C3148uf(this, o5Var2, c0702m.E().a((AbstractC1476Wj) null, o5Var2.getReference()), abstractC1915eM, c0702m.a()), origin).a(o5Var, new DE(c0702m.Y()));
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(o5 o5Var, J5 j5) {
        a(j5);
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(S s, J5 j5) {
        a(j5);
    }

    private void a(J5 j5) {
        if (!n && !j5.e().g()) {
            throw new AssertionError();
        }
        for (com.android.tools.r8.dex.code.B1 b1 : this.i) {
            b1.a(j5);
            if (j5.e().f()) {
                return;
            }
        }
        for (b bVar : this.h) {
            for (b.a aVar : bVar.a) {
                j5.a(aVar.a);
                if (j5.e().f()) {
                    return;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x00ae A[PHI: r6
      0x00ae: PHI (r6v6 java.util.Iterator<com.android.tools.r8.graph.x0>) = 
      (r6v5 java.util.Iterator<com.android.tools.r8.graph.x0>)
      (r6v5 java.util.Iterator<com.android.tools.r8.graph.x0>)
      (r6v5 java.util.Iterator<com.android.tools.r8.graph.x0>)
      (r6v10 java.util.Iterator<com.android.tools.r8.graph.x0>)
     binds: [B:17:0x0085, B:19:0x008b, B:20:0x008d, B:22:0x00a5] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.android.tools.r8.graph.W
    public final String a(W0 w0, com.android.tools.r8.utils.P2 p2) {
        C0797x0 next;
        com.android.tools.r8.dex.code.B1[] b1Arr;
        StringBuilder sb = new StringBuilder();
        if (w0 != null) {
            sb.append(p2.c(w0.getReference())).append("\n");
        }
        sb.append("registers: ").append(this.d);
        sb.append(", inputs: ").append(this.e);
        sb.append(", outputs: ").append(this.f).append("\n------------------------------------------------------------\ninst#  offset  instruction         arguments\n------------------------------------------------------------\n");
        HashMap map = new HashMap();
        for (com.android.tools.r8.dex.code.B1 b1 : this.i) {
            if (b1.D()) {
                map.put(Integer.valueOf(b1.x() + b1.u()), b1);
            }
        }
        Iterator<C0797x0> itEmptyIterator = Collections.emptyIterator();
        boolean z = H0() != null && H0().z0();
        if (z || H0() == null || w0 == null) {
            next = null;
        } else {
            itEmptyIterator = new C0814z0(w0, new C0722p1()).a().iterator();
            if (itEmptyIterator.hasNext()) {
                next = itEmptyIterator.next();
            } else {
                next = null;
            }
        }
        Map<Integer, X> mapEmptyMap = Collections.emptyMap();
        com.android.tools.r8.dex.code.B1[] b1Arr2 = this.i;
        int length = b1Arr2.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            com.android.tools.r8.dex.code.B1 b2 = b1Arr2[i];
            C0797x0 c0797x0 = next;
            int iU = b2.u() - 1;
            C0797x0 next2 = c0797x0;
            while (true) {
                mapEmptyMap = mapEmptyMap;
                if (next2 == null || next2.b > iU) {
                    break;
                }
                next2 = itEmptyIterator.hasNext() ? itEmptyIterator.next() : null;
            }
            next = next2;
            mapEmptyMap = mapEmptyMap;
            while (true) {
                if (next == null) {
                    b1Arr = b1Arr2;
                    break;
                }
                b1Arr = b1Arr2;
                if (next.b != b2.u()) {
                    break;
                }
                if (next.a || !mapEmptyMap.equals(next.f)) {
                    sb.append("         ").append(next.a(false)).append("\n");
                }
                mapEmptyMap = next.f;
                next = itEmptyIterator.hasNext() ? itEmptyIterator.next() : null;
                b1Arr2 = b1Arr;
            }
            int i3 = i2 + 1;
            com.android.tools.r8.utils.Y2.a(sb, Integer.toString(i2), 5);
            sb.append(": ");
            if (b2.M()) {
                sb.append(b2.a(p2, (com.android.tools.r8.dex.code.B1) map.get(Integer.valueOf(b2.u()))));
            } else {
                sb.append(b2.b(p2));
            }
            sb.append('\n');
            i++;
            i2 = i3;
            b1Arr2 = b1Arr;
        }
        C0797x0 c0797x1 = next;
        if (z) {
            sb.append(H0()).append("\n");
        } else if (itEmptyIterator.hasNext()) {
            int iU2 = ((com.android.tools.r8.dex.code.B1) com.android.tools.r8.utils.B.b(this.i)).u();
            C0797x0 next3 = c0797x1;
            while (next3 != null && next3.b <= iU2) {
                next3 = itEmptyIterator.hasNext() ? itEmptyIterator.next() : null;
            }
            if (next3 != null) {
                sb.append("(warning: has unhandled debug events @ pc:").append(next3.b).append(", line:").append(next3.b().e());
            } else {
                sb.append("(has debug events past last pc)\n");
            }
        }
        if (this.g.length > 0) {
            sb.append("Tries (numbers are offsets)\n");
            for (a aVar : this.g) {
                sb.append("  ");
                sb.append(aVar.toString());
                sb.append('\n');
            }
            sb.append("Handlers (numbers are offsets)\n");
            int i4 = 0;
            while (true) {
                b[] bVarArr = this.h;
                if (i4 >= bVarArr.length) {
                    break;
                }
                b bVar = bVarArr[i4];
                sb.append("  ").append(i4).append(": ");
                sb.append(bVar.toString());
                sb.append('\n');
                i4++;
            }
        }
        return sb.toString();
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        this.j = null;
        for (com.android.tools.r8.dex.code.B1 b1 : this.i) {
            boolean z = n;
            if (!z) {
                b1.getClass();
                if (b1 instanceof C0447h2) {
                    throw new AssertionError();
                }
            }
            b1.a(c0702m, l, c2363kB, o5Var);
            if (b1.F()) {
                C0759u2 c0759u2N = b1.d().N();
                if (!z && c0759u2N == null) {
                    throw new AssertionError();
                }
                C0759u2 c0759u2 = this.j;
                if (c0759u2 == null || c0759u2.a(c0759u2N) < 0) {
                    this.j = c0759u2N;
                }
            } else if (b1 instanceof C0435f0) {
                C0759u2 c0759u2N2 = b1.e().N();
                if (!z && c0759u2N2 == null) {
                    throw new AssertionError();
                }
                C0759u2 c0759u3 = this.j;
                if (c0759u3 == null || c0759u3.a(c0759u2N2) < 0) {
                    this.j = c0759u2N2;
                }
            } else {
                continue;
            }
        }
        if (this.k != null) {
            F().a(c0702m, l);
        }
        for (b bVar : this.h) {
            for (b.a aVar : bVar.a) {
                aVar.getClass();
                C0767v2 c0767v2A = aVar.a(c0702m.E());
                c0767v2A.getClass();
                if (l.a(c0767v2A)) {
                    C0759u2 c0759u2C = c0702m.z().c(c0767v2A);
                    c0759u2C.getClass();
                    l.a(c0759u2C);
                }
            }
        }
    }

    @Override // com.android.tools.r8.graph.M2
    public final int a(o5 o5Var) {
        return this.d;
    }

    @Override // com.android.tools.r8.graph.W, com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        if (this.k != null) {
            F().a(v);
        }
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(com.android.tools.r8.dex.r rVar) {
        for (com.android.tools.r8.dex.code.B1 b1 : this.i) {
            C0684j2 c0684j2S = b1.s();
            Y0 field = b1.getField();
            if (field != null) {
                if (!n && c0684j2S != null) {
                    throw new AssertionError();
                }
                rVar.a(field);
            } else if (c0684j2S != null) {
                rVar.a(c0684j2S);
            } else if (b1 instanceof C0410a0) {
                rVar.a(b1.c().N());
            } else if (b1 instanceof com.android.tools.r8.dex.code.A1) {
                rVar.a(b1.i().N());
            } else if (b1.E()) {
                rVar.a(b1.b().N());
            }
        }
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        for (com.android.tools.r8.dex.code.B1 b1 : this.i) {
            b1.a(c0645e5, o5Var, abstractC1476Wj, abstractC1476Wj2, c2363kB, shortBuffer);
        }
    }
}
