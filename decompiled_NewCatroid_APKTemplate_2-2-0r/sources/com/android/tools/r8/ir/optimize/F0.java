package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2561ml;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.N3;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class F0 {
    static final /* synthetic */ boolean d = true;
    private final C2409kl a;
    private final C1721bt b;
    private final InterfaceC2641no c;

    F0(C2409kl c2409kl, C2561ml c2561ml, C1721bt c1721bt) {
        this.c = c2561ml;
        this.a = c2409kl;
        this.b = c1721bt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C2115gt c2115gt, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == c2115gt;
    }

    public final void a(C2115gt c2115gt, C0684j2 c0684j2) {
        if (c2115gt != null) {
            final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K(!c2115gt.b().C());
            c2115gt.b().h0().forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.F0$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(k, (AbstractC2412ko) obj);
                }
            });
            if (k.a()) {
                while (this.c.hasPrevious()) {
                    if (this.c.previous() == c2115gt) {
                        this.c.d(this.a.i());
                        break;
                    }
                }
                this.c.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.F0$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.a((AbstractC2412ko) obj);
                    }
                });
            }
        }
        final C2115gt c2115gtI0 = this.b.b().f0().i0();
        this.c.d(this.a.i());
        this.c.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.F0$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return F0.a(c2115gtI0, (AbstractC2412ko) obj);
            }
        });
        this.c.d(new C1721bt(c0684j2, c2115gtI0.b(), AbstractC1639am.i()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.utils.K k, AbstractC2412ko abstractC2412ko) {
        if (!d) {
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof N3) {
                throw new AssertionError();
            }
        }
        k.a(abstractC2412ko == this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == this.b;
    }
}
