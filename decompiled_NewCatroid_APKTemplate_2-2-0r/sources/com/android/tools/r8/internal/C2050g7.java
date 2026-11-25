package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.g7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2050g7 implements InterfaceC2475ld {
    static final /* synthetic */ boolean c = true;
    private final Map a;
    private final C0753u b;

    private C2050g7(IdentityHashMap identityHashMap, C0753u c0753u) {
        this.a = identityHashMap;
        this.b = c0753u;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    public final com.android.tools.r8.utils.D3 b(Object obj, AbstractC3327x1 abstractC3327x1, BiFunction biFunction) {
        J6 j6 = (J6) obj;
        com.android.tools.r8.utils.D3 d3B = com.android.tools.r8.utils.D3.b(abstractC3327x1);
        for (int i = j6.a; i <= j6.c; i++) {
            d3B = (com.android.tools.r8.utils.D3) biFunction.apply(this.b.l(i), d3B.c().i());
            if (d3B.f()) {
                break;
            }
        }
        return d3B;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final J6 a() {
        B7 b7L = this.b.l(0);
        if (c || this.a.containsKey(b7L)) {
            return (J6) this.a.get(b7L);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    public final com.android.tools.r8.utils.D3 a(Object obj, Object obj2, BiFunction biFunction) {
        return com.android.tools.r8.utils.E3.a(((J6) obj).e, obj2, biFunction);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    public final /* bridge */ /* synthetic */ com.android.tools.r8.utils.D3 a(Object obj, com.android.tools.r8.utils.G3 g3, Object obj2) {
        return a((J6) obj, g3, obj2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    public final com.android.tools.r8.utils.D3 a(Object obj, AbstractC3327x1 abstractC3327x1, BiFunction biFunction) {
        return com.android.tools.r8.utils.E3.a(((J6) obj).d, abstractC3327x1, biFunction);
    }

    public final Collection b() {
        return this.a.values();
    }

    public static C2050g7 a(C0753u c0753u, com.android.tools.r8.utils.A1 a1) {
        return new C1974f7(c0753u).a(a1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.utils.D3 a(BiFunction biFunction, B7 b7, Object obj) {
        if (c || this.a.containsKey(b7)) {
            return (com.android.tools.r8.utils.D3) biFunction.apply((J6) this.a.get(b7), obj);
        }
        throw new AssertionError();
    }

    public static com.android.tools.r8.utils.D3 a(J6 j6, final com.android.tools.r8.utils.G3 g3, Object obj) {
        LinkedHashMap linkedHashMap = j6.f;
        com.android.tools.r8.utils.G3 g4 = new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.g7$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj2, Object obj3, Object obj4) {
                return C2050g7.a(g3, (C0767v2) obj2, (J6) obj3, obj4);
            }
        };
        com.android.tools.r8.utils.D3 d3B = com.android.tools.r8.utils.D3.b(obj);
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            d3B = (com.android.tools.r8.utils.D3) g4.a(entry.getKey(), entry.getValue(), d3B.c().j());
            if (d3B.f()) {
                break;
            }
        }
        return d3B;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2475ld
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final com.android.tools.r8.utils.D3 d(J6 j6, final BiFunction biFunction) {
        C0753u c0753u = this.b;
        j6.getClass();
        B7 b7 = c0753u.I0().get(j6.c);
        C0753u c0753u2 = this.b;
        int i = j6.c + 1;
        return b7.a(new BiFunction() { // from class: com.android.tools.r8.internal.g7$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(biFunction, (B7) obj, obj2);
            }
        }, i < c0753u2.I0().size() ? c0753u2.I0().get(i) : null, (AbstractC2359k7) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(com.android.tools.r8.utils.G3 g3, C0767v2 c0767v2, J6 j6, Object obj) {
        return (com.android.tools.r8.utils.D3) g3.a(j6, c0767v2, obj);
    }
}
