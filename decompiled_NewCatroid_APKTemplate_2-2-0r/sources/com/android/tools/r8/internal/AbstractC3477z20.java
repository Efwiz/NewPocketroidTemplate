package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import com.android.tools.r8.shaking.C3733i;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3477z20 {
    public static AbstractC3477z20 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        return ((C3733i) c0702m.f()).m(o5Var.getReference()) ? new A20(o5Var, o5Var2, c0702m.Y().n1.d0) : JH.w();
    }

    public abstract void a();

    public abstract void a(int i, int i2);

    public abstract void a(com.android.tools.r8.androidapi.h hVar, com.android.tools.r8.androidapi.h hVar2);

    public abstract void a(C1166Ls c1166Ls);

    public abstract void a(C1793co c1793co);

    public abstract void a(AbstractC2412ko abstractC2412ko);

    public abstract void a(EnumC3542a0 enumC3542a0, Set set);

    public abstract void b();

    public abstract void b(int i, int i2);

    public abstract void c();

    public abstract void c(int i, int i2);

    public abstract void d();

    public abstract void d(int i, int i2);

    public abstract void e();

    public abstract void f();

    public abstract void g();

    public abstract void h();

    public abstract void i();

    public abstract void j();

    public abstract void k();

    public abstract void l();

    public abstract void m();

    public abstract void n();

    public abstract void o();

    public abstract void p();

    public abstract void q();

    public abstract void r();

    public abstract void s();

    public abstract void t();

    abstract void u();

    public abstract void v();

    public static void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, AbstractC1305Qs abstractC1305Qs) {
        CN cnG;
        if (((C3733i) c0702m.f()).D() || (cnG = abstractC1305Qs.g(c0702m, o5Var)) == null) {
            return;
        }
        Iterator it = cnG.iterator();
        while (it.hasNext()) {
            a(c0702m, (com.android.tools.r8.graph.o5) it.next(), o5Var).u();
        }
    }
}
