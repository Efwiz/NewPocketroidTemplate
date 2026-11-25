package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.OU;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class U2 implements R2 {
    public static final U2 e = new U2(null);
    static final /* synthetic */ boolean f = true;
    private Y0 a;
    private int b;
    private AbstractC0632d c;
    private AbstractC0632d d;

    public U2(Y0 y0) {
        C0607b c0607b = C0607b.a;
        this.c = c0607b;
        this.d = c0607b;
        this.a = y0;
    }

    public final void a(AbstractC0632d abstractC0632d) {
        this.c = abstractC0632d;
    }

    public final void b(AbstractC0632d abstractC0632d) {
        this.d = abstractC0632d;
    }

    final void c() {
        C0625c c0625c = C0625c.a;
        this.c = c0625c;
        this.d = c0625c;
    }

    final void d() {
        this.c.a(this.a);
        this.d.a(this.a);
    }

    public final Y0 e() {
        return this.a;
    }

    public final int f() {
        return this.d.b();
    }

    public final AbstractC0632d g() {
        return this.c;
    }

    public final AbstractC0632d h() {
        return this.d;
    }

    public final boolean i() {
        AbstractC0632d abstractC0632d = this.c;
        abstractC0632d.getClass();
        return !(abstractC0632d instanceof C0625c);
    }

    public final boolean j() {
        AbstractC0632d abstractC0632d = this.d;
        abstractC0632d.getClass();
        return !(abstractC0632d instanceof C0625c);
    }

    public final boolean k() {
        return l() || m();
    }

    public final boolean l() {
        return (this.b & 8) != 0;
    }

    public final boolean m() {
        return (this.b & 16) != 0;
    }

    public final boolean n() {
        return !this.c.c() || l() || o() || p() || q();
    }

    public final boolean o() {
        return (this.b & 1) != 0;
    }

    public final boolean p() {
        return (this.b & 2) != 0;
    }

    public final boolean q() {
        return (this.b & 32) != 0;
    }

    public final boolean r() {
        return !this.d.c() || m() || s();
    }

    public final boolean s() {
        return (this.b & 4) != 0;
    }

    public final void t() {
        this.b |= 8;
    }

    public final void u() {
        this.b |= 16;
    }

    public final void v() {
        this.b |= 1;
    }

    public final void w() {
        this.b |= 2;
    }

    public final void x() {
        this.b |= 32;
    }

    public final void y() {
        this.b |= 4;
    }

    private void a(Consumer consumer, AbstractC0632d abstractC0632d, final Set set) {
        abstractC0632d.getClass();
        if (abstractC0632d instanceof C0607b) {
            return;
        }
        if (!(abstractC0632d instanceof C0592a)) {
            throw new C3405y10("Should never be iterating the indirect accesses when they are unknown");
        }
        abstractC0632d.a().a(consumer, new Predicate() { // from class: com.android.tools.r8.graph.U2$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(set, (Y0) obj);
            }
        });
    }

    public final void b(Consumer consumer) {
        Set setC = OU.c();
        a(consumer, this.c, setC);
        a(consumer, this.d, setC);
    }

    public final void c(Consumer consumer) {
        this.d.a(consumer);
    }

    public final boolean c(Predicate predicate) {
        return (!this.d.b(predicate) || m() || s()) ? false : true;
    }

    public final boolean b(Predicate predicate) {
        return this.d.a(predicate);
    }

    public final boolean b(Y0 y0, o5 o5Var) {
        AbstractC0632d abstractC0632d = this.d;
        abstractC0632d.getClass();
        if (abstractC0632d instanceof C0607b) {
            this.d = new C0592a();
        }
        AbstractC0632d abstractC0632d2 = this.d;
        abstractC0632d2.getClass();
        if (abstractC0632d2 instanceof C0592a) {
            return this.d.a().a(y0, o5Var);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Set set, Y0 y0) {
        return y0 != this.a && set.add(y0);
    }

    public final void a(Consumer consumer) {
        this.c.a(consumer);
        this.d.a(consumer);
    }

    public final boolean a(Predicate predicate) {
        return (!this.c.b(predicate) || l() || o() || p() || q()) ? false : true;
    }

    public final void b() {
        this.d = C0607b.a;
    }

    public final boolean a(W0 w0) {
        return this.d.a(w0) || m() || s();
    }

    public final boolean a(Y0 y0, o5 o5Var) {
        AbstractC0632d abstractC0632d = this.c;
        abstractC0632d.getClass();
        if (abstractC0632d instanceof C0607b) {
            this.c = new C0592a();
        }
        AbstractC0632d abstractC0632d2 = this.c;
        abstractC0632d2.getClass();
        if (abstractC0632d2 instanceof C0592a) {
            return this.c.a().a(y0, o5Var);
        }
        return false;
    }

    public final void a() {
        boolean z = f;
        if (!z && k()) {
            throw new AssertionError();
        }
        if (!z && o()) {
            throw new AssertionError();
        }
        if (!z && p()) {
            throw new AssertionError();
        }
        this.c = C0607b.a;
        this.b &= -33;
    }

    public final U2 a(Q0 q0, AbstractC1476Wj abstractC1476Wj) {
        U2 u2 = new U2(abstractC1476Wj.c((AbstractC1476Wj) null, this.a));
        u2.b = this.b;
        u2.c = this.c.a(q0, abstractC1476Wj);
        u2.d = this.d.a(q0, abstractC1476Wj);
        return u2;
    }

    public final U2 a(U2 u2) {
        U2 u3 = new U2(this.a);
        u3.b = this.b | u2.b;
        u3.c = this.c.a(u2.c);
        u3.d = this.d.a(u2.d);
        return u3;
    }
}
