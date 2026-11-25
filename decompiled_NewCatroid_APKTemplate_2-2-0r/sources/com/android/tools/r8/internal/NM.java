package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class NM extends SF {
    private final C0702m b;
    private final com.android.tools.r8.graph.H3 c;
    private final IM d = new IM(this);
    private final KM e = new KM(this);
    private final LM f = new LM(this);

    /* JADX WARN: Multi-variable type inference failed */
    public NM(C0702m c0702m, com.android.tools.r8.graph.H3 h3) {
        this.b = c0702m;
        this.c = h3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        if (c2688oE.l()) {
            return AbstractC1639am.c(c2688oE);
        }
        C2720of<AbstractC0717o2> c2720ofC = C2720of.c();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            c2720ofC.a(((C0729q2) it.next()).M1());
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it2 = c2688oE.iterator();
        while (it2.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it2.next();
            C2720of c2720ofC2 = C2720of.c(this.f.a(c0729q2));
            c2720ofC2.removeAll(this.e.a(c0729q2));
            HM hm = new HM();
            for (AbstractC0717o2 abstractC0717o2 : c2720ofC) {
                JM jm = JM.a;
                if (c2720ofC2.b(abstractC0717o2)) {
                    C0722p1 c0722p1A = this.b.a();
                    abstractC0717o2.getClass();
                    C0767v2 type = c0729q2.getType();
                    type.getClass();
                    com.android.tools.r8.graph.I4.c<?> cVarO = ((C0680j) this.b.f()).e(c0729q2, c0722p1A.a(type, abstractC0717o2.b(), abstractC0717o2.a())).o();
                    if (cVarO == null || cVarO.d().isInterface()) {
                        jm = JM.b;
                    }
                }
                JM jm2 = (JM) hm.put(abstractC0717o2, jm);
                if (!HM.a && jm2 != null) {
                    throw new AssertionError();
                }
            }
            ((C2688oE) linkedHashMap.computeIfAbsent(hm, new Function() { // from class: com.android.tools.r8.internal.NM$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return NM.a((HM) obj);
                }
            })).a(c0729q2);
        }
        return WL.a(linkedHashMap.values());
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "PreventClassMethodAndDefaultMethodCollisions";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2688oE a(HM hm) {
        return new C2688oE();
    }
}
