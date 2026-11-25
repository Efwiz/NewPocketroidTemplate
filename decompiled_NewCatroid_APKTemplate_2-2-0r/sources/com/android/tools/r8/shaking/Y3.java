package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AN;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2151hN;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.utils.C3984w2;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Y3 extends C3686a4 {
    static final /* synthetic */ boolean v = true;

    public Y3(A a, AbstractC1639am abstractC1639am, Set set, List list) {
        super(a, abstractC1639am, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), C3984w2.a(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap(), Collections.emptySet(), set, list, AN.e());
    }

    @Override // com.android.tools.r8.shaking.C3686a4
    final void a(InterfaceC0694k5 interfaceC0694k5) {
    }

    @Override // com.android.tools.r8.shaking.C3686a4
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final Y3 a(final AbstractC1476Wj abstractC1476Wj) {
        if (abstractC1476Wj.h()) {
            return this;
        }
        final C1507Xl c1507XlG = AbstractC1639am.g();
        this.g.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.Y3$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Y3.a(abstractC1476Wj, c1507XlG, (AbstractC0744s2) obj);
            }
        });
        this.t.forEach(new Y3$$ExternalSyntheticLambda1());
        if (v || this.e.isEmpty()) {
            return new Y3(((AbstractC3695b4) this).c.a(abstractC1476Wj), c1507XlG.a(), this.t, this.e);
        }
        throw new AssertionError();
    }

    public static Z3 a(C0702m c0702m, AbstractC2151hN abstractC2151hN, B5 b5, List list) {
        return new Z3(c0702m, abstractC2151hN, b5, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC1476Wj abstractC1476Wj, final C1507Xl c1507Xl, AbstractC0744s2 abstractC0744s2) {
        Objects.requireNonNull(c1507Xl);
        com.android.tools.r8.utils.R1.a(abstractC1476Wj, abstractC0744s2, new Consumer() { // from class: com.android.tools.r8.shaking.Y3$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c1507Xl.a((AbstractC0744s2) obj);
            }
        });
    }

    public final Y3 b(u5 u5Var) {
        if (u5Var.k()) {
            return this;
        }
        this.t.forEach(new Y3$$ExternalSyntheticLambda1());
        if (v || this.e.isEmpty()) {
            return new Y3(((AbstractC3695b4) this).c, this.g, this.t, this.e);
        }
        throw new AssertionError();
    }
}
