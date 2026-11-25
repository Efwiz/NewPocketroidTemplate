package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.AbstractC3735i1;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2062gH extends AbstractC2309jV {
    private final C0702m b;
    private final AbstractC3735i1 c;
    private final com.android.tools.r8.utils.A1 d;
    private final Set e = OU.c();

    public C2062gH(C0702m c0702m) {
        this.b = c0702m;
        this.c = c0702m.v();
        this.d = c0702m.Y();
        ((C0680j) c0702m.f()).d().forEach(new Consumer() { // from class: com.android.tools.r8.internal.gH$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0729q2 c0729q2) {
        C0767v2 type = c0729q2.getType();
        boolean zC = this.c.a(c0729q2).c((com.android.tools.r8.shaking.K0) this.d);
        for (com.android.tools.r8.graph.U0 u0 : c0729q2.L1()) {
            if (this.c.a(u0, c0729q2).c((com.android.tools.r8.shaking.K0) this.d)) {
                AbstractC2734ot.a(AbstractC2734ot.c(u0.getReference().a(this.b.a()), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.gH$$ExternalSyntheticLambda0
                    @Override // com.android.tools.r8.internal.InterfaceC3205vM
                    public final boolean apply(Object obj) {
                        return ((C0767v2) obj).O0();
                    }
                }), (Collection) this.e);
                zC = true;
            }
        }
        if (zC) {
            this.e.add(type);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        return !this.e.contains(c0729q2.getType());
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoKeepRules";
    }
}
