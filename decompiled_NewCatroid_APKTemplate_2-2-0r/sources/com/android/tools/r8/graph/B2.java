package com.android.tools.r8.graph;

import com.android.tools.r8.dex.C0586x;
import com.android.tools.r8.internal.C1944ek;
import com.android.tools.r8.internal.C2892r10;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CZ;
import com.gaurav.avnc.vnc.XKeySym;
import java.util.Arrays;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class B2 extends AbstractC0594a1 implements com.android.tools.r8.utils.structural.x<B2> {
    public static final B2[] a = new B2[0];

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends B2 {
        final B2[] b;

        public a(B2[] b2Arr) {
            this.b = b2Arr;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object M0() {
            throw new C3405y10("No boxed value for DexValueArray");
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.p;
        }

        @Override // com.android.tools.r8.graph.B2, com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2, com.android.tools.r8.utils.structural.x
        public final /* bridge */ /* synthetic */ int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
            return a((B2) xVar, aVar);
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            B2[] b2Arr = this.b;
            B2[] b2Arr2 = b2.v0().b;
            aVar.getClass();
            return aVar.a(Arrays.asList(b2Arr), Arrays.asList(b2Arr2));
        }

        public final void d(Consumer consumer) {
            for (B2 b2 : this.b) {
                consumer.accept(b2);
            }
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof a) {
                return Arrays.equals(((a) obj).b, this.b);
            }
            return false;
        }

        @Override // com.android.tools.r8.graph.B2
        public final int hashCode() {
            return Arrays.hashCode(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final void i1() {
            for (B2 b2 : this.b) {
                b2.i1();
            }
        }

        public B2[] j1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object s0() {
            throw new C3405y10("No ASM conversion for DexValueArray");
        }

        @Override // com.android.tools.r8.graph.B2
        public final String toString() {
            return "Array " + Arrays.toString(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final a v0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
            for (B2 b2 : this.b) {
                b2.a(c0702m, l);
            }
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            B2[] b2Arr = this.b;
            oVar.getClass();
            oVar.a(Arrays.asList(b2Arr));
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0586x c0586x, C0645e5 c0645e5) {
            B2.a(E2.p, 0, c0586x);
            com.android.tools.r8.utils.Q1.b(c0586x, this.b.length);
            for (B2 b2 : this.b) {
                b2.a(c0586x, c0645e5);
            }
        }

        @Override // com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            throw new C3405y10();
        }

        @Override // com.android.tools.r8.graph.B2
        public final com.android.tools.r8.internal.B1 a(com.android.tools.r8.internal.C1 c1) {
            return C2892r10.F();
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b extends J2 {
        private static final b c = new b(true);
        private static final b d = new b(false);
        private static final b e = new b(false);
        final boolean b;

        private b(boolean z) {
            this.b = z;
        }

        public static b a(boolean z) {
            return z ? c : d;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object M0() {
            return Boolean.valueOf(k1());
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.s;
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean Q0() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a(this.b, b2.w0().b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof b) && ((b) obj).b == this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final int hashCode() {
            if (this.b) {
                return XKeySym.XK_kana_ME;
            }
            return 4321;
        }

        @Override // com.android.tools.r8.graph.J2
        public final long j1() {
            boolean z = this.b;
            int i = com.android.tools.r8.utils.M.b;
            return z ? 1L : 0L;
        }

        public boolean k1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object s0() {
            return Integer.valueOf(this.b ? 1 : 0);
        }

        @Override // com.android.tools.r8.graph.B2
        public final String toString() {
            return this.b ? "True" : "False";
        }

        @Override // com.android.tools.r8.graph.B2
        public final b w0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            return c0722p1.v1;
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0586x c0586x, C0645e5 c0645e5) {
            B2.a(E2.s, this.b ? 1 : 0, c0586x);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class c extends J2 {
        public static final c c = new c((byte) 0);
        final byte b;

        private c(byte b) {
            this.b = b;
        }

        public static c a(byte b) {
            return b == 0 ? c : new c(b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object M0() {
            return Byte.valueOf(k1());
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean R0() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a((int) this.b, (int) b2.x0().b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof c) && this.b == ((c) obj).b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final int hashCode() {
            return this.b * 3;
        }

        @Override // com.android.tools.r8.graph.J2
        public final long j1() {
            return this.b;
        }

        public byte k1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object s0() {
            return Integer.valueOf(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final String toString() {
            return "Byte " + ((int) this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final c x0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            return c0722p1.w1;
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a((int) this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0586x c0586x, C0645e5 c0645e5) {
            B2.a(E2.b, 0, c0586x);
            com.android.tools.r8.utils.P0.b(c0586x, this.b, 1);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class d extends J2 {
        public static final d c = new d(0);
        final char b;

        private d(char c2) {
            this.b = c2;
        }

        public static d a(char c2) {
            return c2 == 0 ? c : new d(c2);
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object M0() {
            return Character.valueOf(k1());
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.d;
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean S0() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a((int) this.b, (int) b2.y0().b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof d) && this.b == ((d) obj).b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final int hashCode() {
            return this.b * 5;
        }

        @Override // com.android.tools.r8.graph.J2
        public final long j1() {
            return this.b;
        }

        public char k1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object s0() {
            return Integer.valueOf(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final String toString() {
            return "Char " + this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final d y0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            return c0722p1.x1;
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a((int) this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0586x c0586x, C0645e5 c0645e5) {
            c0586x.d(1);
            int iC = com.android.tools.r8.utils.P0.c(c0586x, this.b, 2);
            c0586x.d(-(iC + 1));
            B2.a(E2.d, iC - 1, c0586x);
            c0586x.d(iC);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class e extends J2 {
        public static final e c = new e(0.0d);
        final double b;

        private e(double d) {
            this.b = d;
        }

        public static e a(double d) {
            return Double.compare(d, 0.0d) == 0 ? c : new e(d);
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object M0() {
            return Double.valueOf(k1());
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.h;
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean T0() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a(this.b, b2.z0().b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof e) && Double.compare(this.b, ((e) obj).b) == 0;
        }

        @Override // com.android.tools.r8.graph.B2
        public final int hashCode() {
            return (int) (this.b * 29.0d);
        }

        @Override // com.android.tools.r8.graph.J2
        public final long j1() {
            return Double.doubleToRawLongBits(this.b);
        }

        public double k1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object s0() {
            return Double.valueOf(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final String toString() {
            return "Double " + this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final e z0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            return c0722p1.y1;
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0586x c0586x, C0645e5 c0645e5) {
            c0586x.d(1);
            int iA = com.android.tools.r8.utils.P0.a(c0586x, this.b);
            c0586x.d(-(iA + 1));
            B2.a(E2.h, iA - 1, c0586x);
            c0586x.d(iA);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class f extends J2 {
        public static final f c = new f(0.0f);
        final float b;

        private f(float f) {
            this.b = f;
        }

        public static f a(float f) {
            return Float.compare(f, 0.0f) == 0 ? c : new f(f);
        }

        @Override // com.android.tools.r8.graph.B2
        public final f C0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object M0() {
            return Float.valueOf(k1());
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.g;
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean W0() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof f) && Float.compare(this.b, ((f) obj).b) == 0;
        }

        @Override // com.android.tools.r8.graph.B2
        public final int hashCode() {
            return (int) (this.b * 19.0f);
        }

        @Override // com.android.tools.r8.graph.J2
        public final long j1() {
            return Float.floatToIntBits(this.b);
        }

        public float k1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object s0() {
            return Float.valueOf(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final String toString() {
            return "Float " + this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            return c0722p1.z1;
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a(this.b, b2.C0().b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0586x c0586x, C0645e5 c0645e5) {
            c0586x.d(1);
            int iA = com.android.tools.r8.utils.P0.a(c0586x, this.b);
            c0586x.d(-(iA + 1));
            B2.a(E2.g, iA - 1, c0586x);
            c0586x.d(iA);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class g extends J2 {
        public static final g c = new g(0);
        public final int b;

        private g(int i) {
            this.b = i;
        }

        public static g j(int i) {
            g gVar = c;
            return i == gVar.b ? gVar : new g(i);
        }

        @Override // com.android.tools.r8.graph.B2
        public final g D0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object M0() {
            return Integer.valueOf(k1());
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.e;
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean X0() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            return c0722p1.A1;
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof g) && this.b == ((g) obj).b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final int hashCode() {
            return this.b * 11;
        }

        @Override // com.android.tools.r8.graph.J2
        public final long j1() {
            return this.b;
        }

        public int k1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object s0() {
            return Integer.valueOf(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final String toString() {
            return "Int " + this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0586x c0586x, C0645e5 c0645e5) {
            J2.a(E2.e, this.b, 4, c0586x);
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a(this.b, b2.D0().b);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class h extends J2 {
        public static final h c = new h(0);
        final long b;

        private h(long j) {
            this.b = j;
        }

        public static h a(long j) {
            return j == 0 ? c : new h(j);
        }

        @Override // com.android.tools.r8.graph.B2
        public final h E0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object M0() {
            return Long.valueOf(k1());
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.f;
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean Y0() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a(this.b, b2.E0().b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof h) && this.b == ((h) obj).b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final int hashCode() {
            return ((int) this.b) * 13;
        }

        @Override // com.android.tools.r8.graph.J2
        public final long j1() {
            return this.b;
        }

        public long k1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object s0() {
            return Long.valueOf(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final String toString() {
            return "Long " + this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            return c0722p1.B1;
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0586x c0586x, C0645e5 c0645e5) {
            J2.a(E2.f, this.b, 8, c0586x);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class i extends J2 {
        public static final i c = new i(0);
        final short b;

        private i(short s) {
            this.b = s;
        }

        public static i a(short s) {
            return s == 0 ? c : new i(s);
        }

        @Override // com.android.tools.r8.graph.B2
        public final i J0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object M0() {
            return Short.valueOf(k1());
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.c;
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            oVar.a((int) this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean d1() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof i) && this.b == ((i) obj).b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final int hashCode() {
            return this.b * 7;
        }

        @Override // com.android.tools.r8.graph.J2
        public final long j1() {
            return this.b;
        }

        public short k1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final Object s0() {
            return Integer.valueOf(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final String toString() {
            return "Short " + ((int) this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            return c0722p1.C1;
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            return aVar.a((int) this.b, (int) b2.J0().k1());
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0586x c0586x, C0645e5 c0645e5) {
            J2.a(E2.c, this.b, 2, c0586x);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class k extends l<C0767v2> {
        public k(C0767v2 c0767v2) {
            super(c0767v2);
        }

        @Override // com.android.tools.r8.graph.B2
        public final k L0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.l;
        }

        @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            throw new C3405y10();
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            C0767v2 c0767v2 = (C0767v2) this.b;
            C0767v2 c0767v3 = (C0767v2) b2.L0().b;
            c0767v2.getClass();
            return aVar.a(c0767v2, c0767v3);
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean f1() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
        public final /* bridge */ /* synthetic */ boolean g1() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
        public final /* bridge */ /* synthetic */ void i1() {
        }

        public I3 j1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
            C0767v2 c0767v2 = (C0767v2) this.b;
            c0767v2.getClass();
            if (l.a(c0767v2)) {
                C0759u2 c0759u2C = c0702m.z().c(c0767v2);
                c0759u2C.getClass();
                l.a(c0759u2C);
            }
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            C0767v2 c0767v2 = (C0767v2) this.b;
            c0767v2.getClass();
            oVar.a(c0767v2);
        }

        @Override // com.android.tools.r8.graph.B2
        public final com.android.tools.r8.internal.B1 a(com.android.tools.r8.internal.C1 c1) {
            return C2892r10.F();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class l<T extends I3> extends B2 {
        public final T b;

        /* JADX WARN: Multi-variable type inference failed */
        private l(I3 i3) {
            this.b = i3;
        }

        @Override // com.android.tools.r8.graph.B2
        public Object M0() {
            throw new C3405y10("No boxed value for DexValue " + getClass().getSimpleName());
        }

        @Override // com.android.tools.r8.graph.B2, com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2, com.android.tools.r8.utils.structural.x
        public final /* bridge */ /* synthetic */ int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
            return a((B2) xVar, aVar);
        }

        @Override // com.android.tools.r8.graph.B2
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof l)) {
                return false;
            }
            l lVar = (l) obj;
            return lVar.N0() == N0() && lVar.b.equals(this.b);
        }

        @Override // com.android.tools.r8.graph.B2
        public boolean g1() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2
        public int hashCode() {
            return (this.b.hashCode() * 7) + N0().a;
        }

        @Override // com.android.tools.r8.graph.B2
        public void i1() {
        }

        @Override // com.android.tools.r8.graph.B2
        public Object s0() {
            throw new C3405y10("No ASM conversion for DexValue " + getClass().getSimpleName());
        }

        @Override // com.android.tools.r8.graph.B2
        public String toString() {
            return "Item " + N0() + " " + this.b;
        }

        @Override // com.android.tools.r8.graph.B2
        public C0767v2 a(C0722p1 c0722p1) {
            throw new C3405y10();
        }

        @Override // com.android.tools.r8.graph.B2
        public void a(C0586x c0586x, C0645e5 c0645e5) {
            int iA = this.b.a(c0645e5);
            c0586x.d(1);
            int iC = com.android.tools.r8.utils.P0.c(c0586x, iA, 4);
            c0586x.d(-(iC + 1));
            B2.a(N0(), iC - 1, c0586x);
            c0586x.d(iC);
        }
    }

    public C2 A0() {
        return null;
    }

    public D2 B0() {
        return null;
    }

    public f C0() {
        return null;
    }

    public g D0() {
        return null;
    }

    public h E0() {
        return null;
    }

    public F2 F0() {
        return null;
    }

    public G2 G0() {
        return null;
    }

    public H2 H0() {
        return null;
    }

    public J2 I0() {
        return null;
    }

    public i J0() {
        return null;
    }

    public j K0() {
        return null;
    }

    public k L0() {
        return null;
    }

    public abstract Object M0();

    public abstract E2 N0();

    public boolean O0() {
        return this instanceof C0816z2;
    }

    public boolean P0() {
        return this instanceof a;
    }

    public boolean Q0() {
        return false;
    }

    public boolean R0() {
        return false;
    }

    public boolean S0() {
        return false;
    }

    public boolean T0() {
        return false;
    }

    public com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public boolean U0() {
        return this instanceof C2;
    }

    public boolean V0() {
        return this instanceof D2;
    }

    public boolean W0() {
        return false;
    }

    public boolean X0() {
        return false;
    }

    public boolean Y0() {
        return false;
    }

    public boolean Z0() {
        return this instanceof F2;
    }

    public abstract C0767v2 a(C0722p1 c0722p1);

    public abstract com.android.tools.r8.internal.B1 a(com.android.tools.r8.internal.C1 c1);

    public abstract void a(C0586x c0586x, C0645e5 c0645e5);

    public void a(C0702m c0702m, com.android.tools.r8.dex.L l2) {
    }

    public boolean a1() {
        return this instanceof G2;
    }

    abstract int b(B2 b2, com.android.tools.r8.utils.structural.a aVar);

    abstract void b(com.android.tools.r8.utils.structural.o oVar);

    public boolean b1() {
        return this instanceof I2;
    }

    public boolean c1() {
        return false;
    }

    public boolean d1() {
        return false;
    }

    public boolean e1() {
        return false;
    }

    public abstract boolean equals(Object obj);

    public boolean f1() {
        return false;
    }

    public boolean g1() {
        return false;
    }

    public boolean h1() {
        return !(this instanceof j);
    }

    public abstract int hashCode();

    public abstract void i1();

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        throw new C3405y10();
    }

    public abstract Object s0();

    public C0816z2 t0() {
        return null;
    }

    public abstract String toString();

    public A2 u0() {
        return null;
    }

    public a v0() {
        return null;
    }

    public b w0() {
        return null;
    }

    public c x0() {
        return null;
    }

    public d y0() {
        return null;
    }

    public e z0() {
        return null;
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class j extends l<C0759u2> {
        public j(C0759u2 c0759u2) {
            super(c0759u2);
        }

        @Override // com.android.tools.r8.graph.B2
        public final j K0() {
            return this;
        }

        @Override // com.android.tools.r8.graph.B2
        public final E2 N0() {
            return E2.k;
        }

        @Override // com.android.tools.r8.graph.B2
        public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
            C0759u2 c0759u2 = (C0759u2) this.b;
            c0759u2.getClass();
            l.a(c0759u2);
        }

        @Override // com.android.tools.r8.graph.B2
        final void b(com.android.tools.r8.utils.structural.o oVar) {
            C0759u2 c0759u2 = (C0759u2) this.b;
            c0759u2.getClass();
            oVar.a(c0759u2);
        }

        @Override // com.android.tools.r8.graph.B2
        public final boolean e1() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
        public final /* bridge */ /* synthetic */ boolean g1() {
            return true;
        }

        @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
        public final /* bridge */ /* synthetic */ void i1() {
        }

        public I3 j1() {
            return this.b;
        }

        @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
        public final Object s0() {
            return ((C0759u2) this.b).toString();
        }

        @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
        public final C0767v2 a(C0722p1 c0722p1) {
            return c0722p1.X1;
        }

        @Override // com.android.tools.r8.graph.B2
        final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
            boolean z = C0816z2.d;
            if (!z && N0() != E2.k) {
                throw new AssertionError();
            }
            if (!z && b2.N0() != E2.k) {
                throw new AssertionError();
            }
            b2.getClass();
            int iA = aVar.a(this instanceof C0816z2 ? 1 : 0, b2 instanceof C0816z2 ? 1 : 0);
            if (iA != 0) {
                return iA;
            }
            C0759u2 c0759u2 = (C0759u2) this.b;
            C0759u2 c0759u3 = (C0759u2) b2.K0().b;
            c0759u2.getClass();
            return aVar.a(c0759u2, c0759u3);
        }

        @Override // com.android.tools.r8.graph.B2
        public final com.android.tools.r8.internal.B1 a(com.android.tools.r8.internal.C1 c1) {
            return c1.a((C0759u2) this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(E2 e2, int i2, C0586x c0586x) {
        c0586x.a((byte) (e2.a | (i2 << 5)));
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final int a(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
        if (N0() != b2.N0()) {
            return aVar.a((int) N0().a, (int) b2.N0().a);
        }
        return b(b2, aVar);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a((int) N0().a);
        b(oVar);
    }

    public static B2 a(Object obj, M3 m3, C0767v2 c0767v2) {
        if (obj instanceof Integer) {
            return g.j(((Integer) obj).intValue());
        }
        if (obj instanceof Long) {
            return h.a(((Long) obj).longValue());
        }
        if (obj instanceof Float) {
            return f.a(((Float) obj).floatValue());
        }
        if (obj instanceof Double) {
            return e.a(((Double) obj).doubleValue());
        }
        if (obj instanceof String) {
            return new j(m3.d((String) obj));
        }
        if (obj instanceof CZ) {
            CZ cz = (CZ) obj;
            int iE = cz.e();
            if (iE == 10) {
                return new k(m3.e(cz.b()));
            }
            if (iE == 11) {
                return new H2(m3.c(cz.b()));
            }
            throw new C3405y10("Type sort is not supported: " + cz.e());
        }
        if (obj instanceof C1944ek) {
            return new G2(C0699l2.a((C1944ek) obj, m3, c0767v2));
        }
        throw new C3405y10("Unsupported bootstrap static argument of type " + obj.getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        throw new C3405y10();
    }

    public static J2 a(C0767v2 c0767v2) {
        char cC1 = c0767v2.c1();
        if (cC1 == 'F') {
            return f.c;
        }
        if (cC1 == 'L') {
            return I2.b;
        }
        if (cC1 == 'S') {
            return i.c;
        }
        if (cC1 == 'Z') {
            return b.e;
        }
        if (cC1 == 'I') {
            return g.c;
        }
        if (cC1 == 'J') {
            return h.c;
        }
        switch (cC1) {
            case 'B':
                return c.c;
            case 'C':
                return d.c;
            case 'D':
                return e.c;
            default:
                throw new C3405y10("No default value for unexpected type " + c0767v2);
        }
    }
}
