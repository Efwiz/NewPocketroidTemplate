package com.android.tools.r8.graph;

import com.android.tools.r8.internal.OU;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class t5 {
    private AbstractC0681j0 a;
    private final Set b;
    private final Set c;
    private Set d;
    private final Set e;
    private Set f;

    t5() {
        this.b = OU.c();
        this.c = OU.c();
        this.d = OU.c();
        this.e = OU.c();
        this.f = OU.c();
    }

    public final t5 a(AbstractC0681j0 abstractC0681j0) {
        this.a = abstractC0681j0;
        return this;
    }

    public final t5 b(Set set) {
        this.c.addAll(set);
        this.d.addAll(set);
        return this;
    }

    public final t5 c(Set set) {
        this.e.addAll(set);
        return this;
    }

    public final t5 d(Set set) {
        this.f.addAll(set);
        return this;
    }

    public final t5 e(Set set) {
        this.d = set;
        return this;
    }

    public final t5 f(Set set) {
        this.f = set;
        return this;
    }

    public final t5 a(Set set) {
        this.b.addAll(set);
        return this;
    }

    public final void a(C0767v2 c0767v2) {
        this.c.add(c0767v2);
        this.d.add(c0767v2);
    }

    public final void a(Y0 y0) {
        this.e.add(y0);
    }

    public final void a(C0684j2 c0684j2) {
        this.f.add(c0684j2);
    }

    t5(u5 u5Var) {
        Set setC = OU.c();
        this.b = setC;
        Set setC2 = OU.c();
        this.c = setC2;
        this.d = OU.c();
        Set setC3 = OU.c();
        this.e = setC3;
        this.f = OU.c();
        setC.addAll(u5Var.b());
        setC2.addAll(u5Var.c());
        this.a = u5Var.d();
        this.d.addAll(u5Var.e());
        setC3.addAll(u5Var.f());
        this.f.addAll(u5Var.g());
    }

    public final u5 a() {
        return new u5(this.a, this.b, this.c, this.d, this.e, this.f);
    }
}
