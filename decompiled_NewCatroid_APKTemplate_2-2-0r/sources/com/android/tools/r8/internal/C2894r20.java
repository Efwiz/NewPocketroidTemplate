package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.r20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2894r20 {
    static final /* synthetic */ boolean e = true;
    final C1751cF a = C1751cF.b();
    final IdentityHashMap b = new IdentityHashMap();
    final HashMap c = new HashMap();
    final /* synthetic */ C2965s20 d;

    C2894r20(C2965s20 c2965s20, final C0729q2 c0729q2) {
        this.d = c2965s20;
        c2965s20.b.a(new Consumer() { // from class: com.android.tools.r8.internal.r20$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c0729q2, (C0729q2) obj);
            }
        }, c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final C0729q2 c0729q2, final C0729q2 c0729q3) {
        C2894r20 c2894r20 = (C2894r20) this.d.g.get(c0729q3.a0());
        if (!e && c2894r20 == null) {
            throw new AssertionError();
        }
        this.a.a(this.d.a, c2894r20.a);
        c2894r20.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.r20$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0729q3, (C0767v2) obj, (C1751cF) obj2);
            }
        });
        c2894r20.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.r20$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0729q2, c0729q3, (C1207Nf) obj, (C1751cF) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2, C0767v2 c0767v2, C1751cF c1751cF) {
        if (c0767v2 != c0729q2.getType()) {
            ((C1751cF) this.b.computeIfAbsent(c0767v2, com.android.tools.r8.utils.Z1.a(new r20$$ExternalSyntheticLambda2()))).a(this.d.a, c1751cF);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C0729q2 c0729q2, C0729q2 c0729q3, C1207Nf c1207Nf, C1751cF c1751cF) {
        C1229Oa c1229OaB = c1207Nf.o().b();
        C1229Oa c1229OaB2 = AbstractC3472z00.a(c0729q2.getType(), WH.i(), (C0702m<?>) this.d.a).b();
        boolean zA = ((!c0729q3.isInterface() || c0729q2.isInterface() || c0729q2.n1() == this.d.a.a().Z1) && c1229OaB2.S().f()) ? c1229OaB2.a(c1229OaB) : c1229OaB2.b(c1229OaB, this.d.a);
        if (!zA) {
            ((C1751cF) this.c.computeIfAbsent(c1207Nf, com.android.tools.r8.utils.Z1.a(new r20$$ExternalSyntheticLambda2()))).a(this.d.a, c1751cF);
            return;
        }
        if (c1207Nf.g()) {
            ((C1751cF) this.b.computeIfAbsent(c1207Nf.d().R(), com.android.tools.r8.utils.Z1.a(new r20$$ExternalSyntheticLambda2()))).a(this.d.a, c1751cF);
        } else {
            this.a.a(this.d.a, c1751cF);
        }
        c1751cF.a(new BiConsumer() { // from class: com.android.tools.r8.internal.r20$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0729q2, (AbstractC0717o2) obj, (YE) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2, AbstractC0717o2 abstractC0717o2, YE ye) {
        com.android.tools.r8.graph.I4.c<?> cVarO = ((C3733i) this.d.a.f()).b(c0729q2, abstractC0717o2).o();
        while (cVarO != null && cVarO.r().f.N()) {
            cVarO = ((C3733i) this.d.a.f()).a(cVarO.d().n1(), abstractC0717o2).o();
        }
        if (cVarO == null || !cVarO.d().e0() || cVarO.d() == c0729q2 || !cVarO.r().q1()) {
            return;
        }
        C0729q2 c0729q2A0 = cVarO.d().a0();
        ((C1751cF) ((C2894r20) this.d.g.get(c0729q2A0)).b.computeIfAbsent(c0729q2A0.getType(), com.android.tools.r8.utils.Z1.a(new r20$$ExternalSyntheticLambda2()))).a(this.d.a, cVarO.s(), ye);
    }
}
