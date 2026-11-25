package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0657g;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2613nT extends SF {
    private final C0702m b;
    private final EnumC0928Dk c;

    public C2613nT(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        this.b = c0702m;
        this.c = enumC0928Dk;
    }

    final boolean a(C0729q2 c0729q2) {
        AbstractC0727q0 abstractC0727q0C;
        AbstractC0727q0 abstractC0727q0C2;
        if (!c0729q2.J1() || c0729q2.y0() || AbstractC2734ot.b(c0729q2.L1(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.nT$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((com.android.tools.r8.graph.U0) obj).z0();
            }
        })) {
            return true;
        }
        for (C0767v2 c0767v2 : c0729q2.f1()) {
            if (c0729q2.getType().a(c0767v2) && ((abstractC0727q0C2 = this.b.c(c0767v2)) == null || !abstractC0727q0C2.J1())) {
                return true;
            }
        }
        Iterator it = c0729q2.L1().iterator();
        while (it.hasNext()) {
            AbstractC0657g accessFlags = ((com.android.tools.r8.graph.U0) it.next()).getAccessFlags();
            if (!accessFlags.m() && !accessFlags.j()) {
                return true;
            }
        }
        Iterator<com.android.tools.r8.graph.T0> it2 = c0729q2.W0().iterator();
        while (it2.hasNext()) {
            C0767v2 c0767v2B = it2.next().getType().b(this.b.a());
            if (c0767v2B.O0() && ((abstractC0727q0C = this.b.c(c0767v2B)) == null || !abstractC0727q0C.J1())) {
                return true;
            }
        }
        return c0729q2.d(new Function() { // from class: com.android.tools.r8.internal.nT$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        }).f();
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "RespectPackageBoundaries";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.utils.D3 a(com.android.tools.r8.graph.o5 o5Var) {
        C2539mT c2539mT = new C2539mT(this, this.b, o5Var);
        o5Var.a(c2539mT);
        if (((Boolean) c2539mT.f()).booleanValue()) {
            return com.android.tools.r8.utils.D3.d();
        }
        return com.android.tools.r8.utils.D3.e();
    }

    static void a(C2688oE c2688oE, final LinkedHashMap linkedHashMap) {
        c2688oE.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.nT$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2613nT.a(linkedHashMap, (C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Map map, C0729q2 c0729q2) {
        C2688oE c2688oE = (C2688oE) map.get(c0729q2.d.H0());
        if (c2688oE == null) {
            return false;
        }
        c2688oE.a(c0729q2);
        return true;
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        C2688oE c2688oE2 = new C2688oE();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            if (a(c0729q2)) {
                ((C2688oE) linkedHashMap.computeIfAbsent(c0729q2.getType().H0(), new Function() { // from class: com.android.tools.r8.internal.nT$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C2613nT.a((String) obj);
                    }
                })).a(c0729q2);
            } else {
                c2688oE2.a(c0729q2);
            }
        }
        a(c2688oE2, linkedHashMap);
        WL.a(linkedHashMap.values());
        ArrayList arrayList = new ArrayList(linkedHashMap.size() + 1);
        if (c2688oE2.size() > 1) {
            arrayList.add(c2688oE2);
        }
        arrayList.addAll(linkedHashMap.values());
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2688oE a(String str) {
        return new C2688oE();
    }
}
