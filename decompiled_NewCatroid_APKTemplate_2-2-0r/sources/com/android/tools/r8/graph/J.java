package com.android.tools.r8.graph;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class J {
    static final /* synthetic */ boolean c = true;
    private p5 a;
    private C0635d2 b;

    public final J a(C0729q2 c0729q2) {
        if (!c && this.a != null) {
            throw new AssertionError();
        }
        this.a = c0729q2;
        return this;
    }

    public final J a(C0754u0 c0754u0) {
        if (!c && this.a != null) {
            throw new AssertionError();
        }
        this.a = c0754u0;
        return this;
    }

    public final J a(C0635d2 c0635d2) {
        if (!c && this.b != null) {
            throw new AssertionError();
        }
        this.b = c0635d2;
        return this;
    }

    public final J a(p5 p5Var) {
        if (!c && this.a != null) {
            throw new AssertionError();
        }
        this.a = p5Var;
        return this;
    }

    public final O a() {
        p5 p5Var = this.a;
        if (p5Var == null && this.b == null) {
            return M.a;
        }
        if (p5Var == null) {
            return this.b;
        }
        if (this.b == null) {
            return p5Var;
        }
        if (p5Var.e0()) {
            return new N(this.a.a0(), this.b);
        }
        if (!c) {
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) this.a;
            abstractC0727q0.getClass();
            if (!(abstractC0727q0 instanceof C0754u0)) {
                throw new AssertionError();
            }
        }
        return new K(this.a.m(), this.b);
    }
}
