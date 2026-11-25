package com.android.tools.r8.graph;

/* JADX INFO: renamed from: com.android.tools.r8.graph.o4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0719o4 extends AbstractC0648f {
    C0719o4() {
        super(C0725p4.b(0, false));
    }

    public final void a(int i) {
        ((C0725p4) this.a).d(i);
    }

    public final AbstractC0648f b() {
        return this;
    }

    public final C0719o4 c() {
        ((C0725p4) this.a).f0();
        return this;
    }

    public final C0719o4 d() {
        ((C0725p4) this.a).d(65536);
        return this;
    }

    public final C0719o4 e(boolean z) {
        if (z) {
            ((C0725p4) this.a).d(32);
        } else {
            ((C0725p4) this.a).f(32);
        }
        return this;
    }

    public final AbstractC0648f f() {
        this.a.D();
        return b();
    }

    public final AbstractC0648f g() {
        this.a.E();
        return b();
    }

    public final C0719o4 a(boolean z) {
        if (z) {
            this.a.A();
        } else {
            this.a.I();
        }
        return this;
    }

    public final C0719o4 b(boolean z) {
        if (z) {
            this.a.B();
        } else {
            this.a.K();
        }
        return this;
    }

    public final AbstractC0648f c(boolean z) {
        if (z) {
            this.a.C();
        } else {
            this.a.L();
        }
        return b();
    }

    public final C0719o4 d(boolean z) {
        if (z) {
            ((C0725p4) this.a).d(2048);
        } else {
            ((C0725p4) this.a).f(2048);
        }
        return this;
    }

    public final AbstractC0657g a() {
        return this.a;
    }

    public final AbstractC0648f e() {
        if (AbstractC0648f.b || this.a.i()) {
            return b();
        }
        throw new AssertionError();
    }
}
