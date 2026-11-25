package com.android.tools.r8.graph;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.internal.C1525Yd;
import com.android.tools.r8.internal.C3405y10;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class J0 extends AbstractC0740s implements com.android.tools.r8.utils.structural.x<J0> {
    static final /* synthetic */ boolean c = true;
    public static final /* synthetic */ int d = 0;

    @Override // com.android.tools.r8.utils.structural.x
    /* JADX INFO: renamed from: A0, reason: merged with bridge method [inline-methods] */
    public abstract J0 U();

    abstract int a(J0 j0, com.android.tools.r8.utils.structural.a aVar);

    @Override // com.android.tools.r8.utils.structural.x
    public int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
        J0 j0 = (J0) xVar;
        int iA = aVar.a(AbstractC0403b.b(v0()), AbstractC0403b.b(j0.v0()));
        return iA != 0 ? iA : a(j0, aVar);
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        return com.android.tools.r8.utils.structural.k.a(this, obj);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        throw new C3405y10();
    }

    public a t0() {
        return null;
    }

    public K0 u0() {
        return null;
    }

    abstract int v0();

    public abstract int w0();

    public abstract int x0();

    public boolean y0() {
        return v0() == 1;
    }

    public boolean z0() {
        return v0() == 2;
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends J0 {
        static final /* synthetic */ boolean h = true;
        public final int e;
        public final C0759u2[] f;
        public B0[] g;

        public a(int i, C0759u2[] c0759u2Arr, B0[] b0Arr) {
            if (!h && i < 0) {
                throw new AssertionError();
            }
            this.e = i;
            this.f = c0759u2Arr;
            this.g = b0Arr;
        }

        @Override // com.android.tools.r8.graph.J0
        /* JADX INFO: renamed from: A0 */
        public final J0 U() {
            return this;
        }

        @Override // com.android.tools.r8.graph.J0, com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        @Override // com.android.tools.r8.graph.J0, com.android.tools.r8.utils.structural.x
        public final int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
            J0 j0 = (J0) xVar;
            int iA = aVar.a(AbstractC0403b.b(1), AbstractC0403b.b(j0.v0()));
            return iA != 0 ? iA : a(j0, aVar);
        }

        @Override // com.android.tools.r8.graph.AbstractC0740s
        public final int s0() {
            return (Arrays.hashCode(this.g) * 13) + (Arrays.hashCode(this.f) * 7) + this.e;
        }

        @Override // com.android.tools.r8.graph.J0
        public final a t0() {
            return this;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("DebugInfo (line " + this.e + ") events: [\n");
            for (B0 b0 : this.g) {
                sb.append("  ").append(b0).append("\n");
            }
            sb.append("  END_SEQUENCE\n]\n");
            return sb.toString();
        }

        @Override // com.android.tools.r8.graph.J0
        final int v0() {
            return 1;
        }

        @Override // com.android.tools.r8.graph.J0
        public final int w0() {
            return this.f.length;
        }

        @Override // com.android.tools.r8.graph.J0
        public final int x0() {
            return this.e;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void a(com.android.tools.r8.utils.structural.z zVar) {
            zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.graph.J0$a$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((J0.a) obj).e;
                }
            }).e(new Function() { // from class: com.android.tools.r8.graph.J0$a$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((J0.a) obj).f;
                }
            }).d(new Function() { // from class: com.android.tools.r8.graph.J0$a$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((J0.a) obj).g;
                }
            });
        }

        public final List a(C0684j2 c0684j2) {
            C0814z0 c0814z0 = new C0814z0(this.e, c0684j2);
            for (B0 b0 : this.g) {
                b0.a(c0814z0);
            }
            return c0814z0.a();
        }

        @Override // com.android.tools.r8.utils.structural.x
        public final void a(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a(this, new J0$a$$ExternalSyntheticLambda0());
        }

        @Override // com.android.tools.r8.graph.J0
        final int a(J0 j0, com.android.tools.r8.utils.structural.a aVar) {
            if (h || j0.y0()) {
                return aVar.a(this, j0.t0(), new J0$a$$ExternalSyntheticLambda0());
            }
            throw new AssertionError();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.tools.r8.graph.AbstractC0594a1
        public void a(com.android.tools.r8.dex.V v) {
            throw new C3405y10();
        }
    }

    public static a a(C0772w0 c0772w0, C0722p1 c0722p1) {
        if (c0772w0.H0() == null) {
            return null;
        }
        if (c0772w0.H0().y0()) {
            return c0772w0.H0().t0();
        }
        boolean z = c;
        if (!z && !c0772w0.H0().z0()) {
            throw new AssertionError();
        }
        K0 k0U0 = c0772w0.H0().u0();
        if (!z) {
            C1525Yd.a(c0772w0, k0U0.f);
        }
        com.android.tools.r8.dex.code.B1[] b1Arr = c0772w0.i;
        ArrayList arrayList = new ArrayList(b1Arr.length);
        int iZ = 0;
        for (com.android.tools.r8.dex.code.B1 b1 : b1Arr) {
            if (b1.l()) {
                H0.a(iZ, iZ, arrayList, c0722p1);
                iZ = 0;
            }
            iZ += b1.z();
        }
        return new a(1, new C0759u2[k0U0.e], (B0[]) arrayList.toArray(B0.a));
    }

    public static M0 a(J0 j0) {
        L0IA l0ia = null;
        if (j0 == null) {
            return null;
        }
        if (j0.z0()) {
            return j0.u0();
        }
        a aVarT0 = j0.t0();
        return new L0(aVarT0.e, aVarT0.f, (B0[]) com.android.tools.r8.utils.B.a(aVarT0.g, new Predicate() { // from class: com.android.tools.r8.graph.J0$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((B0) obj).w0();
            }
        }, B0.a));
    }
}
