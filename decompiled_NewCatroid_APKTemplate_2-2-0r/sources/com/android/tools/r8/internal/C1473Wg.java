package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3970u0;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1473Wg {
    private final C0702m a;
    private AbstractC2178hm b;
    private SU c;
    private final com.android.tools.r8.shaking.G0 d = com.android.tools.r8.shaking.I0.a();

    public C1473Wg(C0702m c0702m) {
        this.a = c0702m;
    }

    public final C1473Wg a(AbstractC1048Hm abstractC1048Hm, C3226vg c3226vg) {
        SU suA = SU.a(this.a, c3226vg, abstractC1048Hm, this.d).a();
        C1946em c1946emE = AbstractC2178hm.e();
        Iterator it = abstractC1048Hm.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            c1946emE.a(c0729q2.getType(), C3487zC.a(this.a, c0729q2, c3226vg.a(c0729q2)).a());
        }
        this.b = c1946emE.a();
        this.c = suA;
        return this;
    }

    public final C1502Xg a(final C3012sl c3012sl, ExecutorService executorService) {
        C1502Xg c1502Xg = new C1502Xg(this.c, this.b);
        this.d.a().a(this.a);
        final CK ckA = EK.a(VE.a(), this.a.p());
        c1502Xg.a(new Consumer() { // from class: com.android.tools.r8.internal.Wg$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(ckA, (AbstractC1447Vg) obj);
            }
        });
        final EK ekA = ckA.a();
        ekA.a(new DK() { // from class: com.android.tools.r8.internal.Wg$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.internal.DK
            public final void a(com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
                C1473Wg.a(c3012sl, ekA, o5Var, c1230Ob);
            }
        }, executorService);
        return c1502Xg;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final CK ck, AbstractC1447Vg abstractC1447Vg) {
        abstractC1447Vg.a(this.a);
        C0729q2 c0729q2A = abstractC1447Vg.a();
        Objects.requireNonNull(ck);
        c0729q2A.l(new Consumer() { // from class: com.android.tools.r8.internal.Wg$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ck.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3012sl c3012sl, EK ek, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        c3012sl.b(o5Var, com.android.tools.r8.ir.optimize.info.r.b(), ek, c1230Ob);
    }

    static C0767v2 a(C0729q2 c0729q2, C0722p1 c0722p1) {
        return c0722p1.e(C3970u0.l(C3970u0.f(c0729q2.getType().b1()) + "$r8$EnumUnboxingLocalUtility"));
    }
}
