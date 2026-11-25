package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.b4$$ExternalSyntheticLambda3;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2215iD implements InterfaceC1206Ne {
    private final boolean a;
    private final C2835qD b;
    private final C2597nD c;
    private int d = -1;

    public C2215iD(boolean z, C2835qD c2835qD, C2597nD c2597nD) {
        this.a = z;
        this.b = c2835qD;
        this.c = c2597nD;
    }

    public static C2215iD a(Map<C0767v2, C0767v2> map) {
        final C2522mD c2522mD = new C2522mD();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.iD$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c2522mD.c((C0767v2) obj, (C0767v2) obj2);
            }
        });
        return new C2215iD(true, new C2835qD(EnumC3891f.B, "unused", null, null, false, AbstractC1639am.i()), c2522mD.a());
    }

    public static C2215iD g() {
        return new C2140hD(new C2835qD(EnumC3891f.B, "unused", null, null, false, AbstractC1639am.i()), new C2522mD().a());
    }

    public Map<C0767v2, P20> A() {
        return this.c.s();
    }

    public final boolean B() {
        return this.c.t();
    }

    public final boolean C() {
        return this.c.u();
    }

    public final boolean D() {
        int iA = this.d;
        if (iA == -1) {
            iA = com.android.tools.r8.utils.S2.a((String) com.android.tools.r8.utils.V1.b(com.android.tools.r8.utils.Y2.a(this.b.c(), ':'))).a();
            this.d = iA;
        }
        return iA >= 2;
    }

    public final boolean E() {
        return (v().isEmpty() && this.c.o().isEmpty()) ? false : true;
    }

    public final boolean F() {
        return this.b.g();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final String b() {
        return this.b.d();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final EnumC3891f c() {
        return this.b.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final boolean d() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final List e() {
        return this.b.a();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final String f() {
        return this.b.f();
    }

    public final Map h() {
        return this.c.b();
    }

    public final Map i() {
        return this.c.c();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final boolean isEmpty() {
        return this.c.v();
    }

    public Map<C0684j2, C0684j2[]> j() {
        return this.c.d();
    }

    public final Map k() {
        return this.c.e();
    }

    public Map<C0767v2, C3004sd> l() {
        return this.c.f();
    }

    public final Map m() {
        return this.c.h();
    }

    public final Map n() {
        return this.c.i();
    }

    public final Map o() {
        return this.c.j();
    }

    public final String p() {
        return this.b.c();
    }

    public final Map q() {
        return this.c.k();
    }

    public Set<C0767v2> r() {
        return this.c.l();
    }

    public final Set s() {
        return this.c.m();
    }

    public final Map t() {
        return this.c.n();
    }

    public final Map u() {
        return this.c.o();
    }

    public Map<C0767v2, C0767v2> v() {
        return this.c.p();
    }

    public C2597nD w() {
        return this.c;
    }

    public final Map x() {
        return this.c.q();
    }

    public final Map y() {
        return this.c.r();
    }

    public C2835qD z() {
        return this.b;
    }

    public final boolean b(C0767v2 c0767v2) {
        return this.c.b(c0767v2);
    }

    public boolean b(AbstractC0744s2 abstractC0744s2) {
        if (a(abstractC0744s2)) {
            return true;
        }
        if (!abstractC0744s2.y0()) {
            return false;
        }
        C0684j2 c0684j2V0 = abstractC0744s2.v0();
        if (this.c.r().containsKey(c0684j2V0) || this.c.n().containsKey(c0684j2V0) || this.c.i().containsKey(c0684j2V0)) {
            return true;
        }
        Iterator<C2556mg> it = this.c.h().values().iterator();
        while (it.hasNext()) {
            if (it.next().a().containsKey(c0684j2V0)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final Set a() {
        return (Set) this.c.l().stream().map(new b4$$ExternalSyntheticLambda3()).collect(Collectors.toSet());
    }

    public final void a(Consumer consumer) {
        this.c.a(consumer);
    }

    public final C2404kg a(C0684j2 c0684j2) {
        return this.c.a(c0684j2);
    }

    public final boolean a(C0767v2 c0767v2) {
        return this.c.a(c0767v2);
    }

    public final boolean a(AbstractC0744s2 abstractC0744s2) {
        return v().containsKey(abstractC0744s2.C()) || r().contains(abstractC0744s2.C());
    }

    @Override // com.android.tools.r8.internal.InterfaceC1206Ne
    public final C2215iD a(AbstractC0681j0 abstractC0681j0, com.android.tools.r8.utils.y3 y3Var) {
        AbstractC3112uB.a(abstractC0681j0, this.a, c());
        return this;
    }
}
