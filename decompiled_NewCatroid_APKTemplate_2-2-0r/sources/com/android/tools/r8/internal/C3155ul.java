package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ul, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3155ul {
    static final /* synthetic */ boolean c = true;
    private long a;
    private long b;

    public C3155ul() {
    }

    public static C3155ul N() {
        return new C3155ul(0);
    }

    private boolean a(int i) {
        if (i < 64) {
            return a(1 << i);
        }
        if (c || i < 128) {
            return ((1 << (i - 64)) & this.b) != 0;
        }
        throw new AssertionError();
    }

    public final boolean A() {
        return a(45);
    }

    public final boolean B() {
        return a(49);
    }

    public final boolean C() {
        return a(53);
    }

    public final boolean D() {
        return a(55);
    }

    public final boolean E() {
        return a(57);
    }

    public final boolean F() {
        return a(58);
    }

    public final boolean G() {
        return a(59);
    }

    public final boolean H() {
        return a(60);
    }

    public final boolean I() {
        return a(62);
    }

    public final boolean J() {
        return a(63);
    }

    public final boolean K() {
        boolean zA = a(4611686020574871552L);
        if (!c) {
            if (zA != (a(31) || a(62))) {
                throw new AssertionError();
            }
        }
        return zA;
    }

    public final boolean L() {
        return a(67);
    }

    public final boolean M() {
        return a(68);
    }

    public final boolean b() {
        return a(4);
    }

    public final boolean c() {
        boolean z = true;
        boolean z2 = a(-8745955291979317223L) || (this.b & 24) != 0;
        if (!c) {
            if (!a(0) && !a(21) && !a(45) && !a(55) && !a(63) && !a(4) && !a(53) && !a(57) && !a(58) && !a(67) && !a(68)) {
                z = false;
            }
            if (z2 != z) {
                throw new AssertionError();
            }
        }
        return z2;
    }

    public final boolean d() {
        return a(6);
    }

    public final boolean e() {
        return a(7);
    }

    public final boolean f() {
        return a(10);
    }

    public final boolean g() {
        return a(15);
    }

    public final boolean h() {
        return a(16);
    }

    public final boolean i() {
        return a(19);
    }

    public final boolean j() {
        return a(20);
    }

    public final boolean k() {
        return a(21);
    }

    public final boolean l() {
        boolean zA = a(1729382258252447744L);
        if (!c) {
            if (zA != (a(28) || a(30) || a(59) || a(60))) {
                throw new AssertionError();
            }
        }
        return zA;
    }

    public final boolean m() {
        return a(27);
    }

    public final boolean n() {
        return a(28);
    }

    public final boolean o() {
        return a(29);
    }

    public final boolean p() {
        return a(30);
    }

    public final boolean q() {
        return a(31);
    }

    public final boolean r() {
        return a(33);
    }

    public final boolean s() {
        return a(34);
    }

    public final boolean t() {
        boolean zA = a(2087354105856L);
        if (!c) {
            if (zA != (a(33) || a(34) || a(37) || a(38) || a(39) || a(40))) {
                throw new AssertionError();
            }
        }
        return zA;
    }

    public final boolean u() {
        boolean zA = a(1675037245440L);
        if (!c) {
            if (zA != (a(33) || a(34) || a(39) || a(40))) {
                throw new AssertionError();
            }
        }
        return zA;
    }

    public final boolean v() {
        return a(37);
    }

    public final boolean w() {
        return a(38);
    }

    public final boolean x() {
        return a(39);
    }

    public final boolean y() {
        return a(40);
    }

    public final boolean z() {
        return a(42);
    }

    private C3155ul(int i) {
        this.a = -1L;
        this.b = -1L;
    }

    private boolean a(long j) {
        return (j & this.a) != 0;
    }

    public final void a(AbstractC2412ko abstractC2412ko) {
        int iG2 = abstractC2412ko.G2();
        if (iG2 < 64) {
            this.a = (1 << iG2) | this.a;
        } else {
            if (!c && iG2 >= 128) {
                throw new AssertionError();
            }
            this.b = (1 << (iG2 - 64)) | this.b;
        }
    }

    public final void a(C3155ul c3155ul) {
        this.a |= c3155ul.a;
        this.b |= c3155ul.b;
    }

    public final boolean a() {
        return a(0);
    }
}
