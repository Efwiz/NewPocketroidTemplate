package com.android.tools.r8.graph;

import com.android.tools.r8.dex.C0583u;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C3405y10;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class B0 extends AbstractC0594a1 implements com.android.tools.r8.utils.structural.x<B0> {
    public static final B0[] a = new B0[0];
    static final /* synthetic */ boolean b = true;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends B0 {
        static final /* synthetic */ boolean d = true;
        public final int c;

        public a(int i) {
            this.c = i;
        }

        @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
        public final /* bridge */ /* synthetic */ int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
            return a((B0) xVar, aVar);
        }

        @Override // com.android.tools.r8.graph.B0
        final int b(B0 b0, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a(this.c, ((a) b0).c);
        }

        public final int hashCode() {
            return (this.c * 7) + 1;
        }

        public final String toString() {
            return "ADVANCE_PC " + this.c;
        }

        @Override // com.android.tools.r8.graph.B0
        final int v0() {
            return 1;
        }

        @Override // com.android.tools.r8.graph.B0
        final boolean w0() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B0
        public final void a(C0583u c0583u, AbstractC1476Wj abstractC1476Wj) {
            c0583u.a(1);
            c0583u.c(this.c);
        }

        @Override // com.android.tools.r8.graph.B0
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a(this.c);
        }

        @Override // com.android.tools.r8.graph.B0
        public final void a(I0 i0) {
            if (!d && this.c < 0) {
                throw new AssertionError();
            }
            i0.a(this);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b extends B0 {
        static final /* synthetic */ boolean d = true;
        final int c;

        b(int i) {
            if (!d && (i < 10 || i > 255)) {
                throw new AssertionError();
            }
            this.c = i;
        }

        @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
        public final /* bridge */ /* synthetic */ int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
            return a((B0) xVar, aVar);
        }

        @Override // com.android.tools.r8.graph.B0
        final int b(B0 b0, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a(this.c, ((b) b0).c);
        }

        public final int hashCode() {
            return (this.c * 7) + 10;
        }

        @Override // com.android.tools.r8.graph.B0
        public final b t0() {
            return this;
        }

        public final String toString() {
            return String.format("DEFAULT %d (dpc %d, dline %d)", Integer.valueOf(this.c), Integer.valueOf(y0()), Integer.valueOf(x0()));
        }

        @Override // com.android.tools.r8.graph.B0
        final int v0() {
            return 10;
        }

        @Override // com.android.tools.r8.graph.B0
        final boolean w0() {
            return true;
        }

        public int x0() {
            return ((this.c - 10) % 15) - 4;
        }

        public int y0() {
            return (this.c - 10) / 15;
        }

        @Override // com.android.tools.r8.graph.B0
        public final void a(C0583u c0583u, AbstractC1476Wj abstractC1476Wj) {
            c0583u.a(this.c);
        }

        @Override // com.android.tools.r8.graph.B0
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a(this.c);
        }

        @Override // com.android.tools.r8.graph.B0
        public final void a(I0 i0) {
            i0.a(this);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class c extends B0 {
        C0759u2 c;

        c(C0759u2 c0759u2) {
            this.c = c0759u2;
        }

        @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
        public final /* bridge */ /* synthetic */ int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
            return a((B0) xVar, aVar);
        }

        @Override // com.android.tools.r8.graph.B0
        final int b(B0 b0, com.android.tools.r8.utils.structural.a aVar) {
            C0759u2 c0759u2 = this.c;
            C0759u2 c0759u3 = ((c) b0).c;
            c0759u2.getClass();
            return aVar.a(c0759u2, c0759u3);
        }

        public final int hashCode() {
            return (this.c.hashCode() * 7) + 9;
        }

        public final String toString() {
            return "SET_FILE " + this.c.toString();
        }

        @Override // com.android.tools.r8.graph.B0
        final int v0() {
            return 9;
        }

        @Override // com.android.tools.r8.graph.B0
        final boolean w0() {
            return false;
        }

        @Override // com.android.tools.r8.graph.B0
        public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
            C0759u2 c0759u2 = this.c;
            c0759u2.getClass();
            l.a(c0759u2);
        }

        @Override // com.android.tools.r8.graph.B0
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            C0759u2 c0759u2 = this.c;
            c0759u2.getClass();
            oVar.a(c0759u2);
        }

        @Override // com.android.tools.r8.graph.B0
        public final void a(I0 i0) {
            i0.a(this);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class d extends B0 {
        final int c;
        final C0759u2 d;
        final C0767v2 e;
        final C0759u2 f;

        public d(int i, C0759u2 c0759u2, C0767v2 c0767v2, C0759u2 c0759u3) {
            this.c = i;
            this.d = c0759u2;
            this.e = c0767v2;
            this.f = c0759u3;
        }

        @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
        public final /* bridge */ /* synthetic */ int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
            return a((B0) xVar, aVar);
        }

        public final int hashCode() {
            return (Objects.hashCode(this.f) * 19) + (Objects.hashCode(this.e) * 17) + (Objects.hashCode(this.d) * 13) + (this.c * 7) + 3;
        }

        public final String toString() {
            return "START_LOCAL " + this.c;
        }

        @Override // com.android.tools.r8.graph.B0
        final int v0() {
            return 3;
        }

        @Override // com.android.tools.r8.graph.B0
        final boolean w0() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B0
        final int b(B0 b0, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a(this, (d) b0, new B0$d$$ExternalSyntheticLambda0());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void a(com.android.tools.r8.utils.structural.z zVar) {
            zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.graph.B0$d$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((B0.d) obj).c;
                }
            }).c(new Function() { // from class: com.android.tools.r8.graph.B0$d$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((B0.d) obj).d;
                }
            }).c(new Function() { // from class: com.android.tools.r8.graph.B0$d$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((B0.d) obj).e;
                }
            }).h(new Function() { // from class: com.android.tools.r8.graph.B0$d$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((B0.d) obj).f;
                }
            });
        }

        @Override // com.android.tools.r8.graph.B0
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a(this, new B0$d$$ExternalSyntheticLambda0());
        }

        @Override // com.android.tools.r8.graph.B0
        public final void a(C0583u c0583u, AbstractC1476Wj abstractC1476Wj) {
            c0583u.a(this.f == null ? 3 : 4);
            c0583u.c(this.c);
            c0583u.a(this.d);
            C0767v2 c0767v2 = this.e;
            abstractC1476Wj.getClass();
            c0583u.a(abstractC1476Wj.a(AbstractC1476Wj.e(), c0767v2));
            C0759u2 c0759u2 = this.f;
            if (c0759u2 != null) {
                c0583u.a(c0759u2);
            }
        }

        @Override // com.android.tools.r8.graph.B0
        public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
            C0759u2 c0759u2 = this.d;
            if (c0759u2 != null) {
                l.a(c0759u2);
            }
            if (this.e != null) {
                AbstractC1476Wj abstractC1476WjE = c0702m.E();
                C0767v2 c0767v2 = this.e;
                abstractC1476WjE.getClass();
                C0767v2 c0767v2A = abstractC1476WjE.a(AbstractC1476Wj.e(), c0767v2);
                c0767v2A.getClass();
                if (l.a(c0767v2A)) {
                    C0759u2 c0759u2C = c0702m.z().c(c0767v2A);
                    c0759u2C.getClass();
                    l.a(c0759u2C);
                }
            }
            C0759u2 c0759u3 = this.f;
            if (c0759u3 != null) {
                l.a(c0759u3);
            }
        }

        @Override // com.android.tools.r8.graph.B0
        public final void a(I0 i0) {
            i0.a(this);
        }
    }

    public com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
    }

    public abstract void a(I0 i0);

    public void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
    }

    abstract int b(B0 b0, com.android.tools.r8.utils.structural.a aVar);

    abstract void b(com.android.tools.r8.utils.structural.o oVar);

    public final boolean equals(Object obj) {
        return (obj instanceof B0) && compareTo((B0) obj) == 0;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        throw new C3405y10();
    }

    public A0 s0() {
        return null;
    }

    public b t0() {
        return null;
    }

    public F0 u0() {
        return null;
    }

    abstract int v0();

    boolean w0() {
        return this instanceof A0;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final int a(B0 b0, com.android.tools.r8.utils.structural.a aVar) {
        int iA = aVar.a(v0(), b0.v0());
        return iA != 0 ? iA : b(b0, aVar);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(v0());
        b(oVar);
    }

    void a(C0583u c0583u, AbstractC1476Wj abstractC1476Wj) {
        throw new C3405y10();
    }
}
