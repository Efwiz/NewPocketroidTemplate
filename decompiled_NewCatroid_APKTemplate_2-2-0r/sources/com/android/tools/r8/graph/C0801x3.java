package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2797pg;
import com.android.tools.r8.internal.OU;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.x3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0801x3 extends AbstractC2797pg {
    static final /* synthetic */ boolean c = true;
    private final com.android.tools.r8.shaking.G a;
    private final Set b = OU.c();

    public C0801x3(com.android.tools.r8.shaking.G g) {
        this.a = g;
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(C0729q2 c0729q2, com.android.tools.r8.shaking.F0 f0) {
        c0729q2.getClass();
        a(c0729q2, c0729q2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(m5 m5Var) {
        m5Var.getClass();
        a((InterfaceC0694k5) m5Var, (InterfaceC0694k5) m5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(m5 m5Var, InterfaceC0694k5 interfaceC0694k5) {
        a((InterfaceC0694k5) m5Var, interfaceC0694k5);
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(o5 o5Var) {
        o5Var.getClass();
        a(o5Var, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(o5 o5Var, InterfaceC0694k5 interfaceC0694k5, com.android.tools.r8.shaking.L l) {
        a(o5Var, interfaceC0694k5);
    }

    private void a(InterfaceC0694k5 interfaceC0694k5, InterfaceC0694k5 interfaceC0694k6) {
        if (this.b.add(interfaceC0694k5.getReference())) {
            final com.android.tools.r8.shaking.G g = this.a;
            Objects.requireNonNull(g);
            D3 d3 = new D3(interfaceC0694k6, new BiConsumer() { // from class: com.android.tools.r8.graph.x3$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    g.a((C0767v2) obj, (InterfaceC0694k5) obj2);
                }
            });
            if (interfaceC0694k5.isClass()) {
                C0706m3.b bVarA1 = interfaceC0694k5.asClass().a1();
                if (bVarA1.a()) {
                    return;
                }
                bVarA1.a(d3);
                return;
            }
            if (interfaceC0694k5.h0()) {
                C0706m3.g gVarE1 = interfaceC0694k5.c().e().e1();
                if (gVarE1.a()) {
                    return;
                }
                gVarE1.a(d3);
                return;
            }
            if (!c && !interfaceC0694k5.j0()) {
                throw new AssertionError();
            }
            d3.b(interfaceC0694k5.d().e().T0());
        }
    }
}
