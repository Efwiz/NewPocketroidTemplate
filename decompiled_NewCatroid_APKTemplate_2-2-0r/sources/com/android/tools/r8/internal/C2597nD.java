package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0767v2;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2597nD {
    private final Map a;
    private final Set b;
    private final Map c;
    private final Map d;
    private final Map e;
    private final Map f;
    private final Map g;
    private final Map h;
    private final Map i;
    private final Map j;
    private final Map k;
    private final LinkedHashMap l;
    private final Map m;
    private final Set n;
    private final Map o;
    private final Set p;
    private final Map q;
    private final Map r;

    C2597nD(IdentityHashMap identityHashMap, AbstractC1048Hm abstractC1048Hm, IdentityHashMap identityHashMap2, AbstractC2178hm abstractC2178hm, AbstractC2178hm abstractC2178hm2, AbstractC2178hm abstractC2178hm3, AbstractC2178hm abstractC2178hm4, AbstractC2178hm abstractC2178hm5, AbstractC2178hm abstractC2178hm6, AbstractC2178hm abstractC2178hm7, AbstractC2178hm abstractC2178hm8, LinkedHashMap linkedHashMap, AbstractC2178hm abstractC2178hm9, AbstractC1048Hm abstractC1048Hm2, AbstractC2178hm abstractC2178hm10, AbstractC1048Hm abstractC1048Hm3, AbstractC2178hm abstractC2178hm11, AbstractC2178hm abstractC2178hm12) {
        this.a = identityHashMap;
        this.b = abstractC1048Hm;
        this.c = identityHashMap2;
        this.d = abstractC2178hm;
        this.e = abstractC2178hm2;
        this.f = abstractC2178hm3;
        this.g = abstractC2178hm4;
        this.h = abstractC2178hm5;
        this.i = abstractC2178hm6;
        this.j = abstractC2178hm7;
        this.k = abstractC2178hm8;
        this.l = linkedHashMap;
        this.m = abstractC2178hm9;
        this.n = abstractC1048Hm2;
        this.o = abstractC2178hm10;
        this.p = abstractC1048Hm3;
        this.q = abstractC2178hm11;
        this.r = abstractC2178hm12;
    }

    public static C2522mD a() {
        return new C2522mD();
    }

    public Map<com.android.tools.r8.graph.Y0, com.android.tools.r8.graph.Q2> b() {
        return this.r;
    }

    public Map<C0684j2, C0725p4> c() {
        return this.q;
    }

    public Map<C0684j2, C0684j2[]> d() {
        return this.j;
    }

    public Map<C0684j2, C0684j2> e() {
        return this.e;
    }

    public Map<C0767v2, C3004sd> f() {
        return this.o;
    }

    public Set<C0767v2> g() {
        return this.n;
    }

    public Map<C0767v2, C2556mg> h() {
        return this.k;
    }

    public Map<C0684j2, C2404kg> i() {
        return this.h;
    }

    public Map<C0684j2, C0684j2> j() {
        return this.i;
    }

    public Map<C0767v2, C0767v2> k() {
        return this.m;
    }

    public Set<C0767v2> l() {
        return this.b;
    }

    public final Set m() {
        return this.p;
    }

    public Map<C0684j2, C0684j2> n() {
        return this.g;
    }

    public Map<C0767v2, C0767v2> o() {
        return this.c;
    }

    public Map<C0767v2, C0767v2> p() {
        return this.a;
    }

    public Map<com.android.tools.r8.graph.Y0, com.android.tools.r8.graph.Y0> q() {
        return this.d;
    }

    public Map<C0684j2, C0684j2> r() {
        return this.f;
    }

    public LinkedHashMap<C0767v2, P20> s() {
        return this.l;
    }

    public final boolean t() {
        return !this.k.isEmpty();
    }

    public final boolean u() {
        return (this.e.isEmpty() && this.f.isEmpty() && this.g.isEmpty() && this.h.isEmpty() && this.d.isEmpty()) ? false : true;
    }

    public final boolean v() {
        return this.a.isEmpty() && this.b.isEmpty() && this.c.isEmpty() && !u() && this.k.isEmpty() && this.m.isEmpty();
    }

    public final void a(Consumer consumer) {
        this.f.keySet().forEach(consumer);
        this.g.keySet().forEach(consumer);
        this.h.keySet().forEach(consumer);
    }

    public final boolean b(final C0767v2 c0767v2) {
        return AbstractC2734ot.b(this.k.values(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.nD$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C2597nD.a(c0767v2, (C2556mg) obj);
            }
        });
    }

    public final boolean a(final C0767v2 c0767v2) {
        return AbstractC2734ot.b(this.o.values(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.nD$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C2597nD.a(c0767v2, (C3004sd) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2, C3004sd c3004sd) {
        return c3004sd.a().B0() == c0767v2 || c3004sd.b().B0() == c0767v2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2, C2556mg c2556mg) {
        return c2556mg.b() == c0767v2;
    }

    final C2404kg a(C0684j2 c0684j2) {
        if (this.k.containsKey(c0684j2.B0())) {
            return (C2404kg) ((C2556mg) this.k.get(c0684j2.B0())).a().get(c0684j2);
        }
        return null;
    }
}
